package com.ruijing.base.local.upload.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class PathUtils {
    
    public static String likelyUnescapeGeneric(String object) throws UnsupportedEncodingException {
        object = URLDecoder.decode(object, StandardCharsets.UTF_8.toString());
        return trimLeadingSlash(object);
    }
    
    public static String trimLeadingSlash(String ep) {
        if (StringUtils.isNotBlank(ep) && ep.charAt(0) == '/') {
            ep = Paths.get(ep).normalize().toString();
            ep = ep.replace("\\", "/").substring(1);
        }
        return ep;
    }
    
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        String testPath = "/dasda/dasda//dasda";
        System.out.println(likelyUnescapeGeneric(testPath));  // Outputs: example/path/
    }
    
    public static String buildPath(String... items) {
        // todo 应该有工具类了
        return String.join("/", items);
    }
    
}
