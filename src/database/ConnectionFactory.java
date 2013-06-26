package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;
/** 类名：ConnectionFactory<br>  
 * * 作用：产生数据库连接对象<br> 
 *  * 属性：<br> 
 * 方法：Connection getConnection()<br>  *        
作用：返回数据库连接对象<br>  *        
参数：无<br> 
 *        返回：数据库连接对象<br> 
 * 其它：返回的aConnection不会自动提交JDBC事务<br>  * 创建人：陶尚明<br>  * */
/** 数据库系统的属性文件名:  */
public abstract class ConnectionFactory{
	private static final String propertiesFileName="using_which_dbms";
	/** 获取Connection对象     *  
	    * @return Connection对象 
	    * @throws Exception SqlException异常 */

	static synchronized public Connection getConnection() throws Exception {
		  String dbSystem=null;
		  Connection aConnection;
		  ResourceBundle db = ResourceBundle.getBundle("dbsystem"); //读取配置文件 
		  dbSystem=db.getString(propertiesFileName);
		  ResourceBundle rb = ResourceBundle.getBundle(dbSystem); //读取配置文件
		  Class.forName(rb.getString("database.driver")).newInstance();
		  aConnection = DriverManager.getConnection(rb.getString("database.url"),rb.getString("database.username"),rb.getString("database.password"));//创建aConnection对象
		  aConnection.setAutoCommit(false);//设置不自动提交事务
		  return aConnection; //返回aConnection对象
	}
	
	public static String getCurrentDBMS(boolean echoable){
		String dbSystem=null;
		ResourceBundle rb = ResourceBundle.getBundle("dbsystem"); //读取配置文件
		dbSystem=rb.getString(propertiesFileName);
		if (echoable)System.out.println("the database system what you using are "+dbSystem);
		return dbSystem;
	}
	
}
