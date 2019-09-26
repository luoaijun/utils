package com.luoaijun.utils.quality;

import com.luoaijun.utils.Utils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @dec
 * @Version 1.0
 * @Author aijun.luo
 * @Date 2019.7.22
 */

public class DataQuality {
    private static Logger logger = Logger.getLogger(DataQuality.class);

    /**
     * TODO 提取字符串中的数值
     *
     * @param str
     * @return
     */
    public static int getNumber(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

        return Integer.parseInt(m.replaceAll("").trim());
    }

    /**
     * TODO 判断数据长度是否符合规范
     *
     * @param len
     * @param size
     * @return
     */
    public static boolean isLength(int len, int size) {
        return len <= size;
    }

    /**
     * TODO 校验数据类型
     *
     * @param keyValue
     * @param data
     * @param type
     * @return
     */
    public static boolean isType(String keyValue, String data, String type, int length) {
        data = data.toLowerCase();
        type = type.toLowerCase();
        boolean flag = false;
        if (type.contains("varchar") || type.contains("char")) {
            flag = isLength(data.length(), length);
            if (flag)
                logger.info("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>is\t" + "type of\t" + type);
            else
                logger.error("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>not\t" + "type of\t" + type);
            return flag;
        }
        if (type.contains("hashcode")) {
            flag = data.length() == length;
            if (flag)
                logger.info("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>is\t" + "type of\t" + type);
            else
                logger.error("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>not\t" + "type of\t" + type);
            return flag;
        }
        if (type.contains("date") || type.contains("time")) {
            flag = isValidDate(data);
            if (flag)
                logger.info("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>is\t" + "type of\t" + type);
            else
                logger.error("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>not\t" + "type of\t" + type);
            return flag;
        }
        if (type.contains("varchar")) {
            flag = isLength(data.length(), length);
            if (flag)
                logger.info("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>is\t" + "type of\t" + type);
            else
                logger.error("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>not\t" + "type of\t" + type);
            return flag;
        }
        if (type.contains("text")) {
            flag = true;
            if (flag)
                logger.info("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>is\t" + "type of\t" + type);
            else
                logger.error("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>not\t" + "type of\t" + type);
            return flag;
        }
        if (type.contains("numeric") || type.contains("int")) {
            Pattern pattern = Pattern.compile("[0-9]*");
            flag = pattern.matcher(data).matches();
            if (flag)
                logger.info("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>is\t" + "type of\t" + type);
            else
                logger.error("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>not\t" + "type of\t" + type);
            return flag;
        }
        if (type.contains("uniqueidentifier") || type.contains("guid")) {
            Pattern pattern = Pattern.compile("^[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}$");
            flag = pattern.matcher(data).matches();
            if (flag)
                logger.info("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>is\t" + "type of\t" + type);
            else
                logger.error("PrimaryKey:" + keyValue + "\t>>>>\tFieldValue>>>>\t" + data + "\t>>>>not\t" + "type of\t" + type);
            return flag;
        }
        if (type.contains("bit")) {
            return true;
        }
        return false;
    }

    // 判断数据是否为空
    public static void isNULL(String keyValue, Map restList) {
        String key;
        String value;
        Map map = restList;
        Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            key = entry.getKey();
            value = entry.getValue();
            if (Utils.isEmpty(value))
                logger.warn("PrimaryKey:" + keyValue + ">>>>\tFieldName:\t" + key + "\tIs an empty data" + value);
            else
                logger.info("PrimaryKey:" + keyValue + ">>>>\tFieldName:\t" + key + "\tFieldValue:" + value);
        }
    }

    /**
     * TODO 判断是否为合法日期格式
     *
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        String[] dateFormatKey = {
                "yyyy/MM/dd HH:mm", "yyyy年MM月dd日 HH时mm分ss秒",
                "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy年MM月dd日",
                "yyyy/MM/dd", "dd/MM/yy"
        };
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        for (int i = 0; i < dateFormatKey.length; i++) {
            SimpleDateFormat format = new SimpleDateFormat(dateFormatKey[i]);
            try {
                // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
                format.setLenient(false);
                format.parse(str);
                return true;
            } catch (ParseException e) {
                // e.printStackTrace();
                // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
                convertSuccess = false;
            }
        }
        return convertSuccess;
    }

    /**
     * 已废弃
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        String regex = "^[1][3,4,5,7,8][0-9]{9}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    /**
     * TODO 判断是否为邮箱
     * 已废弃
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) {
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * TODO
     *
     * @param keyValue
     * @param regular  正则表达式
     * @param data     校验的数据
     * @return
     */
    public static boolean isRegular(String keyValue, String regular, String data) {
        if (null == data || "".equals(data)) {
            return false;
        }
        Pattern p = Pattern.compile(regular);
        Matcher m = p.matcher(data);
        if (m.matches()) {
            logger.info("PrimaryKey:" + keyValue + "FieldValue:" + data + "\t is match this regular type" + regular);
            return true;
        } else {
            logger.error("PrimaryKey:" + keyValue + "FieldValue:" + data + "\t is not match this regular type" + regular);
            return false;
        }
    }


    /**
     * TODO 判断表结构是否一致
     *
     * @param keyValue
     * @param mongoList
     * @param structList
     * @return
     */

    public static Boolean structureCheck(String keyValue, Map mongoList, Map structList) {
        Map[] map = new LinkedHashMap[2];
        boolean tag = true;
        map[0] = structList;
        Iterator<Map.Entry<String, String>> iteratorMap1 = map[0].entrySet().iterator();
        while (iteratorMap1.hasNext()) {
            Map.Entry<String, String> entry1 = iteratorMap1.next();
            String m1value = entry1.getValue() == null ? "" : entry1.getKey();
            if (mongoList.containsKey(m1value)) {//若两个map中相同key对应的value不相等
                //其他操作...
                logger.info("PrimaryKey:" + keyValue + ">>>>\tContain Field \t" + m1value);
            } else {
                logger.error("PrimaryKey:" + keyValue + ">>>>\tThere are illegal fields \t" + m1value);
                tag = false;
            }
        }
        return tag;
    }

}
