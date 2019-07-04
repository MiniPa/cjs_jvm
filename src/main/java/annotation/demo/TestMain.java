package annotation.demo;

/**
 * TestMain:
 *
 * @version 0.0.1  @date: 2019-07-04
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>,
 */
@DemoAnnotation(say = "Do it!")
public class TestMain {
    public static void main(String[] args) {
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        DemoAnnotation annotation = TestMain.class.getAnnotation(DemoAnnotation.class);
        System.out.println(annotation.say());
    }
}
