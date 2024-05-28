package com.ruijing.base.local.upload.util.s3;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.util.stream.Collectors;

public class S3Util {

    // IsValidDomain validates if input string is a valid domain name.
    public static boolean isValidDomain(String host) {
        // See RFC 1035, RFC 3696.
        host = host.trim();
        if (host.length() == 0 || host.length() > 255) {
            return false;
        }
        // host cannot start or end with "-"
        if (host.startsWith("-") || host.endsWith("-")) {
            return false;
        }
        // host cannot start or end with "_"
        if (host.startsWith("_") || host.endsWith("_")) {
            return false;
        }
        // host cannot start with a "."
        if (host.startsWith(".")) {
            return false;
        }
        // All non alphanumeric characters are invalid.
        if (host.matches(".*[`~!@#$%^&*()+={}\\[\\]|\\\\\"';:><?/].*")) {
            return false;
        }
        return true;
    }

    // IsValidIP parses input string for IP address validity.
    public static boolean isValidIP(String ip) {
        try {
            InetAddress.getByName(ip);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }





    private static String[] getMatchingGroup(Pattern pattern, String input) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String[] result = new String[matcher.groupCount() + 1];
            for (int i = 0; i <= matcher.groupCount(); i++) {
                result[i] = matcher.group(i);
            }
            return result;
        }
        return new String[]{};
    }
    




    public static String urlDecode(String s) {
        try {
            return java.net.URLDecoder.decode(s, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    // We support '.' with bucket names but we fallback to using path style requests instead for such buckets.
    private static final Pattern VALID_BUCKET_NAME = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9\\.\\-\\_\\:]{1,61}[A-Za-z0-9]$");
    private static final Pattern VALID_BUCKET_NAME_STRICT = Pattern.compile("^[a-z0-9][a-z0-9\\.\\-]{1,61}[a-z0-9]$");
    private static final Pattern IP_ADDRESS = Pattern.compile("^(\\d+\\.){3}\\d+$");

    // Common checker for both stricter and basic validation.
    public static Exception checkBucketNameCommon(String bucketName, boolean strict) {
        if (bucketName.trim().isEmpty()) {
            return new Exception("Bucket name cannot be empty");
        }
        if (bucketName.length() < 3) {
            return new Exception("Bucket name cannot be shorter than 3 characters");
        }
        if (bucketName.length() > 63) {
            return new Exception("Bucket name cannot be longer than 63 characters");
        }
        if (IP_ADDRESS.matcher(bucketName).matches()) {
            return new Exception("Bucket name cannot be an IP address");
        }
        if (bucketName.contains("..") || bucketName.contains(".-") || bucketName.contains("-.")) {
            return new Exception("Bucket name contains invalid characters");
        }
        if (strict) {
            if (!VALID_BUCKET_NAME_STRICT.matcher(bucketName).matches()) {
                return new Exception("Bucket name contains invalid characters");
            }
            return null;
        }
        if (!VALID_BUCKET_NAME.matcher(bucketName).matches()) {
            return new Exception("Bucket name contains invalid characters");
        }
        return null;
    }

    // CheckValidBucketName - checks if we have a valid input bucket name.
    public static Exception checkValidBucketName(String bucketName) {
        return checkBucketNameCommon(bucketName, false);
    }

    // CheckValidBucketNameStrict - checks if we have a valid input bucket name.
    // This is a stricter version.
    // - http://docs.aws.amazon.com/AmazonS3/latest/dev/UsingBucket.html
    public static Exception checkValidBucketNameStrict(String bucketName) {
        return checkBucketNameCommon(bucketName, true);
    }

    // CheckValidObjectNamePrefix - checks if we have a valid input object name prefix.
    //   - http://docs.aws.amazon.com/AmazonS3/latest/dev/UsingMetadata.html
    public static Exception checkValidObjectNamePrefix(String objectName) {
        if (objectName.length() > 1024) {
            return new Exception("Object name cannot be longer than 1024 characters");
        }
        try {
            byte[] bytes = objectName.getBytes(StandardCharsets.UTF_8);
            String decodedString = new String(bytes, StandardCharsets.UTF_8);
            if (!objectName.equals(decodedString)) {
                return new Exception("Object name with non-UTF-8 strings are not supported");
            }
        } catch (Exception e) {
            return new Exception("Object name with non-UTF-8 strings are not supported");
        }
        return null;
    }

    
    // CheckValidObjectName - checks if we have a valid input object name.
    //   - http://docs.aws.amazon.com/AmazonS3/latest/dev/UsingMetadata.html
    public static Exception checkValidObjectName(String objectName) {
        if (objectName.trim().isEmpty()) {
            return new Exception("Object name cannot be empty");
        }
        return checkValidObjectNamePrefix(objectName);
    }

    private static String encodePath(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
}
