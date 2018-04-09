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
 * 创建客户端: 发送数据+接收数据
 * 写出数据：输出流
 * 读取数据：输入流
 * 输入流 与输出流 在同一个线程内 应该 独立处理，彼此独立
 *  加入名称 
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
		new Thread(new Send(client,name)).start(); //一条路径
		new Thread(new Receive(client)).start(); //一条路径
	}

}
