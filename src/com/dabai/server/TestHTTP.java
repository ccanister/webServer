package com.dabai.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TestHTTP implements Runnable{
	public void doinit() throws Exception {
		  Socket s = new Socket("127.0.0.1", 8088);
		  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s
		    .getOutputStream()));
		  bw.write("get http://localhost:8088/d:/test.pdf HTTP/1.1");
		  bw.newLine();
		  bw.write("Content-Type: text/html");
		  bw.newLine();
//		  bw.write("Content-Type: text/html");
//		  bw.newLine();
		  bw.flush();
		  BufferedReader br = new BufferedReader(new InputStreamReader(s
		    .getInputStream()));
		  String str = null;
		  while ((str = br.readLine()) != null) {
		   System.out.println(str);
		  }
		  bw.close();
		  br.close();
		  s.close();
		 }
		 
		 public static void main(String[] args) throws Exception {
			 for(int i=0;i<10000;i++){
				 new Thread(new TestHTTP()).start();
			 }
			 
		 }

		@Override
		public void run() {
			try {
				this.doinit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
}
