package com.dabai.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
	public final static int PORT = 8003;
	public final static String IP = "127.0.0.1";
	public final static int BACKLOG = 3;
	
	private ServerSocket ss = null;
	private BufferedReader standar_in = null;
	private BufferedReader serverSocket_in = null;
	private PrintWriter  socket_out = null;
	
	
//	public void server(){
//		Socket socket;
//		try {
//			socket = ss.accept();
//			standar_in = new BufferedReader(new InputStreamReader(System.in));
//			serverSocket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			socket_out = new PrintWriter (socket.getOutputStream());
//			
////			String system_in = null;
////			String server_in = null;
//			System.out.println("the server accept data:\t"+serverSocket_in.readLine());
//			String readline;
//			
//			readline = standar_in.readLine();
//            while(!readline.equalsIgnoreCase("bye")){
//                socket_out.println(readline);
//                socket_out.flush();
//                System.out.println("Server send data:\t"+readline);
//                
//              //System.out.println(serverSocket_in.readLine());
//              //System.out.println("Server accept data:\t"+serverSocket_in.readLine());
////                if((readline = serverSocket_in.readLine()) != null){
////                	System.out.println("gg");
////                	System.out.println("Server accept data:\t"+serverSocket_in.readLine());
////                }
////                int ch;
////                while((ch=serverSocket_in.read())!=-1){
////                	System.out.print((char)ch);
////                	System.out.println();
////                }
//                readline = standar_in.readLine();
//            }
//			
//			
////			while(((system_in = standar_in.readLine()) != null)||
////					((server_in = serverSocket_in.readLine()) != null)){
////				System.out.println(server_in);
////				socket_out.write(system_in);
////				socket_out.flush();
////			}
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public static void main(String[] args) {
//		Server server = new Server();
//		server.server();
//	}

	@Override
	public void run() {
		try {
			ss = new ServerSocket(PORT,BACKLOG,InetAddress.getByName(IP));
			Socket socket = ss.accept();
			socket = ss.accept();
			standar_in = new BufferedReader(new InputStreamReader(System.in));
			serverSocket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socket_out = new PrintWriter (socket.getOutputStream());
			
			System.out.println("the server accept data:\t"+serverSocket_in.readLine());
			String readline;
			
			readline = standar_in.readLine();
            while(!readline.equalsIgnoreCase("bye")){
                socket_out.println(readline);
                socket_out.flush();
                System.out.println("Server send data:\t"+readline);
                
                readline = standar_in.readLine();
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
