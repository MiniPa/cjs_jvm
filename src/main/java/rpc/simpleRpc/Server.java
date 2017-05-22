package rpc.simpleRpc;

import java.io.IOException;

/**
 * Description: Server 服务中心代码实现
 * Created by Chengjs on 2017/4/21 @version 1.0.
 */
public interface Server {

  void stop();

  void start() throws IOException;

  boolean isRunning();

  int getPort();

  void register(Class serviceInterface, Class impl);

}
