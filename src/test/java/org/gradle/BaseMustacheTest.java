package org.gradle;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public abstract class BaseMustacheTest {

	abstract String getTemplateId();
	abstract Map<String,String> getTemplateMapData();
	
	protected String getJSONString() throws IOException {
		MustacheFactory mf = new DefaultMustacheFactory();
	    Mustache mustache = mf.compile(getTemplateId());
	    StringWriter sw = new StringWriter();
	    mustache.execute(sw,getTemplateMapData()).flush();
	    return sw.toString();
	}
	
	protected String getJSONString(String templateId,Object mp) throws IOException {
		MustacheFactory mf = new DefaultMustacheFactory();
	    Mustache mustache = mf.compile(templateId);
	    StringWriter sw = new StringWriter();
	    mustache.execute(sw,mp).flush();
	    return sw.toString();
	}
	protected String getJSONString(String templateId) throws IOException {
		MustacheFactory mf = new DefaultMustacheFactory();
	    Mustache mustache = mf.compile(templateId);
	    StringWriter sw = new StringWriter();
	    mustache.execute(sw,getTemplateMapData()).flush();
	    return sw.toString();
	}
	
	protected String getJSONString(Map<String,String> mp) throws IOException {
		MustacheFactory mf = new DefaultMustacheFactory();
	    Mustache mustache = mf.compile(getTemplateId());
	    StringWriter sw = new StringWriter();
	    mustache.execute(sw,mp).flush();
	    return sw.toString();
	}
}
