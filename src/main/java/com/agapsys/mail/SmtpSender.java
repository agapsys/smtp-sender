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
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

/**
 * Mail sender
 */
public class SmtpSender {
    private final Properties props;
    private final Authenticator authenticator;

    public SmtpSender() {
        this(new SmtpSettings());
    }

    public SmtpSender(SmtpSettings smtpSettings) {
        if (smtpSettings == null)
            throw new IllegalArgumentException("Null smtpSettings");

        this.props = new Properties();
        props.put("mail.smtp.host", smtpSettings.getServer());
        props.put("mail.smtp.port", String.format("%d", smtpSettings.getPort()));
        props.put("mail.smtp.auth", smtpSettings.isAuthenticationEnabled() ? "true" : "false");
        smtpSettings.getSecurityType()._updateProperties(smtpSettings, props);

        final String username = smtpSettings.getUsername();
        final char[] password = smtpSettings.getPassword();

        if (smtpSettings.isAuthenticationEnabled()) {
            authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, new String(password));
                }
            };
        } else {
            authenticator = null;
        }
    }

    public void sendMessage(Message message) throws MessagingException {
        Session session;
        if (authenticator != null) {
            session = Session.getInstance(props, authenticator);
        } else {
            session = Session.getInstance(props);
        }

        MimeMessage mimeMessage = message.getMimeMessage(session);
        Transport.send(mimeMessage);
    }
}

