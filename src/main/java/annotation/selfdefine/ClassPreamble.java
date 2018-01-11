package annotation.selfdefine;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ClassPreamble: 项目类注释通用注解
 * author: Chengjs, version:1.0.0, 2018-01-10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassPreamble {

  String author();
  String date();
  String version() default "1.0";
  String[] reviewers();

}
