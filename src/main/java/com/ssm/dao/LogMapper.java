package com.ssm.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.ssm.pojo.Log;

public interface LogMapper {
	void insertLog(@Param("file")String fileName, @Param("detail")String result);
	ArrayList<Log> getAllLog();
}
