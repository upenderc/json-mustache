package com.throttle.poc;

import java.util.UUID;
import java.util.concurrent.BlockingDeque;

public class Producer implements Runnable{

 private final BlockingDeque<String> bd ;
 
   public Producer(final BlockingDeque<String> bd) {
	   this.bd=bd;
   }
	public void run() {
		bd.add("produced"+UUID.randomUUID());
		
	}

}
