package com.nbank.study;

import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ico.ico.util.StringUtil;

public class TestUnit {


    private static final byte LENGTH_2_BYTE = (byte) 0x80;
    private static final byte LENGTH_3_BYTE = (byte) 0x00;
    byte lengthAndaccessMode;

    @Test
    public void testBit() {

        System.out.println("=====" + (0x80));
//        try {
//            byte b = (byte) 1167;
//            System.out.println("1===" + b);
//            System.out.println("1===" + (b & 0xffff));
//        } catch (Exception e) {
////            e.printStackTrace();
//        }
//        as((byte) 40, true);

    }

    byte[] data;

    public boolean isNdefSupport() {
        return (data == null) ? false : (data[3] & 0xff) == 1;
    }


    @Test
    public void testReg() {
//        System.out.println("1===" + isNameExistSpecial("`爱上经典款拉伸件看了"));
//        System.out.println("2===" + isNameExistSpecial("~爱上经典款拉伸件看了"));
//        System.out.println("3===" + isNameExistSpecial("!爱上经典款拉伸件看了"));
//        System.out.println("4===" + isNameExistSpecial("@爱上经典款拉伸件看了"));
//        System.out.println("5===" + isNameExistSpecial("#爱上经典款拉伸件看了"));
//        System.out.println("6===" + isNameExistSpecial("$爱上经典款拉伸件看了"));
//        System.out.println("7===" + isNameExistSpecial("%爱上经典款拉伸件看了"));
//        System.out.println("8===" + isNameExistSpecial("^爱上经典款拉伸件看了"));
//        System.out.println("9===" + isNameExistSpecial("&爱上经典款拉伸件看了"));
//        System.out.println("10===" + isNameExistSpecial("*爱上经典款拉伸件看了"));
//        System.out.println("11===" + isNameExistSpecial("(爱上经典款拉伸件看了"));
//        System.out.println("12===" + isNameExistSpecial(")爱上经典款拉伸件看了"));
//        System.out.println("13===" + isNameExistSpecial("+爱上经典款拉伸件看了"));
//        System.out.println("14===" + isNameExistSpecial("=爱上经典款拉伸件看了"));
//        System.out.println("15===" + isNameExistSpecial("|爱上经典款拉伸件看了"));
//        System.out.println("16===" + isNameExistSpecial("{爱上经典款拉伸件看了"));
//        System.out.println("17===" + isNameExistSpecial("}爱上经典款拉伸件看了"));
//        System.out.println("18===" + isNameExistSpecial("'爱上经典款拉伸件看了"));
//        System.out.println("19===" + isNameExistSpecial(":爱上经典款拉伸件看了"));
//        System.out.println("20===" + isNameExistSpecial(";爱上经典款拉伸件看了"));
//        System.out.println("21===" + isNameExistSpecial("'爱上经典款拉伸件看了"));
//        System.out.println("22===" + isNameExistSpecial(",爱上经典款拉伸件看了"));
//        System.out.println("24===" + isNameExistSpecial("\\[爱上经典款拉伸件看了"));
//        System.out.println("26===" + isNameExistSpecial("\\]爱上经典款拉伸件看了"));
//        System.out.println("27===" + isNameExistSpecial(".爱上经典款拉伸件看了"));
//        System.out.println("28===" + isNameExistSpecial("<爱上经典款拉伸件看了"));
//        System.out.println("29===" + isNameExistSpecial(">爱上经典款拉伸件看了"));
//        System.out.println("30===" + isNameExistSpecial("/爱上经典款拉伸件看了"));
//        System.out.println("31===" + isNameExistSpecial("?爱上经典款拉伸件看了"));
//        System.out.println("32===" + isNameExistSpecial("~爱上经典款拉伸件看了"));
//        System.out.println("33===" + isNameExistSpecial("！爱上经典款拉伸件看了"));
//        System.out.println("34===" + isNameExistSpecial("@爱上经典款拉伸件看了"));
//        System.out.println("35===" + isNameExistSpecial("#爱上经典款拉伸件看了"));
//        System.out.println("36===" + isNameExistSpecial("￥爱上经典款拉伸件看了"));
//        System.out.println("37===" + isNameExistSpecial("%爱上经典款拉伸件看了"));
//        System.out.println("38===" + isNameExistSpecial("…爱上经典款拉伸件看了"));
//        System.out.println("39===" + isNameExistSpecial("…爱上经典款拉伸件看了"));
//        System.out.println("40===" + isNameExistSpecial("&爱上经典款拉伸件看了"));
//        System.out.println("41===" + isNameExistSpecial("*爱上经典款拉伸件看了"));
//        System.out.println("42===" + isNameExistSpecial("（爱上经典款拉伸件看了"));
//        System.out.println("43===" + isNameExistSpecial("）爱上经典款拉伸件看了"));
//        System.out.println("44===" + isNameExistSpecial("—爱上经典款拉伸件看了"));
//        System.out.println("45===" + isNameExistSpecial("—爱上经典款拉伸件看了"));
//        System.out.println("46===" + isNameExistSpecial("+爱上经典款拉伸件看了"));
//        System.out.println("47===" + isNameExistSpecial("|爱上经典款拉伸件看了"));
//        System.out.println("48===" + isNameExistSpecial("{爱上经典款拉伸件看了"));
//        System.out.println("49===" + isNameExistSpecial("}爱上经典款拉伸件看了"));
//        System.out.println("50===" + isNameExistSpecial("【爱上经典款拉伸件看了"));
//        System.out.println("51===" + isNameExistSpecial("】爱上经典款拉伸件看了"));
//        System.out.println("52===" + isNameExistSpecial("‘爱上经典款拉伸件看了"));
//        System.out.println("53===" + isNameExistSpecial("；爱上经典款拉伸件看了"));
//        System.out.println("54===" + isNameExistSpecial("：爱上经典款拉伸件看了"));
//        System.out.println("55===" + isNameExistSpecial("”爱上经典款拉伸件看了"));
//        System.out.println("56===" + isNameExistSpecial("“爱上经典款拉伸件看了"));
//        System.out.println("57===" + isNameExistSpecial("’爱上经典款拉伸件看了"));
//        System.out.println("58===" + isNameExistSpecial("。爱上经典款拉伸件看了"));
//        System.out.println("59===" + isNameExistSpecial("，爱上经典款拉伸件看了"));
//        System.out.println("60===" + isNameExistSpecial("、爱上经典款拉伸件看了"));
//        System.out.println("61===" + isNameExistSpecial("？爱上经典款拉伸件看了"));
//        System.out.println("62===" + isNameExistSpecial("0爱上经典款拉伸件看了"));
//        System.out.println("63===" + isNameExistSpecial("1爱上经典款拉伸件看了"));
//        System.out.println("64===" + isNameExistSpecial("2爱上经典款拉伸件看了"));
//        System.out.println("65===" + isNameExistSpecial("3爱上经典款拉伸件看了"));
//        System.out.println("66===" + isNameExistSpecial("4爱上经典款拉伸件看了"));
//        System.out.println("67===" + isNameExistSpecial("5爱上经典款拉伸件看了"));
//        System.out.println("68===" + isNameExistSpecial("6爱上经典款拉伸件看了"));
//        System.out.println("69===" + isNameExistSpecial("7爱上经典款拉伸件看了"));
//        System.out.println("70===" + isNameExistSpecial("8爱上经典款拉伸件看了"));
//        System.out.println("71===" + isNameExistSpecial("9爱上经典款拉伸件看了"));

        long time;
        String strPattern;
        Pattern pattern;
        int size = 9999999;

        time = System.currentTimeMillis();
        strPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？0-9]";
        pattern = Pattern.compile(strPattern);
        for (int i = 0; i < size; i++) {
            Matcher matcher = pattern.matcher("" + i);
            matcher.find();
        }
        System.out.println("===" + (System.currentTimeMillis() - time));


        time = System.currentTimeMillis();
        strPattern = "[`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥…（）—【】‘；：”“’。，、？0-9]";
        pattern = Pattern.compile(strPattern);
        for (int i = 0; i < size; i++) {
            Matcher matcher = pattern.matcher("" + i);
            matcher.find();
        }
        System.out.println("===" + (System.currentTimeMillis() - time));

    }

    public static final boolean isNameExistSpecial(String str) {
        if (str == null || "".equalsIgnoreCase(str)) {
            return false;
        }
        try {
            String strPattern = "[`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥…（）—【】‘；：”“’。，、？0-9]";
            Pattern pattern = Pattern.compile(strPattern);
            Matcher matcher = pattern.matcher(str);
            return matcher.find();
        } catch (Exception e) {
            System.out.println("Common isNameExistSpecial Exception2055 = " + e.toString());
        }
        return false;
    }

    @Test
    public void testInt() {
//        System.out.println("1===" + Integer.valueOf('X'));

//        int x = 2;
//        int y = 5;
//        double value1 = x / y;
//
//        System.out.println("1===" + value1);
//        System.out.println("1===" + Integer.valueOf('X'));
//        System.out.println("2===" + Integer.parseInt('X'));

//        as();


//        List<String> listDay = new ArrayList<>();
//
//        if (listDay != null & listDay.size() == 1) {
//            System.out.println("1111111111");
//        } else {
//            System.out.println("22222222222222");
//        }

        String d = "2019-01-04 14:06:27.867 16916-16936/com.nbank.study W/ico: 爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的1爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的1爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数去我额人他Ae|1";
        System.out.println(d.getBytes().length);
    }


    String getStr() {
        return "564654564x";
    }

    private byte[] string2Bytes(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }


    @Test
    public void testEnum() {
//        System.out.println("==" + AA.A.name());
//        System.out.println("==" + AA.A.toString());
//        System.out.println("==" + AA.valueOf("A"));
//        System.out.println("==" + AA.valueOf("B"));
//        System.out.println("==" + AA.valueOf("C"));
//        System.out.println("==" + Arrays.toString(AA.values()));
//        String d = null;
//        System.out.println("==" + (d instanceof String));
//
//        try {
//            trycatch();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        List<String> list = new ArrayList<>();
        list.add("af1");
        list.add("af2");
        list.add("af3");
        list.add("af4");
//
//        String[] d = new String[0];
//        String[] a;
//
//        a = list.toArray(d);
//        System.out.println("===" + Arrays.toString(d));
//        System.out.println("===" + Arrays.toString(a));


//        byte[] src = {4, 3, 87, 45, 99, 123};

//        System.out.println(Arrays.toString(Arrays.copyOfRange(src, 9, 2)));
//        System.out.println(Arrays.toString(SubBytes(src, 9, 2)));


//        String[] array = new String[5];
//        String[] arr = list.toArray(array);
//        System.out.println(Arrays.toString(arr));
//        System.out.println(Arrays.toString(array));
//        System.out.println(arr == array);


//        String d = "jkasd\ndjkasd\nsfsdf\nweriouo\nweop";
//        System.out.println(Arrays.toString(spliteString(d)));



        String das = "wqo.qwe.asdf";

        System.out.println(Arrays.toString(das.split("\\.")));
        System.out.println(Arrays.toString(das.split(".")));

    }


    public static String[] spliteString(String source) {
        if (source == null) {
            return null;
        }
        String[] result;
        //ICO fix 在单线程操作中，ArrayList效率比Vector高
        List vector = new ArrayList();
        int startIndex = 0;
        String element;
        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) == '\n') {
                if (i != 0 && source.charAt(i - 1) == '\r') {
                    element = source.substring(startIndex, i - 1).trim();
                } else {
                    element = source.substring(startIndex, i).trim();
                }
                if (StringUtil.isNotEmpty(element) && element.charAt(0) != '/') {
                    vector.add(element);
                }
                startIndex = i + 1;
            } else if (i == source.length() - 1) {
                if (i != 0 && source.charAt(i - 1) == '\r') {
                    element = source.substring(startIndex, i - 1).trim();
                } else {
                    element = source.substring(startIndex).trim();
                }
                if (StringUtil.isNotEmpty(element) && element.charAt(0) != '/') {
                    vector.add(element);
                }
            }
        }
        // System.out.println("***SIZE = " + vector.size());
        result = new String[vector.size()];
        vector.toArray(result);
        return result;
    }


    private void trycatch() throws UnsupportedEncodingException, IOException {
        throw new UnsupportedEncodingException();
    }
}
