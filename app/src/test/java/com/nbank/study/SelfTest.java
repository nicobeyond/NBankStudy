package com.nbank.study;

import org.junit.Test;

public class SelfTest {

    StringBuilder sb1 = new StringBuilder();
    StringBuffer sb2 = new StringBuffer();

    @Test
    public void test() {
//        String oldF = "D:\\111\\test.txt";
////        String newF = "D:\\222\\test1.txt";
//        byte[] bytes1 = FileUtil.getFileByte(new File(oldF));
//        byte[] bytes2 = FileUtil.readFile(new File(oldF), "self");
//        System.out.println("===" + new String(bytes1));
//        System.out.println("===" + new String(bytes2));
////        System.out.println("=="+FileUtil.copyFile(new File(oldF), new File(newF),"self"));
//        for (int i = 0; i < 1000; i++) {
//            Thread1 thread1 = new Thread1();
//            thread1.start();
//        }
//        for (int i = 0; i < 1000; i++) {
//            Thread2 thread2 = new Thread2();
//            thread2.start();
//        }
//        try {
//            Thread.currentThread().sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(sb1.length());
//        System.out.println(sb2.length());
//        System.out.println(testrey());
    }

    public int testrey() {

        try {
            return 1;
        } finally {
            return 2;
        }
    }

    class Thread1 extends Thread {
        @Override
        public void run() {
            sb1.append("a");
        }
    }

    class Thread2 extends Thread {
        @Override
        public void run() {
            sb2.append("b");
        }
    }
}
