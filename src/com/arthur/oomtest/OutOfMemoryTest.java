package com.arthur.oomtest;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryTest {

    static class OOMObject {
        public OOMObject() {
        }
    }

    /**
     * 堆内存溢出
     * -verbose:gc -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    @Test
    public void heapOOM() {
        List<OOMObject> oomObjects = new ArrayList<OOMObject>();
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
    public void stackOOM() throws Throwable {
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
        List<String> list = new ArrayList<String>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }

    /**
     * 方法区内存溢出
     * -XX:PermSize=10M -XX:MaxPermSize=10M
     */
    @Test
    public void methodAreaOOM() {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o, objects);
                }
            });
            enhancer.create();
        }
    }

    private static final int _1MB = 1024 * 1024;

    /**
     * 直接内存溢出
     * -verbose:gc -Xms20M -XX:MaxDirectMemorySize=10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * @throws IllegalAccessException
     */
    @Test
    public void directOOM() throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }

}
