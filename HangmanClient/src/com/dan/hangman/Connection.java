package com.dan.hangman;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {
	static Socket s;
	static OutputStream os;
	static InputStream is;
	public void launch() throws Exception
	{
		s = new Socket(Constant.ipAddress,Constant.port);
		os = s.getOutputStream();
		is = s.getInputStream();
	}
}