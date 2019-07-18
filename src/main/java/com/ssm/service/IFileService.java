package com.ssm.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.ssm.pojo.CompiledFile;

public interface IFileService {

	public void saveFile(MultipartFile file, String contentPath);
	public String compileFile(MultipartFile file, String contentPath);
	public ArrayList<CompiledFile> getAllFile(String contentPath);
	public void returnFile(HttpServletResponse response, String filePath);
	
}
