package com.example.lib;

import java.io.IOException;

public class TryTest {
    int i = 0;

    {
        System.out.println("===dasdsa");
    }

    public TryTest() {
        System.out.println("===111");

    }

    private void test() {
        {
            int j = 1;
            i = 1;
        }
        {
            int j = 2;
            i = 2;
        }
        {
            System.out.println("===" + i);
        }
//        System.out.println("===" + j);
    }

    static class B extends A<String> {
        int flag = 1;
    }

    static class A<T> {
        int flag = 0;

        public void set() {
            flag = 2;
        }

        public void eq(Class<T> t) throws Exception {

            try {
                System.out.println(t == String.class);
                throw new Exception();
            } finally {
                System.out.println("aaa");
            }

        }
    }


    public static void main(String args[]) throws IOException {

        String str = null;

        System.out.println("===" + "".equalsIgnoreCase(str));

        System.out.println(Integer.valueOf("AB", 16));
        System.out.println(Integer.parseInt("CD", 16));


        Boolean d1 = new Boolean(true);
        Boolean d2 = new Boolean(true);
        System.out.println((((boolean) d1) == ((boolean) d2)));
        try {
            try {
                System.out.println("===try");
                throw new IOException();
            } finally {
                System.out.println("===fina");
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            System.out.println("---find");
        }


        //45826 45826 45826 42856 24856
//        Integer[] arr = new Integer[]{5, 4, 8, 2, 6};
//        Arrays.sort(arr, new Comparator<Integer>() {
//            @Override
//            public int compare(Integer i0, Integer t1) {
//                System.out.println(i0 + "===" + t1);
//                return i0 - t1;
//            }
//        });
//        System.out.println(Arrays.toString(arr));

//        A a = new B();
//        a.set();
//        B b = new B();
//        System.out.println("==" + a.flag);
//        System.out.println("==" + ((B) a).flag);
//        System.out.println("==" + b.flag);


//        System.out.println(String.format("First {0} and then {1}", "foo", "bar")); //Noncompliant. Looks like there is a confusion with the use of {{java.text.MessageFormat}}, parameters "foo" and "bar" will be simply ignored here
//        System.out.println(String.format("Display %3$d and then %2$d", 1, 2, 3)); //Noncompliant; the second argument '2' is unused
//        System.out.println(String.format("Too many arguments %d and %d", 1, 2, 3)); //Noncompliant; the third argument '3' is unused
//        System.out.println(String.format("First Line\n")); //Noncompliant; %n should be used in place of \n to produce the platform-specific line separator
//        System.out.print("|");
//        System.out.print(String.format("%-5s%s", "abcdefg", "cba"));
//        System.out.println("|");
//        System.out.print("|");
//        System.out.print(String.format("% 5d", 15));
//        System.out.println("|");
//        String.format("Is myObject null ? %b", myObject); //Noncompliant; when a non-boolean argument is formatted with %b, it prints true for any nonnull value, and false for null. Even if intended, this is misleading. It's better to directly inject the boolean value (myObject == null in this case)
//        String.format("value is " + value); // Noncompliant
//        String s = String.format("string without arguments"); // Noncompliant
//        MessageFormat.format("Result '{0}'.", value); // Noncompliant; String contains no format specifiers. (quote are discarding format specifiers)
//        MessageFormat.format("Result {0}.", value, value); // Noncompliant; 2nd argument is not used
//        MessageFormat.format("Result {0}.", myObject.toString()); // Noncompliant; no need to call toString() on objects

//        File file = new File("d://dasda.jpg");
//        String end = file.getName().substring(
//                file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
//        System.out.println(file.getName());

//        B b = new B();
//        try {
//            b.eq(String.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("bbb");
//        }


//        TryTest tryTest = new TryTest();
//        tryTest.test();
//        if (1 == 1) return;
//        try (Tiger tiger = new Tiger(new Lion())) {
//
//            tiger.hunt();
//
//        } catch (Exception e) {
//            e.getSuppressed();
//            System.out.println(e);
//        } finally {
//            System.out.println("Finally.");
//        }
    }


    public static class Lion implements AutoCloseable {
        public Lion() {
            System.out.println("LION is OPEN in the wild.");
        }

        public void hunt() throws Exception {
            throw new Exception("DeerNotFound says Lion!");
        }

        @Override
        public void close() throws Exception {
            System.out.println("LION is CLOSED in the cage.");
            throw new Exception("Unable to close the cage!");
        }
    }

    public static class Tiger implements AutoCloseable {
        private Lion lion;

        public Tiger(Lion lion) {
            this.lion = lion;
            System.out.println("TIGER is OPEN in the wild.");
        }

        public void hunt() throws Exception {
            throw new Exception("DeerNotFound says Tiger!");
        }

        @Override
        public void close() throws Exception {
            System.out.println("TIGER is CLOSED in the cage.");
        }
    }

}