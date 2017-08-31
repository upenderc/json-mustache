package org.gradle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jms.connection.CachingConnectionFactory;

import com.excentus.eveninterface.events.dto.EventResponse;
import com.excentus.eventinterface.events.payload.EventBasePayload;
import com.excentus.eventinterface.events.payload.MemberAddressPayload;
import com.excentus.eventinterface.events.payload.MemberPayload;

public class SendMemberUpdateJsonMessageTest extends BaseMustacheTest {

	
	EventBasePayload eventBasePayload = new EventBasePayload();
	List<EventResponse> responses = new ArrayList<EventResponse>();
	@Override
	String getTemplateId() {
		return "memberupdate.mustache";
	}

	@Override
	Map<String, String> getTemplateMapData() {
		return null;
	}
	static QueueConnection queueConn=null;
	@BeforeClass
	public static void init() throws JMSException {
		final ActiveMQConnectionFactory connFact = new ActiveMQConnectionFactory();
		connFact.setBrokerURL("tcp://localhost:61616");
		final CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setSessionCacheSize(50);
		cachingConnectionFactory.setTargetConnectionFactory(connFact);

		queueConn = connFact.createQueueConnection();
		queueConn.start();
	}
	@AfterClass
	public static void cleanUp() throws JMSException {
		queueConn.stop();
		queueConn.close();
	}
	@Test
	public void sendMemberUpdateMessageForFrExcentus() throws IOException, JMSException {
		MemberPayload memberPayload = toMemberPayLoad("FRNEXCENTUS");
		Map<String, String> commPrefMap = toCommPref();
		MemberAddressPayload address = toAddress();
		memberPayload.setCommPrefMap(commPrefMap);
		memberPayload.setAddress(address);
		String jsonMessage = getJSONString("memberupdate.mustache",memberPayload);
		System.out.println(jsonMessage);
		
		final QueueSession queueSession = queueConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

		final QueueSender queueSender = queueSession.createSender(new ActiveMQQueue("jms/EventInterfaceMemberUpdateDevQueue"));
		
		final Message message = queueSession.createTextMessage(jsonMessage);

		queueSender.send(message);

		queueSender.close();

	
	}

	
	public MemberPayload test() {
		return toMemberPayLoad("");
	}

	private MemberPayload toMemberPayLoad(String programUDK) {
		//Map<String, String> commPrefMap = toCommPref();
		//MemberAddressPayload address = toAddress();
		MemberPayload memberPayload = new MemberPayload();
		memberPayload.setEmail("mpexcentus670287int@gmail.com");
		memberPayload.setFirstName("Kruaapa");
		memberPayload.setLastName("LastChange");
		memberPayload.setAltId(null);
		memberPayload.setReferralId(null);
		memberPayload.setLanguagePref(null);
		memberPayload.setPartSrcId(null);
		//memberPayload.setCommPrefMap(commPrefMap);
		memberPayload.setDateOfBirth("1980-06-20");
		memberPayload.setGenderVal("Male");
		//memberPayload.setAddress(address);
		memberPayload.setEventName("MEMBERUPDATE");
		memberPayload.setFpAccountId(2701090l);
		memberPayload.setProgramId(2790L);
		memberPayload.setProgramUDK(programUDK);
		memberPayload.setProgramUdk(programUDK);
		return memberPayload;
	}

	private Map<String, String> toCommPref() {
		Map<String, String> commPrefMap = new HashMap<String, String>();
		commPrefMap.put("DINING_OPTIN", "Y");
		commPrefMap.put("EMAIL_OPTIN", "Y");
		return commPrefMap;
	}

	private MemberAddressPayload toAddress() {
		MemberAddressPayload address = new MemberAddressPayload();
		address.setStreet1("Street1");
		address.setStreet2("Street2");
		address.setCity("Dallas");
		address.setStateCode("TX");
		address.setZipCode("75254");
		return address;
	}
}
