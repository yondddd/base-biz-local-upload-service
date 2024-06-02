package com.ruijing.base.local.upload.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Main {


    public static String likelyUnescapeGeneric(String object) throws UnsupportedEncodingException {
        object = URLDecoder.decode(object, StandardCharsets.UTF_8.toString());
        return trimLeadingSlash(object);

    }

    public static String trimLeadingSlash(String ep) {
        if (ep.length() > 0 && ep.charAt(0) == '/') {
            // Path ends with '/' preserve it
            if (ep.charAt(ep.length() - 1) == '/' && ep.length() > 1) {
                ep = cleanPath(ep);
                ep += "/";
            } else {
                ep = cleanPath(ep);
            }
            ep = ep.substring(1);
        }
        return ep;
    }

    public static String cleanPath(String path) {
        // This is a placeholder. In Java, you might want to use Apache Commons IO or another library for path cleaning.
        // For simplicity, this example assumes the input path is already clean.
        return path.replaceAll("/+", "/");
    }


}
