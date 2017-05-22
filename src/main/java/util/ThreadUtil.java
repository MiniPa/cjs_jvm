package util;

import java.io.IOException;
import java.net.Socket;

/**
 * Description: ThreadUtil
 * Created by Chengjs on 2017/4/21 @version 1.0.
 */
public class ThreadUtil {

  public static void closeSocket(Socket socket) {
    if (socket != null) {
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }



}
