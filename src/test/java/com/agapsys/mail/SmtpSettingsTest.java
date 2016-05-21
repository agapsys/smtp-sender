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
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class SmtpSettingsTest {

	@Test
	public void emptyConstructorTest() {
		final String       SERVER        = "mail.server.com";
		final boolean      AUTH          = true;
		final String       USERNAME      = "username";
		final String       PASSWORD      = "password";
		final SecurityType SECURITY_TYPE = SecurityType.TLS;
		final int          PORT          = 587;

		SmtpSettings settings = new SmtpSettings();
		settings.setServer(SERVER);
		settings.setAuthenticationEnabled(AUTH);
		settings.setUsername(USERNAME);
		settings.setPassword(PASSWORD);
		settings.setSecurityType(SECURITY_TYPE);
		settings.setPort(PORT);

		assertEquals(SERVER, settings.getServer());
		assertEquals(AUTH, settings.isAuthenticationEnabled());
		assertEquals(USERNAME, settings.getUsername());
		assertEquals(PASSWORD, new String(settings.getPassword()));
		assertEquals(SECURITY_TYPE, settings.getSecurityType());
		assertEquals(PORT, settings.getPort());
	}

	@Test
	public void testPropertyConstructor() {
		final String       SERVER    = "mail.server.com";
		final boolean      AUTH      = true;
		final String       USERNAME  = "username";
		final String       PASSWORD  = "password";
		final SecurityType SECURITY  = SecurityType.TLS;
		final int          PORT      = 587;

		Properties props = new Properties();
		props.setProperty(SmtpSettings.KEY_SERVER, SERVER);
		props.setProperty(SmtpSettings.KEY_AUTH, "" + AUTH);
		props.setProperty(SmtpSettings.KEY_USERNAME, USERNAME);
		props.setProperty(SmtpSettings.KEY_PASSWORD, PASSWORD);
		props.setProperty(SmtpSettings.KEY_SECURITY, SECURITY.name());
		props.setProperty(SmtpSettings.KEY_PORT, "" + PORT);

		SmtpSettings settings = new SmtpSettings(props);

		assertEquals(SERVER, settings.getServer());
		assertEquals(AUTH, settings.isAuthenticationEnabled());
		assertEquals(USERNAME, settings.getUsername());
		assertEquals(PASSWORD, new String(settings.getPassword()));
		assertEquals(SECURITY, settings.getSecurityType());
		assertEquals(PORT, settings.getPort());
	}

	@Test
	public void testDefaultValues() {
		SmtpSettings settings = new SmtpSettings();
		assertEquals(SmtpSettings.DEFAULT_SERVER, settings.getServer());
		assertEquals(SmtpSettings.DEFAULT_AUTH, settings.isAuthenticationEnabled());
		assertEquals(SmtpSettings.DEFAULT_USERNAME, settings.getUsername());
		assertEquals(SmtpSettings.DEFAULT_PASSWORD, new String(settings.getPassword()));
		assertEquals(SmtpSettings.DEFAULT_SECURITY, settings.getSecurityType());
		assertEquals(SmtpSettings.DEFAULT_PORT, settings.getPort());
	}

	private void createInstanceWithEmptyPropertyValue(String key) {
		Properties props = new Properties();
		props.setProperty(key, "");
		SmtpSettings smtpSettings = new SmtpSettings(props);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyServer() {
		createInstanceWithEmptyPropertyValue(SmtpSettings.KEY_SERVER);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyAuth() {
		createInstanceWithEmptyPropertyValue(SmtpSettings.KEY_AUTH);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptySecurity() {
		createInstanceWithEmptyPropertyValue(SmtpSettings.KEY_SECURITY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyPort() {
		createInstanceWithEmptyPropertyValue(SmtpSettings.KEY_PORT);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidPortProperty() {
		Properties props = new Properties();
		props.setProperty(SmtpSettings.KEY_PORT, "" + -2);
		SmtpSettings smtpSettings = new SmtpSettings(props);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setEmptyServer() {
		SmtpSettings settings = new SmtpSettings();
		settings.setServer("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setNullServer() {
		SmtpSettings settings = new SmtpSettings();
		settings.setServer(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidPort() {
		SmtpSettings settings = new SmtpSettings();
		settings.setPort(-2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setNullSecurity() {
		SmtpSettings settings = new SmtpSettings();
		settings.setSecurityType(null);
	}
}