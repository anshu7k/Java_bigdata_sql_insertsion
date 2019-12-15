package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
	public class CSVScanner{
		String path;
		Scanner scanner;
		public CSVScanner(String s) {
			path =s;
			try {
				scanner = new Scanner(new File(path));
			} 
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public String nextattr() {
			String col = null;	
			col =scanner.nextLine();	
			return(col);
			
		}
		

	}

