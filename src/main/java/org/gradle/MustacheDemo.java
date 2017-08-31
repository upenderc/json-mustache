package org.gradle;

import java.io.IOException;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class MustacheDemo {

	public static void main(String[] args) throws IOException {
		MustacheFactory mf = new DefaultMustacheFactory();
	    Mustache mustache = mf.compile("template.mustache");
	    Map<String,String> mp=new HashMap<String, String>();
	    mp.put("registrationToken", "f1");
	    mp.put("securityQuestionId", "f2");
	    mp.put("securityAnswer", "f3");
	    mp.put("password", "f4");
	    mp.put("marcomOptIn", "f5");
	    StringWriter sw = new StringWriter();
	    mustache.execute(sw,mp).flush();
	    System.out.println(sw.toString());


	    final String programUDK = MessageFormat.format("responsys.{0}.programUDK", "dpc");
	    System.out.println(programUDK);
	}

}
