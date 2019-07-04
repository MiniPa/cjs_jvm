package annotation.complex;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DemoAnnotation:
 * author: Chengjs, version:1.0.0, 2018-01-10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DemoAnnotation {

  /*注解属性类型不能是复杂的对象*/
  String color() default "blue";

  String value();

  int[] arrayAttr() default {1,2,3};

  EnmTrafficLamp lamp() default EnmTrafficLamp.RED;

  MetaAnnotation annotationAttr() default @MetaAnnotation("xdp");

}
