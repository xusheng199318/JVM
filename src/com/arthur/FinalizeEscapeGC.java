package com.arthur;

import org.junit.Test;

/**
 * 对象可以在被GC时自我拯救
 * 这种自救的机会只有一次，
 * 一个对象的finalize()方法最多只会被系统自动调用一次
 * 运行结果：
 *      finalize method executed!
        yes, i am still alive :
        no, i am dead :
 */
public class FinalizeEscapeGC {

   public static FinalizeEscapeGC SAVE_HOOK = null;

   public void isAlive() {
       System.out.println("yes, i am still alive :");
   }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    @Test
    public void testEscapeGC() throws InterruptedException {
       SAVE_HOOK = new FinalizeEscapeGC();

       //对象第一次成功拯救自己
        SAVE_HOOK = null;
        System.gc();

        //finalize方法优先级很低，所以暂停0.5秒等待它
        Thread.sleep(5000);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :");
        }

        //对象第一次成功拯救自己
        SAVE_HOOK = null;
        System.gc();

        //下面的方法与上面的相同，但是这次自救失败了
        Thread.sleep(5000);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :");
        }
    }
}
