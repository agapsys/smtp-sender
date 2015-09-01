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

import java.io.UnsupportedEncodingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

class ReadOnlyInternetAddress extends InternetAddress {
	private final InternetAddress wrappedInstance;
	
	public ReadOnlyInternetAddress(InternetAddress wrappedInstance) {
		if (wrappedInstance == null)
			throw new IllegalArgumentException("Wrapped instance cannot be null");
		
		this.wrappedInstance = wrappedInstance;
	}
	
	// Blocked methods ---------------------------------------------------------
	private void raiseError() {
		throw new UnsupportedOperationException("Instance is read-only");
	}
	
	@Override
	public void setPersonal(String name) throws UnsupportedEncodingException {
		raiseError();
	}

	@Override
	public void setPersonal(String name, String charset) throws UnsupportedEncodingException {
		raiseError();
	}

	@Override
	public void setAddress(String address) {
		raiseError();
	}
	 // ------------------------------------------------------------------------
	
	@Override
	public InternetAddress[] getGroup(boolean strict) throws AddressException {
		return wrappedInstance.getGroup(strict);
	}

	@Override
	public boolean isGroup() {
		return wrappedInstance.isGroup();
	}

	@Override
	public void validate() throws AddressException {
		wrappedInstance.validate();
	}

	@Override
	public int hashCode() {
		return wrappedInstance.hashCode();
	}

	@Override
	public boolean equals(Object a) {
		return wrappedInstance.equals(a);
	}

	@Override
	public String toUnicodeString() {
		return wrappedInstance.toUnicodeString();
	}

	@Override
	public String toString() {
		return wrappedInstance.toString();
	}

	@Override
	public String getPersonal() {
		return wrappedInstance.getPersonal();
	}

	@Override
	public String getAddress() {
		return wrappedInstance.getAddress();
	}

	@Override
	public String getType() {
		return wrappedInstance.getType();
	}

	@Override
	public Object clone() {
		return wrappedInstance.clone();
	}
}
