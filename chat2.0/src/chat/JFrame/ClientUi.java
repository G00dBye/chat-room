package chat.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import chat.Sql.Sql;
import chat.main.Main;
import chat.send.Send;

@SuppressWarnings("all")
public class ClientUi extends JFrame implements ActionListener {
	static JTextArea wby;   //�ı���
	JScrollPane gd;			//��ӹ���			
	ServerSocket server;	//������
	Socket mySocket;		//
	JLabel bq1, bq2, bq3;	//��ǩ�� ��ͼ, �û��� ����������
	JPanel mb1, mb2, mb3;	//����� ������� �� �������
	JButton an1, an2, an3;	//��ť �����룬ע�ᣬ����
	JTextField wbk, wbk2;	//�ı��� �û������� ����������
	JPasswordField mmk;		//����� �û�����
//	public static int cs ;

	public ClientUi() throws Exception {
		bq1 = new JLabel(new ImageIcon("images/title1.jpg"));
		bq2 = new JLabel("�û���", JLabel.CENTER);// �������
		bq3 = new JLabel("��������", JLabel.CENTER);
		
		mb1 = new JPanel();		//new ���
		mb2 = new JPanel();
		
		an1 = new JButton("����");
		an1.setForeground(Color.BLUE);// ����������ɫ
		
		
		//��ť��Ӵ����¼�
		an1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				Sql sql = new Sql();
				Main.name = String.valueOf(wbk.getText());	//��ȡ�û���������Client.name
				String pw =  String.valueOf(mmk.getText());
				boolean lg = sql.login(Main.name, pw);
				if (lg) {	//�û�����Ϊ��
					inmb3();    //�������3
					Main.start();		//����������
					}
				else {
					new ErrorUi("����������û�������");
				}
				} catch (Exception e1) {
					new ErrorUi("����"+e1.getMessage());
					}
					
				}
		});
		
		an2 = new JButton("ע��");
		an2.setForeground(Color.BLUE);
		an2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				String name = String.valueOf(wbk.getText());	//��ȡ�û���������Client.name
				String pw =  String.valueOf(mmk.getText());
				wbk.setText("");
				mmk.setText("");
				try {
					if(name.charAt(0)!=' '&&pw.charAt(0)!=' '){
						Sql sql = new Sql();
						sql.addUser(name, pw);
					}
					else
						new ErrorUi("�û��������벻��Ϊ��");
				
				} catch (Exception e1) {
					if(e1.getMessage().equals("[Microsoft][ODBC SQL Server Driver][SQL Server]Υ���� PRIMARY KEY Լ����PK_user���������ڶ���dbo.chat���в����ظ������ظ���ֵΪ (11                  )��"))
						new ErrorUi("�û����Ѵ���");
					else
						new ErrorUi("����"+e1.getMessage());
				}
				
				
			}
			
		});
		wbk = new JTextField(15);
		mmk = new JPasswordField(15);
		mb1.setLayout(new GridLayout(2, 2));  //�����1 �ֳ�2*2�����񲼾�
		mb1.add(bq2);			//���������ӱ�ǩ���ı���ȵ�
		mb1.add(wbk);
		mb1.add(bq3);
		mb1.add(mmk);
		mb2.add(an1);
		mb2.add(an2);
		this.add(bq1, BorderLayout.NORTH);		//����ͼ���ڱ���
		this.add(mb1, BorderLayout.CENTER);		//���1�����в�
		this.add(mb2, BorderLayout.SOUTH);		//�����2�����ϲ�

		//�����������
		wby = new JTextArea();
		gd = new JScrollPane(wby);
		an3 = new JButton("����");
		wbk2 = new JTextField(20);
		wbk2.requestFocus();
		mb3 = new JPanel();
		mb3.add(wbk2);
		mb3.add(an3);

		this.setTitle("�˼�����������");		 //���ý������
		this.setIconImage(new ImageIcon("images/title.jpg").getImage());  //����log
		this.setSize(350, 450);	   //�����С
		this.setResizable(true);	//�������������
		this.setLocation(500, 120);		//��λ������
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);  //����رս���
		this.setVisible(true);
		an3.addActionListener(this);	//���Ͱ�ť����¼�
		wbk2.addActionListener(this);	//������������¼�
	}

	public void actionPerformed(ActionEvent arg0) {
		String s = wby.getText();		//��ȡ�Ѿ���������
		String str = wbk2.getText();	//��ȡ���Ϳ������
		if (str.charAt(0)!=' ') {      //�������ݲ�Ϊ��
			s = s  + " ��  :   " + str + "\n"; //�ѷ�������+��+��������
			wby.setText(s);			//��ʾ����
			Send.getMsg(str);		//��������������
			wbk2.setText("");		//���Ϳ��ÿ�
			wbk2.requestFocus();
		} else {
			wbk2.requestFocus();
				new ErrorUi("���ܷ��Ϳ���Ϣ��");
		}
		

	}

	public static void setText(String s) {  //�� Recice ���շ��������͵�����
		String str = wby.getText();		//��ȡ�Ѿ���������
		str += s;
		wby.setText(str);     //��ʾ����
	}

	public void inmb3() {
		this.remove(bq1);		//����������Ƴ�
		this.remove(mb1);
		this.remove(mb2);
		//�����������
		this.getContentPane().add(gd, "Center"); //��������
		this.getContentPane().add(mb3, "South");  //��ӽ���3
		this.setVisible(true);
	}
}
