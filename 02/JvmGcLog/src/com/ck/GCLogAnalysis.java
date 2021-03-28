package com.ck;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * <p>
 * 演示GC日志生成与解读
 * 执行指令 java -XX:+PrintGCDetails -XX:+PrintGCDataStamps -Xloggc:gc.demo.log -Xmx1g -Xms1g GCLogAnalysis
 * </p>
 *
 * @SINCE 2021/3/28 11:27
 * @AUTHOR CodeByck-write
 */
public class GCLogAnalysis {
    private static Random random = new Random();

    public static void main(String[] args) {
        // 当前毫秒时间戳
        long startMillis = System.currentTimeMillis();
        // 持续运行毫秒数; 可根据需要进行修改
        long timeoutMillis = TimeUnit.SECONDS.toMillis(1);
        // 结束时间戳
        long endMillis = startMillis + timeoutMillis;
        LongAdder counter = new LongAdder();
        System.out.println("正在执行...");
        // 缓存一部分对象; 进入老年代
        int cacheSize = 2000;
        // 缓存的垃圾对象
        Object[] cachedGarbage = new Object[cacheSize];
        // 在此时间范围内,持续循环
        while (System.currentTimeMillis() < endMillis) {
            // 生成垃圾对象
            Object garbage = generateGarbage(100 * 1024);
            counter.increment();
            // randomIndex的范围是 0~4000
            int randomIndex = random.nextInt(2 * cacheSize);
            // 如果randomIndex小于cacheSize对象的大小，将 new的对象存放在 while外的数组里，进入了老年代，不会被GC
            // 如果随机到相同的随机数，将会覆盖掉之前的随机数，前面的被缓存的对象，有可能会被GC清理掉
            if (randomIndex < cacheSize) {
                cachedGarbage[randomIndex] = garbage;
            }
        }
        System.out.println("执行结束!共生成对象次数:" + counter.longValue());
    }

    /**
     * @author CodeByck-write
     * @Description 创建或收集垃圾对象
     * @Param int max 写死 100 * 1024
     * @Date 2021/3/28 11:28
     */
    private static Object generateGarbage(int max) {
        // 长度在 0-10W的范围内
        int randomSize = random.nextInt(max);
        // 传建一个随机类型
        int type = randomSize % 4;
        Object result = null;
        switch (type) {
            case 0:
                result = new int[randomSize];
                break;
            case 1:
                result = new byte[randomSize];
                break;
            case 2:
                result = new double[randomSize];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                String randomString = "randomString-Anything";
                while (builder.length() < randomSize) {
                    builder.append(randomString);
                    builder.append(max);
                    builder.append(randomSize);
                }
                result = builder.toString();
                break;
        }
        return result;
    }
}