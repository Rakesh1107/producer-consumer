package reader;

public class Producer implements Runnable {
	
	Storage storage;
	
	public Producer(Storage storage) {
		this.storage = storage;
	}

	@Override
	public synchronized void run() {
		storage.write();
	}

}
