package base;

class Task {
	private int num;
	boolean valueSet = false;
	
	// Synchronized is used to make sure notify() is not called between 
	// checking the condition and calling wait()
	// Also a third thread can set the condition false again by the time waiting thread wakes up
	public synchronized void put(int num) {
		while (valueSet) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Put " + num);
		this.num = num;
		valueSet = true;
		notify();
	}
	
	public synchronized void get() {
		while (!valueSet) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Get " + num);
		valueSet = false;
		notify();
	}
}

class Producer implements Runnable {
	Task task;
	
	public Producer(Task task) {
		this.task = task;
		Thread thread = new Thread(this, "Producer");
		thread.start();
	}

	@Override
	public void run() {
		int i = 0;
		
		while(true) {
			task.put(i++);
			try { 
				Thread.sleep(1000); 
			} catch (InterruptedException e) {
				e.printStackTrace(); 
			}
		}
		
	}
	
}

class Consumer implements Runnable {
	Task task;
	
	public Consumer(Task task) {
		this.task = task;
		Thread thread = new Thread(this, "Consumer");
		thread.start();
	}

	@Override
	public void run() {
		
		while(true) {
			task.get();
			try { 
				Thread.sleep(1000); 
			} catch (InterruptedException e) { 
				e.printStackTrace(); 
			}
		}
	}
}



public class Main {

	public static void main(String[] args) {
		Task task = new Task();
		new Producer(task);
		new Consumer(task);
	}

}
