package com.dabai.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	  public final static int PORT = 8088;
	  public final static String IP = "127.0.0.1";
	  private final static int BACKLOG = 300;
	  private final static int MaxConn = 50;
	  
	  public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(MaxConn);
		 
		try(ServerSocket ss = new ServerSocket(PORT,BACKLOG,InetAddress.getByName(IP))){
			while(true){
				Socket socket = ss.accept();
				System.out.println(socket.getInetAddress()+"  "+socket.getPort());
				Callable<Void> task = new RequestThread(socket);
				pool.submit(task);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	  }
	  

	
}
