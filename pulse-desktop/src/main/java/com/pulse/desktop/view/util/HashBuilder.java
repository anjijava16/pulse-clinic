package com.pulse.desktop.view.util;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author befast
 */
public enum HashBuilder {

    INSTANCE;

    private HashBuilder() {
    }

    public String calculate() {
        String hash = null;
        
        try {
            hash = new String(
                    Base64.encodeBase64(
                            String.valueOf(System.currentTimeMillis()).getBytes("UTF-8")
                    )
            );
            hash = hash.replaceAll("\\/", "d");
            hash = hash.replaceAll("\\=", "a");
            hash = hash.replaceAll("\\+", "v");
            hash = hash.replaceAll("\\-", "i");
        } catch (UnsupportedEncodingException uee) {
            hash = null;
        }
        
        return hash;
    }
    
    public String token(String fullname) throws UnsupportedEncodingException {
        String token = String.valueOf(Base64.encodeBase64String(fullname.getBytes("UTF-8")));
        
        token = token.replaceAll("/", "_");
        
        return token;
    }
}