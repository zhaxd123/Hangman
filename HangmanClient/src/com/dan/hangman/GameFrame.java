package com.dan.hangman;

import java.awt.Frame;
import java.awt.Image;

import javax.swing.ImageIcon;

class GameFrame extends Frame {
	ImageIcon icon = new ImageIcon("E:\\JAVA\\HangmanClientV2\\src\\com\\dan\\hangman\\icon.png");
	Image image = icon.getImage();
	
	public void launch(boolean visible)
	{

		this.setIconImage(image);;
		this.setLayout(null);
		this.setVisible(visible);
		this.setBounds(500, 220, 400, 210);
		this.setResizable(false);
	}
	
//	public void errlaunch ()
//	{
//		this.setLayout(null);
//		this.setVisible(false);
//		this.setBounds(650, 380, 40, 30);
//		this.setResizable(false);
//	}
}
