package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
	public class CSVScanner{
		String path;
		Scanner scanner;
		String[] column;
		int col_len;
		public CSVScanner(String s) {
			path =s;
			try {
				String col;
				scanner = new Scanner(new File(path));
				col =scanner.nextLine();
				column = col.split(",");
				col_len=column.length;	
			} 
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public String nextattr(){
			String row = null;	
			row =scanner.nextLine();	
			return(row);
			
		}
	}

