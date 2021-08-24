package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Storage {
	
	Queue<String> queue = new LinkedList<>();
	boolean ready = false;
	
	public synchronized void write() {
		try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))){
			String st;
			while ((st = br.readLine()) != null) {
				queue.add(st);

				while (ready) {
					try {
						wait();
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				ready = true;
				notify();

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void read() {

		while (!queue.isEmpty()) {
			while (!ready) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			System.out.println(queue.remove());

			ready = false;
			notify();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
