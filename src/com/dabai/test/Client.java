package com.dabai.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private Socket socket;
	
	//public final static int PORT = 80;
	public final static String IP = "127.0.0.1";
	private BufferedReader standar_in = null;
	private BufferedReader serverSocket_in = null;
	private PrintWriter  socket_out = null;
	//private BufferedWriter bw = null;
	
	public void connect(){
		try {
			socket = new Socket(IP,Server.PORT);
			standar_in = new BufferedReader(new InputStreamReader(System.in));
			serverSocket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socket_out = new PrintWriter (socket.getOutputStream());
			//bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
//			String system_in = null;
//			String server_in = null;
			String readline;
			
			readline = standar_in.readLine();
            while(!readline.equalsIgnoreCase("bye")){
                socket_out.println(readline);
                socket_out.flush();
//            	bw.write(readline+"\n");
//            	bw.flush();
                System.out.println("Client send data:\t"+readline);
                System.out.println("Client accept data:\t"+serverSocket_in.readLine());
//                if((readline = serverSocket_in.readLine()) != null){
//                	System.out.println("Client accept data:\t"+serverSocket_in.readLine());
//                }
                readline = standar_in.readLine();
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.connect();
	}
}
