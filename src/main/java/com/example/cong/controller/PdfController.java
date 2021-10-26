package com.example.cong.controller;

import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Base64;

@Controller
@RequestMapping("/pdf")
public class PdfController {

    @RequestMapping("/toIndex")
    public String toIndex() throws Exception {

        return "pdfIndex";
    }
    @RequestMapping("/getPdfBase64")
    @ResponseBody
    public String getPdfBase64()throws Exception{
        File file = new File("D:\\点的点能源零售业务系统-新-打标签.pdf");
        InputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = IOUtils.toByteArray(fileInputStream);
        String encoded = "data:application/pdf;base64,";
        encoded += Base64.getEncoder().encodeToString(bytes);

        return  encoded;
    }
    @RequestMapping("/toPdfJsIndex")
    public String toPdfJsIndex() throws Exception {

        return "pdfjsIndex";
    }
    @RequestMapping("/getPdfjsBase64")
    @ResponseBody
    public String getPdfjsBase64()throws Exception{
        File file = new File("D:\\点的点能源零售业务系统-新-打标签.pdf");
        InputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = IOUtils.toByteArray(fileInputStream);
        String encoded = "data:application/pdf;base64,";
        encoded += Base64.getEncoder().encodeToString(bytes);

        return  encoded;
    }
}
