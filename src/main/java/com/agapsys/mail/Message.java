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

import java.util.Collections;
import java.util.Set;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Represents an e-mail message.
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class Message {
	private final InternetAddress senderAddress;
	private final Set<InternetAddress> recipients;
	
	private final String subject;
	private final String text;
	private final String charset;
	private final String mimeSubtype;
	
	Message(InternetAddress senderAddress, Set<InternetAddress> recipients, String subject, String text, String charset, String mimeSubtype) {
		this.senderAddress = senderAddress;
		this.recipients = Collections.unmodifiableSet(recipients);
		this.subject = subject;
		this.text = text;
		this.charset = charset;
		this.mimeSubtype = mimeSubtype;
	}
	
	public InternetAddress getSenderAddress() {
		return senderAddress;
	}
	
	public Set<InternetAddress> getRecipients() {
		return recipients;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getText() {
		return text;
	}
	
	public String getCharset() {
		return charset;
	}
	
	public String getMimeSubtype() {
		return mimeSubtype;
	}
	
	public String getMime() {
		return "text/" + mimeSubtype;
	}
	
	MimeMessage getMimeMessage(Session session) throws MessagingException {
		MimeMessage mimeMessage = new MimeMessage(session);
		
		mimeMessage.setFrom(senderAddress);
		mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, recipients.toArray(new InternetAddress[recipients.size()]));
		mimeMessage.setSubject(subject);
		mimeMessage.setText(text, charset, mimeSubtype);
		
		return mimeMessage;
	}
}
