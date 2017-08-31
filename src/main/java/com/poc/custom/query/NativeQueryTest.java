package com.poc.custom.query;

import java.util.Arrays;
import java.util.List;

public class NativeQueryTest {

	public static void main(String ...args) {
		Object rec1[]={"scott",40,2};
		Object rec2[]={"miller",20,1};
		
		List<Object[]> records = Arrays.asList(rec1,rec2);
		List<NonEntityDomain> dList=NativeQueryResultsMapper.map(records, NonEntityDomain.class);
		
		
		
		for(NonEntityDomain d:dList) {
			System.out.println(d.getName()+" "+d.getAge());
		}
	}
}
