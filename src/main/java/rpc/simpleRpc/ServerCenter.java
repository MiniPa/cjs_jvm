package rpc.simpleRpc;

import util.IOUtil;
import util.ThreadUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description: ServerCenter 服务中心实现类
 *
 * Created by Chengjs on 2017/4/21 @version 1.0.
 */
public class ServerCenter implements Server {

  private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

  private static final HashMap<String, Class> serviceRegistry = new HashMap<String,Class>();

  private static boolean isRunning = false;

  private static int port;

  public ServerCenter(int port) {
    this.port = port;
  }

  @Override
  public void stop() {
    isRunning = false;
    executor.shutdown();
  }

  @Override
  public void start() throws IOException {
    ServerSocket serverSocket = new ServerSocket();
    serverSocket.bind(new InetSocketAddress(port));
    System.out.println("start server");
    /*监听client的TCP连接,将之封装成task,再由线程池执行*/
    try {
      while (true) {
        executor.execute(new ServiceTask(serverSocket.accept()));
      }
    } finally {
      serverSocket.close();
    }
  }

  @Override
  public boolean isRunning() {
    return isRunning;
  }

  @Override
  public int getPort() {
    return port;
  }

  @Override
  public void register(Class serviceInterface, Class impl) {
    serviceRegistry.put(serviceInterface.getName(), impl);
  }

  private static class ServiceTask implements Runnable {
    Socket client = null;
    public ServiceTask(Socket client) {
      this.client = client;
    }

    @Override
    public void run() {
      ObjectInputStream ois = null;
      ObjectOutputStream oos = null;
      try {

        /*2.将client发送的码流反序列化为对象,反射调用服务实现者,获取执行结果*/
        ois = new ObjectInputStream(client.getInputStream());
        String serviceName = ois.readUTF();
        String methodName = ois.readUTF();
        Class<?>[] parameterTypes = (Class<?>[]) ois.readObject();
        Object[] arguments = (Object[]) ois.readObject();

        Class serviceClass = serviceRegistry.get(serviceName);
        if (serviceClass == null) {
          throw new ClassNotFoundException(serviceName + " not found");
        }
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        Object result = method.invoke(serviceClass.newInstance(), arguments);

        /*3.将执行结果序列化, 通过socket返送给客户端*/
        oos = new ObjectOutputStream(client.getOutputStream());
        oos.writeObject(result);

      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        IOUtil.closeIS(ois);
        IOUtil.closeOS(oos);
        ThreadUtil.closeSocket(client);
      }
    }
  }

}
