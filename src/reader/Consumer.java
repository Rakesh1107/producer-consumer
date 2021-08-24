package reader;

public class Consumer implements Runnable {
	Storage storage;
	
	public Consumer(Storage storage) {
		this.storage = storage;
	}

	@Override
	public synchronized void run() {
		storage.read();
	}

	
}
