package chat.send;

import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;

import chat.server.CloseUtil;


/**
 * 发送数据 线程
 * 
 * @author Administrator
 *
 */
public class Send implements Runnable {
	// 管道输出流
	private DataOutputStream dos;
	// 控制线程
	private boolean isRunning = true;
	// 名称
	private String name;
	private static String msg;
	private static boolean isgetMsg = false;

	public Send(Socket client, String name) {
		try {
			dos = new DataOutputStream(client.getOutputStream());
			this.name = name;
			send(this.name);
		} catch (IOException e) {
			
			isRunning = false;
			CloseUtil.closeAll(dos);

		}
	}

	public static void getMsg(String s) {
		if (s.length()!=0){
			msg = s;
		isgetMsg = true;}
	}

	/**
	 * 1、从控制台接收数据 2、发送数据
	 */
	public void send(String msg) {
		try {
			if (null != msg && !msg.equals("")) {
				dos.writeUTF(msg);
				dos.flush(); // 强制刷新
				isgetMsg = false;
			}
		} catch (IOException e) {
			
			isRunning = false;
			CloseUtil.closeAll(dos);
		}
	}


	public void run() {
		//线程体
		while(isRunning){
			if(isgetMsg)
			send(msg);
		}
	}

}
