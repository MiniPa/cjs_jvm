package annotation.hello;

import java.lang.reflect.Method;

/**
 * HelloProcessor:
 *
 * @author: <a href="mailto:chengjs@servyou.com.cn">chengjs</a>
            <a href="https://github.com/MiniPa">minipa_github</a>
 * @version: 1.0.0, 2017-09-22
 *
 * shared by all free coders
 **/
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
