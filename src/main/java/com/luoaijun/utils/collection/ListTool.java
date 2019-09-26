package com.luoaijun.utils.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * TODO list 工具类
 *
 * @author 罗爱军
 * @date 2018年3月8日
 * @email aijun.luo@outlook.com
 * @include :
 * @category :
 */
public class ListTool {


    /**
     * @param list
     * @return null
     * @category 打印list类内容
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
}
