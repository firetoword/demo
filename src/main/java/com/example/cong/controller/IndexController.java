package com.example.cong.controller;

import com.example.cong.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@RestController
@RequestMapping("/cong")
public class IndexController {

    @Autowired
    private TestService testService;

    @RequestMapping("/index")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/getTest")
    public String getTest() throws Exception {
//        ClassPathResource cpr = new ClassPathResource("ca.jks");
//        String absolutePath = new cn.hutool.core.io.resource.ClassPathResource("ca.jks").getAbsolutePath();
        FileOutputStream fileOutputStream = new FileOutputStream(" /static/test1.pdf");
        System.out.println(fileOutputStream);
        return fileOutputStream.toString();
    }


}
