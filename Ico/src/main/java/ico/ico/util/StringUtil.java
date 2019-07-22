package ico.ico.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     */
    public static String join(String str, ArrayList<String> texts) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texts.size(); i++) {
            String tmp = texts.get(i);
            sb.append(tmp);
            if (i < texts.size() - 1) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    /** 将一个字符串集合根据某个字符串连接 */
    public static String join(String str, LinkedList<String> texts) {
        Iterator<String> iter = texts.iterator();
        return join(str, iter);
    }

    /** 将一个字符串集合根据某个字符串连接 */
    public static String join(String str, Iterator<String> iter) {
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            String tmp = iter.next();
            sb.append(tmp);
            if (iter.hasNext()) {
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
     * @param src      原字符串
     * @param insert   要插入的字符串
     * @param interval 间隔字符数量
     * @return
     */
    public static String insert(String src, String insert, int interval) {
        StringBuffer s1 = new StringBuffer(src);
        int index;
        for (index = interval; index < s1.length(); index += (interval + 1)) {
            s1.insert(index, insert);
        }
        return s1.toString();
    }

    /** 将字符串中第一个匹配到的数值进行格式化，添加，和保留小数点两位（不足两位+0） */
    public static String formatAmount(String str) {
        //通过正则表达式把数字部分截取出来，处理完成后通过替换的方式再放回去
        Pattern pattern = Pattern.compile("(\\d+[.]{1}\\d+)|([.]{1}\\d+)|(\\d+)");
        Matcher mat = pattern.matcher(str);
        if (!mat.find()) {
            return str;
        }
        String matchStr = mat.group();
        //通过.进行分隔
        String[] splitMatchStr = matchStr.split("\\.");
        String zheng = "";//整数部分
        String xiaoshu = "";//小数部分
        /* 处理整数部分 */
        if (splitMatchStr[0].length() <= 3) {
            zheng = splitMatchStr[0];
        } else {
            int yu = splitMatchStr[0].length() % 3;
            if (yu == 0) {
                zheng = insert(splitMatchStr[0], ",", 3);
            } else {
                zheng = splitMatchStr[0].substring(0, yu) + "," + insert(splitMatchStr[0].substring(yu, splitMatchStr[0].length()), ",", 3);
            }
        }
        if (StringUtil.isBlank(zheng)) {
            zheng = "0";
        }
        /* 处理小数部分 */
        if (splitMatchStr.length == 1) {
            xiaoshu = "00";
        } else {
            if (splitMatchStr[1].length() <= 2) {
                xiaoshu = splitMatchStr[1];
            } else {
                xiaoshu = splitMatchStr[1].substring(0, 2);
            }
        }
        if (xiaoshu.length() == 1) {
            xiaoshu += "0";
        }
        String result = zheng + "." + xiaoshu;
        return str.replace(matchStr, result);
    }

    /** 将比例字符串中第一个匹配到的数值进行格式化，只保留两位小数点（不补0） */
    public static String formatRateAll(String str) {
        //通过正则表达式把数字部分截取出来，处理完成后通过替换的方式再放回去
        Pattern pattern = Pattern.compile("(\\d+[.]{1}\\d+)|([.]{1}\\d+)|(\\d+)");
        Matcher mat = pattern.matcher(str);
        while (mat.find()) {
            String matchStr = mat.group();
            //通过.进行分隔
            String[] splitMatchStr = matchStr.split("\\.");
            String zheng = "";//整数部分
            String xiaoshu = "";//小数部分
            if (splitMatchStr[0].length() == 0) {
                zheng = "0";
            } else {
                zheng = splitMatchStr[0];
            }
            /* 处理小数部分 */
            if (splitMatchStr.length == 1) {
                return str.replace(matchStr, zheng);
            }
            if (splitMatchStr[1].length() <= 2) {
                xiaoshu = splitMatchStr[1];
            } else {
                xiaoshu = splitMatchStr[1].substring(0, 2);
            }
            String replaceStr = zheng + "." + xiaoshu;
            str = str.replace(matchStr, replaceStr);
        }
        return str;
    }

    /** 将比例字符串中第一个匹配到的数值进行格式化，只保留两位小数点（不补0） */
    public static String formatRate(String str) {
        //通过正则表达式把数字部分截取出来，处理完成后通过替换的方式再放回去
        Pattern pattern = Pattern.compile("(\\d+[.]{1}\\d+)|([.]{1}\\d+)|(\\d+)");
        Matcher mat = pattern.matcher(str);
        if (!mat.find()) {
            return str;
        }
        String matchStr = mat.group();
        //通过.进行分隔
        String[] splitMatchStr = matchStr.split("\\.");
        String zheng = "";//整数部分
        String xiaoshu = "";//小数部分
        if (splitMatchStr[0].length() == 0) {
            zheng = "0";
        } else {
            zheng = splitMatchStr[0];
        }
        /* 处理小数部分 */
        if (splitMatchStr.length == 1) {
            return str.replace(matchStr, zheng);
        }
        if (splitMatchStr[1].length() <= 2) {
            xiaoshu = splitMatchStr[1];
        } else {
            xiaoshu = splitMatchStr[1].substring(0, 2);
        }
        String result = zheng + "." + xiaoshu;
        return str.replace(matchStr, result);
    }

    /** 对字符串比例值进行转化，添加百分号标志 */
    public static String mapRate(String str) {
        if (!str.endsWith("%")) {
            return str + "%";
        }
        return str;
    }
}
