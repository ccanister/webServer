package com.dabai.test;

public class ExceptionTest extends Thread{
	static String s = "abc";
	byte i = 3;
	int j = i;
	public static void main(String[] args){
		Thread t1 = new Thread(new ExceptionTest());
		Thread t2 = new Thread(new ExceptionTest());
		t1.start();
		t2.start();
		System.out.println(s);
	}
	static String change(String s){
		s = "afdsf";
		char[] e= new char[4];
		
		return s;
	}
	public void run(){
		s = "abcde";
	}
}
