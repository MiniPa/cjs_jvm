package lambda;

/**
 * Hello: lambda 基于词法作用域
 *
 * @version 0.0.1  @date: 2019-07-04
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>,
 */
public class Hello {
    Runnable r1 = () -> { System.out.println(this); };
    Runnable r2 = () -> {
        System.out.println(toString());
    };

    @Override
    public String toString() {
        return "Hello Lambda";
    }

    public static void main(String[] args) {
        new Hello().r1.run();
        new Hello().r2.run();
    }

}
