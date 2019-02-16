package com.arthur.classLoaderTest;

import org.junit.Test;

public class ClassLoaderTest {

    @Test
    public void testGetCLassLoader() {
        String str = "";
        System.out.println(str.getClass().getClassLoader());
        Member member = new Member();
        System.out.println(member.getClass().getClassLoader());
        System.out.println(member.getClass().getClassLoader().getParent());
        System.out.println(member.getClass().getClassLoader().getParent().getParent());
    }
}

class Member{

}
