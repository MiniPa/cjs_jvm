package annotation;

/**
 * HelloAnoTest:
 *
 * @author: Chengjs, version:1.0.0, 2017-04-26
 */
public class HelloAnoTest {
  @HelloAno
  public void sayHello(final String name) {
    System.out.println("===>> Hi, " + name + "[sayHello]");
  }

  @HelloAno(say = "Michael")
  public void sayHello2Boy(final String name) {
    System.out.println("===>> Hi, " + name + "[sayHello]");
  }

  public static void main(final String[] args) throws Exception {
    final HelloProcessor hp = new HelloProcessor();
    hp.parseMethod(HelloAnoTest.class);
  }
}
