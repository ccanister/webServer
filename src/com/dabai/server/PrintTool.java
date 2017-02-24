package com.dabai.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

/**
 * 使用单例设计模式
 */
public class PrintTool {
	public static PrintTool printTool = new PrintTool();
	
	private PrintTool(){}
	
	public static PrintTool getInstance(){
		return printTool;
	}
	
	/**
	 * 将文件输出到浏览器
	 * @param mes 起始行和首部
	 * @param pw
	 * @param fileName
	 */
	public void printScreenWithFile(String mes,File file, OutputStream os) {
		try {
			if (file != null) {
				System.out.println(mes); //TODO
				// 获得文件的绝对路径
				String fileUrl = file.getAbsolutePath();
				if (fileUrl.contains(".png") || fileUrl.contains(".jpg")) {
					// 读取二进制文件流，如图片
					BufferedInputStream bis = new BufferedInputStream(
							new FileInputStream(file));
					// 写到浏览器流
					//BufferedOutputStream bos = new BufferedOutputStream(os);
					if(mes!=null)
						os.write(mes.getBytes());
					// 传输图片
					this.printImage(bis, os);
				} else {
					// 获得文本文件字符集
					String charsetName = this.judgeChar(file);
					// 读取字符文件流
					BufferedReader br = new BufferedReader(
							new InputStreamReader(new FileInputStream(file), charsetName));
					// 写到浏览器流
					PrintWriter pw = new PrintWriter(os);
					if(mes!=null)
						pw.println(mes);
					// 传输文本
					this.printText(br, pw,charsetName);
				}
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printScreenWithString(String message,OutputStream os){
		PrintWriter pw = new PrintWriter(os);
		pw.println(message);
		pw.flush();//有无必要先刷新缓冲区
	}

	private void printText(BufferedReader br, PrintWriter pw,String charsetName) throws IOException {
		pw.println("\r\n");
		String readLine;
		while ((readLine = br.readLine()) != null) {
			readLine = new String(readLine.getBytes(),charsetName);
			pw.println(readLine);
		}
		pw.flush();
		//pw.close();
	}

	private void printImage(BufferedInputStream bis, OutputStream bos) throws IOException {
		bos.write("\r\n".getBytes());
		byte[] b = new byte[1024];
		while (bis.read(b) != -1) {
			bos.write(b);
		} 
		bis.close();
	}
	
	/**
	 * 将信息写入文件
	 * @param mes
	 * @param file
	 * @throws IOException 
	 */
	public void writeToFile(String mes,File file){
		try {
			//以读写方式创建随机访问流
			RandomAccessFile randomFile = new RandomAccessFile(file,"rws");
			//获取长度
			long fileLength = file.length();
			//指向文件末尾
			randomFile.seek(fileLength);
			//以字节方式写入文件
			randomFile.writeBytes(mes);
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断该文件字符编码
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private String judgeChar(File file) {
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		/*
		 * ParsingDetector可用于检查HTML、XML等文件或字符流的编码,
		 * 构造方法中的参数用于指示是否显示探测过程的详细信息，为false不显示。
		 */
		detector.add(new ParsingDetector(false));
		/*
		 * JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码测定。
		 * 所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以再多加几个探测器，
		 * 比如下面的ASCIIDetector、UnicodeDetector等。
		 */
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		Charset charset = null;
		try {
			// charset = detector.detectCodepage(file.toURI().toURL());
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			charset = detector.detectCodepage(is, 8);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String charsetName = "GBK";
		if (charset != null) {
			if (charset.name().equals("US-ASCII")) {
				charsetName = "ISO_8859_1";
			} else if (charset.name().startsWith("UTF")) {
				charsetName = charset.name();// 例如:UTF-8,UTF-16BE.
			}
		}
		System.out.println("charsetName=" + charsetName);
		return charsetName;
	}
}
