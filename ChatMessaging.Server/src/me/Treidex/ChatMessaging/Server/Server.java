package me.Treidex.ChatMessaging.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
	public static final int PORT = 25569;
	
	public static void main(String[] args)
	{
		new Server(PORT).start();
	}
	
	private Socket socket;
	private ServerSocket server;
	
	private int port;
	
	public Server(int port)
	{
		this.port = port;
	}
	
	public void start()
	{
		try
		{
			server = new ServerSocket(port);
			System.out.println("[$]: Server Running at port " + port + "!");
			System.out.println("[$]: Maximum Users=" + Client.maxClients);
		}
		catch (IOException e)
		{
			System.err.println("[!]: Server could not Start: " + e);
		}
		
		while (true)
		{
			try {
                socket = server.accept();
            } catch (IOException e) {
            	System.err.println("[!]: Server could not get Client: " + e);
            }
			
			if (Client.enoughSpace())
				new Client(socket).start();
		}
	}
}
