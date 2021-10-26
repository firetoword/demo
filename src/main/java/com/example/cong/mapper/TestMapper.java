package com.example.cong.mapper;

import com.example.cong.bean.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    List<Test> getAllTest() throws Exception;
}
