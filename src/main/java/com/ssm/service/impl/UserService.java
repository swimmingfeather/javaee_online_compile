package com.ssm.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssm.dao.LogMapper;
import com.ssm.dao.UserMapper;
import com.ssm.pojo.User;
import com.ssm.service.IUserService;
import com.ssm.pojo.Log;

@Service("userService")
public class UserService implements IUserService {

    @Resource
    private UserMapper userDao;
    @Resource
    private LogMapper logDao;

    public User getUserById(int userId) {
        return this.userDao.selectByPrimaryKey(userId);
    }

	@Override
	public void insertLog(MultipartFile file, String result) {
		this.logDao.insertLog(file.getOriginalFilename(), result);
		
	}

	@Override
	public ArrayList<Log> getAllLog() {
		return this.logDao.getAllLog();
		 
	}

}