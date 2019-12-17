package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class App {
	static JDBCHelper jdbc;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CSVScanner cv =new CSVScanner("C:\\Users\\Subhanshu\\Downloads\\abc.csv");
		jdbc= new JDBCHelper();
		jdbc.createConnection();
		System.out.println(Arrays.toString(cv.column)+cv.col_len);
		jdbc.createTable("Stud1",cv.column,cv.col_len);
		jdbc.startBatch();
		//jdbc.connectionClose();
		String rows=null;
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
					System.out.println(Arrays.toString(arrOfstr));
					jdbc.processBatch(arrOfstr);
					
				}
				else{
					System.out.println("record invalid, length of record :"+arrOfstr.length);	
				}
				
							
				}else {
					break;
				}
			}
			if(rows ==null) {
				jdbc.executeBatch();
				break;
			}
			jdbc.executeBatch();
			
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
	

