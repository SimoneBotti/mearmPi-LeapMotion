package com.simo.leapmotion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client{
	//Socket Data
	private String ip;				// ip adress of server
	private int port=49676;			// port of the server
	
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;
    
    public Client(String ip) throws UnknownHostException, IOException {
        this.ip = ip;
        System.out.println("Ip Adress:" + ip);
    	try {
            socket = new Socket(ip, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread inThread = new Thread(new ServerInput(in));
            inThread.start();
        } catch (UnknownHostException e) {
            System.out.println("Error: " + e.getMessage());
        } catch(IOException e){
            System.out.println("Errore di scrittura:"+e.getMessage());
        }
    }

    public void sendMessage(String message) throws IOException {
            String msg =message;
            try {
                out.writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public String readMessage() throws IOException{
    	byte[] bites=new byte[2]; 
    	in.read(bites);
    	 String str = new String(bites,"UTF-8");
    	 return str;
    }

    public static DataOutputStream getOut() {
        return out;
    }

    public static void setOut(DataOutputStream out) {
        Client.out = out;
    }

    public static DataInputStream getIn() {
        return in;
    }

    public static void setIn(DataInputStream in) {
        Client.in = in;
    }

}


class ServerInput implements Runnable {
    private DataInputStream in;
    private Client client;
    
    public ServerInput(DataInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = in.readUTF();
                System.out.println(""+msg);

            } catch (IOException e) {
                //disconected
            }
        }

    }

}

