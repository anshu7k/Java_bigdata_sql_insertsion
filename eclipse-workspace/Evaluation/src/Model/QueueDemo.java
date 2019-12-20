package Model;

import java.util.LinkedList;
import java.util.Queue;

public class QueueDemo {

	public static void main(String[] args) {

		//FIFO 
		//sorts the data automatically for us
		Queue<Integer> p1 = new LinkedList<>();
		p1.add(2);
		p1.add(0);
		p1.add(34);
		p1.add(20);
		p1.add(48);
		p1.add(1);
		Queue<Integer> p2 = new LinkedList<>();
		//p2.add(p1.peek());
		for(int i=0;i< 6; i++) {
			System.out.println(p1.poll());
			System.out.println("size:"+p1.size());
			
			
		}
		for(int i=0;i< 6; i++) {
			System.out.println(p2.poll());
			System.out.println("size:"+p2.size());
			
			
		}
	}
	

}
