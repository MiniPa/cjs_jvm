package rpc.simpleRpc;

import rpc.simpleRpc.HelloService;

/**
 * Description: HelloServiceImpl
 * Created by Chengjs on 2017/4/21 @version 1.0.
 */
public class HelloServiceImpl implements HelloService {
  @Override
  public String sayHi(String name) {
    return "Hi" + name;
  }
}
