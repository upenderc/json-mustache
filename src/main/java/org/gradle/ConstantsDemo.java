package org.gradle;

import java.text.MessageFormat;

public class ConstantsDemo {

	public static void main(String... args) {
		
		org.springframework.core.Constants c = new org.springframework.core.Constants(Constants.class);
		System.out.println(c.asNumber("MAX_VALUE"));
		System.out.println(c.asString("PROPERTY_NAME"));
		System.out.println(MessageFormat.format("{0}{1}?{2}", "",null,null));
	}
}
