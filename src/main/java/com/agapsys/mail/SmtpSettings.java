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

/**
 * SMTP Settings
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class SmtpSettings  {
	//  CLASS SCOPE ============================================================
	public static final String KEY_SERVER   = "com.agapsys.mail.server";
	public static final String KEY_USERNAME = "com.agapsys.mail.username";
	public static final String KEY_PASSWORD = "com.agapsys.mail.password";
	public static final String KEY_SECURITY = "com.agapsys.mail.security";
	public static final String KEY_PORT     = "com.agapsys.mail.port";
	// =========================================================================
	
	// INSTANCE SCOPE ==========================================================
	private final Properties properties;
	
	public SmtpSettings() {
		properties = new Properties();
	}
	
	public SmtpSettings(Properties properties) {
		if (properties == null)
			throw new IllegalArgumentException("Null properties");
		
		this.properties = properties;
	}
	
	public String getServer() {
		return properties.getProperty(KEY_SERVER, "").trim();
	}
	public void setMailServer(String mailServer) {
		if (mailServer == null || mailServer.trim().isEmpty())
			throw new IllegalArgumentException("Null/empty mail server");
		
		properties.setProperty(KEY_SERVER, mailServer.trim());
	}
	
	public int getPort() {
		return (Integer) properties.getOrDefault(KEY_PORT, -1);
	}
	public void setPort(int port) {
		if (port < 0 || port > 65536)
			throw new IllegalArgumentException(String.format("Invalid port: %d", port));
		
		properties.put(KEY_PORT, port);
	}
	
	public String getUsername() {
		return properties.getProperty(KEY_USERNAME, null);
	}
	public void setUsername(String username) {
		if (username == null || username.isEmpty())
			throw new IllegalArgumentException("Null/Empty username");
		
		properties.put(KEY_USERNAME, username);
	}
	
	public String getPassword() {
		return properties.getProperty(KEY_PASSWORD, null);
	}
	public void setPassword(String password) {
		if (password == null || password.isEmpty())
			throw new IllegalArgumentException("Null/Empty password");
		
		properties.put(KEY_PASSWORD, password);
	}
	
	public SecurityType getSecutiryType() {
		String val = properties.getProperty(KEY_SECURITY, null);
		
		if (val == null)
			return null;
		
		return SecurityType.valueOf(val.trim().toUpperCase());
	}
	public void setSecurityType(SecurityType secutiryType) {
		if (secutiryType == null)
			throw new IllegalArgumentException("Null security type");
		
		properties.put(KEY_SECURITY, secutiryType.name().toLowerCase());
	}
	
	Properties getProperties() {
		return properties;
	}
	// =========================================================================
}
