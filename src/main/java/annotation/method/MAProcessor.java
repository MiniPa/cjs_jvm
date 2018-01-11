package annotation.method;

import java.lang.reflect.Method;

/**
 * MAProcessor:
 * author: Chengjs, version:1.0.0, 2018-01-10
 */
public class MAProcessor {

  public static void main(String[] args) {
    for (Method method : MADemo.class.getMethods()) {
      MethodAnnotation methodAnnotation = method.getAnnotation(MethodAnnotation.class);
      if (methodAnnotation != null) {
        System.out.println("author:" + methodAnnotation.author());
        System.out.println("date:" + methodAnnotation.date());
      }
    }
  }

}
