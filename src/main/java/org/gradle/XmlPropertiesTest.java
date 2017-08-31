package org.gradle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlPropertiesTest {

	
	public static void main(String ...args) {
		//<util:properties id="myXmlProps" location="classpath:/com/myProject/spring_prop.xml" />
		ApplicationContext ctx=new ClassPathXmlApplicationContext("appContext.xml");
		MessageSource msg =(MessageSource) ctx.getBean("messageSource");
		System.out.println(msg.getMessage("offer.sql", null, null));
	}
}
