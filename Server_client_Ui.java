package chat;

import java.awt.HeadlessException;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

public class Server_client_Ui extends JFrame implements ActionListener {
	JTextArea wby ;
	JTextField wbk;
	JScrollPane gd;
	JButton an;
	JPanel mb;
	int port;
	String s ="";
	String myID;
	Date date;
	ServerSocket server;
	Socket mySocket;
	BufferedReader is;
	PrintWriter os;
	String line;
	
	
	public Server_client_Ui(String ID,String remoteID,String IP,int port,boolean isServer) {
		super(ID);
		myID = ID;
		this.port =port;
		wby = new JTextArea();
		
		gd =  new JScrollPane(wby);
		this.setSize(330,400);
		this.setResizable(false);
		this.getContentPane().add(gd,"Center");
		mb = new JPanel();
		this.getContentPane().add(mb,"South");
		an = new JButton("·¢ËÍ");
		wbk = new JTextField(20);
		wbk.requestFocus();
		mb.add(wbk);
		mb.add(an);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		an.addActionListener(this);
		wbk.addActionListener(this);
		if(isServer) {
			
			try {
				server = null;
				server = new ServerSocket(port);
				mySocket = null;
				mySocket = server.accept();
				is = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
				os = new PrintWriter(mySocket.getOutputStream());
		
			  } catch (IOException e) {
				System.out.println("error:"+e);
			 }
			
				 }//end of if
		 else
			try {
				mySocket = new Socket(IP,port);
				os = new PrintWriter(mySocket.getOutputStream());
				is = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("error:"+e);
			}
		while(true) {
			try {
				line = is.readLine();
				date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yy-mm-dd:HH:mm:ss");
				String currentTime = formatter.format(date);
				s += currentTime +" "+remoteID+"   say:\n"+line+"\n";
				wby.setText(s);
			} catch (IOException e) {
				
				System.out.println("error:"+e);
			}
				
		}
		
		
	}


	public void actionPerformed(ActionEvent arg0) {
		date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yy-mm-dd:HH:mm:ss");
		String currentTime = formatter.format(date);
		String str =wbk.getText();
		s += currentTime +" "+myID+"   say:\n"+str+"\n";
		wby.setText(s);
		os.println(str);
		os.flush();
		wbk.setText("");
		wbk.requestFocus();
	
	}

}
