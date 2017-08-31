package com.throttle.poc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

public class PersonthrottlerException {

	public static void main(String[] args) {

		Integer threadCounter = 0;
	      /*BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(50);
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
	      // Let start all core threads initially
	      executor.prestartAllCoreThreads();*/
		
		ApplicationContext context =
                new AnnotationConfigApplicationContext(MyConfig.class);
      ConcurrentTaskExecutor executor = context.getBean(ConcurrentTaskExecutor.class);
	      while (true)
	      {
	         threadCounter++;
	         // Adding threads one by one
	         //System.out.println("Adding DemoTask : " + threadCounter);
	         executor.execute(new Person(threadCounter.toString()));
	         if (threadCounter == 10)
	            break;
	      }
	      
	}

}
