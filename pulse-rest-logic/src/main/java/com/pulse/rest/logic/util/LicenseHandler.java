/*
 * Copyright 2015 Vladimir Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pulse.rest.logic.util;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public enum LicenseHandler {
    INSTANCE;

    private LicenseHandler() {
    }

    public boolean isEnabled() throws IOException, ParseException {
        final FileInputStream instream = new FileInputStream(LICENSE_PATH);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
        String value = reader.readLine();

        if (value != null)
            value = value.replaceAll("_", "=");

        final String decodedValue = new String(Base64.decodeBase64(value), "UTF-8");

        final SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yyyy");
        final Date now = new Date();
        final Date licenseDay = dateFormater.parse(decodedValue);

        if (now.before(licenseDay))
            return true;

        return false;
    }

    private final String LICENSE_PATH = "./license";
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
}
