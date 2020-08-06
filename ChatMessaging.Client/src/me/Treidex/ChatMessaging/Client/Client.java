package me.Treidex.ChatMessaging.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread
{
	public static final int PORT = 25569;
	
	public static void main(String[] args) throws Throwable
	{
		Window window = new Window();
		window.create();
		
		String ip = window.readLine();
		Client c = new Client(window, ip, PORT);
		c.connect();
	}
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Window window;
	
	private String ip;
	private int port;
	
	public Client(Window window, String ip, int port)
	{
		this.window = window;
		
		this.ip = ip;
		this.port = port;
		
		window.setTitle("Connecting with " + ip);
	}
	
	public void connect()
	{
		try {
			window.println("[$]: Connecting to " + ip + ":" + port + "...");
			socket = new Socket(ip, port);
			
			in  = new BufferedReader ( new InputStreamReader  (socket.getInputStream()  ));
            out = new PrintWriter    ( new OutputStreamWriter (socket.getOutputStream() ));
            
            new IO(this, window).start();
			
			start();
		} catch (IOException e) {
			window.println("[!]: Cannot connect to " + ip + ":" + port + ":" + e);
			System.exit(0);
		}
	}
	
	public void run()
	{
		String line;
		while (true)
		{
			try {
                line = in.readLine();
                if ((line == null) || line.equalsIgnoreCase("EXIT")) {
                    socket.close();
                    System.exit(0);
                } else {
                	window.println(line);
                }
            } catch (IOException e) {
                System.exit(0);
            }
		}
	}
	
	public void sendMessage(String msg)
	{
		if (out != null)
		{
			out.println(msg);
			out.flush();
		}
	}
}
