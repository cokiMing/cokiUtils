package coki.utils.common;

/**
 * Created by wuyiming on 2016/8/6.
 */
public class NumberUtils {

    public static Double format(double data,int scope){
        //10的位数次方 如保留2位则 tempDouble=100
        double tempDouble=Math.pow(10, scope);
        //原始数据先乘tempDouble再转成整型，作用是去小数点
        data=data*tempDouble;
        int tempInt=(int) data;
        //返回去小数之后再除tempDouble的结果
        return tempInt/tempDouble;
    }

}
