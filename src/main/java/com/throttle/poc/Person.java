package com.throttle.poc;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Person implements Runnable, Serializable {

	private final String name;
	public Person(final String nm) {
		this.name=nm;
	}
	public String toString() {
		return "Name = "+name;
	}
	public String getName() {
		return this.name;
	}
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName());
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
