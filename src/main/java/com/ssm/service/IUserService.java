package com.ssm.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.ssm.pojo.Log;
import com.ssm.pojo.User;

public interface IUserService {

	public User getUserById(int userId);

	public void insertLog(MultipartFile file, String result);

	public ArrayList<Log> getAllLog();
}