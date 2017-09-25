package rpc.simpleRpc;

import util.IOUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Description: RPCClient client远程代理对象
 * <p>
 * Created by Chengjs on 2017/4/21 @version 1.0.
 */
public class RPCClient<T> {

  public static <T> T getRemoteProxyObj(final Class<?> serviceInterface, final InetSocketAddress addr) {

    /*1.将本地接口调用转成jdk动态代理,在动态代理中实现接口远程调用*/
    return (T) Proxy.newProxyInstance(
        serviceInterface.getClassLoader(),
        new Class<?>[]{serviceInterface},
        new InvocationHandler() {
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Socket socket = null;
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;
            try {
              /*2.创建socket客户端,根据地址连接指定Server*/
              socket = new Socket();
              socket.connect(addr);

              /*3.将远程服务调用需要的接口类,方法名,参数列表等编码后发送给Server*/
              oos = new ObjectOutputStream(socket.getOutputStream());
              oos.writeUTF(serviceInterface.getName());
              oos.writeUTF(method.getName());
              oos.writeObject(method.getParameterTypes());
              oos.writeObject(args);

              /*4.同步阻塞等待服务器返回应答,获取应答后返回*/
              ois = new ObjectInputStream(socket.getInputStream());
              return ois.readObject();
            } finally {
              IOUtil.closeOS(oos);
              IOUtil.closeIS(ois);
            }
          }
        }
    );

  }
}
