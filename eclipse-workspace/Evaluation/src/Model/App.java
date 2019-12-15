package Model;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CSVScanner cv =new CSVScanner("C:\\Users\\Subhanshu\\Downloads\\US_Accidents_May19.csv");
		String col =cv.nextattr();
		System.out.println(col);
		JDBCHelper jdbc= new JDBCHelper();
		jdbc.createConnection();
		jdbc.createTable();
		//jdbc.connectionClose();
		jdbc.startBatch();
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
				if(arrOfstr.length==49) {
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
			
			
	}
		
}
	

