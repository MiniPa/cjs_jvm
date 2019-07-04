package thread;

/**
 * SyncThread: 同步类属性
 *
 * @version 0.0.1  @date: 2019-06-29
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>,
 */
public class SyncThread extends Thread{

    /**
     * 类属性
     */
    private static String sync = "";

    private String methodType = "";

    private static void method(String s) {
        synchronized (sync) {
            sync = s;
            System.out.println(s);
            while (true) {}
        }
    }

    public void mehtod1() {
        System.out.println("method1() run");
        method("method1");
    }

    public static void staticMethod1() {
        System.out.println("staticMethod1() run");
        method("staticMethod1");
    }

    public SyncThread(String methodType) {
        this.methodType = methodType;
    }

    @Override
    public void run() {
        if (methodType.equals("static")) {
            staticMethod1();
        } else if (methodType.equals("nonstatic")) {
            mehtod1();
        }
    }

    public static void main(String[] args) {
        SyncThread nostatic = new SyncThread("nonstatic");
        SyncThread istatic = new SyncThread("static");
        istatic.start();
        nostatic.start();
    }
}
