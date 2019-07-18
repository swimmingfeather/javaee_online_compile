package com.ssm.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssm.pojo.CompiledFile;
import com.ssm.service.IFileService;
import com.ssm.service.IUserService;

@Controller
@RequestMapping("/file")
public class FileController {
	@Resource
	private IFileService fileService;
	@Resource
	private IUserService userService;
	
	@RequestMapping("/fileUpLoad")
	public @ResponseBody String saveAndCompileFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		System.out.println("开始上传文件");
		
		String contentPath = request.getSession().getServletContext().getRealPath("/");
		
		this.fileService.saveFile(file, contentPath);
		String result = this.fileService.compileFile(file, contentPath);
		this.userService.insertLog(file, result);
		return "1";
	}
	@RequestMapping("/fileDownLoad")
	public String downloadPage(HttpServletRequest request, Model model) {
		String contentPath = request.getSession().getServletContext().getRealPath("/");
		ArrayList<CompiledFile> fileList = this.fileService.getAllFile(contentPath);
		model.addAttribute("fileList", fileList);
		return "download";
	}
	
	@RequestMapping("/getFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String filePath = request.getSession().getServletContext().getRealPath("build/")+ request.getParameter("name");  

        this.fileService.returnFile(response, filePath);
        
	}
}
