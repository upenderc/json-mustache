package com.throttle.poc;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestThrottle {

	public static void main(String args[]) throws InterruptedException {
		//ExecutorService es = Executors.newFixedThreadPool(10);
		//es.submit(task);
		ScheduledExecutorService ste= Executors.newScheduledThreadPool(10);
		BlockingDeque bd = new LinkedBlockingDeque<String>();
		ste.submit(new Producer(bd));
		while(true)
	    {
		ste.submit(new Consumer(bd));
		TimeUnit.MICROSECONDS.sleep(15000);
	    }
		
		
		
		
		
	}
}
