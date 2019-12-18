package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime; 

public class JDBCHelper {
	
	Connection con;
	Statement st;
	PreparedStatement pst;
	CallableStatement cst;
	String sql=null;
	static String table_name = null;
	static int col_len;
	public JDBCHelper() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
		}
		catch(Exception e){
			
			System.out.println("error: "+ e);
		} 
	}
	
	public void createConnection() {
		System.out.println("Inside create connection");
		try {
			String url ="jdbc:mysql://localhost/Demo";
			String user = "root";
			String password= "1234";
			con = DriverManager.getConnection(url,user,password);
			System.out.println("Connection created");
		}
		catch(Exception e) {}
		
	}
	
	void createTable(String table_name,String[] column,int col_len) {
		this.table_name = table_name;
		this.col_len =col_len;
		try {
			System.out.println("Deleting table if any...");
		      st = con.createStatement();
		      String sql = "DROP TABLE "+ table_name;
		      st.executeUpdate(sql);
		      System.out.println("Table  deleted in given database...");  	
		}catch(SQLException se){
	      //Handle errors for JDBC
			System.out.println("No dublicate table found");
			se.printStackTrace();
		}
		
		try {
			st = con.createStatement();
			//+ "serial_no INTEGER  not null AUTO_INCREMENT, " +
			String sql = "CREATE TABLE "+ table_name +"(";
				for(int i=0;i<col_len;i++) {
					String col_name= new Temp().manupulation(column[i],i);
					//System.out.println(col_name);
					if(i==col_len-1) {
						sql=sql.concat(col_name + " TEXT)");
					}
					else {
						sql=sql.concat(col_name + " TEXT,");
						
					}
				}
				
			System.out.println(sql);

			st.executeUpdate(sql);
			System.out.println("Table created");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 	}	
	      
	}
	
	void startBatch() {
		try {
			String temp="";
			int i=0;
			//System.out.println(i);
			while(true){
				System.out.println(col_len);
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
			
		}catch(Exception e) {
			System.out.println(e);
			
		 }
	}
	void processBatch(String s[]) {
		try {
			for(int i=0;i<col_len;i++) {
				pst.setString(i+1, s[i]);
			}
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
			//con.close();
			System.out.println("Connection closed");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("error in closing sql connection :"+ e);
		}
	}
	
	public ResultSet fetch(String sql) {
		ResultSet rs = null;
		try {
			pst =con.prepareStatement(sql);
			
			rs = pst.executeQuery();
			System.out.println("Query executed sucessfully");
			return rs;
		/*	Student temp = new Student();
			while(rs.next()) {
				temp.rollno = rs.getInt(1);
				temp.name = rs.getString(2);
				temp.email = rs.getString(3);
				temp.address = rs.getString(4);
				temp.age= (byte)rs.getInt(5);
				System.out.println(temp.toString());
			}*/
			
			
		}
		catch(Exception e) {
			System.out.println(e);
			return rs;
		}
		
	}
	

}
