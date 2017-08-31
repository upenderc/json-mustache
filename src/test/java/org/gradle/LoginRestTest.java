package org.gradle;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource.Builder;

public class LoginRestTest extends BaseMustacheTest {

	static private Client client;
	static private Builder webResource;

	@BeforeClass
	public static void setUp() throws Exception, IOException,
	ParserConfigurationException, SAXException {
		client = Client.create();

		webResource = client.resource("http://10.10.120.142:8080//fuelrewards/public/rest/v2/yyProgram01/login").
				    header("access_token", "3558aab5-de30-41e9-ad85-a852af55d250").header("Content-Type", "application/json");
		

	}
	
	@Override
	String getTemplateId() {
		return "loginbody.mustache";
	}

	@Override
	Map<String, String> getTemplateMapData() {
		 Map<String,String> mp=new HashMap<String, String>();
		    mp.put("userId", "optin@gmail.com");
		    mp.put("password", "abcd1234");
		return mp;
	}
	
	@Test
	public void testSuccessJsonResponse() throws IOException,
	
	ParserConfigurationException, SAXException {

		final String inputObject = getJSONString(getTemplateMapData()) ;
	
		ClientResponse response;
		try {
			response = webResource.accept(
					MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,
							inputObject);
			String result = response.getEntity(String.class);
			System.out.println(result);
			Set<String> responsesSet = new HashSet<String>();
			responsesSet.add(getJSONString("errorResponse.mustache",toMap("1039","Registration Token not found or expired")));
			responsesSet.add(getJSONString("errorResponse.mustache",toMap("704","Email Already used for another account")));
			assertThat(response.getStatus(), is(200));
			assertThat(response.getType(), is(APPLICATION_JSON_TYPE));
			assertTrue(responsesSet.contains(result)
					|| result.contains("accountNumber"));

		} catch (Exception  e) {
			e.printStackTrace();
			assertFalse(true);
		}
	}

	
	private Map<String,String> toMap(String code, String message) {
		 Map<String,String> mp=new HashMap<String, String>();
		    mp.put("code", code);
		    mp.put("message", message);
		    return mp;
	}
	
	
}
