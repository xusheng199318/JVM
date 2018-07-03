package com.arthur;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryTest {

    class OOMObject {}

    /**
     * 堆内存溢出
     * -verbose:gc -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    @Test
    public void heapOOM() {
        List<OOMObject> oomObjects = new ArrayList<>();
        while (true) {
            oomObjects.add(new OOMObject());
        }
    }


    private int stackLength = 1;

    /**
     * 栈内存溢出
     * -Xss128K
     */
    @Test
    public void stackOOM() {
        try {
            stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + this.stackLength);
            throw  e;
        }
    }

    private void stackLeak() {
        this.stackLength++;
        stackLeak();
    }


    /**
     * 创建线程导致内存溢出
     * Java线程映射到操作系统的内核线程上导致系统假死
     */
    @Test
    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {

                    }
                }
            });
            thread.start();
        }
    }

    /**
     * 运行时常量池内存溢出
     * -XX:PermSize=10M -XX:MaxPermSize=10M
     */
    @Test
    public void runtimeConstantPoolOOM() {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }

}
