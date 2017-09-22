package enums;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;

/**
 * EnumCollection: EnumSet EnumMap 测试
 *
 * @author: Chengjs, version:1.0.0, 2017/4/24
 */
public class EnumCollection {

  private enum WeekEnum {
    MON,TUS,THUS,FRI;
  }

  public static void main(String[] args) {
    EnumSet<WeekEnum> weekSet = EnumSet.allOf(WeekEnum.class);
    for (WeekEnum weekEnum : weekSet) {
      System.out.println(weekEnum);
    }

    EnumMap<WeekEnum, String> weekMap = new EnumMap<WeekEnum, String>(WeekEnum.class);
    weekMap.put(WeekEnum.FRI, "星期五");
    weekMap.put(WeekEnum.MON, "星期一");
    for(Iterator<Map.Entry<WeekEnum,String>> iter = weekMap.entrySet().iterator(); iter.hasNext();) {
      Map.Entry<WeekEnum,String> entry = iter.next();
      System.out.println(entry.getKey().name() + ":" + entry.getValue());
    }
    System.out.println("+++++++++++++++++++");
    for (Map.Entry<WeekEnum, String> entry : weekMap.entrySet()) {
      System.out.println(entry.getKey().name() + ":" + entry.getValue());
    }
  }
}
