package reader;

public class Runner {

	public static void main(String[] args) {
		
		Storage storage = new Storage();
		
		Producer producer = new Producer(storage);
		Consumer consumer = new Consumer(storage);
		
		Thread t1 = new Thread(producer);
		Thread t2 = new Thread(consumer);
		
		t1.start();
		t2.start();
		
	}

}
