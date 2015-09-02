/*
 * Copyright 2015 Agapsys Tecnologia Ltda-ME.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.agapsys.mail;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageBuilderTest {
	private final InternetAddress sender;
	private final InternetAddress recipient1;
	private final InternetAddress recipient2;
	private final Set<InternetAddress> recipients = new LinkedHashSet<>();
	
	private MessageBuilder testBuilder;
	
	@Before
	public void before() {
		testBuilder = new MessageBuilder(sender, recipient1, recipient2);
	}
	
	public MessageBuilderTest() throws AddressException {
		this.sender = new InternetAddress("Sender <sender@host.com>");
		this.recipient1 = new InternetAddress("Recipient1 <recipient1@host.com>");
		this.recipient2 = new InternetAddress("Recipient2 <recipient2@host.com>");
		recipients.add(recipient1);
		recipients.add(recipient2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullInternetAddressInConstructor() {
		new MessageBuilder((InternetAddress) null, null, null);
	}
	
	@Test(expected = AddressException.class)
	public void nullStringInConstructor() throws AddressException {
		new MessageBuilder((String) null, null, null);
	}
	
	@Test(expected = AddressException.class)
	public void emptyStringInConstructor() throws AddressException {
		new MessageBuilder("", "", "");
	}
	
	@Test
	public void validConstructor() {
		Message message = testBuilder.build();
		Assert.assertEquals("Sender", message.getSenderAddress().getPersonal());
		
		Assert.assertTrue(message.getSenderAddress() instanceof ReadOnlyInternetAddress);
		Assert.assertEquals(sender, message.getSenderAddress());
		Assert.assertEquals(recipients, message.getRecipients());
		Assert.assertEquals(2, message.getRecipients().size());
		
		Iterator<InternetAddress> iterator = message.getRecipients().iterator();
		InternetAddress msgRecipient1 = iterator.next();
		InternetAddress msgRecipient2 = iterator.next();
		Assert.assertEquals(recipient1, msgRecipient1);
		Assert.assertEquals(recipient2, msgRecipient2);
		Assert.assertTrue(msgRecipient1 instanceof ReadOnlyInternetAddress);
		Assert.assertTrue(msgRecipient2 instanceof ReadOnlyInternetAddress);
		
		Assert.assertEquals("plain", message.getMimeSubtype());
		Assert.assertEquals("", message.getSubject());
		Assert.assertEquals("", message.getText());
		Assert.assertEquals(Charset.defaultCharset().name(), message.getCharset());
	}
	
	// Mime --------------------------------------------------------------------
	@Test
	public void validMime() {
		Message message = testBuilder.setMimeSubtype("html").build();
		Assert.assertEquals(sender, message.getSenderAddress());
		Assert.assertEquals(recipients, message.getRecipients());
		Assert.assertEquals("html", message.getMimeSubtype());
		Assert.assertEquals("", message.getSubject());
		Assert.assertEquals("", message.getText());
		Assert.assertEquals(Charset.defaultCharset().name(), message.getCharset());
	}
	
	@Test(expected = IllegalStateException.class)
	public void doubleSetMime() {
		testBuilder.setMimeSubtype("text/plain").setMimeSubtype("html");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullMime() {
		testBuilder.setMimeSubtype(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyMime() {
		testBuilder.setMimeSubtype("");
	}
	
	// Subject -----------------------------------------------------------------
	@Test
	public void validSubject() {
		Message message = testBuilder.setSubject("Test subject").build();
		Assert.assertEquals(sender, message.getSenderAddress());
		Assert.assertEquals(recipients, message.getRecipients());
		Assert.assertEquals("plain", message.getMimeSubtype());
		Assert.assertEquals("Test subject", message.getSubject());
		Assert.assertEquals("", message.getText());
		Assert.assertEquals(Charset.defaultCharset().name(), message.getCharset());
	}
	
	@Test(expected = IllegalStateException.class)
	public void doubleSetSubject() {
		testBuilder.setSubject("hello").setSubject("world");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullSubject() {
		Message message = testBuilder.setSubject(null).build();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptySubject() {
		Message message = testBuilder.setSubject("").build();
	}
	
	// Text --------------------------------------------------------------------
	@Test
	public void validText() {
		Message message = testBuilder.setText("hello world!").build();
		Assert.assertEquals(sender, message.getSenderAddress());
		Assert.assertEquals(recipients, message.getRecipients());
		Assert.assertEquals("plain", message.getMimeSubtype());
		Assert.assertEquals("", message.getSubject());
		Assert.assertEquals("hello world!", message.getText());
		Assert.assertEquals(Charset.defaultCharset().name(), message.getCharset());
	}
	
	@Test(expected = IllegalStateException.class)
	public void doubleSetText() {
		testBuilder.setText("hello").setText("world");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullText() {
		Message message = testBuilder.setText(null).build();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyText() {
		Message message = testBuilder.setText("").build();
	}
	
	// Charset -----------------------------------------------------------------
	@Test
	public void validCharset() {
		Message message = testBuilder.setCharset("utf-8").build();
		Assert.assertEquals("utf-8", message.getCharset());
		Assert.assertEquals(sender, message.getSenderAddress());
		Assert.assertEquals(recipients, message.getRecipients());
		Assert.assertEquals("plain", message.getMimeSubtype());
		Assert.assertEquals("", message.getSubject());
		Assert.assertEquals("", message.getText());
	}
	
	@Test(expected = IllegalStateException.class)
	public void doubleSetCharset() {
		testBuilder.setCharset("iso-8956-1").setCharset("utf-8");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullCharset() {
		Message message = testBuilder.setCharset(null).build();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyCharset() {
		Message message = testBuilder.setCharset("").build();
	}
	
	// Altogether --------------------------------------------------------------
	@Test
	public void altogether() {
		Message message = testBuilder.setMimeSubtype("html").setSubject("Subject").setText("Text").setCharset("utf-8").build();
		Assert.assertEquals(sender, message.getSenderAddress());
		Assert.assertEquals(recipients, message.getRecipients());
		Assert.assertEquals("html", message.getMimeSubtype());
		Assert.assertEquals("Subject", message.getSubject());
		Assert.assertEquals("Text", message.getText());
		Assert.assertEquals("utf-8", message.getCharset());
	}
}
