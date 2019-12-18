package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class App {
	CSVScanner cv;
	static int j=0;
	JDBCHelper[] jdbc = new JDBCHelper[100];
	static App a;
	Thread[] th = new Thread[100];
	

	public static void main(String[] args) {
		a = new App();
		a.cv =new CSVScanner("C:\\Users\\Subhanshu\\Downloads\\US_Accidents_May19.csv");	
		System.out.println("main started");
		String rows=null;
		boolean executed = true;
		while(true) {
			
			a.jdbc[j]=new JDBCHelper();
			a.jdbc[j].createConnection();
			System.out.println("inside a");
			if(executed) {
				a.jdbc[j].createTable("accident",a.cv.column,a.cv.col_len);
				executed=false;
			}
			a.jdbc[j].startBatch();
			//System.out.println(Arrays.toString(cv.column)+cv.col_len);
			//a.start();
			for(int i=0;i<70000;i++) {
				try{
					rows = a.cv.nextattr();
				}
				catch(Exception e) {
					System.out.println("last recod being uploaded"+""+rows);
					rows=null;
				}
				if(rows !=null) {
				String[] arrOfstr = rows.split(",");
				if(arrOfstr.length==a.cv.col_len) {
					//System.out.println(Arrays.toString(arrOfstr));
					 a.jdbc[j].processBatch(arrOfstr);	
				}
				else{
					System.out.println("record invalid, length of record :"+arrOfstr.length);	
				}
							
				}else {
					break;
				}
			}
			int num=j;
			if(rows ==null) {
				System.out.println("Starting thread");
				Thread th1 = new Thread()
				{
					public void run() {
						System.out.println("j="+j+"num="+num);
						System.out.println("Thread Started");
						a.jdbc[num].executeBatch();
						//a.jdbc[j].connectionClose();	
							
					}
					
				};
				th1.start();
				
				//jdbc.executeBatch();
				break;
			}
			System.out.println("Starting thread");
			
			a.th[num]=new Thread(){
				public void run() {
					System.out.println(j+"Thread Started");
					System.out.println("j="+j+"num="+num);
				//	System.out.println(a.jdbc[num].col_len);
					a.jdbc[num].executeBatch();
					//a.jdbc[j].connectionClose();	
						
				}
				
			};
			a.th[num].start();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			j++;
			
		}
				
	}
	
	static void analytics() {
		System.out.println("inside analytics");
		String sql ="select severity,count(*) from accident group by Severity;";
		ResultSet rs=a.jdbc[j].fetch(sql);
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
	

