package com.example.cong.controller;

import ac.cn.iie.bc.Blockchain;
import ac.cn.iie.bc.params.BlockchainDecryptionItem;
import ac.cn.iie.bc.params.BlockchainEncryptionItem;
import ac.cn.iie.bc.params.ExtItem;
import ac.cn.iie.bc.response.BlockchainCheckResponse;
import ac.cn.iie.bc.response.BlockchainQueryResponse;
import ac.cn.iie.bc.response.BlockchainUploadResponse;
import cn.hutool.http.HttpResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/blockchain")
public class BlockchainController {
    static String endpoint = "10.92.224.23:30004";
    static String caPath = "/D:/jar/certificate/ca.jks";
    static String certPath = "/D:/jar/certificate/cert.jks";
    static String caPwd = "123456";
    static String certPwd = "123456";
    static String appSecretID = "b00f332f-d933-447a-b816-d12cce64999a";
    static String appSecretKey= "94a9e6dc1cd7881217830d3394adcf9feaa94052dd6c38ee5e259455f4d84b9c";
    static String a;
    BlockchainUploadResponse uploadResponse;
    static Blockchain blockchain;
    static {
        blockchain = new Blockchain(endpoint, caPath, certPath, caPwd, certPwd);
    }



    @RequestMapping("/test")
    public String upload() throws FileNotFoundException {
//        blockchain = new Blockchain(endpoint, caPath, certPath, caPwd, certPwd);

        BlockchainEncryptionItem blockchainEncryptionItem = new BlockchainEncryptionItem(Blockchain.Algorithm.NULL, null);
        File file = new File("D:\\test1.pdf");
        ExtItem extItem = new ExtItem();
        extItem.setYWY("营销域");
        extItem.setYWXT("点对点业务系统");
        extItem.setSJBM("合同");
        uploadResponse = blockchain.uploadFile(appSecretID, appSecretKey, file, Blockchain.Mode.DIRECT, blockchainEncryptionItem, extItem);
        String accessid = "s";
        if (uploadResponse != null && uploadResponse.getStatus()) {
            accessid = uploadResponse.getAccessid();
            System.out.println(uploadResponse.getStatusContent());
            System.out.println("访问令牌：" + accessid);
        }
        return accessid;
    }

    @RequestMapping("/getFile")
    public String getFile (String accessid, HttpServletResponse response) throws IOException {
        BlockchainDecryptionItem blockchainDecryptionItem = new BlockchainDecryptionItem("");
        File output = new File("output");
        OutputStream outputStream = response.getOutputStream();
        BlockchainQueryResponse queryResponse = blockchain.queryFile(appSecretID, appSecretKey, accessid, output, Blockchain.Mode.DIRECT, blockchainDecryptionItem);
        if (queryResponse != null && queryResponse.getStatus()) {
            System.out.println(queryResponse.getStatusContent());
        }
        return queryResponse.getStatusContent();
    }
    @RequestMapping("/check")
    public String check (String accessid){
        File file = new File("D:\\test1.pdf");
        BlockchainCheckResponse checkResponse = blockchain.checkFile(appSecretID, appSecretKey, file, accessid);
        if (checkResponse != null && checkResponse.getStatus()) {
            boolean checkResult = checkResponse.getResult();
            System.out.println(checkResponse.getStatusContent());
            System.out.println("校核结果：" + checkResult);
        }
        return checkResponse.getStatusContent();
    }
}
