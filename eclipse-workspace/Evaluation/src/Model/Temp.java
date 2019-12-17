package Model;

public class Temp {

	public String manupulation(String col_name, int num) {
		col_name = col_name.trim();
		if(col_name =="" || col_name ==" " || col_name == null || col_name.isEmpty()) {
			col_name= "unnamed_col"+ ""+num;
		}
		else {
			col_name =col_name.split(" ")[0].replaceAll("[^a-zA-Z0-9_-]","");
		}
		
	return col_name;	
	}
}
