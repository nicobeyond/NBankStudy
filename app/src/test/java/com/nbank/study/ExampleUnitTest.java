package com.nbank.study;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        /*List<Integer> intList = Arrays.asList(2, 3, 1, 6, 4, 5);
        System.out.println("before sort:" + intList.toString());
        System.out.println("=========================");
        int[] d = new int[]{2, 3, 1, 6, 4, 5};
        Collections.sort(intList, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {

                System.out.println("==" + o1 + "|" + o2);
                // 返回值为int类型，大于0表示正序，小于0表示逆序
//                if (o1 < o2) {
//                    return 1;
//                }
//                if (o1 > o2) {
//                    return -1;
//                }
                return o1 - o2;
                //231645 321645 123645 623145 423165 523164
            }
        });
        System.out.println("after sort:" + intList.toString());*/

//        Scanner input = new Scanner(System.in);
//        String str = "";
//        System.out.println("请输入：");
//        str = input.nextLine();
//        try {
//            System.out.println(Common.encodeByMd5(str));
//            System.out.println(Common.toMd5(str));
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

//        StringBuffer _params = new StringBuffer();
//        _params.append(",");
//        if (_params.length() != 0) {
//            if (",".equals(_params.charAt(_params.length() - 1) + "")) {
//                _params.deleteCharAt(_params.length() - 1);
//            }
//        }
//        System.out.println("===" + _params.toString());


//        String das = "0123456789abcdefABCDEF";
//        boolean flag = das.matches("[0-9a-fA-F]+");
//        System.out.println("===" + flag);

//        String d = WebViewHelper.genJsCode("dasdasd(das,dasd)");
//        System.out.println("=="+d);


        IThread thread1 = new IThread() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("i==" + i);
                    if (i == 10) {
                        sleeps(this);
                    }
                }
                System.out.println("------------------");
            }

            public void sleeps(IThread thread) {
                try {
                    thread.sleeps(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        IThread thread2 = new IThread() {
            @Override
            public void run() {
                for (int j = 0; j < 10; j++) {
                    System.out.println("j==" + j);
                }
//                System.out.println("------------------");
//                thread1.waits(100000);
//                System.out.println("------------------");
            }
        };
        thread1.start();
//        thread2.start();
    }

    public class IThread extends Thread {
        public void sleeps(long time) {
            try {
                this.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void waits(long time) {
            synchronized (this) {
                try {
                    this.wait(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sleep(Thread thread) {
        try {
            thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void wait(Thread thread) {
        synchronized (thread) {
            try {
                thread.wait(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}