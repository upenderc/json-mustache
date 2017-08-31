package org.gradle;

import com.google.gson.Gson;

public class GsonSerializer {

	public static void main(String[] args) {
		
		String str="{\"name\": \"Upender\"}";
		Person p = new Gson().fromJson(str, Person.class);
		System.out.println(p);
	}

}
