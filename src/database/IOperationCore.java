package database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/** 类名：IOperationCore<br>  *  
 * 作用: 该接口封装了数据库操作的大部分方法<br>  *  
 * 创建人：陶尚明<br>  */
public interface IOperationCore {
	/**返回查询结果集*/
	ResultSet executeQuery(String queryString) throws SQLException;
	/**更新数据影响行数*/
	int executeUpdate(String updateString) throws SQLException;
	/**读取行个数*/
	int getRowCount(String queryString) throws SQLException;
	/**读取列个数*/
	int getColumnCount(String queryString) throws SQLException;
	/**读取指定的列名*/
	String getColumnName(int columnIndex, String queryString) throws SQLException;
	/**返回的结果接*/
   Collection<String>getColumnNames(String queryString) throws SQLException;
   /**读取queryString查询结果集ResultSet表中的所有字段类型名称*/
   Collection<?> getColumnTypeNames(String queryString) throws  SQLException;
   /**获取ResultSet二维表中指定位置的值目前只支持mysql*/
   Object getValueAt(int rowIndex,int columnIndex,String queryString) throws SQLException;
   /**释放系统连接资源*/
   void dispose() throws SQLException;
   
}
