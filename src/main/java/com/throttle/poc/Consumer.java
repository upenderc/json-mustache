package com.throttle.poc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

public class Consumer implements Runnable {
	
	private final BlockingDeque<String> bd ;
	public Consumer(final BlockingDeque<String> bd) {
		this.bd=bd;
	}
	public void run() {
		List<String> str = new ArrayList<String>();
		System.out.println(bd.drainTo(str, 5));
		System.out.println("Retrieved "+str);

	}

}
