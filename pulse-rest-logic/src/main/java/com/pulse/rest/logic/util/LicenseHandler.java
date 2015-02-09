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
