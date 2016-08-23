package com.dan.hangman;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class RcvThread extends Thread{
    Socket s = Connection.s;
	InputStream is = Connection.is;
	int len;
	byte [] dataByte = new byte [1024];
	static String dataString = null;
	static String winString = Constant.win;
	static String scoreString = "SCORE: 0";
	static String wordString = "";
	static String attemptString = "10";
	static String loseString = "cmd:lose";
	static String subString = "";
	
	public void run()
	{
		while(true)
		{
			try
			{
				len = is.read(dataByte);
				RcvThread.dataString = new String (dataByte,0,len);	
				System.out.println("dataString: "+dataString);
				if (dataString.contains(winString))
				{
					wordString = "YOU WIN!";
					subString = dataString.replaceAll("cmd:winScore:\\d{1,5}", "");
					scoreString =  dataString.replaceAll(winString, "").replaceAll(subString, "");
					System.out.print("sub: "+subString);
					System.out.print("score "+scoreString);
				}
				
				else if (dataString.contains(loseString))
				{
					wordString = "YOU LOSE!";
					subString = dataString.replaceAll("cmd:loseScore:\\d{1,5}", "");
					scoreString =  dataString.replaceAll(loseString, "").replaceAll(subString, "");
				}				
				
				else
				{
					wordString = dataString.replaceAll("\\d{1,3}", "");
					attemptString = "ATTEMPT: "+dataString.replaceAll("\\D{1,15}", "");				
				}

			} catch (IOException e){}
		}
		
	}
}
