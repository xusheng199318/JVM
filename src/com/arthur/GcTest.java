package com.arthur;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GcTest {

    private static final int _1MB = 1024 * 1024;

    /**
     * 对象优先在Eden区进行分配
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails
     */
    @Test
    public void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];//出现一次Minor GC
    }

    /**
     * 大对象直接进入老年代
     * -verbose:gc -Xmx20M -Xms20M -Xmn10M -XX:+PrintGCDetails
     * -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728(3M)
     */
    @Test
    public void testPretenureSizeThreadshold() {
        byte[] allocation;
        allocation = new byte[5 * _1MB];
    }

    /**
     * 长期存活的对象进入老年代
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails
     * -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1
     * -XX:+PrintTenuringDistribution
     */
    @Test
    public void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    /**
     * 动态对象年龄判定
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails
     * -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15
     * -XX:+PrintTenuringDistribution
     */
    @Test
    public void testTenuringThreshold2() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[_1MB / 4];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    /**
     * 空间分配担保
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails
     * -XX:SurvivorRatio=8 -XX:HandlePromotionFailure
     */
    @Test
    public void testHandlePromotion() {
        byte[] allocation1, allocation2, allocation3, allocation4,
                allocation5, allocation6, allocation7;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation1 = null;
        allocation4 = new byte[2 * _1MB];
        allocation5 = new byte[2 * _1MB];
        allocation6 = new byte[2 * _1MB];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2 * _1MB];
    }

    @Test
    public void testList() {
        List<String> sList = new ArrayList<String>();
        sList.add("a");
        sList.add("b");
        sList.add("c");
        sList.add("d");
        sList.add("e");
        sList.add("f");
        sList.add("g");
//sList.size() = 7

        List<String> subList = sList.subList(0, 4);
//subList = [a, b, c, d]

        subList.remove(1);
        subList.remove(2);

        subList.add("h");
        subList.add("J");
        System.out.println(sList);

    }

    @Test
    public void testException() {
        System.out.println(testException1());
    }


    public int testException1() {
        int x;
        try {
            x = 1;
            return x;
        } catch (Exception e) {
            x = 2;
            return x;
        } finally {
            System.out.println("haha");
            x = 3;
            return x;
        }
    }

    public int testException2() {
        try {
            return 0;
        } catch (Exception e) {
            return 1;
        } finally {
            return 2;
        }
    }

}
