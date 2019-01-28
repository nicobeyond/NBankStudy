package ico.ico.util;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StringUtil {
    /** 判断字符串是否为null或者"" */
    public static boolean isEmpty(String value) {
        return null == value || "".equals(value.trim());
    }

    /** 判断字符串是否为null，""，或者长度大于0全部有空格组成 */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0)
            return true;
        for (int i = 0; i < strLen; i++)
            if (!Character.isWhitespace(str.charAt(i)))
                return false;

        return true;
    }

    /** !{@link #isEmpty(String)} */
    public static boolean isNotEmpty(String value) {
        return null != value && !"".equals(value.trim());
    }

    /** !{@link #isBlank(String)} */
    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    /** 判断字符串是否是集合中的某一个值 */
    public static boolean in(String value, String... eq) {
        if (value == null || eq == null) {
            return false;
        }
        for (int i = 0; i < eq.length; i++) {
            if (value.equals(eq[i])) {
                return true;
            }
        }
        return false;
    }

    /** 判断字符串是否是集合中的某一个值,忽略大小写 */
    public static boolean inIgnoreCase(String value, String... eq) {
        if (value == null || eq == null) {
            return false;
        }
        for (int i = 0; i < eq.length; i++) {
            if (value.equalsIgnoreCase(eq[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将字符串转化为MD5
     *
     * @param text
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encodeByMd5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());
        return Common.bytes2Int16("", digest);
    }

    /**
     * 将一个字符串数组根据某个字符串连接
     * <p>
     * 为了解耦合，这个函数有其他分支，以下列出分支
     * <p>
     * {@link log#join(String, String...)}
     *
     * @param str   要插入的字符串
     * @param texts 要被拼接的字符串数组,如果传入null或者空数组，则将返回空字符串
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String join(String str, String... texts) {
        //java1.8可以通过String.join来实现，不过效率上比本方法要慢
//        try {
//            Class.forName("java.util.StringJoiner");
//            log.w("===1111");
//            return String.join(str,texts);
//        } catch (ClassNotFoundException e) {
//            log.w("===2222");
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
//        }
    }

    /**
     * 将一个字符串集合根据某个字符串连接
     *
     * @param str   连接的字符串
     * @param texts 用于拼接的字符串集合，使用list
     * @return String 拼接后的字符串
     * @deprecated 这个方法已经过时，因为如果参数texts使用linkedlist，效率会非常的慢，所以建议使用迭代器参数的join方法
     */
    @Deprecated
    public static String join(String str, List<String> texts) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texts.size(); i++) {
            log.w("===" + i);
            String tmp = texts.get(i);
            sb.append(tmp);
            if (i < texts.size() - 1) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    /** 将一个字符串集合根据某个字符串连接 */
    public static String join(String str, Iterator<String> texts) {
        StringBuilder sb = new StringBuilder();
        while (texts.hasNext()) {
            String tmp = texts.next();
            sb.append(tmp);
            if (texts.hasNext()) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    /**
     * 将多个字节数组进行拼接
     *
     * @param buffer
     * @return
     */
    public static byte[] fit(byte[]... buffer) {
        //计算数据总长度
        int length = 0;
        for (int i = 0; i < buffer.length; i++) {
            length += buffer[i].length;
        }
        byte[] datas = new byte[length];
        //依次进行copy
        int sp = 0;//起始存放位置
        for (int i = 0; i < buffer.length; i++) {
            System.arraycopy(buffer[i], 0, datas, sp, buffer[i].length);
            sp += buffer[i].length;
        }
        return datas;
    }

    /**
     * 将一个字符串根据指定长度进行分割
     * <p>
     * 为了解耦合，这个函数有其他分支，以下列出分支
     * <p>
     * {@link log#split(String, int)}
     *
     * @param str  要分割的字符串，如果传入的是个null值，则将拼接 空字符串 添加到集合中进行返回
     * @param size 指定的长度，分割的每个部分保证不大于这个长度
     * @return List(String) 返回一个集合，集合必定不为空并且至少有一个数据
     */
    @NonNull
    public static List<String> split(String str, int size) {
        List<String> data = new ArrayList<>();
        if (TextUtils.isEmpty(str) || str.length() <= size) {
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
     * 每隔几个字符插入一个指定字符
     *
     * @param s        原字符串
     * @param iStr     要插入的字符串
     * @param interval 间隔字符数量
     * @return
     */
    public static String insert(String s, String iStr, int interval) {
        StringBuffer s1 = new StringBuffer(s);
        int index;
        for (index = interval; index < s1.length(); index += (interval + 1)) {
            s1.insert(index, iStr);
        }
        return s1.toString();
    }
}
