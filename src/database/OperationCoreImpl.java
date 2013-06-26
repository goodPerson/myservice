package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;


public class OperationCoreImpl implements IOperationCore {
	protected Connection aConnection=null;
	protected Statement   aStatement=null;
	protected ResultSet     aResultSet=null;
	protected ResultSetMetaData rsmd=null;
	protected static OperationCoreImpl  m_instance=null;
	
	private OperationCoreImpl() throws Exception{
		init();
	}
	
	private void init() throws Exception{
		aConnection=ConnectionFactory.getConnection();
	}
	
	public static OperationCoreImpl  createFactory() throws Exception{
		  if (null==m_instance)
			  m_instance=new OperationCoreImpl();
		  return m_instance;			  
	}

	public ResultSet executeQuery(String queryString) throws SQLException {
		// TODO Auto-generated method stub
		try{
			aStatement=aConnection.createStatement();
			aResultSet=aStatement.executeQuery(queryString);
		}catch(SQLException e){
			aResultSet=null;
			e.printStackTrace();
		}
		return aResultSet;
	}

	public int executeUpdate(String updateString) throws SQLException {
		// TODO Auto-generated method stub
		  int effectedRow=0;
		  try{
			   aConnection.setAutoCommit(false);
			   aStatement=aConnection.createStatement();
			   effectedRow=aStatement.executeUpdate(updateString);
			   aConnection.commit();
		  }catch(SQLException e){
			  System.out.println("数据库操作失败");
			  if(aConnection!=null){
				  try{
					  aConnection.rollback();
					  System.out.println("数据库回滚成功");
				  }catch(SQLException ex){
					  System.out.println("数据库回滚失败");
					  ex.printStackTrace();					  
				  }
			  }
			  //e.printStackTrace();
		  }
		return effectedRow;
	}

	public int getRowCount(String queryString) throws SQLException {
		// TODO Auto-generated method stub
		int rowCount=0;
		aResultSet=executeQuery(queryString);
		while(aResultSet.next()){
			rowCount=aResultSet.getInt(1);
		}
		return rowCount;
	}

	public int getColumnCount(String queryString) throws SQLException {
		// TODO Auto-generated method stub
		int columnCount=0;
		aResultSet=executeQuery(queryString);
		rsmd=aResultSet.getMetaData();
		columnCount=rsmd.getColumnCount();
		return columnCount;
	}

	public String getColumnName(int columnIndex, String queryString)
			throws SQLException {
		// TODO Auto-generated method stub
	  	String columnName=null;
	   		aResultSet=executeQuery(queryString);
	   		rsmd=aResultSet.getMetaData();
	   		columnName=rsmd.getColumnName(columnIndex+1);

	   	return columnName;
	}

	public Collection<String> getColumnNames(String queryString)
			throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<String> columnNames=new ArrayList<String>();
	    aResultSet=executeQuery(queryString);
	    rsmd=aResultSet.getMetaData();
	    int j=rsmd.getColumnCount();
	    for (int i=0;i<j;i++){
	    	columnNames.add(rsmd.getColumnName(i+1));
	    }
		return columnNames;
	}

	public Collection<String> getColumnTypeNames(String queryString)
			throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<String> columnNames=new ArrayList<String>();
	    aResultSet=executeQuery(queryString);
	    rsmd=aResultSet.getMetaData();
	    int j=rsmd.getColumnCount();
	    for (int i=0;i<j;i++){
	    	columnNames.add(rsmd.getColumnName(i+1));
	    }
		return columnNames;
	}

	public Object getValueAt(int rowIndex, int columnIndex, String queryString)
			throws SQLException {
		// TODO Auto-generated method stub
		Object values=null;
		aResultSet=executeQuery(queryString);
		aResultSet.absolute(rowIndex+1);
		values=aResultSet.getObject(columnIndex+1);		
		return values;
	}

	public void dispose() throws SQLException {
		// TODO Auto-generated method stub
		try{
			if (aResultSet!=null)
				aResultSet.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			if (aStatement!=null)
				aStatement.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			if(aConnection!=null)
				aConnection.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
