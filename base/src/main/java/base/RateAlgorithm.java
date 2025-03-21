package base;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *限流算法
 * @author &{USER}
 * @date 2022/2/1321:02
 * lastRequestTime:上次请求时间点
 * windowUnit: 单位时间段
 * threshold: 限流阈值
 */
public class RateAlgorithm {
    /**
     * @Description: 固定时间窗口
     * @defect: 如果连续的两段单位时间内都达到了阈值，合并起来就超阈值了;
     * 假设限流阀值为5个请求，单位时间窗口是1s,如果我们在单位时间内的前0.8-1s和1-1.2s，分别并发5个请求。虽然都没有超过阀值，但是如果算0.8-1.2s,则并发数高达10
     * @Date: 2022/2/13
     **/
    private boolean fixedWindowsTryAcquire(long lastRequestTime,long windowUnit,int threshold){
        int counter=0;
        long currentTime=System.currentTimeMillis();
        if(currentTime-lastRequestTime>windowUnit){
            counter=0;
            lastRequestTime=currentTime;
        }
        if(counter<threshold){
            counter++;
            return true;
        }
        return false;
    }

    /**
     * @Description: 滑动窗口,解决了临界阈值问题
     * @defect: 到达限流后，请求暴力被拒绝，会丢失部分请求
     * @Date: 2022/2/13
     **/
    //单位时间划分的小周期（单位时间是1分钟，10s一个小格子窗口，一共6个格子）

    private int SUB_CYCLE = 10;
    //每分钟限流请求数
    private int thresholdPerMin = 100;

    //计数器, key为当前窗口的开始时间值秒，value为当前窗口的计数

    private final TreeMap<Long, Integer> counters = new TreeMap<>();

    private boolean slidingWindowsTryAcquire(){
        //获取当前时间在哪个小周期窗口
        long currentWindowTime= LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)/SUB_CYCLE*SUB_CYCLE;
        //当前窗口总请求数
        int currentWindowNum = countCurrentWindow(currentWindowTime);
        if(currentWindowNum>=thresholdPerMin){
            return false;
        }
        counters.put(currentWindowTime,counters.getOrDefault(currentWindowTime,0)+1) ;
        return true;
    }
    /**
     * 统计当前窗口的请求数
     */
    private int countCurrentWindow(long currentWindowTime) {
        //计算窗口开始位置
        long startTime = currentWindowTime - SUB_CYCLE* (60/SUB_CYCLE-1);
        int count = 0;

        //遍历存储的计数器
        Iterator<Map.Entry<Long, Integer>> iterator = counters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Integer> entry = iterator.next();
            // 删除无效过期的子窗口计数器
            if (entry.getKey() < startTime) {
                iterator.remove();
            } else {
                //累加当前窗口的所有计数器之和
                count =count + entry.getValue();
            }
        }
        return count;
    }

    /**
     * @Description: 漏桶算法: 解决滑动窗口遗留问题
     * @Date: 2022/2/14
     * @defect: 处理不了突发流量:按照常量固定速率流出请求，流入请求速率任意
     * refreshTime 最后刷新时间
     * rate 每秒处理数
     * currentWater 当前剩余水量
     * capacity 桶容量
     **/
    private boolean leakybucketLimitTryAcquire(long refreshTime,long rate,long currentWater,long capacity) {
        long currentTime=System.currentTimeMillis();
        //流出水量
        long outWater=(currentTime-refreshTime)/1000*rate;
        //当前剩余水量
        currentWater=Math.max(0,currentWater-outWater);
        //刷新时间
        refreshTime=currentTime;
        if(currentWater<capacity){
            currentWater++;
            return true;
        }
        return false;
    }
    /**
     * @Description: 令牌桶算法,允许一定程度的突发：限制的是平均流入速率，只要有令牌就可以处理
     * @Date: 2022/2/14
     * putTokenRate 放入令牌速率
     * refreshTime 最后刷新时间
     * capacity 令牌桶容量
     * currentToken 当前桶内令牌数
     **/
    boolean tokenBucketTryAcquire(long putTokenRate,long refreshTime,long capacity,long currentToken) {
        long currentTime = System.currentTimeMillis();
        //生成的令牌
        long generateToken=(currentTime-refreshTime)/1000*putTokenRate;
        //当前剩余令牌数量
        currentToken=Math.min(capacity,generateToken+currentToken);
        refreshTime=currentTime;
        if(currentToken>0){
            currentToken--;
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        RateAlgorithm test=new RateAlgorithm();
        test.slidingWindowsTryAcquire();
    }

}
