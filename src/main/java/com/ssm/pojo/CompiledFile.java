package com.ssm.pojo;

public class CompiledFile {
	private String fileName;
	private String fileSize;
	
	public CompiledFile(String fileName, long l){
		this.fileName = fileName;
		String[] tmp = {"Byte", "KB", "MB", "GB"};
		int flag = 0;
		if(l/1024 > 0) {
			l= l/1024;
			flag++;
		}
		this.fileSize = l + tmp[flag];
	}
	public String getFileName() {
        return fileName;
    }
	public String getFileSize() {
        return fileSize;
    }
	public void setFileName(String fileName) {
        this.fileName = fileName;
    }
	public void setFileSize(long fileSize) {
        this.fileSize = fileSize+"";
    }
}
