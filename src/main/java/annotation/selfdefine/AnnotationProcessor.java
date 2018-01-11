package annotation.selfdefine;

/**
 * AnnotationProcessor:
 * author: Chengjs, version:1.0.0, 2018-01-10
 */
public class AnnotationProcessor {

  public static void main(String[] args) {
    ClassPreamble preamble = AnnotationClass.class.getAnnotation(ClassPreamble.class);
    System.out.println("preamble author: " + preamble.author());
    System.out.println("preamble date: " + preamble.date());
    System.out.println("preamble version: " + preamble.version());
    System.out.println("preamble reviewers: " + preamble.reviewers().toString());
  }
}
