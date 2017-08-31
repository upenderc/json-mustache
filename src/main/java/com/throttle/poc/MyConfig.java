package com.throttle.poc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

@Configuration
public  class MyConfig {

    
    @Bean
    TaskExecutor taskExecutor () {
    	BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(50);
    	PersonThrottler executor = new PersonThrottler(10, 20, 5000, TimeUnit.MILLISECONDS, blockingQueue);
	      executor.setRejectedExecutionHandler(new RejectedExecutionHandler()
	         {
	            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
	            {
	               System.out.println("DemoTask Rejected : " + ((Person) r).getName());
	               try
	               {
	            	   
	                  Thread.sleep(1000);
	               } catch (InterruptedException e)
	               {
	                  e.printStackTrace();
	               }
	               System.out.println("Lets add another time : " + ((Person) r).getName());
	               executor.execute(r);
	            }
	         });
	      ConcurrentTaskExecutor t = new ConcurrentTaskExecutor(executor);
        return t;
    }


}