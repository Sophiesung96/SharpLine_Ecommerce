package com.example.demo01.src.Filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

public class LogApiFilter extends OncePerRequestFilter {
    private static Logger log = LoggerFactory.getLogger(LogApiFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);
        //Print API
        logAPI(request, response);
        //Print Requestbody and ResponseBody
        logBody(contentCachingRequestWrapper, contentCachingResponseWrapper);
        //Using copyBodyToResponse() to copy the response body and
        // send it back to the original response's output stream.
        contentCachingResponseWrapper.copyBodyToResponse();


    }

    //Get Json Request and response data
    private String getContent(byte[] content) {
        String body = new String(content, StandardCharsets.UTF_8);

        return body.replaceAll("[\n\t]", "");
    }

    //Get byte [] from Wrapper and turn it into a String using getContent()
    private void logBody(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) {
        String requestBody = getContent(requestWrapper.getContentAsByteArray());
        String responseBody = getContent(responseWrapper.getContentAsByteArray());
        log.info("Request:{}", requestBody);
        log.info("Response:{}", responseBody);


    }

    private void logAPI(HttpServletRequest request, HttpServletResponse response) {
        int httpstatus = response.getStatus();
        String httpmethod = request.getMethod();
        String url = request.getRequestURI();
        String params = request.getQueryString();
        if (params != null) {
            url += "?" + params;
        }
        log.info("{} {} {}", httpstatus, httpmethod, url);


    }
}

