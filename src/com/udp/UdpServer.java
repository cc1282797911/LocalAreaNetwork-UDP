package com.udp;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class UdpServer {

	public static final int PROT = 50000;
	
	public static void main(String[] args) throws Throwable {
		File file = new File("D:\\ubuntu-18.04.1-desktop-amd64.iso");
		DatagramSocket udpServer = new DatagramSocket(PROT);
		System.out.println("已创建 udp服务端 ");
		while (true) {
			byte[] buff = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buff, buff.length);
			udpServer.receive(packet);
			String tcpIp = packet.getAddress().getHostAddress();
			System.out.println("接收  = ip:" + tcpIp);
			System.out.println("开始连接 tcp服务端 ");
			Socket socket = new Socket(tcpIp, UdpClient.PROT);
			System.out.println("成功连接到 tcp服务端");
			OutputStream outputStream = socket.getOutputStream();
			FileInputStream fileInputStream = new FileInputStream(file);
			byte[] sendData = new byte[1024];
			int readLength = 0;
			//全部发送的长度
			int allSendLength = 0;
			//计算发送速度/s
			long startTime = System.currentTimeMillis();
			long sendLength = 0;
			System.out.println("开始发送文件");
			while((readLength = fileInputStream.read(sendData)) > 0) {
				outputStream.write(sendData, 0, readLength);
				sendLength += readLength;
				allSendLength += readLength;
				if(System.currentTimeMillis() - startTime >= 1000) {
					System.out.println("发送速度 = " + sendLength / 1024 / 1024 + " M/s");
					sendLength = 0;
					startTime = System.currentTimeMillis();
				}
			}
			fileInputStream.close();
			outputStream.flush();
			outputStream.close();
			socket.close();
			break;
		}
		udpServer.close();
		System.out.println("tcp服务端 关闭连接");
	}
	
}
