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

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

/**
 * Mail sender
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class SmtpSender {
	private final SmtpSettings smtpSettings;
	private final Properties props;
	private final Session session;
	
	public SmtpSender() {
		this(new SmtpSettings());
	}
	
	public SmtpSender(SmtpSettings smtpSettings) {
		if (smtpSettings == null)
			throw new IllegalArgumentException("Null smtpSettings");
		
		this.smtpSettings = smtpSettings;
		
		this.props = new Properties();
		props.put("mail.smtp.host", smtpSettings.getServer());
		props.put("mail.smtp.port", String.format("%d", smtpSettings.getPort()));
		props.put("mail.smtp.auth", smtpSettings.isAuthenticationEnabled() ? "true" : "false");
		
		if (smtpSettings.isAuthenticationEnabled()) {
			this.session = Session.getInstance(props, 
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(SmtpSender.this.smtpSettings.getUsername(), SmtpSender.this.smtpSettings.getPassword());
					}
				}
			);
		} else {
			this.session = Session.getInstance(props);
		}
	}

	public void sendMessage(Message message) throws MessagingException {
		MimeMessage mimeMessage = new MimeMessage(session);

		message.preSend(mimeMessage);

		Transport.send(mimeMessage);
	}
}

