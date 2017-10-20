package coki.utils.concurrent;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>锁池</p>
 * 用于特殊并发场景下的锁管理
 * Created by wuyiming on 2017/10/17.
 */
public final class LockPool<L> {
    //内部锁数组
    private final Set<ExpireUnit<L>> lockArray = new HashSet<>(16);
    //锁的生命时长：ms
    private long expire = 0L;
    //锁的生命周期线程
    private Thread lockManager;
    //内部重入锁
    private final ReentrantLock poolLock = new ReentrantLock();
    //控制锁的生命周期线程是否自动销毁
    private boolean lockManagerKeepAlive;
    //锁的生命周期线程循环周期
    private long lockManagerCycle;

    /**
     * @param expire 过期时长:ms
     * @param lockManagerKeepAlive 是否保持生命周期线程活动
     * @param lockManagerCycle 生命周期线程活动周期
     */
    public LockPool(long expire, boolean lockManagerKeepAlive, long lockManagerCycle) {
        this.expire = expire;
        this.lockManagerKeepAlive = lockManagerKeepAlive;
        this.lockManagerCycle = lockManagerCycle;
    }

    public LockPool(long expire, boolean lockManagerKeepAlive) {
        this(expire,lockManagerKeepAlive,2 * expire);
    }

    public LockPool(long expire) {
        this(expire,false,2 * expire);
    }

    /**
     * <p>获取锁</p>
     *
     * @param lock 锁对象：需重写hashCode方法
     * @return 锁对象
     * @apiNote 获取锁时，若参数池中不存在参数对象，则将该对象放入锁池，并返回该对象；
     * 若已存在，则返回池中已有的对象锁，并更新存活时间；
     * 检查锁的生命周期线程状态，若线程已死亡则重新创建并启动；
     */
    public L obtainLock(L lock) {
        poolLock.lock();
        for (ExpireUnit<L> unit : lockArray) {
            if (unit.hashCode() == lock.hashCode()) {
                unit.expireTime = System.currentTimeMillis();
                poolLock.unlock();
                return unit.lock;
            }
        }

        ExpireUnit<L> unit = new ExpireUnit<>(lock, System.currentTimeMillis());
        lockArray.add(unit);

        if (!lockManagerKeepAlive) {
            notifyManager();
        }
        poolLock.unlock();
        return lock;
    }

    /**
     * 获取锁池的大小
     * @return 锁池的体积
     */
    public int size() {
        return lockArray.size();
    }

    /**
     * 创建一个控制锁池中所有锁生命周期的线程
     * 当锁池为空时会自动销毁
     *
     * @return
     */
    private Thread createLifeThread() {
        return new Thread("Thread-lockManager") {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(lockManagerCycle);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Set<ExpireUnit> newArray = new HashSet<>();
                    poolLock.lock();
                    for (ExpireUnit unit : lockArray) {
                        if (System.currentTimeMillis() - unit.expireTime > expire)
                            newArray.add(unit);
                    }

                    if (!newArray.isEmpty())
                        lockArray.removeAll(newArray);

                    if (lockArray.isEmpty() && !lockManagerKeepAlive) {
                        poolLock.unlock();
                        return;
                    }
                    poolLock.unlock();
                }
            }
        };
    }

    private void notifyManager() {
        if (lockManager == null ||
                lockManager.getState().equals(Thread.State.TERMINATED)) {
            lockManager = createLifeThread();
            lockManager.start();
        }
    }

    /**
     * 内部单元类
     * @param <L>
     */
    private static class ExpireUnit<L> {
        private final L lock;
        private long expireTime;

        ExpireUnit(L lock, long expireTime) {
            this.lock = lock;
            this.expireTime = expireTime;
        }

        @Override
        public int hashCode() {
            return lock.hashCode();
        }
    }
}
