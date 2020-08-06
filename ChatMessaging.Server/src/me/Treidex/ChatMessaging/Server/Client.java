package me.Treidex.ChatMessaging.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client extends Thread {
	public static int maxClients = 20;
	private static List<Client> clients = new ArrayList<Client>();
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	private String name;
	
	public Client(Socket socket)
	{
		this.socket = socket;
		
		clients.add(this);
		name = socket.getLocalAddress().toString();
	}
	
	public void run() {
        try {
            in  = new BufferedReader ( new InputStreamReader  (socket.getInputStream()  ));
            out = new PrintWriter    ( new OutputStreamWriter (socket.getOutputStream() ));
        } catch (IOException e) {
        	clients.remove(this);
            return;
        }
        
        broadcastMessage("[$]: '" + name + "' Joined.");
        
        String line;
        while (true) {
            try {
                line = in.readLine();
                if ((line == null) || line.equalsIgnoreCase("EXIT")) {
                    exit();
                    return;
                } else if (line.length() > 0) {
                	System.out.println("[#]<" + name + ">" + line);
                	
                	if (line.contains("$name"))
                	{
                		name = line.split(" ")[1];
                	}
                	
                	if (line.charAt(0) == '@')
                	{
                		StringBuilder sb = new StringBuilder();
                		
                		for (int i = 1; i < line.split(" ").length; i++)
                		{
                			sb.append(line.split(" ")[i] + " ");
                		}
                		
                		sendMessage("[#]<" + name + "> " + sb, line.split(" ")[0].substring(1, line.split(" ")[0].length() - 1));
                	}
                	else
                	{
	                	broadcastMessage("[#]<" + name + "> " + line);
                	}
                }
            } catch (IOException e) {
            	clients.remove(this);
                return;
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
	
	public void sendMessage(String msg, String name)
	{
		for (Client client: clients)
			if (client.name == name)
				client.sendMessage(msg);
	}
	
	public void exit() throws IOException
	{
		broadcastMessage("[$]: '" + name + "' Left.");
		socket.close();
        clients.remove(this);
	}
	
	public static void broadcastMessage(String msg)
	{
		for (Client client: clients)
			client.sendMessage(msg);
		System.out.println(msg);
	}
	
	public static boolean enoughSpace()
	{
		return clients.size() < maxClients;
	}
}
