package com.luoaijun.utils.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.PrimitiveIterator.OfDouble;

/**
 * 
 * TODO 反射工具类
 * 
 * @author 罗爱军
 * @date 2018年3月8日
 * @email aijun.luo@outlook.com
 * @package Coolibcom.luoaijun.tool.reflectReflectTool.java
 * @describe TODO:
 * @include :
 * @category :反射工具类
 */
public class ReflectTool {
	/**
	 * @category 获取一个反射
	 * @param t
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Class getARect(T t) {
		Class<T> className = (Class<T>) t.getClass();
		return className;
	}

	/**
	 * @category 获取指定方法的参数类型
	 * @param tClass
	 * @param methodName
	 * @return
	 */
	public static <T> Class<?>[] getClassType(Class tClass, String methodName) {
		// Type[] types = tClass.getTypeParameters();
		Class<?>[] classes = getMethod(tClass, methodName).getParameterTypes();
		return classes;
	}

	/**
	 * @category 获取指定方法的参数个数
	 * @param tClass
	 * @param methodName
	 * @return
	 */
	public static int getTypeNumber(Class tClass, String methodName) {
		Class<?>[] types = getMethod(tClass, methodName).getParameterTypes();
		return types.length;
	}

	/**
	 * @category 通过反射获取指定方法
	 * @param t
	 * @param methodName
	 * @return
	 */
	public static Method getMethod(Class t, String methodName) {
		Method[] method = t.getDeclaredMethods();
		for (Method method2 : method) {
			if (methodName.equals(method2.getName())) {
				return method2;
			}
		}
		return null;
	}

	/**
	 * @category 给指定属性设置指定值
	 * @param t
	 * @param fieldName
	 * @param values
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> Object setField(Class t, String fieldName, T values)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = t.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(t, values);
		return values;

	}

	/**
	 * @category 返回一个method.invoke()
	 * @param t
	 * @param method
	 * @param args
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object getInvoke(Class t, Method method, Object... args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		method.setAccessible(true);
		return method.invoke(t, args);
	}

	/**
	 * TODO 获取一个类的构造器
	 * 
	 * @param t
	 * @param parameterTypes
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Object getConstructor(Class t, T... parameterTypes)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Constructor constructor = t.getDeclaredConstructor((Class[]) parameterTypes);
		constructor.setAccessible(true);
		Object object = constructor.newInstance(parameterTypes);

		return object;
	}

	/**
	 * TODO 强制爆破 任意类型的数据
	 * 
	 * @param t
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T setAcessForces(Class<?> t, T obj) {
		if (obj instanceof Method) {
			Method method = ((Method) obj);
			method.setAccessible(true);
			return (T) method;
		} else if (obj instanceof Field) {
			Field field = (Field) obj;
			field.setAccessible(true);
			return (T) field;
		} else if (obj instanceof Constructor) {
			Constructor<T> constructor = (Constructor<T>) obj;
			constructor.setAccessible(true);
			return (T) constructor;
		}
		return obj;
	}

	/**
	 * TODO 通过反射, 获得定义 Class 时声明的父类的泛型参数的类型 如: public EmployeeDao extends
	 * BaseDao<Employee, String>
	 * 
	 * @param clazz
	 * @param index
	 * @return
	 * @Create By Teacher
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			return Object.class;
		}

		if (!(params[index] instanceof Class)) {
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * TODO 获取t的父类的参数泛型类型
	 * 
	 * @param t
	 * @return
	 * @Create By Teacher
	 */
	public static <T> Class<T> getSuperType(Class t) {
		return getSuperClassGenricType(t, 0);
	}

}
