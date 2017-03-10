package com.simo.leapmotion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Math;
import java.net.Socket;
import java.net.UnknownHostException;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

public class LeapListener extends Listener {
	private Vector myVector=new Vector();    
	private float x,y,z;
	private Socket socket;
	private static DataInputStream in;
	private static DataOutputStream out;
	private LeapMotion leapmotion;
	
	
	public void onInit(Controller controller) {
	        System.out.println("Initialized");
	        Vector myVector=new Vector();
	}

	    public void onConnect(Controller controller) {
	        System.out.println("Connected");
	      }

	    public void onDisconnect(Controller controller) {
	        //Note: not dispatched when running in a debugger.
	        System.out.println("Disconnected");
	    }

	    public void onExit(Controller controller) {
	        System.out.println("Exited");
	    }

	    public void onFrame(Controller controller) {
	        
	    	// Get the most recent frame and report some basic information
	        Frame frame = controller.frame();
	        

            
	        //Get hands
	        for(Hand hand : frame.hands()) {
	            String handType = hand.isLeft() ? "Left hand" : "Right hand";
	            
	            //Obtaining handCenter Coordinates.
	            Vector handCenter = hand.palmPosition();
	            x=handCenter.get(0);
	            y=handCenter.get(1);
	            z=handCenter.get(2);
	            leapmotion.setCoordinate(x,y,z);
			
	            // Get the hand's normal vector and direction
	            Vector normal = hand.palmNormal();
	            Vector direction = hand.direction();
	            
	            // Get arm bone
	            Arm arm = hand.arm();
	 

	       }
	    }
	   public void setLeapMotion(LeapMotion leap){
		   this.leapmotion=leap;
	   }

}
