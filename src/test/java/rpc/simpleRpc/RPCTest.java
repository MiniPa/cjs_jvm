package rpc.simpleRpc;

import rpc.simpleRpc.HelloService;
import rpc.simpleRpc.HelloServiceImpl;
import rpc.simpleRpc.RPCClient;
import rpc.simpleRpc.ServerCenter;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Description: rpcTest
 *
 * rpc 缺陷和劣势 ---- 改善
 * 1.基于json传输数据, 这里代码传输的内容基于的 是string 如何改
 * 2.基于的是BIO socket获取网络流 如何改用Netty或者NIO
 * 3.如何使用开源序列化机制 Avro Protobuf
 * 4.引入Zookeeper注册机
 * Created by Chengjs on 2017/4/21 @version 1.0.
 */
public class RPCTest {
  public static void main(String[] args) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          ServerCenter server = new ServerCenter(8099);
          server.register(HelloService.class, HelloServiceImpl.class);
          server.start();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();

    HelloService service = RPCClient.getRemoteProxyObj(HelloService.class,
        new InetSocketAddress("localhost", 8099));
    System.out.println(service.sayHi("demo_groovy"));
  }
}