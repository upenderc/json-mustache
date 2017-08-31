package org.gradle;

import org.apache.commons.collections.list.GrowthList;

public class Person {
    private  String name;

    public Person(){
    	super();
    }
    public Person(String name) {
    	super();
        this.name = name;
        new GrowthList();
    }

    public void setName(String name) {
    	this.name=name;
    	System.out.println("Gson called");
    	
    }
    public String getName() {
        return name;
    }
    public String toString() {
    	return "Name="+this.name;
    }
}
