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
import org.junit.Test;

public class ReadOnlyInternetAddressTest {
    private final InternetAddress address;

    public ReadOnlyInternetAddressTest() throws AddressException {
        this.address = new ReadOnlyInternetAddress(new InternetAddress("User <user@host.com>"));
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void setPersonal() throws UnsupportedEncodingException {
        address.setPersonal("test");
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void setPersonal1() throws UnsupportedEncodingException {
        address.setPersonal("test", "utf-8");
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void setAddress() {
        address.setAddress("test@host.com");
    }
}
