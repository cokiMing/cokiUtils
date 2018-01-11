package coki.utils.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuyiming
 * Created by wuyiming on 2017/12/5.
 */
public final class ScheduleCache<K,V> {

    private ConcurrentHashMap<K,Unit<V>> map;

    private long expire;

    public ScheduleCache(long expire) {
        this.expire = expire;
        this.map = new ConcurrentHashMap<>();
    }

    public void put(K key,V value) {
        Unit<V> unit = new Unit<>();
        unit.content = value;
        unit.deadline = System.currentTimeMillis() + expire;
        map.put(key,unit);
    }

    public V get(K key) {
        checkExpire();
        Unit<V> unit = map.get(key);
        return unit == null ? null:unit.content;
    }

    private synchronized void checkExpire() {
        long current = System.currentTimeMillis();
        List<K> removeList = new ArrayList<>(map.size());
        for (Map.Entry<K, Unit<V>> entry : map.entrySet()) {
            long deadline = entry.getValue().deadline;
            if (deadline < current) {
                removeList.add(entry.getKey());
            }
        }

        for (K key : removeList) {
            map.remove(key);
        }
    }

    private static class Unit<V> {
        private V content;
        private long deadline;
    }
}
