package com.wisifi.nutch.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fwang on 1/5/15.
 */
public class Encoding {
    private static Pattern encodingPattern = Pattern.compile("\\<meta\\s*http-equiv=[\\\"\\']content-type[\\\"\\']\\s*content\\s*=\\s*[\"']text/html\\s*;\\s*charset=([a-z\\d\\-]*)[\\\"\\'\\>]",
            Pattern.CASE_INSENSITIVE);
    public static String defaultEncoding = "UTF-8";


    public static String getCharsetFromContent(byte[] content) throws IOException {
        String stringContent = new String(content);
        Matcher matcher = encodingPattern.matcher(stringContent);
        if (matcher.find()) {
            String charset = matcher.group(1);
            if (Charset.isSupported(charset)) {
                return charset;
            }
        }
        return defaultEncoding;
    }
}
