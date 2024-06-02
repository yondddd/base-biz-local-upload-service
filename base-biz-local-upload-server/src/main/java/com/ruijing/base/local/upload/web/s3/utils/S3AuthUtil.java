package com.ruijing.base.local.upload.web.s3.utils;

import com.ruijing.base.local.upload.util.ConvertOp;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class S3AuthUtil {


    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();


    public static boolean validAuthorizationHead(HttpServletRequest request, String accessKeyId, String secretAccessKey) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) return false;

        String requestDate = request.getHeader("x-amz-date");
        String contentHash = request.getHeader("x-amz-content-sha256");
        String httpMethod = request.getMethod();
        String uri = getUri(request);
        String queryString = ConvertOp.convert2String(request.getQueryString());

        Authorization authHeader = fromHeader(authorization);
        if (!accessKeyId.equals(authHeader.accessKey)) {
            return false;
        }

        String stringToSign = generateStringToSign(httpMethod, uri, queryString, authHeader.signedHeaders, authHeader.signedHeader, contentHash, requestDate, authHeader.date, authHeader.region, authHeader.service, authHeader.aws4Request, request);
        String strHexSignature = generateSignature(secretAccessKey, stringToSign, authHeader.date, authHeader.region, authHeader.service, authHeader.aws4Request);

        return authHeader.signature.equals(strHexSignature);
    }

    public static boolean validAuthorizationUrl(HttpServletRequest request, String accessKeyId, String secretAccessKey) throws Exception {
        String requestDate = request.getParameter("X-Amz-Date");
        if (requestDate == null) return false;

        String contentHash = "UNSIGNED-PAYLOAD";
        String httpMethod = request.getMethod();
        String uri = getUri(request);
        String queryString = ConvertOp.convert2String(request.getQueryString());

        Authorization authUrl = fromRequest(request);
        if (!accessKeyId.equals(authUrl.accessKey)) {
            return false;
        }

        if (isExpired(requestDate, authUrl.expires)) {
            return false;
        }

        String stringToSign = generateStringToSign(httpMethod, uri, queryString, authUrl.signedHeaders, authUrl.signedHeader, contentHash, requestDate, authUrl.date, authUrl.region, authUrl.service, authUrl.aws4Request, request);
        String strHexSignature = generateSignature(secretAccessKey, stringToSign, authUrl.date, authUrl.region, authUrl.service, authUrl.aws4Request);

        return authUrl.signature.equals(strHexSignature);
    }

    private static String getUri(HttpServletRequest request) {
        return request.getRequestURI().split("\\?")[0];
    }

    private static boolean isExpired(String requestDate, String expires) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
        LocalDateTime startDate = LocalDateTime.parse(requestDate, formatter);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime localDateTime = startDate.atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneId);
        startDate = localDateTime.toLocalDateTime();
        LocalDateTime endDate = startDate.plusSeconds(ConvertOp.convert2Int(expires));
        return endDate.isBefore(LocalDateTime.now());
    }

    private static String generateStringToSign(String httpMethod, String uri, String queryString, String[] signedHeaders, String signedHeader, String contentHash, String requestDate, String date, String region, String service, String aws4Request, HttpServletRequest request) throws Exception {
        StringBuilder hashedCanonicalRequest = new StringBuilder();
        hashedCanonicalRequest.append(httpMethod).append("\n")
                .append(uri).append("\n");

        Map<String, String> queryStringMap = parseQueryParams(queryString);
        List<String> keyList = new ArrayList<>(queryStringMap.keySet());
        Collections.sort(keyList);
        for (String key : keyList) {
            if (!key.equals("X-Amz-Signature")) {
                hashedCanonicalRequest.append(key).append("=").append(queryStringMap.get(key)).append("&");
            }
        }
        if (hashedCanonicalRequest.charAt(hashedCanonicalRequest.length() - 1) == '&') {
            hashedCanonicalRequest.deleteCharAt(hashedCanonicalRequest.length() - 1);
        }
        hashedCanonicalRequest.append("\n");

        for (String name : signedHeaders) {
            hashedCanonicalRequest.append(name).append(":").append(request.getHeader(name)).append("\n");
        }
        hashedCanonicalRequest.append("\n")
                .append(signedHeader).append("\n")
                .append(contentHash);

        return "AWS4-HMAC-SHA256\n" +
                requestDate + "\n" +
                date + "/" + region + "/" + service + "/" + aws4Request + "\n" +
                doHex(hashedCanonicalRequest.toString());
    }

    private static String generateSignature(String secretAccessKey, String stringToSign, String date, String region, String service, String aws4Request) throws Exception {
        byte[] kSecret = ("AWS4" + secretAccessKey).getBytes(StandardCharsets.UTF_8);
        byte[] kDate = doHmacSHA256(kSecret, date);
        byte[] kRegion = doHmacSHA256(kDate, region);
        byte[] kService = doHmacSHA256(kRegion, service);
        byte[] signatureKey = doHmacSHA256(kService, aws4Request);
        byte[] authSignature = doHmacSHA256(signatureKey, stringToSign);
        return doBytesToHex(authSignature);
    }

    private static String doHex(String data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] digest = messageDigest.digest(data.getBytes(StandardCharsets.UTF_8));
            return String.format("%064x", new java.math.BigInteger(1, digest));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.", e);
        }
    }

    private static byte[] doHmacSHA256(byte[] key, String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    private static String doBytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static Map<String, String> parseQueryParams(String queryString) {
        Map<String, String> queryParams = new HashMap<>();
        if (queryString != null && !queryString.isEmpty()) {
            String[] queryParamsArray = queryString.split("&");
            for (String param : queryParamsArray) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 1) {
                    queryParams.put(keyValue[0], "");
                } else if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return queryParams;
    }


    public static Authorization fromHeader(String authorization) {
        String[] parts = authorization.trim().split(",");
        String[] credentials = parts[0].split("=")[1].split("/");
        return new Authorization.Builder()
                .accessKey(credentials[0])
                .date(credentials[1])
                .region(credentials[2])
                .service(credentials[3])
                .aws4Request(credentials[4])
                .signedHeader(parts[1].split("=")[1])
                .signature(parts[2].split("=")[1])
                .build();
    }

    public static Authorization fromRequest(HttpServletRequest request) {
        String[] credentials = request.getParameter("X-Amz-Credential").split("\\/");
        return new Authorization.Builder()
                .accessKey(credentials[0])
                .date(credentials[1])
                .region(credentials[2])
                .service(credentials[3])
                .aws4Request(credentials[4])
                .signedHeaders(request.getParameter("X-Amz-SignedHeaders").split("\\;"))
                .signature(request.getParameter("X-Amz-Signature"))
                .expires(request.getParameter("X-Amz-Expires"))
                .build();
    }


    public static class Authorization {
        private String accessKey;
        private String date;
        private String region;
        private String service;
        private String aws4Request;
        private String[] signedHeaders;
        private String signedHeader;
        private String signature;
        private String expires;

        private Authorization() {
        }


        public static class Builder {
            private final Authorization auth = new Authorization();

            public Builder accessKey(String accessKey) {
                auth.accessKey = accessKey;
                return this;
            }

            public Builder date(String date) {
                auth.date = date;
                return this;
            }

            public Builder region(String region) {
                auth.region = region;
                return this;
            }

            public Builder service(String service) {
                auth.service = service;
                return this;
            }

            public Builder aws4Request(String aws4Request) {
                auth.aws4Request = aws4Request;
                return this;
            }

            public Builder signedHeaders(String[] signedHeaders) {
                auth.signedHeaders = signedHeaders;
                auth.signedHeader = String.join(";", signedHeaders);
                return this;
            }

            public Builder signedHeader(String signedHeader) {
                auth.signedHeader = signedHeader;
                auth.signedHeaders = signedHeader.split(";");
                return this;
            }

            public Builder signature(String signature) {
                auth.signature = signature;
                return this;
            }

            public Builder expires(String expires) {
                auth.expires = expires;
                return this;
            }

            public Authorization build() {
                return auth;
            }
        }
    }
}
