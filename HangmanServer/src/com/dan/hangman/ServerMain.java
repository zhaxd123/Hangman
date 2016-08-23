package com.dan.hangman;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain{
	public static void main(String Args[]) throws Exception
	{
		ServerSocket ss = new ServerSocket(Integer.parseInt(Args[0]));
		while (true)
		{
			Socket s = ss.accept();
			UsrThread usrthread = new UsrThread();
			usrthread.s = s;
			usrthread.is = s.getInputStream();
			usrthread.os = s.getOutputStream();
			usrthread.start();
			Thread.sleep(20);
			System.out.println("1 user added, current users:"+ ServerMonitor.usrnum);
		}
	
	}
}
