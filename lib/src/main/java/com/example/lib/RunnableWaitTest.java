package com.example.lib;

import java.util.Arrays;

public class RunnableWaitTest {

    int[] ints;

    public void setList(int[] ints) {
        this.ints = Arrays.copyOf(ints,ints.length);;

    }

    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
        Thread thread3 = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (thread.lock) {
                    thread.lock.notify();
                }
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                synchronized (thread.lock) {
//                    thread.lock.notify();
//                }
//                Thread thread3 = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
            }
        };
        thread3.start();
    }

    static class MyThread extends Thread {
        Object lock = new Object();

        Thread thread1;
        Thread thread2;

        @Override
        public void run() {
            thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread1 start");
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("thread1 end");
                }
            });
            thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    synchronized (lock) {
                        System.out.println("thread2 start");
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("thread2 end");
                }
            });

            thread1.start();
            thread2.start();
        }
    }
}
