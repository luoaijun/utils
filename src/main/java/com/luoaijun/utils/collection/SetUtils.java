package com.luoaijun.utils.collection;

import java.util.Iterator;
import java.util.Set;

import com.luoaijun.iterface.PrintInter;

/**
 * 
 * TODO set工具类
 * 
 *  罗爱军
 *  2018年3月8日
 *   aijun.luo@outlook.com
 *  Coolibcom.luoaijun.tool.collectionSetTool.java
 *  TODO:
 *
 * TODO :
 */
public class SetUtils implements PrintInter {

	@Override
	public <T> Object print(T t) {
		StringBuilder stringBuilder = null;
		if (t instanceof Set) {
			Set set = (Set) t;
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				stringBuilder.append(iterator.next());
			}
		}
		return stringBuilder;
		// TODO Auto-generated method stub
	}

	@Override
	public <Set> Object println(Set t) {
		return t;
		// TODO Auto-generated method stub

	}

}
