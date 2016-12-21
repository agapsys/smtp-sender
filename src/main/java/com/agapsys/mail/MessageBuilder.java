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
import java.util.LinkedHashSet;
import java.util.Set;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Builder for e-mail messages
 */
public class MessageBuilder {
	// CLASS SCOPE =============================================================
	private static InternetAddress[] getRecipients(String...recipients) throws AddressException {
		InternetAddress[] array = new InternetAddress[recipients.length];

		int i = 0;
		for (String recipient : recipients) {
			if (recipient == null || recipient.trim().isEmpty())
				throw new AddressException("Null/Empty address at index " + i);

			InternetAddress tmpRecipient = new InternetAddress(recipient.trim());
			array[i] = tmpRecipient;
			i++;
		}

		return array;
	}

	private static InternetAddress getAddress(String address) throws AddressException {
		if (address == null || address.trim().isEmpty())
				throw new AddressException("Null/Empty address");

		return new InternetAddress(address.trim());
	}
	//==========================================================================

	// INSTANCE SCOPE ==========================================================
	private final InternetAddress senderAddress;
	private final Set<InternetAddress> recipients = new LinkedHashSet<>();

	private String subject  = null;
	private String text     = null;
	private String charset = null;
	private String mimeSubtype = null;

	public MessageBuilder(InternetAddress senderAddress, InternetAddress...recipients) {
		this.senderAddress = new ReadOnlyInternetAddress(senderAddress);

		if (recipients.length == 0)
			throw new IllegalArgumentException("Empty recipients");

		for (InternetAddress recipient : recipients) {
			InternetAddress tmpRecipient = new ReadOnlyInternetAddress(recipient);

			if (this.recipients.contains(tmpRecipient))
				throw new IllegalArgumentException("Dupplicate recipient: " + recipient.toString());

			this.recipients.add(tmpRecipient);
		}
	}

	public MessageBuilder(String senderAddress, String...recipients) throws AddressException {
		this(getAddress(senderAddress), getRecipients(recipients));
	}

	public MessageBuilder setSubject(String subject) {
		if (this.subject != null)
			throw new IllegalStateException("Subject is already set");

		if (subject == null || subject.trim().isEmpty())
			throw new IllegalArgumentException("Null/Empty subject");

		this.subject = subject.trim();
		return this;
	}

	public MessageBuilder setText(String text) {
		if (this.text != null)
			throw new IllegalStateException("Text is already set");

		if (text == null || text.trim().isEmpty())
			throw new IllegalArgumentException("Null/Empty text");

		this.text = text;
		return this;
	}

	public MessageBuilder setCharset(String charset) {
		if (this.charset != null)
			throw new IllegalStateException("Charset is already set");

		if (charset == null || charset.trim().isEmpty())
			throw new IllegalArgumentException("Null/Empty charset");

		this.charset = charset;
		return this;
	}

	public MessageBuilder setMimeSubtype(String subtype) {
		if (this.mimeSubtype != null)
			throw new IllegalStateException("MIME type is already set");

		if (subtype == null || subtype.trim().isEmpty())
			throw new IllegalArgumentException("Null/Empty subtype");

		this.mimeSubtype = subtype;
		return this;
	}

	public Message build() {
		if (subject == null)
			subject = "";

		if (text == null)
			text = "";

		if (charset == null)
			charset = Charset.defaultCharset().name();

		if (mimeSubtype == null)
			mimeSubtype = "plain";

		return new Message(senderAddress, recipients, subject, text, charset, mimeSubtype);
	}
	// =========================================================================
}