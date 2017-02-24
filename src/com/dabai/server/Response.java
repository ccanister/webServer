package com.dabai.server;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.dabai.exception.ServerException;

public abstract class Response {
	
	private String firstLine;
	
	public Response(String firstLine) {
		this.firstLine = firstLine;
	}

	public void sendMessage(OutputStream os,File file) throws ServerException{
		try {
			String message = this.sendFirstLine() + this.sendRequire() +
					this.sendBody(null,file,os);
			PrintTool.getInstance().printScreenWithString(message, os);
			os.close();
		} catch (IOException e) {
			throw new ServerException(e.getMessage());
		}
	} 
	
	/**
	 * 发送起始行
	 */
	String sendFirstLine() {
		firstLine =  "HTTP/1.1 " + firstLine + " \r\n";
		return firstLine;
	}

	/**
	 * 发送首部
	 * 
	 * @param pw
	 */
	String sendBody(String messge,File file,OutputStream os) {
		return null;
	}

	/**
	 * 发送主体
	 * 
	 * @param os
	 */
	String sendRequire() {
		String message = "Content-Type: *\r\n";
		return message;
	}
}
