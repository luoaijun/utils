package com.luoaijun.utils.algorithm;

import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @dec
 * @Version 1.0
 * @Author aijun.luo
 * @Date 15:30
 */
public class DataAlgorithm {

    public static Logger logger = Logger.getLogger(DataAlgorithm.class);

    /**
     * TODO 判断一组数据中任意两条数据是否相同，并返回重复的数据。时间复杂度O(n)
     * 高效判断重复数据，千万数据毫秒级响应
     *
     * @param data :返回数据结构为数据所在list的索引值和数据值
     * @return
     */
    public static Map noRepetitionDataCheck(List data) {
        //....
        long start = System.currentTimeMillis();
        logger.info(">>>>Start time\t\t" + start);
        logger.info(">>>>Total data volume\t\t" + data.size());
        boolean flag = true;
        Map map = new LinkedHashMap();
        Map count = new LinkedHashMap();
        for (int i = 0; i < data.size(); i++) {
            if (map.size() >= 1) {
                if (!map.containsKey(data.get(i))) {
                    map.put(data.get(i), i);
                } else {
                    count.put(i, data.get(i));
                }
            } else {
                map.put(data.get(i), i);
            }
        }
        logger.info(">>>>Duplicate data\t\t" + count.size() + "条");
        long end = System.currentTimeMillis();
        logger.info(">>>>End time\t\t" + end);
        logger.info(">>>>Total time consuming\t\t" + (end - start) + "ms");
        return count;
    }
}
