package com.example.cong.service.impl;

import com.example.cong.bean.Test;
import com.example.cong.mapper.TestMapper;
import com.example.cong.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;


    @Override
    public List<Test> getAllTest() throws Exception {
        //1
        //2
        //3
        //4
        //5
        return testMapper.getAllTest();
        //6
    }
}
