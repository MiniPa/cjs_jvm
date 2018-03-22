package annotation.selfdefine;

import java.lang.annotation.Inherited;

/**
 * InheritedAnnotation: @Inherited 注解表示当前注解会被注解类的子类继承
 * author: Chengjs, version:1.0.0, 2018-01-10
 */
@Inherited
public @interface InheritedAnnotation {
  String inher() default "nothing";
}
