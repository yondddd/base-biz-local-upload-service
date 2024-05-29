package com.ruijing.base.local.upload.web.s3.response;

import com.ruijing.base.local.upload.constant.S3Headers;
import com.ruijing.base.local.upload.enums.ApiErrorEnum;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author yond
 * @date 5/28/2024
 * @description response util
 */
public class ResponseUtil {

    public static void writeErrorResponse(final ApiErrorEnum errorEnum) throws JAXBException, IOException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setRequestId(request.getHeader(S3Headers.AmzRequestID));
        errorResponse.setHostId(request.getHeader(S3Headers.AmzRequestHostID));
        errorResponse.setCode(errorEnum.getCode());
        errorResponse.setMessage(errorEnum.getDescription());
        errorResponse.setEc(errorEnum.getEc());
        JAXBContext context = JAXBContext.newInstance(ErrorResponse.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter writer = new StringWriter();
        marshaller.marshal(errorResponse, writer);
        String xmlData = writer.toString();

        // 设置响应的内容类型和状态
        response.setContentType(MediaType.APPLICATION_XML_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);

        // 将 XML 数据写入响应的输出流
        response.getWriter().write(xmlData);

    }

    // write http
}
