package enum_ja;

/**
 * EnumTest: Enum 测试文档
 *
 * @author: Chengjs, version:1.0.0, 2017/4/24
 */
class EnumTest {

  /**
   * 普通枚举
   *
   */
  public enum ColorEnum {
    red, green, yellow, blue;
  }

  /**
   * enum 和普通类类一样能添加 静态非静态方法
   */
  private enum SeasonEnum {
    SPRING, SUMMER, AUTUMN, WINTER;
    private final static String position = "demo_groovy";

    public static SeasonEnum getSeason() {
      if ("demo_groovy".equals(position)) {
        return SPRING;
      } else {
        return WINTER;
      }
    }
  }

  /**
   * 性别
   *
   * 带有constructor的构造器
   */
  private enum GenderEnum {


    MAN("MAN"), WOMEN("WOMEN");
    private final String value;

    GenderEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  /**
   * 订单状态
   *
   * 实现带有抽象方法的枚举
   *
   */
  public enum OrderStateEnum {
    /** 已取消 */
    CANCEL {public String getName(){return "已取消";}},
    /** 待审核 */
    WAITCONFIRM {public String getName(){return "待审核";}},
    /** 等待付款 */
    WAITPAYMENT {public String getName(){return "等待付款";}},
    /** 正在配货 */
    ADMEASUREPRODUCT {public String getName(){return "正在配货";}},
    /** 等待发货 */
    WAITDELIVER {public String getName(){return "等待发货";}},
    /** 已发货 */
    DELIVERED {public String getName(){return "已发货";}},
    /** 已收货 */
    RECEIVED {public String getName(){return "已收货";}};

    public abstract String getName();
  }


  public static void main(String[] args) {
    //枚举是一种类型，用于定义变量，以限制变量的赋值；赋值时通过“枚举名.值”取得枚举中的值
    ColorEnum colorEnum = ColorEnum.blue;
    switch (colorEnum) {
      case red:
        System.out.println("color is red");
        break;
      case green:
        System.out.println("color is green");
        break;
      case yellow:
        System.out.println("color is yellow");
        break;
      case blue:
        System.out.println("color is blue");
        break;
    }

    //遍历枚举
    System.out.println("遍历ColorEnum枚举中的值");
    for(ColorEnum color : ColorEnum.values()){
      System.out.println(color);
    }

    //获取枚举的个数
    System.out.println("ColorEnum枚举中的值有"+ColorEnum.values().length+"个");

    //获取枚举的索引位置，默认从0开始
    System.out.println(ColorEnum.red.ordinal());//0
    System.out.println(ColorEnum.green.ordinal());//1
    System.out.println(ColorEnum.yellow.ordinal());//2
    System.out.println(ColorEnum.blue.ordinal());//3

    //枚举默认实现了java.lang.Comparable接口
    System.out.println(ColorEnum.red.compareTo(ColorEnum.green));//-1

    System.out.println("===========");
    System.err.println("季节为" + SeasonEnum.getSeason());

    System.out.println("===========");
    for(GenderEnum gender : GenderEnum.values()){
      System.out.println(gender.value);
    }

    System.out.println("===========");
    for(OrderStateEnum order : OrderStateEnum.values()){
      System.out.println(order.getName());
    }
  }
}