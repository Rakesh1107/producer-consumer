package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class Storage {
	
	Queue<String> queue = new ArrayDeque<>();
	boolean ready = false;
	
	public synchronized void write() {
		try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))){
			
			String st;
			while (ready) {
				try {
					wait();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			while ((st = br.readLine()) != null) {
				queue.add(st);
			}
			ready = true;
			notify();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void read() {
		while (!ready) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while (!queue.isEmpty()) {
			System.out.println(queue.remove());
		}
		ready = false;
		notify();
	}
	

}
