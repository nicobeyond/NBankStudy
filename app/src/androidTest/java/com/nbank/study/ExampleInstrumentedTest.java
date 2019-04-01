package com.nbank.study;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import ico.ico.util.Common;
import ico.ico.util.FileUtil;
import ico.ico.util.log;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void testTime() {
//        Object obj1 = null;
//        Object obj2 = null;
//        try {
//            obj1 = new Object();
//            log.w("obj2===" + obj2.toString());
//            obj2 = new Object();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            log.w("obj1===" + obj1);
//            log.w("obj1===" + obj2);
//        }
//        BufferedReader reader = new BufferedReader(null);
        MDragGridView mDragGridView = new MDragGridView();

        Person person = new Person();
        log.w("1===" + person.flag);
        log.w("2===" + ((Base) person).flag);


    }

    @Test
    public void testNumber() throws JSONException {

        BigDecimal one = new BigDecimal("1");

        log.w("===" + one);
        log.w("===" + BigDecimal.ONE);

//        log.w("===" + (0.2f + 0.1f));
//        log.w("===" + (0.3f - 0.1f));
//        log.w("===" + (0.2f * 0.1f));
//        log.w("===" + (0.3f / 0.1f));

        String str = "qwe";
        log.w("===" + Arrays.toString(str.split("\\|")));


//        HashMap<String, String>[] maps = new HashMap[3];
//        HashMap<String, String> map1 = new HashMap<>();
//        HashMap<String, String> map2 = new HashMap<>();
//        HashMap<String, String> map3 = new HashMap<>();
//        map1.put("1", "1");
//        map2.put("2", "2");
//        map3.put("3", "3");
//        maps[0] = map1;
//        maps[1] = map2;
//        maps[2] = map3;
//        ArrayList<HashMap<String, String>> list = new ArrayList<>(Arrays.asList(maps));
//        log.w("===" + list.toString());
    }

    @Test
    public void testJson() throws JSONException {
//        JSONObject json = new JSONObject();
//        json.put("111", "das");
//
//        log.w("===" + json.getInt("222"));

//        HashMap<String, Object> map = new HashMap<>();
//        map.put("aaa", 1);
//        String s = map.get("aaa").toString();
//        log.w("===" + s);

//         double d = 2;
//        double d1 = d/0;
//        log.w("===" + d1);

//        String d = "dasdas";
//        String d2 = "1";
//        String d3 = "2";
//        String d4 = "4";
//        log.w("2===" + TextUtils.equals(d2, d3));
//        log.w("3===" + "".equals(d3));
//        log.w("4===" + d.getBytes().equals(d));
//        log.w("5===" + StringUtil.in(d2, "1","2"));
//        log.w("6===" + StringUtil.in(d3, "1","2"));
//        log.w("7===" + StringUtil.in(d4, "1","2"));

        //测试String.join StringUtil.join StringUtil.join1的效率
        /*int size = 999999;
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = "dasdas";
        }

        LinkedList<String> linklist = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            linklist.add("dasdas");
        }

        ArrayList<String> arraylist = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arraylist.add("dasdas");
        }
        long time = 0;

        time = System.currentTimeMillis();
        String.join(" ", array);
        log.w("String.join===" + (System.currentTimeMillis() - time), "1");

        time = System.currentTimeMillis();
        StringUtil.join(" ", array);
        log.w("StringUtil.join===" + (System.currentTimeMillis() - time), "1");


        time = System.currentTimeMillis();
        StringUtil.join(",", arraylist.iterator());
        log.w("arriter===" + (System.currentTimeMillis() - time), "1");

        time = System.currentTimeMillis();
        StringUtil.join(",", linklist.iterator());
        log.w("linkiter===" + (System.currentTimeMillis() - time), "1");*/

//        String str1 = "abc";
//        String str2 = "a" + "b" + "c";
//        log.w("1===" + (str1 == str2));
//        log.w("2===" + (str1.substring(0, 1) == "a"));
//        log.w("3===" + (str1.substring(0, 1) == str1.substring(0, 1)));
//        log.w("4===" + "汉".length());
//        log.w("5===" + "汉".codePointCount(0, "汉".length()));
//        log.w("6===" + "a".length());
//        log.w("7===" + "a".codePointCount(0, "a".length()));
//        log.w("8===" + "\u246a".charAt(0));
//        log.w("10===" + "\u246a".length());
//        log.w("11===" + "\u246a".codePointCount(0, "\u246a".length()));
//        log.w("12===" + "汉".codePointAt(0));
//        int[] ints = new int[]{27721};
//        log.w("13===" + new String(ints, 0, 1));

        DateFormat df0 = DateFormat.getDateInstance(DateFormat.ERA_FIELD,new Locale("zh","CN")) ;	// 得到日期的DateFormat对象
        DateFormat df10 = DateFormat.getDateTimeInstance(DateFormat.YEAR_FIELD,DateFormat.ERA_FIELD,new Locale("zh","CN")) ;	// 得到日期时间的DateFormat对象
        DateFormat df1 = DateFormat.getDateInstance(DateFormat.YEAR_FIELD,new Locale("zh","CN")) ;	// 得到日期的DateFormat对象
        DateFormat df2 = DateFormat.getDateInstance(DateFormat.MONTH_FIELD,new Locale("zh","CN")) ;	// 得到日期的DateFormat对象
        DateFormat df3 = DateFormat.getDateInstance(DateFormat.DATE_FIELD,new Locale("zh","CN")) ;	// 得到日期的DateFormat对象
        log.w("df0：" + df0.format(new Date())) ; // 按照日期格式化
        log.w("df1：" + df1.format(new Date())) ;	 // 按照日期时间格式化
        log.w("df2：" + df2.format(new Date())) ;	 // 按照日期时间格式化
        log.w("df3：" + df3.format(new Date())) ;	 // 按照日期时间格式化
        log.w("DATETIME：" + df10.format(new Date())) ;	 // 按照日期时间格式化

    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0)
            return true;
        for (int i = 0; i < strLen; i++)
            if (!Character.isWhitespace(str.charAt(i)))
                return false;

        return true;
    }

    @Test
    public void testLog() {
        String d = "爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的1爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的1爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数据库的爱上了很大数去我额人他Ae|1";

        Log.w("ico", "===" + d.getBytes().length);
        Log.w("ico", d);
        Log.w("ico_", d);
        Log.w("ico__", d);
    }

    @Test
    public void testIO() throws JsonProcessingException {
        String d = "/dasd/dasdsa/das3";
        String s = FileUtil.getFileName(d);
        String[] a = s.split("\\.");
        log.w("2===" + FileUtil.getFileName(d));
//        log.w("3===" + Common.join(".", new String[]{"dasd"}));
        log.w("4===" + Arrays.toString(a));

//        File oldFile = new File(Environment.getExternalStorageDirectory() + "dasd.jpg");
//        File newFile = new File(Environment.getExternalStorageDirectory() + "123.jpg");
//        log.w("===dsad" + oldFile.renameTo(newFile));

//        Log.w("ico_", "testIO: ", new IOException("dasd"));
//        String id = "330211199103270052";
//        log.w("===" + IDCardValidateTool.getAgeByIdCard(id));
//        log.w("===" + IDCardValidateTool.getBirthByIdCard(id));
//        log.w("===" + IDCardValidateTool.getGenderByIdCard(id));
//        log.w("===" + IDCardValidateTool.getProvinceByIdCard(id));
//        log.w("===" + IDCardValidateTool.getDateByIdCard(id));
//        log.w("===" + IDCardValidateTool.getMonthByIdCard(id));
//        log.w("===" + IDCardValidateTool.getYearByIdCard(id));


//        HashMap<String, String> map = new HashMap<>();
//        map.put("a", "a");
//        map.put("b", "b");
//        map.put("c", "c");

//        List<HashMap<String, String>> list = new ArrayList<>(Arrays.asList(map));

//        log.w("===" + new ObjectMapper().writeValueAsString(list));

//        log.w("---"+IDCardValidateTools.getAgeByIdCard(id));
//        log.w("---"+IDCardValidateTools.getBirthByIdCard(id));
//        log.w("---"+IDCardValidateTools.getGenderByIdCard(id));
//        log.w("---"+IDCardValidateTools.getProvinceByIdCard(id));
//        log.w("---"+IDCardValidateTools.getDateByIdCard(id));
//        log.w("---"+IDCardValidateTools.getMonthByIdCard(id));
//        log.w("---"+IDCardValidateTools.getYearByIdCard(id));


    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.nbank.study", appContext.getPackageName());
    }

    @Test
    public void testChar() {

//        String str = null;

//        log.w("1===" + Integer.valueOf(str));
//        log.w("1===" + Integer.parseInt(str));
        // Context of the app under test.
//        log.w("===" + Common.insert("1234567890abcdefff", ":", 3));
//        log.w("1===" + Common.int162Byte("c"));
//        log.w("2===" + Integer.valueOf("0c", 16).byteValue());
//        log.w("2===" + Common.bytes2Int16(" ", Common.int162Bytes("ashkzxncbagqwa")));
//        log.w("3===" + string2Bytes("ashkzxncbagqw"));

//        StringBuffer sb = new StringBuffer("dasdaskqouowe");
//        String c = sb.substring(0, sb.length() - 1);
//        double a = 19 / 4;
//        double b = 19 / 4D;
//        log.w("31===" + a);
//        log.w("32===" + b);
//        log.w("33===" + c);
//        log.w("34===" + genPath("a", "b", "c", "d", "a.apk"));
        log.w("34===" + 3 / 2);
        log.w("34===" + 3 / 2d);

        List<Base> list = new ArrayList<>();
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.remove(Integer.parseInt("2"));
        log.w("345===" + list.size());

    }

    @Test
    public void testByte() {
        /*String d = "ABCDEF";
        byte[] bytes = Common.int162Bytes(d);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
//            sb.append(Integer.toHexString(b & 0xFF)); // Noncompliant }
            sb.append(String.format("%02X", b)); // Noncompliant }
        }
        log.w("===" + sb.toString());*/


//        try {
//            String password = "djasldhiquwe";
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] bytes = md.digest(password.getBytes("UTF-8"));
//            StringBuilder sb = new StringBuilder();
//            StringBuilder sb1 = new StringBuilder();
//            StringBuilder sb2 = new StringBuilder();
//            for (byte b : bytes) {
//                sb.append(Integer.toHexString(b));
//                sb1.append(String.format("%02x", b));
//            }
//            sb2.append(Integer.toHexString(4508));
//            log.w("0===" + sb.toString());
//            log.w("1===" + sb1.toString());
//            log.w("2===" + sb2.toString());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        byte i = (byte) 129;
//        log.w("2===" + Integer.toHexString(i));
//        log.w("2===" + String.format("%02x", i));
//        log.w("2===" + String.format("%02x", i));

        log.w("23===" + Common.bytes2Int16(" ", hexStringToBytes("abcde fg0123456789")));
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
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
    public void testTry() throws IOException {
        try {
            throw new IOException();
        } catch (IOException e) {
            throw e;
        }

//        log.w("======test");
//        try {
//            HttpURLConnection urlConnection = (HttpURLConnection) new URL("").openConnection();
//            urlConnection.setConnectTimeout(5000);
//            urlConnection.setReadTimeout(5000);
//            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                InputStream inputStream = urlConnection.getInputStream();
//            }
//        } catch (Exception e) {
////            e.printStackTrace();
////            log.w("===" + e.toString());
//            log.w("===");
//        }
//
//        try {
//            String url = "kldajskldjaskljdklas.pdf";
//            log.w("222===" + url.substring(0, url.indexOf(".pdf") + 4));
////            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            log.w("===end");
//        }
//        log.w("===111");
    }

    @Test
    public void testKey() throws IOException {
//        /**
//         * 对称加密DESede密钥算法
//         * JAVA 6 支持长度为112和168位
//         * Bouncy Castle支持密钥长度为128位和192位
//         */
//        String KEY_ALGORITHM = "DESede";
//
//        /**
//         * 加解密算法/工作模式/填充方式
//         * JAVA 6 支持PKCS5Padding填充方式
//         * Bouncy Castle支持PKCS7Padding填充方式
//         */
//        String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
//
//        //Bouncy Castle算法提供者
//        String PROVIDER_BOUNCY_CASTOLE = "AndroidOpenSSL";
////        KeyGenerator kg = null;
////        try {
////            kg = KeyGenerator.getInstance("DESede/ECB/PKCS5Padding");
//////		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
////            kg.init(192);
////            SecretKey secretKey = kg.generateKey();
////            log.w("1===" + secretKey.getAlgorithm());
////            log.w("2===" + secretKey.getFormat());
////            log.w("3===" + Common.bytes2Int16(" ", secretKey.getEncoded()));
////        } catch (NoSuchAlgorithmException e) {
////            e.printStackTrace();
////        }
//
////        try {
////            Key deskey = null;
////            DESedeKeySpec spec = new DESedeKeySpec("DAdasdsdasdsadasdasdasfasdfasdsadsaadS".getBytes("UTF-8"));
////            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
////            deskey = keyfactory.generateSecret(spec);
////            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM, PROVIDER_BOUNCY_CASTOLE);
////            cipher.init(Cipher.ENCRYPT_MODE, deskey);
////            cipher.doFinal("qwe".getBytes("UTF-8"));
////            log.w("================" + Arrays.toString(Security.getProviders()));
////        } catch (NoSuchAlgorithmException e) {
////            e.printStackTrace();
////        } catch (NoSuchPaddingException e) {
////            e.printStackTrace();
////        } catch (BadPaddingException e) {
////            e.printStackTrace();
////        } catch (IllegalBlockSizeException e) {
////            e.printStackTrace();
////        } catch (InvalidKeySpecException e) {
////            e.printStackTrace();
////        } catch (InvalidKeyException e) {
////            e.printStackTrace();
////        } catch (NoSuchProviderException e) {
////            e.printStackTrace();
////        }
//        log.w("===" + md5("dasdas dsad dasd"));
//        log.w("===" + md5("dasdasdsaddasd"));
//
//        byte[] bytes1 = new byte[]{0x06, 0x79};
//        byte[] bytes2 = new byte[]{0x67, 0x09};
//
//        StringBuilder sb1 = new StringBuilder();
//        for (byte b : bytes1) {
//            sb1.append(Integer.toHexString(b & 0xFF));
//        }
//        StringBuilder sb2 = new StringBuilder();
//        for (byte b : bytes2) {
//            sb2.append(Integer.toHexString(b & 0xFF));
//        }
//        log.w("==="+sb1.toString());
//        log.w("==="+sb2.toString());
//        log.w("==="+0x1.0p-3);

        try {
            SecretKeySpec keySpec = new SecretKeySpec(("CopyRight YITU.INC 2015!").getBytes(), "AES");

            byte[] IV = "1234567890123456".getBytes();
            IvParameterSpec IVSpec = new IvParameterSpec(IV);
            Cipher cipher = Cipher.getInstance("AES/OFB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, IVSpec);
            //ICO fix 创建变量保存函数返回值并立即返回这个变量是无意义的，应直接返回函数返回值
            byte[] bytes = cipher.doFinal("dasdasdasfadfdasfsafqwerqw".getBytes("UTF-8"));

            log.w("1===" + Common.bytes2Int16(" ", bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String md5(String message) {
        try {
            // Generate MD5 hash code (16 bytes)
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(message.getBytes(Charset.forName("UTF-8")));
            byte messageDigest[] = digest.digest();

            log.w("1===" + Common.bytes2Int16(" ", messageDigest));
            // Encode the 16 bytes hash code to 32 bytes hex code.
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString((messageDigest[i] & 0xFF) | 0x100).substring(1, 3));

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testThread() {
        new Thread() {
            @Override
            public void run() {
                ObjectClass obj = new ObjectClass();

                Thread t2 = new ChangeValue(obj);
                t2.start();

                Thread t1 = new AlwaysRun(obj);
                t1.start();

                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                t1.stop();
            }
        }.start();
    }


    class AlwaysRun extends Thread {

        ObjectClass obj;

        public AlwaysRun(ObjectClass obj) {
            // TODO Auto-generated constructor stub
            this.obj = obj;
        }

        public void run() {
            obj.Loop();
        }
    }

    class ChangeValue extends Thread {

        ObjectClass obj;

        ChangeValue(ObjectClass obj) {
            this.obj = obj;
        }

        public void run() {

            log.w("Thread2");
            ObjClass2 obj2 = obj.getObj();

            try {
                sleep(1500);
            } catch (InterruptedException e) {
                log.w("Error!");
            }

            obj2.str = "aaa";
            log.w("Thread2 Finish!");
        }
    }

    public class ObjectClass extends Thread {

        private ObjClass2 obj;
        private Object lockTable = new Object();

        public ObjectClass() {
            // TODO Auto-generated constructor stub
            obj = new ObjClass2();
        }

        public void setObj(ObjClass2 obj) {
            synchronized (lockTable) {
                this.obj = obj;
            }
        }

        public ObjClass2 getObj() {
            synchronized (lockTable) {
                return this.obj;      //出问题处！！
            }
        }

        public void Loop() {
            synchronized (lockTable) {
                while (true) {

                    log.w(obj.str);
                }
            }
        }
    }

    public class ObjClass2 {
        public String str = "ddddddd";
    }

    /**
     * 将一个字符串数组根据某个字符串连接
     *
     * @param texts 要被拼接的字符串数组
     * @return
     */
    public String genPath(String... texts) {
        StringBuilder sb = new StringBuilder(File.separator);
        for (int i = 0; i < texts.length; i++) {
            String tmp = texts[i];
            sb.append(tmp);
            if (i < texts.length - 1) {
                sb.append(File.separator);
            }
        }
        return sb.toString();
    }
}
