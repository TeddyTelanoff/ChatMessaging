package me.Treidex.ChatMessaging.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader extends Thread
{
	private Client client;
	private BufferedReader in;
	
	public ConsoleReader(Client client)
	{
		this.client = client;
	}
	
	public void run()
	{
		in = new BufferedReader(new InputStreamReader(System.in));
		
		String line;
		while (true)
		{
			try {
				line = in.readLine();
				client.sendMessage(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
