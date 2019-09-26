package com.luoaijun.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.*;

/**
 * @dec
 * @Version 1.0
 * @Author aijun.luo
 * @Date 10:12
 */
public class JsonUtils {
    /**
     * TODO
     *
     * @param json
     * @return
     */
    public static LinkedHashMap<String, Object> jsonConvertMap(JSONObject json) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        if (json != null && json.size() > 0) {
            for (String k : json.keySet()) {
                Object v = json.get(k);
                if (v instanceof JSONArray) {
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    Iterator<Object> it = ((JSONArray) v).iterator();
                    while (it.hasNext()) {
                        JSONObject json2 = (JSONObject) it.next();
                        list.add(jsonConvertMap(json2));
                    }
                    map.put(k, list);
                } else if (v instanceof JSONObject) {
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    JSONObject child = (JSONObject) v;
                    for (String stk : child.keySet()) {
                        Object value = child.get(stk);
                        if (value instanceof JSONArray) {
                            Iterator<Object> it = ((JSONArray) value).iterator();
                            while (it.hasNext()) {
                                JSONObject json2 = (JSONObject) it.next();
                                list.add(jsonConvertMap(json2));
                            }
                        } else {
                            map.put(stk, value.toString());
                        }
                    }
                    if (list.size() > 0) {
                        for (int m = 0; m < list.size(); m++) {
                            Map<String, Object> chMap = list.get(m);
                            for (String chKey : chMap.keySet()) {
                                map.put(chKey, chMap.get(chKey).toString());
                            }
                        }
                    }
                } else {
                    map.put(k, v);
                }
            }
        }
        return map;
    }

    /**
     * TODO 将json数据转换为list<map>
     *
     * @param json
     * @return
     */
    public static List<LinkedHashMap> convertJsonArrayMapToList(String json) {
        JSONArray jsonArray = JSON.parseArray(json);
        JSONObject jsonObject;
        List<LinkedHashMap> list = new ArrayList<LinkedHashMap>();
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            list.add(jsonConvertMap(jsonObject));
        }
        return list;
    }

    /**
     * TODO 将json转换为map<map>
     *
     * @param json
     * @return
     */
    public static LinkedHashMap convertJsonObjToListMap(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Map map = JSON.parseObject(json);
        LinkedHashMap<Object, Map> linkedHashMap = new LinkedHashMap();
        for (Object obj : map.keySet()) {
            map.get(obj);
            linkedHashMap.put(obj, (Map) map.get(obj));
        }
        return linkedHashMap;
    }

    /**
     * TODO list to json
     *
     * @param map
     * @return
     */
    public static String getJson(List map) {
        Iterator iterator = map.iterator();
        List list = new ArrayList();
        while (iterator.hasNext()) {
            list.add(JSON.toJSONString(iterator.next()));
        }
        return list.toString();
    }

    /**
     * TODO map to json
     *
     * @param map
     * @return
     */
    public static String getMapToJson(Map map) {
        return JSON.toJSONString(map);
    }

    /**
     * TODO 从mongodb 获取数据并转换为List<Map>
     *
     * @return
     */
    public static List<LinkedHashMap> getMongoData(String name, String collection, String port, int host, String mongoQuery) throws UnknownHostException {

        DBObject o = (DBObject) com.mongodb.util.JSON.parse(mongoQuery);
        DB db = new Mongo(port, host).getDB(name);
        DBCollection coll = db.getCollection(collection);
        DBCursor mongoCursor = coll.find(o);  //slaveOk是说在replica模式下，访问secondary（扶手）需要slaveOk  erator();
        List<Object> objects = new ArrayList<Object>();
        List<Object> list = new ArrayList<Object>();
        List<LinkedHashMap> result = new ArrayList();
        LinkedHashMap map = null;
        int n = 0;
        String key;
        String value;
        while (mongoCursor.hasNext()) {
            map = new LinkedHashMap();
            objects.add(mongoCursor.next());
            list.add(((BasicDBObject) objects.get(n)).values().toArray()[4]);
            for (int i = 0; i < ((ArrayList) list.get(n)).size(); i++) {
                key = ((BasicDBObject) ((ArrayList) list.get(n)).get(i)).get("name").toString();
                value = ((BasicDBObject) ((ArrayList) list.get(n)).get(i)).get("value").toString();
                map.put(key, value);
            }
            result.add(map);
            n++;
        }
        return result;
    }
}
