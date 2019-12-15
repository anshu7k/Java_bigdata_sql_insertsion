package Model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;


public class JDBCHelper {
	
	Connection con;
	Statement st;
	PreparedStatement pst;
	CallableStatement cst;
	String sql=null;
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
		try {
			String url ="jdbc:mysql://localhost/Demo";
			String user = "root";
			String password= "1234";
			con = DriverManager.getConnection(url,user,password);
			System.out.println("Connection created");
		}
		catch(Exception e) {}
		
	}
	
	void createTable() {
		try {
			System.out.println("Deleting table if any...");
		      st = con.createStatement();
		      String sql = "DROP TABLE accident ";
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
			String sql = "CREATE TABLE Accident " +
	                   "("
	                   
	                   +"id VARCHAR(10) not NULL, " +
	                   " Source VARCHAR(255), " + 
	                   " TMC VARCHAR(255), " + 
	                   " Severity INTEGER, " + 
	                   "Start_Time VARCHAR(255),"+
	                   "End_Time_Time VARCHAR(255),"+
	                   "Start_Lat VARCHAR(255),"+
	                   "Start_Lng VARCHAR(255),"+
	                   "End_Lat VARCHAR(255),"+
	                   "End_Lng VARCHAR(255),"+
	                   "Distance VARCHAR(255),"+
	                   "Description TEXT,"+
	                   "Number VARCHAR(255),"+
	                   "Street VARCHAR(255),"+
	                   "Side VARCHAR(255),"+
	                   "City VARCHAR(255),"+
	                   "County VARCHAR(255),"+
	                   "State VARCHAR(255),"+
	                   "Zipcode VARCHAR(255),"+
	                   "Country VARCHAR(255),"+
	                   "Timezone VARCHAR(255),"+
	                   "Airport_Code VARCHAR(255),"+
	                   "Weather_Timestamp VARCHAR(255),"+
	                   "Temperature VARCHAR(10),"+
	                   "Wind_Chill VARCHAR(255),"+
	                   "Humidity VARCHAR(255),"+
	                   "Pressure VARCHAR(255),"+
	                   "Visibility VARCHAR(255),"+
	                   "Wind_Direction VARCHAR(255),"+
	                   "Wind_Speed VARCHAR(255),"+
	                   "Precipitation VARCHAR(255),"+
	                   "Weather_Condition VARCHAR(255),"+
	                   "Amenity VARCHAR(255),"+
	                   "Bump VARCHAR(255),"+
	                   "Crossing VARCHAR(255),"+
	                   "Give_Way VARCHAR(255),"+
	                   "Junction VARCHAR(255),"+
	                   "No_Exit VARCHAR(255),"+
	                   "Railway VARCHAR(255),"+
	                   "Roundabout VARCHAR(255),"+
	                   "Station VARCHAR(255),"+
	                   "Stop VARCHAR(255),"+
	                   "Traffic_Calming VARCHAR(255),"+
	                   "Traffic_Signal VARCHAR(255),"+
	                   "Turning_Loop VARCHAR(255),"+
	                   "Sunrise_Sunset VARCHAR(255),"+
	                   "Civil_Twilight VARCHAR(255),"+
	                   "Nautical_Twilight VARCHAR(255),"+
	                   "Astronomical_Twilight VARCHAR(255),"+
	                   " PRIMARY KEY ( ID))"; 
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
			sql = "insert into Accident values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			pst =con.prepareStatement(sql);
		}catch(Exception e) {
			System.out.println(e);
			
		}}
	void processBatch(String s[]) {
		try {
			for(int i=0;i<49;i++) {
				pst.setString(i+1, s[i]);
				//System.out.println(s[i]+""+i);
			}
			pst.setInt(4, (int)Float.parseFloat(s[3]));
			//System.out.println(pst.toString());
			pst.addBatch();
			con.setAutoCommit(false);
			//System.out.println("batch added");	
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}		
	void executeBatch() {
		try {
			pst.executeBatch();
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

}
