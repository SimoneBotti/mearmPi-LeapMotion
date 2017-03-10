package com.simo.leapmotion;

import java.io.IOException;
import java.lang.Math;
import java.net.UnknownHostException;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

public class LeapMotion extends Thread implements Runnable{
	//Ip adress of the server
	private String IpAdress="192.168.43.25"; 	//change IP Adress
	
	private int x,y,z,toggle=0,i=0;
	private String coord;
	
	private Thread keyboardThread;
	private Client client;
	private MykeyboardListener keyboardList=new MykeyboardListener();
	
	@Override
	public void run(){
		try{
			client=new Client(IpAdress);
		}catch(IOException e){
			System.out.println("Connection Error...");
		}
		
		//Starting Thread to listen for keyboard press
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

	public void setCoordinate(float x,float y,float z){
		//Convert coordinates to int
		this.x=(int) x;
		this.y=(int) y;
		this.z=(int) z;
		
		this.x=this.x/2;
		//Control Coordinates
		if (this.x<0){
			this.x=1000-this.x;
		}
		if (this.y<0){
			this.y=1000-this.y;
		}
		if (this.z<0){
			this.z=1000-this.z;
		}
		try {
			//Formatting string to send to the server
			System.out.println("x:"+String.valueOf(this.x) +" y:"+String.valueOf(this.y)+" z:"+String.valueOf(this.z)+" Gripper:"+toggle);	
			coord=this.x+":"+this.y+":"+this.z+":"+this.toggle;
			//send string to server
			client.sendMessage(coord);
			this.sleep(50);
			System.out.println("message sent");
			
		} catch (IOException e) {
			System.out.println("error while sending");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String readMessage(){
		String msg=null;
		try {
			msg = client.readMessage();
			System.out.println("message received"+msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	public void setToggle(int toggle){
		this.toggle=toggle;
	}
	
	
	public Client getClient(){
		return this.client;
	}

}

