package com.dan.hangman;

public class Word {
	
	String xString = null;
	String revString = null;
	String dataString = null;
	String word = null;
	boolean [] wordflag = new boolean [20];
	boolean isTrue = false;
	
	
	void judge ()
	{
		String rString = null;
		String nString = null;

		byte[] rByte = new byte [word.length()] ;
		byte[] revByte = new byte [word.length()];
		byte[] wordByte = word.getBytes();
		byte[] nByte = new byte [dataString.length()];
		byte[] nullByte = new byte [1];
		nullByte[0] = 0;
		String nullString = new String(nullByte,0,1);


		for (int i = 0; i < dataString.length(); i++)
		{
			nByte[i] = 0;
		}
		nString = new String (nByte,0,nByte.length);
		revString = word.replaceAll(dataString, nString);
		System.out.println("dataString: " + dataString);
		System.out.println("word: " + word);
		
		revByte = revString.getBytes();
		for (int j = 0; j < word.length(); j++)
		{
			rByte[j] =(byte) (revByte[j] ^ wordByte[j]);
			if (rByte[j] != 0)
				wordflag[j] = true;
		}

		for (int k = 0; k < word.length(); k++)
		{
			if (wordflag[k] == true)
			{
				rByte[k] = wordByte[k];
			}
		}

		rString = new String (rByte,0,rByte.length);
		xString = rString.replaceAll(nullString, "*");
		
		isTrue = word.contains(dataString);
	}

}
