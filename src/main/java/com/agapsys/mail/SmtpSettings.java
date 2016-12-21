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
 */
public class SmtpSettings  {
	//  CLASS SCOPE ============================================================
	public static final String KEY_SERVER   = "agapsys.mail.server";
	public static final String KEY_AUTH     = "agapsys.mail.auth";
	public static final String KEY_USERNAME = "agapsys.mail.username";
	public static final String KEY_PASSWORD = "agapsys.mail.password";
	public static final String KEY_SECURITY = "agapsys.mail.security";
	public static final String KEY_PORT     = "agapsys.mail.port";

	public static final String       DEFAULT_SERVER   = "localhost";
	public static final boolean      DEFAULT_AUTH     = false;
	public static final String       DEFAULT_USERNAME = "";
	public static final String       DEFAULT_PASSWORD = "";
	public static final SecurityType DEFAULT_SECURITY = SecurityType.NONE;
	public static final int          DEFAULT_PORT     = 25;
	// =========================================================================

	// INSTANCE SCOPE ==========================================================
	private String       server       = DEFAULT_SERVER;
	private boolean      authenticate = DEFAULT_AUTH;
	private String       username     = DEFAULT_USERNAME;
	private char[]       password     = DEFAULT_PASSWORD.toCharArray();
	private SecurityType securityType = DEFAULT_SECURITY;
	private int          port         = DEFAULT_PORT;

	public SmtpSettings() {}

	public SmtpSettings(Properties properties) {
		if (properties == null)
			throw new IllegalArgumentException("Null properties");

		String propVal;

		// Server ...
		propVal = properties.getProperty(KEY_SERVER);
		if (propVal == null) {
			server = DEFAULT_SERVER;
		} else {
			propVal = propVal.trim();

			if (propVal.isEmpty()) {
				throw new IllegalArgumentException("Empty value for " + KEY_SERVER);
			} else {
				server = propVal;
			}
		}

		// Authenticate...
		propVal = properties.getProperty(KEY_AUTH);
		if (propVal == null) {
			authenticate = DEFAULT_AUTH;
		} else {
			propVal = propVal.trim();

			if (propVal.isEmpty()) {
				throw new IllegalArgumentException("Empty value for " + KEY_AUTH);
			} else {
				authenticate = Boolean.parseBoolean(propVal);
			}
		}

		// Username...
		propVal = properties.getProperty(KEY_USERNAME);
		if (propVal == null) {
			username = DEFAULT_USERNAME;
		} else {
			username = propVal;
		}

		// Password...
		propVal = properties.getProperty(KEY_PASSWORD);
		if (propVal == null) {
			password = DEFAULT_PASSWORD.toCharArray();
		} else {
			password = propVal.toCharArray();
		}

		// Security type...
		propVal = properties.getProperty(KEY_SECURITY);
		if (propVal == null) {
			securityType = DEFAULT_SECURITY;
		} else {
			propVal = propVal.trim();

			if (propVal.isEmpty()) {
				throw new IllegalArgumentException("Empty value for " + KEY_SECURITY);
			} else {
				securityType = SecurityType.valueOf(propVal);
			}
		}

		// Port...
		propVal = properties.getProperty(KEY_PORT);
		if (propVal == null) {
			port = DEFAULT_PORT;
		} else {
			propVal = propVal.trim();

			if (propVal.isEmpty()) {
				throw new IllegalArgumentException("Empty value for " + KEY_PORT);
			} else {
				try {
					Integer propPort = Integer.parseInt(propVal);
					if (propPort < 0 || propPort > 65536) {
						throw new IllegalArgumentException(String.format("Invalid value for %s: %d", KEY_PORT, propPort));
					} else {
						port = propPort;
					}
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException(String.format("Invalid value for %s: %s", KEY_PORT, propVal));
				}
			}
		}
	}

	public synchronized String getServer() {
		return server;
	}
	public synchronized void setServer(String server) throws IllegalArgumentException {
		if (server == null || server.trim().isEmpty())
			throw new IllegalArgumentException("Null/empty mail server");

		this.server = server.trim();
	}

	public synchronized int getPort() {
		return port;
	}
	public synchronized void setPort(int port) {
		if (port < 0 || port > 65536)
			throw new IllegalArgumentException(String.format("Invalid port: %d", port));

		this.port = port;
	}

	public synchronized String getUsername() {
		return username;
	}
	public synchronized void setUsername(String username) {
		if (username == null)
			throw new IllegalArgumentException("Null username");

		this.username = username;
	}

	public synchronized char[] getPassword() {
		return password;
	}
	public synchronized void setPassword(String password) {
		if (password == null)
			throw new IllegalArgumentException("Null password");

		this.password = password.toCharArray();
	}

	public synchronized SecurityType getSecurityType() {
		return securityType;
	}
	public synchronized void setSecurityType(SecurityType secutiryType) {
		if (secutiryType == null)
			throw new IllegalArgumentException("Null security type");

		this.securityType = secutiryType;
	}

	public synchronized boolean isAuthenticationEnabled() {
		return authenticate;
	}
	public synchronized void setAuthenticationEnabled(boolean enabled) {
		this.authenticate = enabled;
	}
	// =========================================================================
}
