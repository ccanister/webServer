package com.dabai.server;

import java.io.File;
import java.io.OutputStream;

public class InnerError extends Response {

	private PrintTool pt = PrintTool.getInstance();
	private String[] res = new String[3];
	private String excMes = null;
	
	public InnerError(String firstLine) {
		super(firstLine);
	}
	
	public String getExcMes() {
		return excMes;
	}

	public void setExcMes(String excMes) {
		this.excMes = excMes;
	}

	@Override
	public void sendMessage(OutputStream os, File file) {
		try{
			String message = this.sendFirstLine() + this.sendRequire();
			this.sendBody(message, file, os);
			//输出错误信息
			pt.printScreenWithString(excMes, os);
			os.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public String sendFirstLine() {
		res[0] = "HTTP/1.1 500 Internal Server Error\r\n";
		return res[0];
	}

	@Override
	public String sendBody(String message, File file, OutputStream os) {
		// 获取当前项目绝对路径,会加上前缀file:/
		String projectPath = ClassLoader.getSystemResource("").toString();
		// 获取500页面路径
		String FileName = (projectPath + "resource/InnerError.html").substring(6);
		file = new File(FileName);
//		if(excMes!=null)
//			pt.writeToFile(excMes, file);
		pt.printScreenWithFile(message, file, os);
		return null;
	}

	@Override
	String sendRequire() {
		res[1] = "Content-Type: *\r\n\r\n";
		return res[1];
	}
}
