package com.dabai.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Date;
import java.util.concurrent.Callable;

public class RequestThread implements Callable<Void> {

	private Socket socket;
	private BufferedReader br;
	private OutputStream os;

	public RequestThread(Socket socket) {
		this.socket = socket;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Void call() {
		String url = this.getUrl();
		this.handleUrl(url, socket);
		return null;
	}

	private String getUrl() {
 		String method = null;
		try {
			// 读取起始行
			String startLine = br.readLine();
			if (startLine == null) {
				throw new Exception();
			}
			// 对url进行解码成utf-8
			startLine = URLDecoder.decode(startLine, "utf-8");
			//得到方法名
			method = startLine.substring(0,startLine.indexOf(" "));
			if(method.equalsIgnoreCase("Get")){
				// 删去方法名
				startLine = startLine.substring(method.length(), startLine.length());
				// 删去http版本协议
				return startLine.substring(0, startLine.lastIndexOf("HTTP"));
			} else {
				//暂时不支持get意外的请求方法，所以此时排除501的代码
				new NotImpl(" 501 Not Implement ").sendMessage(os, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private void handleUrl(String url, Socket socket) {
		String fileUrl = url.split("\\?")[0];
		// 删去url中/
		fileUrl = fileUrl.substring(1, fileUrl.length());
		// 对空格进行解析替代
		fileUrl.replaceAll("%20", " ");
		System.out.println(fileUrl);
		File file = new File(fileUrl);
		try {
			os = socket.getOutputStream();
			if (!file.exists()) {
				// 抛出404错误
				new NotFound(" 404 Not Found ").sendMessage(os, null);
			} else {
				//返回请求文件
				new OkGet(" 200 Ok ").sendMessage(os, file);
			}
		} catch (Exception e) {
			// 抛出500错误
			InnerError ie = new InnerError(" 500 Internal Server Error ");
			//设置错误消息
			StringBuffer ExcMes = new StringBuffer();
			Date now = new Date();
			ExcMes.append(now.toString());
			for(StackTraceElement stack:e.getStackTrace()){
				ExcMes.append(stack.getClassName() + ":");
				ExcMes.append(stack.getMethodName() + " ");
				ExcMes.append(stack.getLineNumber() + "\n\n");
			}
			//TODO调整输出格式
			ie.setExcMes(ExcMes.toString());
			ie.sendMessage(os, null);
		}
	}
}
