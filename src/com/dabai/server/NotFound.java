package com.dabai.server;

import java.io.File;
import java.io.OutputStream;

import com.dabai.exception.ServerException;

public class NotFound extends Response {
	private PrintTool pt = PrintTool.getInstance();
	
	public NotFound(String firstLine) {
		super(firstLine);
	}


	@Override
	public void sendMessage(OutputStream os, File file) throws ServerException{
		try{
			String message = this.sendFirstLine() + this.sendRequire();
			this.sendBody(message, file, os);
			os.close();
		}catch(Exception e){
			throw new ServerException(e.getMessage());
		}
	}

	@Override
	public String sendFirstLine() {
		String message = "HTTP/1.1 404 Not Found\r\n";
		return message;
	}

	@Override
	public String sendBody(String message, File file, OutputStream os) {
		// 获取当前项目绝对路径,会加上前缀file:/
		String projectPath = ClassLoader.getSystemResource("").toString();
		// 获取404页面路径
		String FileName = (projectPath + "resource/notFound.html").substring(6);
		file = new File(FileName);
		pt.printScreenWithFile(message, file, os);
		return null;
	}

}
