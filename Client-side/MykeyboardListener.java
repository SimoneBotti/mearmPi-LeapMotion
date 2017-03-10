package com.simo.leapmotion;

import java.io.IOException;
import java.util.Scanner;

public class MykeyboardListener implements Runnable{

	private LeapMotion leap;
	private int toggle=0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Starting keyboard Listener");
		Scanner sc=new Scanner(System.in);
		String msg;
		while (true){
			msg=sc.nextLine();
			if(msg.equals(" ")){
				if(leap!=null){
					if(toggle==0){
						toggle=1;
					}else{
						toggle=0;
					}
					leap.setToggle(toggle);
					
				}
			}
		}
	}
	public void setLeapMotion(LeapMotion leap){
		this.leap=leap;
	}
	

	
}
