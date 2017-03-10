package com.simo.leapmotion;

import java.awt.Graphics;
import java.io.IOException;
import java.lang.Math;
import java.net.UnknownHostException;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

public class LeapMotion extends Thread implements Runnable{
	
	private static Thread launchThread,clientThread,keyboardThread;
	private static float fx,fy,fz;
	private String coord;
	private int x,y,z,toggle=0;
	private Board board;
	private Client client;
	private int i=0;
	private MykeyboardListener keyboardList=new MykeyboardListener();
	@Override
	public void run(){
		try{
			client=new Client("192.168.43.25");
		}catch(IOException e){
			System.out.println("Connection Error...");
		}
		keyboardThread=new Thread(keyboardList);
		keyboardThread.start();
		keyboardList.setLeapMotion(this);		
		//Create an instance of Controller
    	Controller controller = new Controller();
    	//Create the listener
    	LeapListener listener = new LeapListener();
        //Add the listener to the controller
        controller.addListener(listener);
        listener.setLeapMotion(this);
	
        // Keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        
            while(true){
            }
          
       
        // Remove the sample listener when done
      //  controller.removeListener(listener);
	}

	public void setBoard(Board board){
		this.board=board;
	}
	
	public void setCoordinate(float x,float y,float z){
		this.fx=x;
		this.fy=y;
		this.fz=z;
		this.x=(int)this.fx;
		this.y=(int)this.fy;
		this.z=(int)this.fz;
		this.x=this.x/2;
		if (this.x<0){
			this.x=1000-this.x;
		}
		//if (this.y<0){
		//	this.y=1000-this.y;
		//}
		//if (this.z<0){
		//	this.z=1000-this.z;
		//}
		try {
			System.out.println("x:"+String.valueOf(this.x) +" y:"+String.valueOf(this.y)+" z:"+String.valueOf(this.z)+" Gripper:"+toggle);	
			coord=this.x+":"+this.y+":"+this.z+":"+this.toggle;
			client.sendMessage(coord);
			this.sleep(50);
		//	System.out.println("messaggi inviati");
			
		} catch (IOException e) {
			System.out.println("cant send");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setToggle(int toggle){
		this.toggle=toggle;
	//	System.out.println("Toggle in LeapMotion:"+toggle);
	}
	
	public String readMessage(){
		String msg=null;
		try {
			msg = client.readMessage();
			System.out.println("Stringa ricevuta:"+msg);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	public Client getClient(){
		return this.client;
	}

}

