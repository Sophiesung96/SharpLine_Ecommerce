package com.example.demo01.src.Configuration.Exporter;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AbstractExporter {


    public void setResponseHeader(String contentType, String extension, HttpServletResponse response, String filename) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        String file = filename + timestamp + extension;
        try {
            response.setContentType(contentType);
            String headerkey = "Content-Disposition";
            String headerValue = "attachment; filename=" + file;
            response.setHeader(headerkey, headerValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;

    }
}
