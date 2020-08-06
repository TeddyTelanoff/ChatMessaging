package me.Treidex.ChatMessaging.Client;

public class IO extends Thread
{
	private Client client;
	private Window window;
	
	public IO(Client client, Window window)
	{
		this.client = client;
		
		this.window = window;
	}
	
	public void run()
	{
		while (true)
		{
			client.sendMessage(window.readLine());
		}
	}
}
