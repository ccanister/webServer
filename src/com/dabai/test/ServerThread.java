package com.dabai.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ServerThread implements Runnable{
	private Socket socket;
	private PrintWriter pw;
	private BufferedReader br;
	private Map<String,Socket> clients;
	private String name;
	private String message;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Socket> getClients() {
		return clients;
	}

	public void setClients(Map<String, Socket> clients) {
		this.clients = clients;
	}

	public ServerThread(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
 			message = "now we hava "+clients.size()+" people"+"he is"+socket.getInetAddress();
 			this.sendMessage();
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while((message=br.readLine())!=null){
				message = this.getName()+" say "+message;
				this.sendMessage();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(){
		try{
			System.out.println(message);
			
			Iterator<Entry<String,Socket>> clientsIt = clients.entrySet().iterator();
			while(clientsIt.hasNext()){
				Entry<String,Socket> client = clientsIt.next();
				Socket socket = client.getValue();
				String name = client.getKey();
				if(!name.equals(this.getName())){
					PrintWriter pw = new PrintWriter(socket.getOutputStream());
					pw.println(message);
					pw.flush();
				}
			}
		}catch(IOException e){
			
		}
		
	}
}
