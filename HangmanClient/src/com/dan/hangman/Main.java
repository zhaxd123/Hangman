package com.dan.hangman;
/**
 * 实现的功能：
 * 1.点击restart之后本地端分数清零。
 * 目前存在的问题：
 * 1.连胜的情况下，Score还不能累加[已达成！]
 * 不对。。分数还是有bug，好像只会加上上次的。[还是解决了，把score单独变量进行改变就可以]
 * 2.双模式问题
 * 3.服务器多用户线程问题 [已经解决]
 * 4.加边框？
 * 5.还是需要在完成的时候加上词。。[解决]
 * 6.Server端需要执行用户减1的指令。[解决]
 */
import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


public class Main {
	public static void main(String Args[])
	{
		Connection con = new Connection(); //
		
		GameFrame loginframe = new GameFrame();
		loginframe.setTitle("LOGIN");
		loginframe.launch(true);
		
		Button conbtn = new Button("Play Now!");
		conbtn.setBounds(150, 165, 100, 30);
		
		TextField iptf = new TextField("127.0.0.1");
		iptf.setBounds(220, 65, 150, 25);
		iptf.setFont(Constant.font_Small);
		
		TextField porttf = new TextField("80");
		porttf.setBounds(220, 105, 50, 25);
		porttf.setFont(Constant.font_Small);
		
		Label iplb = new Label ("IP address:",Label.RIGHT);
		iplb.setBounds(50, 65, 150, 25);
		iplb.setFont(Constant.font_Small);
		
		Label portlb = new Label ("Port:",Label.RIGHT);
		portlb.setBounds(100, 105, 100, 25);
		portlb.setFont(Constant.font_Small);
		
		
		loginframe.add(conbtn);
		loginframe.add(iptf);
		loginframe.add(porttf);
		loginframe.add(iplb);
		loginframe.add(portlb);
		
		
		GameFrame mainframe = new GameFrame();
		mainframe.setTitle("Hangman Game   by Xiaodong Zhang");
		mainframe.launch(false);
		
		Button sendbtn = new Button("SEND!");
		sendbtn.setBounds(344, 175, 46, 23);
		
		TextField wordtf = new TextField();
		wordtf.setBounds(10,175, 300, 23);
		
		Label startlb = new Label(Constant.newgame,Label.CENTER);
		startlb.setBounds(50, 50, 300, 100);
		startlb.setFont(Constant.font_Big);
		
		Label wordlb = new Label(Constant.newgame,Label.CENTER);
		wordlb.setBounds(50, 80, 300, 40);
		wordlb.setFont(Constant.font_Big);
		
		Label scorelb = new Label("SCORE:");
		scorelb.setBounds(10, 30, 120, 20);
		scorelb.setFont(Constant.font_Small);
		
		Label attemptlb = new Label("ATTEMPT:");
		attemptlb.setBounds(250, 30, 140, 25);
		attemptlb.setFont(Constant.font_Small);
		
		Button restartbtn = new Button ("Restart!");
		restartbtn.setBounds(160,150,80,30);
		restartbtn.setVisible(false);
		
		Button continuebtn = new Button ("Continue!");
		continuebtn.setBounds(160,160,80,30);
		continuebtn.setVisible(false);
		
		Label sublb = new Label("",Label.CENTER);
		sublb.setBounds(50, 120, 300, 30);
		sublb.setForeground(new Color(0,134,139));
		sublb.setFont(Constant.font_Small);
		sublb.setVisible(false);
		
		mainframe.add(sendbtn);
		mainframe.add(wordtf);
		mainframe.add(startlb);
		mainframe.add(wordlb);
		mainframe.add(scorelb);
		mainframe.add(attemptlb);
		mainframe.add(restartbtn);
		mainframe.add(continuebtn);
		mainframe.add(sublb);
		
		loginframe.addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e)
					{
						System.exit(0);
					}			
		});
		
		conbtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loginframe.setVisible(false);
				mainframe.setVisible(true);
				Constant.ipAddress = iptf.getText(); //
				Constant.port = Integer.parseInt(porttf.getText());	//
				try
				{
					con.launch();
					RcvThread rt = new RcvThread();
					rt.s=Connection.s;
					rt.is=Connection.is;
					rt.start();
				} catch (Exception e1){}
			}
		});
		
		startlb.addMouseListener(new MouseAdapter (){
			
			 public void mouseClicked(MouseEvent e) 
			 {
				 try
				{
					if(Connection.os!=null) 
					{
						Connection.os.write(("cmd:start").getBytes());
						Constant.showFlag = true;
					}
					else
					{
						System.out.println("Wrong Connection, Game will shut down in 3s.");
						Thread.sleep(1000);
						System.out.println("Wrong Connection, Game will shut down in 2s.");
						Thread.sleep(1000);
						System.out.println("Wrong Connection, Game will shut down in 1s.");
						Thread.sleep(1000);
						System.exit(0);						
					}					
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			 }
			
		});
		
		mainframe.addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e)
					{
						try
						{
							Connection.os.write("cmd:closed".getBytes());
							Connection.is.close();
							Connection.os.close();
							Connection.s.close();
						} catch (IOException e1)
						{
							e1.printStackTrace();
						}
						System.exit(0);
					}
			
		});
		
		sendbtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Connection.os.write(wordtf.getText().getBytes());
					Connection.os.flush();

				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
				
			}
		});
		
		restartbtn.addMouseListener(new MouseAdapter (){
			
			 public void mouseClicked(MouseEvent e) 
			 {
				 try
				{
					Connection.os.write(("cmd:start").getBytes());
					Constant.showFlag = true;
					RcvThread.scoreString = "SCORE: 0";
					
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			 }
			
		});
		
		continuebtn.addMouseListener(new MouseAdapter (){
			
			 public void mouseClicked(MouseEvent e) 
			 {
				 try
				{
					Connection.os.write("cmd:continue".getBytes());
					Constant.showFlag = true;
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			 }
			
		});
		
		
		
		while(true)
		{
			if (Constant.showFlag == false)
			{
				startlb.setText(Constant.newgame);
			}
			else
			{				
				
				if (RcvThread.wordString.equals("YOU LOSE!"))
				{

					wordlb.setText(RcvThread.wordString);
					scorelb.setText(RcvThread.scoreString);
					attemptlb.setText(RcvThread.attemptString);
					sublb.setText("The word is "+RcvThread.subString+".");	

					Constant.wordlbvisible = true;
					Constant.wordtfvisible = false;
					Constant.startlbvisible = false;
					Constant.sendbtnvisible = false;
					Constant.restartbtnvisible = true;
					Constant.scorelbvisible = true;
					Constant.attemptlbvisible = false;
					Constant.sublbvisible = true;
					
				}
				else if (RcvThread.wordString.equals("YOU WIN!"))
				{
					wordlb.setText(RcvThread.wordString);
					scorelb.setText(RcvThread.scoreString);
					attemptlb.setText(RcvThread.attemptString);			
					sublb.setText("The word is "+RcvThread.subString);					

					Constant.wordtfvisible = false;
					Constant.startlbvisible = true;
					Constant.sendbtnvisible = false;
					Constant.restartbtnvisible = false;
					Constant.scorelbvisible = true;
					Constant.attemptlbvisible = true;
					Constant.continuebtnvisible = true;
					Constant.sublbvisible = true;
										
				}
				else
				{
					startlb.setVisible(false);
					wordlb.setText(RcvThread.wordString);
					wordlb.setVisible(true);
					scorelb.setText(RcvThread.scoreString);
					attemptlb.setText(RcvThread.attemptString);
					
					Constant.wordlbvisible = true;
					Constant.wordtfvisible = true;
					Constant.startlbvisible = false;
					Constant.sendbtnvisible = true;
					Constant.restartbtnvisible = false;
					Constant.scorelbvisible = true;
					Constant.attemptlbvisible = true;
					Constant.continuebtnvisible = false;
					Constant.sublbvisible = false;
				}
				
				wordlb.setVisible(Constant.wordlbvisible);
				wordtf.setVisible(Constant.wordtfvisible);
				sendbtn.setVisible(Constant.sendbtnvisible);
				restartbtn.setVisible(Constant.restartbtnvisible);
				scorelb.setVisible(Constant.scorelbvisible);
				attemptlb.setVisible(Constant.attemptlbvisible);
				continuebtn.setVisible(Constant.continuebtnvisible);
				sublb.setVisible(Constant.sublbvisible);
			}
			
			
		}		
		
	}
}
