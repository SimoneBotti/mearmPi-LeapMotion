package com.simo.leapmotion;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Main extends JFrame{
	private static Thread thread;
	private static LeapMotion LeapMotion=new LeapMotion();
	private static Graphics g;
	private static Board board=new Board();
	
/*	public Main() {
		initUI();
	}
	
	private void initUI() {
		add(board);
		setSize(2160,1440);
		setTitle("Radar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}    
	*/
	
	public static void main(String[] args) {
	//	 EventQueue.invokeLater(new Runnable() {
	//	 @Override
	//	 public void run() {
//			LeapMotion.setBoard(board);
			thread=new Thread(LeapMotion);
			thread.start();
			Main ex = new Main();
		    ex.setVisible(true);
		 }
	//	 });
	}
//}


