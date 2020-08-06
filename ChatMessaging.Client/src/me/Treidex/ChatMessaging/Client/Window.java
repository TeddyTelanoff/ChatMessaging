package me.Treidex.ChatMessaging.Client;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Window extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JTextArea in;
	private JTextArea out;
	
	public Window()
	{
		in = new JTextArea("Type in the IP you want to connect to!");
		
		out = new JTextArea("[$]: Default Port set to " + Client.PORT + ".");
		out.setEditable(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void create() throws IOException
	{
		setIconImage(ImageIO.read(Window.class.getResourceAsStream("/icon.png")));
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setVisible(true);
		
		setLayout(new BorderLayout());
		
		add(out, BorderLayout.PAGE_START);
		add(in, BorderLayout.SOUTH);
	}
	
	public synchronized String readLine()
	{
		for (;;)
			if (in.getText().contains("\n"))
			{
				String text = in.getText().substring(0, in.getText().length() - 1);
				
				in.setText("");
				
				return text;
			}
	}
	
	public void println(String msg)
	{
		out.setText(out.getText() + msg + "\n");
	}
}
