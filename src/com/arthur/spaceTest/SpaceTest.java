package com.arthur.spaceTest;

import org.junit.Test;

public class SpaceTest {

    @Test
    public void testSpace() {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.maxMemory() / 1024 / 1024 / 1024);
        System.out.println(runtime.totalMemory() / 1024 / 1024 / 1024);
    }


    @Test
    /**
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M
     * -XX:+PrintGCDetails -XX:+UseSerialGC -XX:PretenureSizeThreshold=3145728
     */
    public void testEden() {
        int m = 1024 * 1024;
        byte[] allocation;
        allocation = new byte[5 * m];
    }

    @Test
    /**
     * -verbose:gc -XX:+PrintGCDetails
     * -Xms20M -Xmx20M -Xmn10M -XX:+UseSerialGC
     * -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
     */
    public void testMaxTenuringThreshold() throws InterruptedException {
        int m = 1024 * 1024;
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[m / 4];
        allocation2 = new byte[4 * m];
        allocation3 = new byte[4 * m];
        allocation3 = null;
        allocation3 = new byte[4 * m];
    }

    @Test
    /**
     * -verbose:gc -Xms20M -Xmx20M  -Xmn10M
     * -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * -XX:MaxTenuringThreshold=15 -XX:+UseSerialGC
     * -XX:+PrintTenuringDistribution
     */
    public void testMaxTenuringThreshold1() {
        int m = 1024 * 1024;
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[m / 4];
        allocation2 = new byte[m / 2];
        allocation3 = new byte[m * 4];
        allocation4 = new byte[m * 4];
        allocation4 = null;
        allocation4 = new byte[m * 4];
    }



}
