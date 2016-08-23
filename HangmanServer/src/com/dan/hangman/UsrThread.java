package com.dan.hangman;
/**
 * 之前一直测试无限死循环，是因为阻塞方法read放在try中被无视了。解决方法是将它
 * 和其他所有语句块放在相同的try语句块中。我草！坑了我两小时来改了个try的位置，真坑！
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class UsrThread extends Thread{
	Socket s = null;
	InputStream is = null;
	OutputStream os = null;
	String word = null;
	byte [] dataByte = new byte [1024];
	int attempt = 0;
	Word wordprocess = new Word();
	String xword = null;
	String winString = "cmd:win";
	String loseString = "cmd:lose";
	String scoreString = "Score:";
	Score sc = new Score(); 
	int len = 0;
	public void run()
	{
		ServerMonitor.usrnum = ServerMonitor.usrnum + 1;
		try
		{
		String readword = null;
        ArrayList<String> wordlist = new ArrayList<String>();
        FileReader fr = new FileReader("E:\\JAVA\\words.txt");
        BufferedReader reader = new BufferedReader(fr);
        while((readword = reader.readLine()) != null)
        {
        	wordlist.add(readword);
        }
		
		while(true)
		{			

			len = is.read(dataByte);
			String dataString = new String (dataByte,0,len);
			if (dataString.equals("cmd:start"))
			{
	        	int num =  (int)(Math.random()*wordlist.size());  
				word = wordlist.get(num);
		        wordprocess.word = word;
		        attempt = 0;
		        sc.score = 0;
		        for (int i=0;i<20;i++)
		        {
		        	wordprocess.wordflag[i] = false;
		        }
			}
			
			else if (dataString.equals("cmd:continue"))
			{
				int num =  (int)(Math.random()*wordlist.size());  
				word = wordlist.get(num);
		        wordprocess.word = word;
		        attempt = 0;
		        for (int i=0;i<20;i++)
		        {
		        	wordprocess.wordflag[i] = false;
		        }
			}
			

			else if (dataString.equals("cmd:closed"))
			{
				s.close();
				ServerMonitor.usrnum = ServerMonitor.usrnum - 1;
				System.out.println("1 user left, current users:"+ ServerMonitor.usrnum);
				break;
			}
			else if(dataString.length()!=1 & dataString.length()!=word.length())
			{
				dataString = "wrong data!";
			}	

				wordprocess.dataString = dataString;
				wordprocess.judge();
				xword = wordprocess.xString;
				if (!dataString.equals("cmd:start")& !dataString.equals("cmd:continue") & !dataString.equals("cmd:closed") & !wordprocess.isTrue)
				{
					attempt=attempt + 1;
				}


				if (xword.equals(word))
				{
					sc.score = sc.score+sc.getFreeScore(attempt);
					os.write((winString+scoreString+sc.score+word).getBytes());
					continue;
				}
				
				else if (attempt == 10)
				{
					os.write((loseString+scoreString+sc.score+word).getBytes());
					continue;
				}
				else
				{
					os.write((xword+(10-attempt)).getBytes());
				}



		} 
		}catch(Exception e)
		{
			e.printStackTrace();
		};
	}
}
