package com.luoaijun.utils.collection;


import com.luoaijun.utils.JsonUtils;
import com.luoaijun.utils.Utils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @dec
 * @Version 1.0
 * @Author aijun.luo
 * @Date 11:22
 */
public class MapDataUtils {
    public static Logger logger = Logger.getLogger(MapDataUtils.class);

    public static String mapCovertToJSON(List data) {
        Map result = new HashMap();
        String ValidData = JsonUtils.getJson(data);
        ValidData = JsonUtils.getMapToJson(result);
        return ValidData;
    }

    public static LinkedHashMap getMapFromMapByKeys(LinkedHashMap map, String key) {
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            ((LinkedHashMap) iterator.next()).containsKey(key);
        }
        return map;
    }

    /**
     * TODO 通過tagValue從list中獲取keyValue
     *
     * @param data
     * @param key
     * @param tagValue
     * @return
     */
    public static String getKeyValueByTagValueFromList(List<LinkedHashMap> data, String key, String tagValue) {
        Iterator iterator = data.iterator();
        while (iterator.hasNext()) {
            LinkedHashMap next = (LinkedHashMap) iterator.next();
            if (next.containsValue(tagValue)) return (String) next.get(key);
        }
        return null;
    }

    /**
     * TODO 通过标记字段的值从数组型的json串获取数据
     *
     * @param json 待解析的json串
     * @param name 标记
     * @param key  待提取的字段
     * @return
     */
    public static Map getDataFromArraysByKey(List json, String name, String key) {
        List list = json;
        Iterator iterator = list.iterator();
        Map map = new LinkedHashMap();
        Map result = new LinkedHashMap();
        while (iterator.hasNext()) {
            map = (Map) iterator.next();
            result.put(map.get(name), getDataByKey(map, key));
        }
        return result;
    }

    /**
     * TODO 通过标记字段的值从对象型的json串获取数据
     *
     * @param json
     * @param key
     * @return
     */
    public static Map getDataFromObjectsByKey(Map json, String key) {
        Map list = json;
        Map result = new LinkedHashMap();
        for (int i = 0; i < list.size(); i++) {
            result.put(list.keySet().toArray()[i], getDataByKey((Map) list.values().toArray()[i], key));
        }
        return result;
    }

    /**
     * TODO 通过标记字段的值从数组型的json串获取数据
     *
     * @param json
     * @param key
     * @return
     */
    public static Map getDataFromArrayByKey(String json, String key, String tag) {
        List<LinkedHashMap> list = new ArrayList();
        list = JsonUtils.convertJsonArrayMapToList(json);
        Map map = new LinkedHashMap();
        for (int i = 0; i < list.size(); i++) {
            String tagValue = getDataByKey((Map) (list.toArray()[i]), tag);
            String keyValue = getDataByKey((Map) (list.toArray()[i]), key);
            map.put(keyValue, tagValue);
        }
        return map;
    }

    /**
     * TODO 通过标记字段的值从数组型的json串获取数据
     *
     * @param json
     * @param key
     * @return
     */
    public static String getDataStringFromArrayByKey(String json, String key) {
        List<LinkedHashMap> list = new ArrayList();
        list = JsonUtils.convertJsonArrayMapToList(json);
        return (String) ((Map) (list.toArray()[0])).get(key);
    }

    /**
     * TODO 通过标记字段的值从数组型的json串获取数据
     *
     * @param data
     * @param key
     * @return
     */
    public static List getDataStringFromArraysByKey(List data, String key) {
        List list = data;
        List result = new ArrayList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            result.add(getDataByKey((Map) iterator.next(), key));
        }
        return result;
    }

    /**
     * TODO 通过标记字段的值从对象型的json串获取数据
     *
     * @param json
     * @param key
     * @return
     */
    public static String getDataFromObjectByKey(String json, String key) {
        Map<Object, LinkedHashMap> map = new LinkedHashMap();
        map = (Map) JsonUtils.convertJsonObjToListMap(json);
        String result = getDataByKey((Map) ((LinkedHashMap) map).values().toArray()[0], key);
        return result;
    }

    /**
     * TODO 通过字段属性判断字段，并获取字段
     *
     * @param struct
     * @param key
     * @return
     */
    public static String getDataByKey(Map struct, String key) {
        return (String) struct.get(key);
    }

    /**
     * TODO 合併兩個map數據
     *
     * @param map1
     * @param map2
     * @return
     */
    public static Map merge(Map map1, Map map2) {
        Map result = new LinkedHashMap();
        result.putAll(map1);
        Iterator<Map.Entry<String, String>> entryIterator = map2.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, String> next = entryIterator.next();
            if (!result.containsKey(next.getKey()) || result.get(next.getKey()).equals("")) {
                result.put(next.getKey(), next.getValue());
            }
        }
        return result;
    }

    /**
     * TODO 通过验证指定标记的值来获取需求字段
     *
     * @param struct   数据
     * @param key      关键字
     * @param tag      辨别标记
     * @param tagValue 辨别标记的值
     * @return
     */
    public static String getKeyByTagValue(Map struct, String key, String tag, String tagValue) {
        String keyValue = null;
        if (struct.containsKey(tag)) {
            if (struct.get(tag).equals(tagValue)) {
                keyValue = (String) struct.get(key);
            }
        }
        return keyValue;
    }

    /**
     * TODO 通过标记字段的值从数组型json串中获取指定字段的数据
     *
     * @param json
     * @param key
     * @param tag
     * @param tagValue
     * @return
     */
    public static Map getKeyByTagValueFromArray(List<LinkedHashMap> json, String key, String tag, String tagValue) {
        String data = null;
        List list = json;
        Iterator iterator = list.iterator();
        Map result = new LinkedHashMap();
        Map da = new LinkedHashMap();
        int i = 0;
        while (iterator.hasNext()) {
            da = (Map) iterator.next();
            data = getKeyByTagValue(da, key, tag, tagValue);
            result.put(getDataByKey(da, "N"), data);
            i++;
        }
        return result;
    }

    /**
     * TODO 通过标记字段的值从对象型json串中获取指定字段的数据
     *
     * @param json
     * @param key
     * @param tag
     * @param tagValue
     * @return
     */
    public static Map getKeyByTagValueFromObject(String json, String key, String tag, String tagValue) {
        Map[] map = new LinkedHashMap[3];
        map[0] = new LinkedHashMap();//存放json 转Map<Map>的值
        map[1] = new LinkedHashMap();//存放Map<map1>中的map1
        map[2] = new LinkedHashMap();//存放返回结果
        map[0] = JsonUtils.convertJsonObjToListMap(json);
        for (int i = 0; i < map[0].size(); i++) {
            if (((Map) (map[0].values().toArray()[i])).containsKey(tag)) {
                if (((Map) (map[0].values().toArray()[i])).get(tag).equals(tagValue))
                    map[2].put((map[0]).keySet().toArray()[i], ((Map) (map[0].values().toArray()[i])).get(key));
            }
        }
        return map[2];
    }

    /**
     * TODO 通过标记字段的值从对象型json串中获取指定字段的数据
     *
     * @param json
     * @param key
     * @param tag
     * @param tagValue
     * @return
     */
    public static String getKeyStringByTagValueFromObject(String json, String key, String tag, String tagValue) {
        Map[] map = new LinkedHashMap[2];
        map[0] = new LinkedHashMap();//存放json 转Map<Map>的值
        map[1] = new LinkedHashMap();//存放Map<map1>中的map1
        map[0] = JsonUtils.convertJsonObjToListMap(json);
        String result = "";
        for (int i = 0; i < map[0].size(); i++) {
            if (((Map) (map[0].values().toArray()[i])).containsKey(tag)) {
                if (((Map) (map[0].values().toArray()[i])).get(tag).equals(tagValue))
                    result = (String) ((Map) (map[0].values().toArray()[i])).get(key);
            }
        }
        return result;
    }

    /**
     * TODO 去除map中值为keyValue的键值对
     *
     * @param map
     * @param keyValue
     * @return
     */
    public static Map removeDataByKeyValue(Map map, Object keyValue) {
        List tag = new ArrayList();
        for (int i = 0; i < map.size(); i++) {
            if (!Utils.isEmpty(map.values().toArray()[i]) && map.values().toArray()[i].equals(keyValue)) {
                tag.add(map.keySet().toArray()[i]);
            }
            if (Utils.isEmpty(keyValue) && Utils.isEmpty(map.values().toArray()[i])) {
                tag.add(map.keySet().toArray()[i]);
            }

        }
        Iterator iterator = tag.iterator();
        while (iterator.hasNext()) {
            map.remove(iterator.next());
        }
        return map;
    }

    /**
     * TODO 如果在list中有且只有一条该数据则返回一个true
     *
     * @param data
     * @param list
     * @return
     */
    public static Boolean exist(String data, String key, List<LinkedHashMap> list) {
        Iterator iterator = list.iterator();
        Map map = new LinkedHashMap();
        int index = 0;
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            map = (Map) iterator.next();
            if (map.containsKey(key)) {
                String o = map.get(key).toString();
                boolean equals = o.equals(data);
                if (equals) {
                    index = index + 1;
                }
            }
        }
        if (index == 1) return true;
        else if (index > 1)
            logger.error("There are multiple records in the table，Number of recorded values:\t" + index);
        else logger.error("The record does not exist in the table，Number of recorded values:\t" + index);
        return false;
    }

    /**
     * TODO 根据tag和tag的值修改key的数据
     *
     * @param data
     * @param key
     * @param keyValue
     * @param tag
     * @param tagValue
     */
    public static void updateListByTagValue(List data, String key, Object keyValue, String tag, String tagValue) {
        Iterator iterator = data.iterator();
        LinkedHashMap map = new LinkedHashMap();
        boolean flag = false;
        while (iterator.hasNext()) {
            map = (LinkedHashMap) iterator.next();
            if (map.get(tag).equals(tagValue)) {
                map.put(key, keyValue);
                flag = true;
            }
            if (flag)
                return;
        }
    }

    /**
     * TODO 获取data2中存在但是data1中不存在的数据
     *
     * @param data1
     * @param data2
     * @return
     */
    public static List<String> getUnJoinData(List<String> data1, List<String> data2) {
        List<String> result = new ArrayList();
        result.addAll(data2);
        String temp;
        Iterator iterator2 = data2.iterator();
        while (iterator2.hasNext()) {
            temp = (String) iterator2.next();
            if (data1.contains(temp)) {
                result.remove(temp);
            }
        }
        return result;
    }

    /**
     * TODO 判断Map中有且只有一个相同的value
     *
     * @param map
     * @param value
     * @return
     */
    public static Boolean valueCheck(Map map, String value) {
        int index = 0;
        String equal;
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            equal = next.getValue();
            if (equal.equals(value)) {
                index = index + 1;
            }
        }
        if (index == 1) {
            return true;
        }
        return false;
    }

    /**
     * TODO 根据key和keyValue从list中获取对应的map数据
     *
     * @param list
     * @param key
     * @param keyValue
     * @return
     */
    public static Map getMapFromListByKey(List<LinkedHashMap> list, String key, String keyValue) {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Map result = (Map) iterator.next();
            if (result.containsKey(key) && result.get(key).equals(keyValue)) {
                return result;
            }
        }
        return null;
    }

    /**
     * TODO 根据key获取map中value的list数据集
     *
     * @param list
     * @param key
     * @return
     */
    public static List mapToList(List<LinkedHashMap> list, String key) {
        List lists = new ArrayList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Map map = (Map) iterator.next();
            lists.add(map.get(key));
        }
        return lists;
    }

    /**
     * TODO list数据转换成带分隔符的字符串
     *
     * @param list
     * @param delimit
     * @return
     */
    public static String listToStringByDelimit(List list, String delimit) {
        Iterator iterator = list.iterator();
        String result = "";
        while (iterator.hasNext()) {
            result = result + iterator.next().toString() + delimit;
        }
        return result.substring(0, result.length() - 1);
    }

    /**
     * TODO list数据转换成带分隔符的字符串
     *
     * @param list
     * @param delimit
     * @return
     */
    public static String listToStringMD5ByDelimit(List list, String delimit) {
        Iterator iterator = list.iterator();
        String result = "";
        while (iterator.hasNext()) {
            result = result + Utils.string2MD5(iterator.next().toString()) + delimit;
        }
        return result.substring(0, result.length() - 1);
    }


    /**
     * TODO 如果map1中含有与map2相同的key，则两个map中该key的value值做为新的键值对加入map1中
     *
     * @param map1
     * @param map2
     * @return
     */
    public static Map MapMerge(Map map1, Map map2) {
        Map temp = new LinkedHashMap();
        temp.putAll(map1);
        Iterator<Map.Entry> iterator = temp.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry next = iterator.next();
            Object key = next.getKey();
            if (map2.containsKey(key)) {
                map1.put(map2.get(key), map1.remove(key));
            }
        }
        return map1;
    }

    /**
     * @param data
     * @param keyValue
     * @return
     */
    public static boolean removeDataFromListByKeyValue(List data, Map keyValue) {
        Iterator set = keyValue.keySet().iterator();
        keyValue.keySet();

        boolean flag = true;
        while (set.hasNext()) {
            flag = data.remove(set.next());
            if (!flag) return false;
        }
        return flag;
    }

    /**
     * TODO 根据keyList的key值移除list<Map>中map的字段
     *
     * @param data
     * @param key
     */
    public static void removeDataFromListByKeyList(List<Map> data, List key) {
        Iterator<Map> iterator = data.iterator();
        Iterator keyIterator = key.iterator();
        while (iterator.hasNext()) {
            Map next = iterator.next();
            while (keyIterator.hasNext()) {
                next.remove(keyIterator.next());
            }
        }
    }

    /**
     * TODO 根据value获取key
     *
     * @param data
     * @param value
     * @return
     */
    public static Object getKeyByValue(Map data, Object value) {
        Iterator<Map.Entry<String, String>> iterator = data.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            if (next.getValue().equals(value)) return next.getKey();
        }
        return "";
    }


    /**
     * TODO map 反转
     *
     * @param map
     * @return
     */
    public static Map reversal(Map map) {
        Iterator<Map.Entry> iterator = map.entrySet().iterator();
        Map result = new LinkedHashMap();

        while (iterator.hasNext()) {
            Map.Entry next = iterator.next();
            result.put(next.getValue(), next.getKey());
        }
        return result;
    }

}