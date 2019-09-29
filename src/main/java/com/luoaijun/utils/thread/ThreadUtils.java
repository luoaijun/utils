package com.luoaijun.utils.thread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.luoaijun.utils.reflect.ReflectUtils;

/**
 * TODO
 * <p>
 * 罗爱军
 * 2018年3月8日
 * aijun.luo@outlook.com
 * Coolibcom.luoaijun.tool.threadThreadTool.java
 * TODO:
 * <p>
 * TODO :线程工具类
 */
public class ThreadUtils {
    /**
     * TODO 单线程池 -根据传入的对象，创建一个线程，该线程内执行对象中的给定方法
     *
     * @param t         ：获取的类对象
     * @param methodStr ：需要运行的方法名
     * @param para      ：参数类型 object ，传参
     * @return return：返回一个Thread
     * @throws Exception
     * @throws Throwable
     */
    public static <T, E> Thread getAThread(final T t, final String methodStr, final Object... para)
            throws Exception, Throwable {
        @SuppressWarnings("unchecked") final Class<T> classT = (Class<T>) t.getClass();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (para.length != 0) {
                    final Method method;
                    try {
                        method = classT.getDeclaredMethod(methodStr,
                                ReflectUtils.getClassType(classT, methodStr));
                        method.setAccessible(true);
                        // TODO Auto-generated method stub
                        method.invoke(t, para);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        final Method method = classT.getDeclaredMethod(methodStr);
                        // TODO Auto-generated method stub
                        method.setAccessible(true);
                        method.invoke(t);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return thread;
    }

    /**
     * TODO 多线程池
     *
     * @param t         获取的类对象
     * @param methodStr 需要运行的方法名
     * @param index     线程数量
     * @param para
     * @param <T>
     * @return return
     * @throws Exception
     * @throws Throwable
     */
    public static <T> Thread[] getIndexThread(final T t, final String methodStr, int index, final Object... para)
            throws Exception, Throwable {
        Thread[] threads = new Thread[index];
        for (int i = 0; i < index; i++) {
            threads[i] = getAThread(t, methodStr, para);
        }
        return threads;
    }

    /**
     * @param t
     * @param methodStr
     * @param index
     * @param para
     * @return return ExecutorService
     * @throws Exception
     * @throws Throwable TODO 创建一个线程池
     */
    public static <T> ExecutorService getThreadPool(final T t, final String methodStr, int index, final Object... para)
            throws Exception, Throwable {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < index; i++) {
            threadPool.submit(getAThread(t, methodStr, para));
        }
        return threadPool;
    }

    /**
     * @param thread TODO 销毁线程
     */
    @SuppressWarnings("deprecation")
    public static void killThreads(Thread[] thread) {
        for (int i = 0; i < thread.length; i++) {
            if (thread[i].isAlive()) {
                thread[i].destroy();
            }
        }

    }

    public static void getConstructor(Class Aclass, Object... objects) {

    }

    /**
     * @param threads TODO 启动所有的线程
     */
    public void startAll(Thread... threads) {
        // TODO Auto-generated method stub
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }

}
