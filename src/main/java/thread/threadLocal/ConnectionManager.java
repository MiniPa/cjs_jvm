package thread.threadLocal;

import java.sql.Connection;
import java.sql.SQLException;

/*
 * ThreadLocal: 存放值thread内共享, thread间互斥
 * 
 * ThreadLocalMap: ThreadLocal内部类 存放[ThreadLcoal对象,存放的值]
 * 每个线程都对应一个本地变量的Map,一个线程可以存在多个线程本地变量
 */
public class ConnectionManager {
	/*thread内共享Connection,ThreadLocal通常是全局的,支持泛型*/
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	
	public static Connection getCurrentConnection() {
		Connection conn = threadLocal.get();
		try {
			if (conn == null || conn.isClosed()) {
				// 创建新的Connection并赋值给conn
				// 保存Connection
				threadLocal.set(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void closeCurrentConnection() {
		Connection conn = threadLocal.get();
		try {
			if (null != conn  || !conn.isClosed()) {
				conn.close();
				threadLocal.remove();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
