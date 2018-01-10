package annotation.complex;

/**
 * DemoAnnotationTest:
 * author: Chengjs, version:1.0.0, 2018-01-10
 */
@DemoAnnotation(
    color = "red",
    value = "minipa",
    arrayAttr = {3,4,5,6,7},
    lamp = EnmTrafficLamp.GREEN,
    annotationAttr = @MetaAnnotation("minipa")
  )
public class DemoAnnotationTest {

  @DemoAnnotation("main()上的注解DemoAnnotation")
  public static void main(String[] args) {
    if(DemoAnnotationTest.class.isAnnotationPresent(DemoAnnotation.class)) {

      /*反射方式获得注解对应的实例对象，再通过该对象调用属性对应的方法*/
      DemoAnnotation annotation = (DemoAnnotation) DemoAnnotationTest.class.getAnnotation(DemoAnnotation.class);
      System.out.println("color " + annotation.color());
      System.out.println("value " + annotation.value());
      System.out.println("array length " + annotation.arrayAttr().length);
      System.out.println("lamp " + annotation.lamp());
      System.out.println("annotationAttr.value " + annotation.annotationAttr().value());
      MetaAnnotation ma = annotation.annotationAttr();
      System.out.println("MetaAnnotation.value " + ma.value());

    }
  }

}
