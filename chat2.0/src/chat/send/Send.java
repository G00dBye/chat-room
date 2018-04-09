package chat.send;

import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;

import chat.server.CloseUtil;


/**
 * �������� �߳�
 * 
 * @author Administrator
 *
 */
public class Send implements Runnable {
	// �ܵ������
	private DataOutputStream dos;
	// �����߳�
	private boolean isRunning = true;
	// ����
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
	 * 1���ӿ���̨�������� 2����������
	 */
	public void send(String msg) {
		try {
			if (null != msg && !msg.equals("")) {
				dos.writeUTF(msg);
				dos.flush(); // ǿ��ˢ��
				isgetMsg = false;
			}
		} catch (IOException e) {
			
			isRunning = false;
			CloseUtil.closeAll(dos);
		}
	}


	public void run() {
		//�߳���
		while(isRunning){
			if(isgetMsg)
			send(msg);
		}
	}

}
