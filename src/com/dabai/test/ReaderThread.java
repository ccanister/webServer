package com.dabai.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ReaderThread implements Runnable {
	private String message;
	private PrintWriter pw;
	private BufferedReader br;
	

	public ReaderThread(InputStream is,OutputStream os){
		br = new BufferedReader(new InputStreamReader(is));
		this.pw = new PrintWriter(os);
	}

	@Override
	public void run() {
		try {
			
			while(true){
				message = br.readLine();
				pw.println(message);
				pw.flush();	//如果使用wirte方法必须在后面加上一个\n或者println
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
