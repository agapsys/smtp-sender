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

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Represents a plain text message
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class Message {
	private InternetAddress senderAddress;
	private InternetAddress[] recipients;
	private String subject;
	private String text;
	
	public InternetAddress getSenderAddress() {
		return this.senderAddress;
	}
	public void setSenderAddress(InternetAddress senderAddress) {
		if (senderAddress == null)
			throw new IllegalArgumentException("Null senderAddress");
		
		this.senderAddress = senderAddress;
	}
	public void setSenderAddress(String senderAddress) throws AddressException {
		this.senderAddress = new InternetAddress(senderAddress);
	}
	
	public InternetAddress[] getRecipients() {
		return this.recipients;
	}
	public void setRecipients(InternetAddress...recipients) {
		if (recipients.length == 0)
			throw new IllegalArgumentException("Empty recipients");
		
		this.recipients = recipients;
	}
	public void setRecipients(String...recipients) throws AddressException {
		if (recipients.length == 0)
			throw new IllegalArgumentException("Empty recipients");

		this.recipients = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			this.recipients[i] = new InternetAddress(recipients[i]);
		}
	}
	
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(String subject) {
		if (subject == null)
			subject = "";
		
		this.subject = subject;
	}

	public String getText() {
		return this.text;
	}
	public void setText(String text) {
		if (text == null)
			text = "";
		
		this.text = text;
	}
	
	final void preSend(MimeMessage mimeMessage) throws MessagingException {
		// Sender...
		if (senderAddress == null)
			throw new IllegalStateException("Missing sender address");

		mimeMessage.setFrom(senderAddress);

		// Recipients...
		if (recipients == null || recipients.length == 0)
			throw new IllegalStateException("Missing recipients");

		mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, recipients);

		// Subject
		if (subject == null)
			subject = "";

		mimeMessage.setSubject(subject);

		// Text...
		if (text == null)
			text = "";
		
		setMimeText(mimeMessage, text);
	}
	
	void setMimeText(MimeMessage mimeMessage, String text) throws MessagingException {
		mimeMessage.setText(text);
	}
}