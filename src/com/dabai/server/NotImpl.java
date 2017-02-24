package com.dabai.server;

import java.io.File;
import java.io.OutputStream;

public class NotImpl extends Response{
	private PrintTool pt = PrintTool.getInstance();

	public NotImpl(String firstLine) {
		super(firstLine);
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
