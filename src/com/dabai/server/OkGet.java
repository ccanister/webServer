package com.dabai.server;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.dabai.exception.ServerException;

public class OkGet extends Response {
	private PrintTool pt = PrintTool.getInstance();
	
	public OkGet(String firstLine) {
		super(firstLine);
	}

	@Override
	public void sendMessage(OutputStream os, File file) throws ServerException{
		try {
			String message = this.sendFirstLine() + this.sendRequire();
			this.sendBody(message,file, os);
			os.close();
		} catch (IOException e) {
			throw new ServerException(e.getMessage());
		}
	}
	
	
	@Override
	public String sendFirstLine() {
		String message = "HTTP/1.1 200 OK\r\n";
		return message;
	}

	@Override
	public String sendBody(String message,File file,OutputStream os) {
		if(file!=null){
			pt.printScreenWithFile(message,file, os);
		}
		return message;
	}
	
 }
