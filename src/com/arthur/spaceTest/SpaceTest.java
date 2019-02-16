package com.arthur.spaceTest;

import org.junit.Test;

public class SpaceTest {

    @Test
    public void testSpace() {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.maxMemory() / 1024 / 1024 / 1024);
        System.out.println(runtime.totalMemory() / 1024 / 1024 / 1024);
    }
}
