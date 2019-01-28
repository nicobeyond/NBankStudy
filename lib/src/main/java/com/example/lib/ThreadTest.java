package com.example.lib;

public class ThreadTest {
    int i = 0;

    public void set() {
        i = 1;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("thread1===" + i);
    }

    public static boolean flag = false;
    public static Object obj = new Object();

    public static void main(String args[]) {
//        ThreadTest threadTest = new ThreadTest();
//        Thread thread1 = new Thread() {
//            @Override
//            public void run() {
//                threadTest.set();
//            }
//        };
//        Thread thread2 = new Thread() {
//            @Override
//            public void run() {
//                threadTest.i = 2;
//                System.out.println("thread2===" + threadTest.i);
//            }
//        };

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                while (!flag) {
                    synchronized (obj) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("===thread1");
            }
        };

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flag = !flag;
                obj.notify();
                System.out.println("===thread2");
            }
        };

        thread1.start();
        thread2.start();
    }
}
