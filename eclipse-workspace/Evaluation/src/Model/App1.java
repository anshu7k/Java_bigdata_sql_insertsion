package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


public class App1 {
	static JDBCHelper jdbc;
	static Thread[] th = new Thread[100];
	static int j=0;

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		CSVScanner cv =new CSVScanner("C:\\Users\\Subhanshu\\Downloads\\US_Accidents_May19.csv");
		jdbc= new JDBCHelper();
		jdbc.createConnection();
		System.out.println(Arrays.toString(cv.column)+cv.col_len);
		jdbc.createTable("accident",cv.column,cv.col_len);
		DatabaseUtility d = new DatabaseUtility();
		d.createConnection();
		d.startBatch();
		//jdbc.connectionClose();
		String rows=null;
		Queue<Thread> p1 = new LinkedList<>();
		while(true) {
			for(int i=0;i<300000;i++) {
				try{
					rows = cv.nextattr();
				}
				catch(Exception e) {
					System.out.println("last recod being uploaded"+""+rows);
					rows=null;
				}
				if(rows !=null) {
				String[] arrOfstr = rows.split(",");
				if(arrOfstr.length==cv.col_len) {
					//System.out.println(Arrays.toString(arrOfstr));
					Thread th = new Thread() {
						public void run() {
							
						}
						
					};
					p1.add(th);
					d.processBatch(arrOfstr);
					
				}
				else{
					System.out.println("record invalid, length of record :"+arrOfstr.length);	
				}
				
							
				}else {
					break;
				}
			}
			if(rows ==null) {
				d.executeBatch();
				break;
			}
			d.executeBatch();
			j++;
			
		}
		analytics();
				
	}
	
	static void analytics() {
		System.out.println("inside analytics");
		String sql ="select severity,count(*) from accident group by Severity;";
		ResultSet rs=jdbc.fetch(sql);
		try {
			while(rs.next()) {
				System.out.print(rs.getInt(1)+" "+rs.getInt(2));
				System.out.println("");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
		
}
	

