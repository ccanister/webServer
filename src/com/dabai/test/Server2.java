package com.dabai.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server2 {
	public final static int PORT = 8003;
	public final static String IP = "127.0.0.1";
	public final static int BACKLOG = 3;
	private Map<String,Socket> clients = new HashMap<String,Socket>();
	private int count = 0;
	
	private ServerSocket ss = null;
	
	public static void main(String[] args) {
		Server2 server = new Server2();
		server.server();
	}

	private void server() {
		try {
			ss = new ServerSocket(PORT,BACKLOG,InetAddress.getByName(IP));
			while(true){
				Socket socket = ss.accept();
				String name="第"+(++count)+"个客户端";
				clients.put(name,socket);
				ServerThread st = new ServerThread(socket);
				st.setName(name);
				st.setClients(clients);
				new Thread(st).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
