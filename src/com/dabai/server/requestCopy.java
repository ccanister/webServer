package com.dabai.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class requestCopy {
	public static void main(String[] args) {
		try {
			FileReader fr = new FileReader("D:/tqest.sql");
			System.out.println(fr.read());
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}
