package com.poc.custom.query;
@NativeQueryResultEntity
public class NonEntityDomain {
	@NativeQueryResultColumn(index=0)
	private String name;
	@NativeQueryResultColumn(index=1)
	private Integer age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}
