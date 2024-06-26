package com.ruijing.base.local.upload.web.s3.server.controller;


import com.ruijing.base.local.upload.model.*;
import com.ruijing.base.local.upload.service.S3Service;
import com.ruijing.base.local.upload.util.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@RestController
//@RequestMapping("/s3")
//api参考地址https://docs.aws.amazon.com/AmazonS3/latest/API/
public class S3Controller {

    @Autowired
    private S3Service s3Service;


    @GetMapping("/")
    public ResponseEntity<String> listBuckets() throws Exception {
        String xml = "";
        ListBucketsResult result = new ListBucketsResult(s3Service.listBuckets());
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("ListAllMyBucketsResult");
        Element owner = root.addElement("Owner");
        Element id = owner.addElement("ID");
        id.setText("20230529");
        Element displayName = owner.addElement("DisplayName");
        displayName.setText("admin");
        Element buckets = root.addElement("Buckets");
        for (Bucket item : result.getBuckets()) {
            Element bucket = buckets.addElement("Bucket");
            Element name = bucket.addElement("Name");
            name.setText(item.getName());
            Element creationDate = bucket.addElement("CreationDate");
            creationDate.setText(item.getCreationDate());
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        StringWriter out;
        out = new StringWriter();
        XMLWriter writer = new XMLWriter(out, format);
        writer.write(doc);
        writer.close();
        xml = out.toString();
        out.close();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(xml);
    }


    @RequestMapping(value = "/{headBucket}", method = RequestMethod.HEAD)
    public ResponseEntity<Object> headBucket(@PathVariable(value = "headBucket") String headBucket) throws Exception {
        headBucket = URLDecoder.decode(headBucket, "utf-8");
        if (s3Service.headBucket(headBucket)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{deleteBucket}")
    public ResponseEntity<String> deleteBucket(@PathVariable String deleteBucket) throws Exception {
        deleteBucket = URLDecoder.decode(deleteBucket, "utf-8");
        s3Service.deleteBucket(deleteBucket);
        return ResponseEntity.noContent().build();
    }

    // Object相关接口
    @GetMapping("/{bucketName}")
    public ResponseEntity<String> listObjects(@PathVariable String bucketName, HttpServletRequest request) throws Exception {
        bucketName = URLDecoder.decode(bucketName, "utf-8");
        String xml = "";
        String prefix = ConvertOp.convert2String(request.getParameter("prefix"));
        List<S3Object> s3ObjectList = s3Service.listObjects(bucketName, prefix);
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("ListObjectsResult");
        Element name = root.addElement("Name");
        name.setText(bucketName);
        Element prefixElement = root.addElement("Prefix");
        prefixElement.setText(prefix);
        Element isTruncated = root.addElement("IsTruncated");
        isTruncated.setText("false");
        Element maxKeys = root.addElement("MaxKeys");
        maxKeys.setText("100000");
        for (S3Object s3Object : s3ObjectList) {
            Element contents = root.addElement("Contents");
            Element key = contents.addElement("Key");
            key.setText(s3Object.getKey());
            if (!StringUtil.isEmpty(s3Object.getMetadata().getLastModified())) {
                Element lastModified = contents.addElement("LastModified");
                lastModified.setText(s3Object.getMetadata().getLastModified());
                Element size = contents.addElement("Size");
                size.setText(s3Object.getMetadata().getContentLength() + "");
            }
        }

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        StringWriter out;
        out = new StringWriter();
        XMLWriter writer = new XMLWriter(out, format);
        writer.write(doc);
        writer.close();
        xml = out.toString();
        out.close();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(xml);
    }

    @RequestMapping(value = "/{headObjectBucketName}/**", method = RequestMethod.HEAD)
    public ResponseEntity<Object> headObject(@PathVariable String headObjectBucketName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        headObjectBucketName = URLDecoder.decode(headObjectBucketName, "utf-8");
        String pageUrl = URLDecoder.decode(request.getRequestURI(), "utf-8");
        if (pageUrl.indexOf("\\?") >= 0) {
            pageUrl = pageUrl.split("\\?")[0];
        }
        String objectKey = pageUrl.replace(request.getContextPath() + "/s3/" + headObjectBucketName + "/", "").replace("/metadata", "");
        HashMap<String, String> headInfo = s3Service.headObject(headObjectBucketName, objectKey);
        if (headInfo.containsKey("NoExist")) {
            return ResponseEntity.notFound().build();
        } else {
            for (String key : headInfo.keySet()) {
                response.addHeader(key, headInfo.get(key));
            }
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/{putObjectBucketName}/**")
    public ResponseEntity<String> putObject(@PathVariable String putObjectBucketName, HttpServletRequest request) throws Exception {
        putObjectBucketName = URLDecoder.decode(putObjectBucketName, "utf-8");
        String pageUrl = URLDecoder.decode(request.getRequestURI(), "utf-8");
        if (pageUrl.indexOf("\\?") >= 0) {
            pageUrl = pageUrl.split("\\?")[0];
        }
        String objectKey = pageUrl.replace(request.getContextPath() + "/s3/" + putObjectBucketName + "/", "");
        String copySource = URLDecoder.decode(request.getHeader("x-amz-copy-source"), "utf-8");
        if (StringUtil.isEmpty(copySource)) {
            s3Service.putObject(putObjectBucketName, objectKey, request.getInputStream());
            return ResponseEntity.ok().build();
        } else {
            if (copySource.indexOf("\\?") >= 0) {
                copySource = copySource.split("\\?")[0];
            }
            String[] copyList = copySource.split("\\/");
            String sourceBucketName = "";
            for (String item : copyList) {
                if (!StringUtil.isEmpty(item)) {
                    sourceBucketName = item;
                    break;
                }
            }

            StringBuilder result = new StringBuilder();
            for (int i = 1; i < copyList.length; i++) {
                result.append(copyList[i]).append("/");
            }
            String sourceObjectKey = result.toString();
            s3Service.copyObject(sourceBucketName, sourceObjectKey, putObjectBucketName, objectKey);

            String xml = "";
            Document doc = DocumentHelper.createDocument();
            Element root = doc.addElement("CopyObjectResult");
            Element lastModified = root.addElement("LastModified");
            lastModified.setText(DateUtil.getUTCDateFormat());
            Element eTag = root.addElement("ETag");
            eTag.setText(EncryptUtil.encryptByMD5(copySource));
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf-8");
            StringWriter out;
            out = new StringWriter();
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(doc);
            writer.close();
            xml = out.toString();
            out.close();
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(xml);
        }
    }

    @GetMapping("/{getObjectBucketName}/**")
    public void getObject(@PathVariable String getObjectBucketName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        getObjectBucketName = URLDecoder.decode(getObjectBucketName, "utf-8");
        String pageUrl = URLDecoder.decode(request.getRequestURI(), "utf-8");
        if (pageUrl.indexOf("\\?") >= 0) {
            pageUrl = pageUrl.split("\\?")[0];
        }
        String objectKey = pageUrl.replace(request.getContextPath() + "/s3/" + getObjectBucketName + "/", "");
        S3ObjectInputStream objectStream = s3Service.getObject(getObjectBucketName, objectKey);
        response.setContentType(objectStream.getMetadata().getContentType());
        response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(objectStream.getMetadata().getFileName(), "utf-8"));
        response.setCharacterEncoding("utf-8");
        response.setContentLength(ConvertOp.convert2Int(objectStream.getMetadata().getContentLength()));
        byte[] buff = new byte[ConvertOp.convert2Int(objectStream.getMetadata().getContentLength())];
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            bufferedInputStream = new BufferedInputStream(objectStream);
            int i = 0;
            while ((i = bufferedInputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, i);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @DeleteMapping("/{deleteObjectBucketName}/**")
    public ResponseEntity<String> deleteObject(@PathVariable String deleteObjectBucketName, HttpServletRequest request) throws Exception {
        deleteObjectBucketName = URLDecoder.decode(deleteObjectBucketName, "utf-8");
        String pageUrl = URLDecoder.decode(request.getRequestURI(), "utf-8");
        if (pageUrl.indexOf("\\?") >= 0) {
            pageUrl = pageUrl.split("\\?")[0];
        }
        String objectKey = pageUrl.replace(request.getContextPath() + "/s3/" + deleteObjectBucketName + "/", "");
        s3Service.deleteObject(deleteObjectBucketName, objectKey);
        return ResponseEntity.noContent().build();
    }


    // 分片上传
    @RequestMapping(value = "/{createMultipartUploadBucketName}/**", method = RequestMethod.POST, params = "uploads")
    public ResponseEntity<Object> createMultipartUpload(@PathVariable String createMultipartUploadBucketName, HttpServletRequest request) throws Exception {
        createMultipartUploadBucketName = URLDecoder.decode(createMultipartUploadBucketName, "utf-8");
        String pageUrl = URLDecoder.decode(request.getRequestURI(), "utf-8");
        if (pageUrl.indexOf("\\?") >= 0) {
            pageUrl = pageUrl.split("\\?")[0];
        }
        String objectKey = pageUrl.replace(request.getContextPath() + "/s3/" + createMultipartUploadBucketName + "/", "");
        InitiateMultipartUploadResult result = s3Service.initiateMultipartUpload(createMultipartUploadBucketName, objectKey);

        String xml = "";
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("InitiateMultipartUploadResult");
        Element bucket = root.addElement("Bucket");
        bucket.setText(createMultipartUploadBucketName);
        Element key = root.addElement("Key");
        key.setText(objectKey);
        Element uploadId = root.addElement("UploadId");
        uploadId.setText(result.getUploadId());
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        StringWriter out;
        out = new StringWriter();
        XMLWriter writer = new XMLWriter(out, format);
        writer.write(doc);
        writer.close();
        xml = out.toString();
        out.close();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(xml);
    }

    @RequestMapping(value = "/{uploadPartBucketName}/**", method = RequestMethod.PUT, params = {"partNumber", "uploadId"})
    public ResponseEntity<String> uploadPart(@PathVariable String uploadPartBucketName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        uploadPartBucketName = URLDecoder.decode(uploadPartBucketName, "utf-8");
        String pageUrl = URLDecoder.decode(request.getRequestURI(), "utf-8");
        if (pageUrl.indexOf("\\?") >= 0) {
            pageUrl = pageUrl.split("\\?")[0];
        }
        String objectKey = pageUrl.replace(request.getContextPath() + "/s3/" + uploadPartBucketName + "/", "");
        int partNumber = ConvertOp.convert2Int(request.getParameter("partNumber"));
        String uploadId = request.getParameter("uploadId");
        PartETag eTag = s3Service.uploadPart(uploadPartBucketName, objectKey, partNumber, uploadId, request.getInputStream());
        response.addHeader("ETag", eTag.geteTag());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{completeMultipartUploadBucketName}/**", method = RequestMethod.POST, params = "uploadId")
    public ResponseEntity<String> completeMultipartUpload(@PathVariable String completeMultipartUploadBucketName, HttpServletRequest request) throws Exception {
        completeMultipartUploadBucketName = URLDecoder.decode(completeMultipartUploadBucketName, "utf-8");
        String pageUrl = URLDecoder.decode(request.getRequestURI(), "utf-8");
        if (pageUrl.indexOf("\\?") >= 0) {
            pageUrl = pageUrl.split("\\?")[0];
        }
        String objectKey = pageUrl.replace(request.getContextPath() + "/s3/" + completeMultipartUploadBucketName + "/", "");
        String uploadId = request.getParameter("uploadId");
        List<PartETag> partETags = new ArrayList<>();

        SAXReader reader = new SAXReader();
        Document bodyDoc = reader.read(request.getInputStream());
        Element bodyRoot = bodyDoc.getRootElement();
        List<Element> elementList = bodyRoot.elements("Part");
        for (Element element : elementList) {
            int partNumber = ConvertOp.convert2Int(element.element("PartNumber").getText());
            String eTag = element.element("ETag").getText();
            PartETag partETag = new PartETag(partNumber, eTag);
            partETags.add(partETag);
        }
        CompleteMultipartUploadResult result = s3Service.completeMultipartUpload(completeMultipartUploadBucketName, objectKey, uploadId, new CompleteMultipartUpload(partETags));
        String xml = "";
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("CompleteMultipartUploadResult");
        Element location = root.addElement("Location");
        location.setText(CommonUtil.getApiPath() + "s3/" + completeMultipartUploadBucketName + "/" + objectKey);
        Element bucket = root.addElement("Bucket");
        bucket.setText(completeMultipartUploadBucketName);
        Element key = root.addElement("Key");
        key.setText(objectKey);
        Element etag = root.addElement("ETag");
        etag.setText(result.geteTag());

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        StringWriter out;
        out = new StringWriter();
        XMLWriter writer = new XMLWriter(out, format);
        writer.write(doc);
        writer.close();
        xml = out.toString();
        out.close();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(xml);
    }
}
