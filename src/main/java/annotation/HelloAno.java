package annotation;

import java.lang.annotation.*;

/**
 * DemoAno:
 *
 * @author: Chengjs, version:1.0.0, 2017-04-26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface HelloAno {
  String say() default "Me";
}
