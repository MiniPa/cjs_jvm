package util;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Description: IOUtil
 * Created by Chengjs on 2017/4/21 @version 1.0.
 */
public class IOUtil {

  public static void closeOS(OutputStream stream) {
    if (stream != null) {
      try {
        stream.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void closeIS(InputStream stream) {
    if (stream != null) {
      try {
        stream.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
