package Model;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import com.mchange.v2.c3p0.*;
public class DatabaseUtility {
	Connection con = null;
    PreparedStatement pst = null;
    ResultSet resultSet = null;
    String sql=null;
	static String table_name =JDBCHelper.table_name;
	static int col_len= JDBCHelper.col_len;
    
	public static ComboPooledDataSource getDataSource() throws PropertyVetoException
    {
		System.out.println("1");
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl("jdbc:mysql://localhost/Demo");
        cpds.setUser("root");
        cpds.setPassword("1234");
 
        // Optional Settings
//cpds.setInitialPoolSize(5);
  //      cpds.setMinPoolSize(5);
    //    cpds.setAcquireIncrement(10);
      //  cpds.setMaxPoolSize(49);
//cpds.setMaxStatements(100);
        return cpds;
    }
	
	 public void createConnection() throws SQLException{
	        try
	        {
	            ComboPooledDataSource dataSource = DatabaseUtility.getDataSource();
	            con = dataSource.getConnection();
	            System.out.println("connection created");
	            
	 
	        }
	        catch (Exception e)
	        {
	            con.rollback();
	            e.printStackTrace();
	            
	        }
	   
	 }
	 
	 
	 
	 void startBatch() {
			try {
				String temp="";
				int i=0;
				//System.out.println(i);
				while(true){
					//System.out.println(col_len);
					if(i==col_len-1) {
						//System.out.println(i);
						temp=temp.concat("?)");
						break;
							
					}
					else {
						//System.out.println("else1");
						temp=temp.concat("?,");
						//System.out.println("else2");
						i++;
						//System.out.println("else3");
					}
				}
				sql = "insert into " + table_name+" values("+temp+";";
				
				System.out.println(sql);
				pst =con.prepareStatement(sql);
				System.out.println("Statement prepared complete");
				pst.setPoolable(true);
				
			}catch(Exception e) {
				System.out.println(e);
				
			 }
		}
		void processBatch(String s[]) {
			try {
				for(int i=0;i<col_len;i++) {
					pst.setString(i+1, s[i]);
				}
				//pst.executeUpdate();
				pst.addBatch();
				con.setAutoCommit(false);
				//System.out.println("batch added");	
				
			}catch(Exception e) {
				System.out.println(e);
			}
			
		}		
		void  executeBatch() {
			try {
				LocalTime myObj = LocalTime.now();
			    System.out.println(myObj);
				pst.executeBatch();
				LocalTime myObj1 = LocalTime.now();
			    System.out.println(myObj1);
				//System.out.println(pst.getUpdateCount());
				con.commit();
				System.out.println("commeted sucessfully");
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					System.out.println("Starting rollback");
					con.rollback();
					System.out.println("ROllback sucessfull");
				} catch (SQLException e1) {
					System.out.println("ROllback error:"+e1);
					e1.printStackTrace();
				  }
			
			}
				
		}
		void connectionClose() {
			try {
				con.close();
				System.out.println("Connection closed");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("error in closing sql connection :"+ e);
			}
		}


}
	


