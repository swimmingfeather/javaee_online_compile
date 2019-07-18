package com.ssm.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.ssm.pojo.CompiledFile;
import com.ssm.service.IFileService;
@Service("fileService")
public class FileService implements IFileService{

	
	@Override
	public void saveFile(MultipartFile file, String contentPath) {

		String fileName = file.getOriginalFilename();
		this.createDir(contentPath+"src"+File.separator);
		this.createDir(contentPath+"build"+File.separator);
		File fileLocal = new File(contentPath + "src"+File.separator + fileName);
		
		try {
			file.transferTo(fileLocal);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO异常");
			e.printStackTrace();
		}
	}

	@Override
	public String compileFile(MultipartFile file, String contentPath) {
		String buildPath = contentPath+"build"+File.separator;
		String srcPath = contentPath+"src"+File.separator;
		List<String> command = Arrays.asList("javac","-d",buildPath, srcPath+file.getOriginalFilename());
		StringBuilder sb = new StringBuilder();
		
		try {
			ProcessBuilder compileProcess = new ProcessBuilder();
			Process process = compileProcess.command(command).start();
			BufferedReader bufr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String tmp;
			while((tmp = bufr.readLine()) != null) {
				System.out.println("1"+tmp);
				sb.append(tmp);
			}
		} catch (IOException e) {
			System.out.println("IO错误");
			e.printStackTrace();
		}
		if(sb.length() == 0){
			return "success";
		}
		return sb.toString();
		
	}
	private void createDir(String dir) {
		File fileLocal = new File(dir);
		if(!fileLocal.exists())
			fileLocal.mkdirs();
	}

	@Override
	public ArrayList<CompiledFile> getAllFile(String contentPath) {
		ArrayList<CompiledFile> fileList = new ArrayList<CompiledFile>();
		String path = contentPath + "build" +File.separator;
		File file = new File(path);
		File[] fs = file.listFiles();
		for(File f:fs){
			if(!f.isDirectory()) {
				fileList.add(new CompiledFile(f.getName(), f.length()));
			}
		}

		
		return fileList;
	}

	@Override
	public void returnFile(HttpServletResponse response, String filePath) {
		try {
			File file = new File(filePath);
			InputStream bis = new BufferedInputStream(new FileInputStream(file));  
	        //假如以中文名下载的话  
	        String filename = file.getName();  
	        //转码，免得文件名中文乱码  
	        filename = URLEncoder.encode(filename,"UTF-8");  
	        //设置文件下载头  
	        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
	        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型    
	        response.setContentType("multipart/form-data");   
	        BufferedOutputStream out;
			out = new BufferedOutputStream(response.getOutputStream());
			int len = 0;  
	        while((len = bis.read()) != -1){  
	            out.write(len);  
	            out.flush();  
	        }  
	        out.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
