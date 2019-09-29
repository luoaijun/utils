package com.luoaijun.utils.collection;

import java.lang.reflect.Array;
import java.util.*;

/**
 * TODO list 工具类
 * <p>
 * 罗爱军
 * 2018年3月8日
 * aijun.luo@outlook.com
 *
 *
 * TODO :
 */
public class ListUtils {


    /**
     * @param list
     * @return  return null
     * TODO 打印list类内容
     */
    @SuppressWarnings("rawtypes")
    public static <T> Object printLn(List list) {
        Iterator iterator = list.iterator();
        StringBuilder builder = new StringBuilder();
        while (iterator.hasNext()) {
            builder.append(iterator.next());

        }
        return builder.toString();
    }

    /**
     * TODO 返回一个list
     *
     * @param object
     * @return  return
     */
    public static List createList(Object... object) {
        return Arrays.asList(object);
    }
}
