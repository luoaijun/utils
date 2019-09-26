package com.luoaijun.utils.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.luoaijun.iterface.PrintInter;

/**
  * 
  * TODO map 工具类
  * @author 罗爱军
  * @date 2018年3月8日
  * @email aijun.luo@outlook.com
  * @package Coolibcom.luoaijun.tool.collectionMapTool.java
  * @describe TODO:
  * @include :
  * @category :
  */
public class MapTool implements PrintInter {

	/**
	 * 
	 * @category 迭代遍历获取map中的字符串
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> Object print(T t) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		if (t instanceof Map) {
			HashMap map = (HashMap) t;
			Collection collection = map.keySet();
			Iterator<HashMap> iterator = collection.iterator();

			while (iterator.hasNext()) {
				builder.append(iterator.next());
			}
		}
		return builder.toString();
	}

	public <T> Object println(T t) {
		return null;
	}
}
