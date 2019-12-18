package Model;

class MyTask extends Thread{
	public void run() {
		for(int i=0;i<100;i++) {
			System.out.println("I am in Mytask class "+i);
		}
			
	}
}
class CA{
	
}

class YourTask extends CA implements Runnable{
	public void run() {
		for(int i=0;i<10;i++) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("I am in Yourtask class "+i);
		}
			
	}
}
public class ThreadDemo {

	public static void main(String[] args) {
		MyTask mref = new MyTask();
		System.out.println("state is"+mref.getState());
		mref.start();
		System.out.println("state is"+mref.getState());
		
		
		Runnable r = new YourTask();
		Thread yref = new Thread(r);
		yref.start();yref.setPriority(1);
//		try {
//			mref.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		for(int i=0;i<10;i++) {
			System.out.println("I am in Main class "+i);
		}
		System.out.println("name of maine thread is:"+Thread.currentThread().getName()+" and the priority for that thread is "+Thread.currentThread().getPriority());
		System.out.println("name of Yourthread is: "+yref.getName()+" and the priority is: "+yref.getPriority());
		System.out.println("name of Mythread is: "+mref.getName()+" and the priority is: "+mref.getPriority());
		System.out.println("state is"+mref.getState());
		
	}

}
