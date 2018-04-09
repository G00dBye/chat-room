package chat.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;


import chat.JFrame.ClientUi;

import chat.receive.Receive;
import chat.send.Send;

/**
 * �����ͻ���: ��������+��������
 * д�����ݣ������
 * ��ȡ���ݣ�������
 * ������ ������� ��ͬһ���߳��� Ӧ�� ���������˴˶���
 *  �������� 
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public class Main {

	public static String name =null ;
	public static void main(String[] args) throws Exception {
			
		 new ClientUi();
	}
	public static void start() throws UnknownHostException, IOException{
		Socket client = new Socket("localhost",9988);
		new Thread(new Send(client,name)).start(); //һ��·��
		new Thread(new Receive(client)).start(); //һ��·��
	}

}
