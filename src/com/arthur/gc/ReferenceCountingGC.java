package com.arthur.gc;

import org.junit.Test;

/**循环引用
 * Created by xusheng on 2018/7/5.
 */
public class ReferenceCountingGC {

    public Object instance = null;

    private static final int _1MB = 1024 * 1024;

    //占用内存,方便查看对象是否被回收过
    private byte[] bigSize = new byte[2 * _1MB];

    @Test
    public void testGC() {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        System.gc();
    }

}
