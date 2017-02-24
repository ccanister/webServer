package com.dabai.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client2 {
	private Socket socket;
	
	public final static String IP = "127.0.0.1";
	private BufferedReader serverSocket_in = null;
	
	public static void main(String[] args) {
		Client2 client = new Client2();
		client.connect();
	}

	private void connect() {
		try {
			socket = new Socket(IP,Server2.PORT);
			serverSocket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String readline;
			
			new Thread(new ReaderThread(System.in,socket.getOutputStream())).start();
			while((readline=serverSocket_in.readLine())!=null){
				System.out.println("the client accept data:\t"+readline);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
