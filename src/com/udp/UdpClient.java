package com.udp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class UdpClient {

	public static final String IP = "255.255.255.255";
	
	public static final int PROT = 1001;
	
	public static void main(String[] args) throws Throwable {
		System.out.println("��ʼ���ӵ�udp�����");
		DatagramSocket udpClient = new DatagramSocket();
		byte[] sendData = "�㲥ip".getBytes();
		udpClient.send(new DatagramPacket(sendData, sendData.length, InetAddress.getByName(IP), UdpServer.PROT));
		udpClient.close();
		System.out.println("udp�ͻ��˲�������");
		ServerSocket serverSocket = new ServerSocket(PROT);
		System.out.println("tcp����˴����ɹ�");
		Socket socket = serverSocket.accept();
		InputStream inputStream = socket.getInputStream();
		byte[] receiverData = new byte[1024];
		int receiverLength = 0;
		File outFile = new File(".\\receive\\reveice");
		if(!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}
		FileOutputStream fileOutputStream = new FileOutputStream(outFile);
		while((receiverLength = inputStream.read(receiverData)) > 0) {
			fileOutputStream.write(receiverData, 0, receiverLength);
		}
		fileOutputStream.flush();
		fileOutputStream.close();
		inputStream.close();
		socket.close();
		System.out.println("tcp����� �ر�");
	}

}
