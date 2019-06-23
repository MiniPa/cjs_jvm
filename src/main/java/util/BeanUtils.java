package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * BeanUtils: bean 之间的属性的复制, 同属性名复制
 *
 * @version 0.0.1  @date: 2018-09-25
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>,
 */
public class BeanUtils {

	private static final ConcurrentHashMap<Class<?>, List<Method>> CACHE_CLASS_GET_METHOD = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<Class<?>, List<Method>> CACHE_CLASS_SET_METHOD = new ConcurrentHashMap<>();

	private BeanUtils() {
	}

	/**
	 * 将src java bean的属性拷贝到 tar java bean的属性中，默认不拷贝src对象中的空对象的值.
	 *
	 * @param src 源对象
	 * @param tar 目标对象
	 */
	public static void copyProperties(Object src, Object tar) {
		copyProperties(src, tar, true);
	}

	/**
	 * 将src java bean的属性拷贝到 tar java bean的属性中
	 *
	 * @param src       源对象
	 * @param tar       目标对象
	 * @param skipEmpty 如果src中属性的值时null,是否跳过这个拷贝， true:跳过 false:不跳过
	 */
	public static void copyProperties(Object src, Object tar, boolean skipEmpty) {
		List<Method> getterMethods = getGetMethod(src.getClass());
		List<Method> setterMethods = getSetMethod(tar.getClass());
		try {
			for (Method getMethod : getterMethods) {
				Object value = getMethod.invoke(src);
				if (skipEmpty && value == null) {
					continue;
				}
				Method method = setterMethods.stream().filter(m -> Objects.equals(m.getName(), getInvokedSetterMethodName(getMethod.getName()))).findFirst().orElse(null);
				if (null != method) {
					method.invoke(tar, value);
				}
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("bean复制属性过程中产生异常", e);
		}
	}

	/**
	 * 根据getter的方法名拿到setter的方法名
	 *
	 * @param getterMethodName getter的方法名
	 * @return setter的方法名
	 */
	public static String getInvokedSetterMethodName(String getterMethodName) {
		if (getterMethodName.startsWith("get")) {
			return "set" + getterMethodName.substring(3);
		} else {
			return "set" + getterMethodName.substring(2);
		}
	}

	/**
	 * 获取clazz中的所有public getter 方法
	 *
	 * @param clazz 需要获取getter方法的类
	 * @return public getter方法
	 */
	public static List<Method> getGetMethod(Class<?> clazz) {
		Class<?> lockClazz = clazz;
		if (!CACHE_CLASS_GET_METHOD.containsKey(clazz)) {
			synchronized (lockClazz) {
				if (!CACHE_CLASS_GET_METHOD.containsKey(clazz)) {
					CACHE_CLASS_GET_METHOD.put(clazz, Arrays.stream(getMethods(clazz)).filter(BeanUtils::isGetterMethod).filter(m -> !Objects.equals(m.getName(), "getClass")).collect(Collectors.toList()));
				}
			}
		}
		return CACHE_CLASS_GET_METHOD.get(clazz);
	}

	/**
	 * 获取clazz中的所有public setter 方法
	 *
	 * @param clazz 需要获取setter方法的类
	 * @return public setter方法
	 */
	public static List<Method> getSetMethod(Class<?> clazz) {
		Class<?> lockClazz = clazz;
		if (!CACHE_CLASS_SET_METHOD.containsKey(clazz)) {
			synchronized (lockClazz) {
				if (!CACHE_CLASS_SET_METHOD.containsKey(clazz)) {
					CACHE_CLASS_SET_METHOD.put(clazz, Arrays.stream(getMethods(clazz)).filter(BeanUtils::isSetterMethod).collect(Collectors.toList()));
				}
			}
		}
		return CACHE_CLASS_SET_METHOD.get(clazz);
	}

	/**
	 * 获取一个类中中的所有的方法
	 *
	 * @param clazz
	 * @return
	 */
	public static Method[] getMethods(Class<?> clazz) {
		return clazz.getMethods();
	}

	/**
	 * 判断一个方式是否是getter方法
	 *
	 * @param method 需要判断的方法
	 * @return true:是getter方法 false：不是getter方法
	 */
	public static boolean isGetterMethod(Method method) {
		if (method.getName().startsWith("get") && method.getParameterCount() == 0 && method.getReturnType() != Void.class) {
			return true;
		} else if (method.getName().startsWith("is") && method.getParameterCount() == 0 && (method.getReturnType().equals(boolean.class) || method.getReturnType().equals(Boolean.class))) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是setter方法
	 *
	 * @param method 需要判断的方法
	 * @return true是setter方法 false不是setter方法
	 */
	public static boolean isSetterMethod(Method method) {
		boolean isSetter = false;
		if (method.getName().startsWith("set") && method.getParameterCount() == 1 && method.getReturnType() != Void.class) {
			isSetter = true;
		}
		return isSetter;
	}

}
