package com.luoaijun.iterface;

import org.apache.log4j.Logger;

public interface PrintInter {
	Logger logger =  Logger.getLogger(PrintInter.class);
	public abstract <T> Object print(T t);

	public abstract <T> Object println(T t);

}
