package com.luoaijun.utils.array;

import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;


public class arrayUtils {
    public static Logger logger = Logger.getLogger(arrayUtils.class);

    /**
     * TODO 数组清零操作
     */
    public static int[] clear(int[] temp) {
        int count = 0;
        int[] index = new int[temp.length];
        int[] NoTemp = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != 0) {
                index[count++] = i;// 获取temp中值不为0的数据的下标

            }
        }
        int[] arrayName = new int[count];// 初始化一个大小为count的数组
        int i = 0;
        if (count != 0) {
            try {
                for (i = 0; i < arrayName.length; i++) {
                    arrayName[i] = temp[index[i]];
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // TODO Auto-generated catch block
                System.out.println("index 溢出:第" + i + "个位置");
                e.printStackTrace();
            }
            return arrayName;
        } else {
            return NoTemp;
        }

    }

    /**
     * TODO 数组清零操作
     */
    public static String[] clearString(String[] temp) {

        int count = 0;
        int[] index = new int[temp.length];
        String[] NoTemp = new String[temp.length];

        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null) {
                index[count++] = i;// 获取temp中值不为0的数据的下标
            }
        }
        String[] arrayName = new String[count];
        int i = 0;
        if (count != 0) {

            try {
                for (i = 0; i < arrayName.length; i++) {
                    arrayName[i] = temp[index[i]];
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // TODO Auto-generated catch block
                System.out.println("String 溢出:第" + i + "个位置");
                e.printStackTrace();
            }
            return arrayName;
        } else {
            return NoTemp;
        }
    }

    /**
     * @param number
     * TODO 延时函数 毫秒
     */
    public static void delayWith(int number, final String str) {

        try {
            Thread.currentThread().sleep(number);
            System.out.print(str);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param number
     * TODO 延时函数 秒
     */
    public static void delay_s(int number) {
        try {
            for (int i = 0; i < number; i++) {
                Thread.currentThread().sleep(1000);
                System.out.print(".");
            }
            System.out.println();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param number
     * TODO 延时函数 毫秒
     */
    public static void delay_ms(int number) {
        try {
            Thread.currentThread().sleep(number);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
