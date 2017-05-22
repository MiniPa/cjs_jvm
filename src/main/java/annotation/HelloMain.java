package annotation;

/**
 * HelloMain:
 *
 * @author: Chengjs, version:1.0.0, 2017-04-26
 */
@HelloAno(say="Do it")
public class HelloMain {
  public static void main(String[] args) {
//    导出运行时候生成的动态带来对象到文件
//    在代码中加入System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
//    在运行时加入jvm 参数 -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true
    HelloAno anno = HelloMain.class.getAnnotation(HelloAno.class);
    System.out.println(anno.say());
  }
}
