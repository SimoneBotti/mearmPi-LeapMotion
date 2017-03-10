package com.simo.leapmotion;

public class Main {
	private static Thread thread;
	private static LeapMotion LeapMotion=new LeapMotion();
	
	public static void main(String[] args) {
		//Starting the Thread for the Leap Motion Sensor
		thread=new Thread(LeapMotion);
		thread.start();
	}
}



