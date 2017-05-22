package annotation;

import java.lang.reflect.Method;

/**
 * ReflectProcessor:
 *
 * @author: Chengjs, version:1.0.0, 2017-04-26
 */
public class HelloProcessor {
  public void parseMethod(final Class<?> clazz) throws Exception {
    final Object obj = clazz.getConstructor(new Class[]{}).newInstance(new Object[]{});
    final Method[] methods = clazz.getDeclaredMethods();
    for (final Method method : methods) {
      final HelloAno helloAno = method.getAnnotation(HelloAno.class);
      if (null != helloAno) {
        method.invoke(obj, helloAno.say());
      }
    }
  }
}
