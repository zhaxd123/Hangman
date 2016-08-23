package com.dan.hangman;

import java.awt.Font;

class Constant {
	static boolean showFlag = false;
	static Font font_Big = new Font("Serif",Font.PLAIN,40);
	static Font font_Small = new Font("Dialog",Font.PLAIN,20);	
	static String newgame = "== New Game ==";
	static String ipAddress = "127.0.0.1";
	static int port = 80;
	static String start = "cmd:start";
	static String win = "cmd:win";
	static String lose = "cmd:lose";
	static boolean startlbvisible = true;
	static boolean wordlbvisible = true;
	static boolean wordtfvisible = false;
	static boolean sendbtnvisible = false;
	static boolean restartbtnvisible = false;
	static boolean scorelbvisible = false;
	static boolean attemptlbvisible = false;
	static boolean continuebtnvisible = false;	
	static boolean sublbvisible = false;
}
