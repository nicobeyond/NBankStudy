package com.nbank.study;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ico.ico.util.StringUtil;

/**
 * Created by admin on 2015/4/21.
 */
public class log {
    final static String COMMON_TAG = "ico_";
    /**
     * 日志等级,从e到v依次为1到5，若输出全关则设置0
     * out等同i，err等同e
     */
    final static int LEVEL = 5;

    public static void v(String msg, String... tags) {
        if (LEVEL < 5 || StringUtil.isEmpty(msg)) {
            return;
        }
        out(msg, tags);
    }

    public static void d(String msg, String... tags) {
        if (LEVEL < 4 || StringUtil.isEmpty(msg)) {
            return;
        }
        out(msg, tags);
    }

    public static void i(String msg, String... tags) {
        if (LEVEL < 3 || StringUtil.isEmpty(msg)) {
            return;
        }
        out(msg, tags);
    }

    public static void w(String msg, String... tags) {
        if (LEVEL < 2 || StringUtil.isEmpty(msg)) {
            return;
        }
        out(msg, tags);
    }

    public static void e(String msg, String... tags) {
        if (LEVEL < 1 || StringUtil.isEmpty(msg)) {
            return;
        }
        out(msg, tags);
    }

    public static void e(String msg, Exception e, String... tags) {
        if (LEVEL < 1) {
            return;
        }
        out(msg, tags);
    }

    public static void out(String msg, String... tags) {
        if (LEVEL < 3 || StringUtil.isEmpty(msg)) {
            return;
        }
        String tag = COMMON_TAG + join("_", tags);
        System.out.println(tag + "," + msg);
    }

    public static void err(String msg, String... tags) {
        if (LEVEL < 1 || StringUtil.isEmpty(msg)) {
            return;
        }
        String tag = COMMON_TAG + join("_", tags);
        System.err.println(tag + "," + msg);
    }

    /**
     * 将一个字符串数组根据某个字符串连接
     *
     * @param str   要插入的字符串
     * @param texts 要被拼接的字符串数组,如果传入null或者空数组，则将返回空字符串
     * @return
     */
    public static String join(String str, String... texts) {
        if (texts == null || texts.length == 0) return "";
        if (texts.length == 1) return texts[0];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texts.length; i++) {
            String tmp = texts[i];
            sb.append(tmp);
            if (i < texts.length - 1) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    /**
     * 将一个字符串根据指定长度进行分割
     *
     * @param str  要分割的字符串，如果传入的是个null值，则将拼接 空字符串 添加到集合中进行返回
     * @param size 指定的长度，分割的每个部分保证不大于这个长度
     * @return List(String) 返回一个集合，集合必定不为空并且至少有一个数据
     */
    @NonNull
    public static List<String> split(String str, int size) {
        List<String> data = new ArrayList<>();
        if (StringUtil.isEmpty(str) || str.length() <= size) {
            data.add(str + "");
            return data;
        }
        while (true) {
            if (str.length() > size) {
                data.add(str.substring(0, size));
            } else {
                data.add(str);
                break;
            }
            str = str.substring(size);
        }
        return data;
    }

    /**
     * 读取文件
     *
     * @param file
     */
    public static List<Byte> readFile(File file) throws IOException {
        List<Byte> list = new ArrayList<Byte>();
        FileInputStream fileInputStream = new FileInputStream(file);
        while (true) {
            byte[] buffer = new byte[1024 * 4 * 4];
            int len = fileInputStream.read(buffer);
            if (len == -1) {
                break;
            }
            for (int i = 0; i < len; i++) {
                List<Byte> _list = Arrays.asList(buffer[i]);
                list.add(_list.get(0));
            }
        }
        return list;
    }

    /**
     * 将byte集合转换为字符串
     *
     * @param list
     * @return
     * @throws {@link UnsupportedEncodingException}
     */
    public static String bytes2Str(List<Byte> list) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[(list.size() > Integer.MAX_VALUE ? Integer.MAX_VALUE : list.size())];
        for (int i = 0, j = 0; i < list.size(); i++, j++) {
            buffer[j] = list.get(i);
            if (j == buffer.length - 1) {
                sb.append(new String(buffer, "UTF-8"));
                buffer = new byte[(list.size() > Integer.MAX_VALUE ? Integer.MAX_VALUE : list.size())];
                j = -1;
            }
        }
        String str = sb.toString();
        return str;
    }
}
