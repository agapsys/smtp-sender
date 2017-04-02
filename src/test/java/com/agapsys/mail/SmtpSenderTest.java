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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.junit.Test;

public class SmtpSenderTest {
    // CLASS SCOPE =============================================================
    public static final String KEY_SENDER       = "agapsys.mail.sender";
    public static final String KEY_RECIPIENTS   = "agapsys.mail.recipients";
    public static final String KEY_SUBJECT      = "agapsys.mail.subject";
    public static final String KEY_TEXT         = "agapsys.mail.text";
    public static final String KEY_MIME_SUBTYPE = "agapsys.mail.mimeSubtype";
    public static final String KEY_CHARSET      = "agapsys.mail.charset";
    public static final String KEY_IGNORE_TEST  = "agapsys.mail.ignoreTest";
    
    public static final String RECIPIENT_DELIMITER = ",";
    
    private static final File HOME_FOLDER = new File(System.getProperty("user.home"));
    private static final File SETTINGS_FILE = new File(HOME_FOLDER, ".smtp-sender-test.conf");
    // =========================================================================
    
    // INSTANCE SCOPE ==========================================================
    private SmtpSettings settings;
    private String       sender;
    private String[]     recipients;
    private String       subject;
    private String       text;
    private String       mime;
    private String       charset;
    private boolean      ignoreTest;
    
    private void throwMissingProperty(String key) {
        throw new IllegalStateException("Missing property: " + key);
    }
    
    public SmtpSenderTest() throws IOException {
        if (!SETTINGS_FILE.exists()) {
            ignoreTest = true;
            return;
        }
        
        try (FileInputStream fis = new FileInputStream(SETTINGS_FILE)) {
            Properties props = new Properties();
            props.load(fis);
            settings = new SmtpSettings(props);
            
            String property;
            
            // Sender...
            property = props.getProperty(KEY_SENDER, null);
            if (property == null || property.trim().isEmpty())
                throwMissingProperty(KEY_SENDER);
            
            sender = property.trim();
            
            // Recipients...
            property = props.getProperty(KEY_RECIPIENTS, null);
            if (property == null || property.trim().isEmpty())
                throwMissingProperty(KEY_RECIPIENTS);
            
            property = property.trim();
            recipients = property.split(RECIPIENT_DELIMITER);
            for (int i = 0; i < recipients.length; i++) {
                recipients[i] = recipients[i].trim();
                if (recipients[i].trim().isEmpty())
                    throw new IllegalStateException("Empty recipient at index " + i);
            }
            
            // Subject...
            property = props.getProperty(KEY_SUBJECT, null);
            if (property == null || property.trim().isEmpty())
                throwMissingProperty(KEY_SUBJECT);
            
            subject = property.trim();
            
            // MIME...
            property = props.getProperty(KEY_MIME_SUBTYPE, null);
            if (property == null || property.trim().isEmpty())
                throwMissingProperty(KEY_MIME_SUBTYPE);
            
            mime = property.trim();
            
            // Text...
            property = props.getProperty(KEY_TEXT, null);
            if (property == null || property.trim().isEmpty())
                throwMissingProperty(KEY_TEXT);
            
            text = property.trim();
            
            // Charset...
            property = props.getProperty(KEY_CHARSET, null);
            if (property == null || property.trim().isEmpty())
                throwMissingProperty(KEY_CHARSET);
            
            charset = property.trim();
            
            // Ignore test...
            property = props.getProperty(KEY_IGNORE_TEST);
            if (property == null || property.trim().isEmpty())
                throwMissingProperty(KEY_IGNORE_TEST);
            
            ignoreTest = Boolean.parseBoolean(property.trim());
        }
    }
    
    @Test
    public void sendMessage() throws AddressException, MessagingException {
        if (!ignoreTest) {
            SmtpSender smtpSender = new SmtpSender(settings);
            Message message = new MessageBuilder(sender, recipients).setMimeSubtype(mime).setCharset(charset).setSubject(subject).setText(text).build();
            smtpSender.sendMessage(message);
        } else {
            System.out.println("=== WARNING: Test was ignored in settings ===");
        }
    }
    // =========================================================================
}
