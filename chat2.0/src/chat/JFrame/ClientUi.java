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
	static JTextArea wby;   //文本域
	JScrollPane gd;			//添加滚动			
	ServerSocket server;	//服务器
	Socket mySocket;		//
	JLabel bq1, bq2, bq3;	//标签： 主图, 用户名 ，登入密码
	JPanel mb1, mb2, mb3;	//主面板 ，副面版 ， 聊天界面
	JButton an1, an2, an3;	//按钮 ：登入，注册，发送
	JTextField wbk, wbk2;	//文本框： 用户名输入 ，发送内容
	JPasswordField mmk;		//密码框： 用户密码
//	public static int cs ;

	public ClientUi() throws Exception {
		bq1 = new JLabel(new ImageIcon("images/title1.jpg"));
		bq2 = new JLabel("用户名", JLabel.CENTER);// 字体居中
		bq3 = new JLabel("登入密码", JLabel.CENTER);
		
		mb1 = new JPanel();		//new 面板
		mb2 = new JPanel();
		
		an1 = new JButton("登入");
		an1.setForeground(Color.BLUE);// 设置字体颜色
		
		
		//按钮添加触发事件
		an1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				Sql sql = new Sql();
				Main.name = String.valueOf(wbk.getText());	//获取用户名并传给Client.name
				String pw =  String.valueOf(mmk.getText());
				boolean lg = sql.login(Main.name, pw);
				if (lg) {	//用户名不为空
					inmb3();    //进入面板3
					Main.start();		//启动服务器
					}
				else {
					new ErrorUi("输入密码或用户名错误");
				}
				} catch (Exception e1) {
					new ErrorUi("错误"+e1.getMessage());
					}
					
				}
		});
		
		an2 = new JButton("注册");
		an2.setForeground(Color.BLUE);
		an2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				String name = String.valueOf(wbk.getText());	//获取用户名并传给Client.name
				String pw =  String.valueOf(mmk.getText());
				wbk.setText("");
				mmk.setText("");
				try {
					if(name.charAt(0)!=' '&&pw.charAt(0)!=' '){
						Sql sql = new Sql();
						sql.addUser(name, pw);
					}
					else
						new ErrorUi("用户名或密码不能为空");
				
				} catch (Exception e1) {
					if(e1.getMessage().equals("[Microsoft][ODBC SQL Server Driver][SQL Server]违反了 PRIMARY KEY 约束“PK_user”。不能在对象“dbo.chat”中插入重复键。重复键值为 (11                  )。"))
						new ErrorUi("用户名已存在");
					else
						new ErrorUi("错误"+e1.getMessage());
				}
				
				
			}
			
		});
		wbk = new JTextField(15);
		mmk = new JPasswordField(15);
		mb1.setLayout(new GridLayout(2, 2));  //将面板1 分成2*2的网格布局
		mb1.add(bq2);			//向面板中添加标签，文本框等等
		mb1.add(wbk);
		mb1.add(bq3);
		mb1.add(mmk);
		mb2.add(an1);
		mb2.add(an2);
		this.add(bq1, BorderLayout.NORTH);		//将主图放在北部
		this.add(mb1, BorderLayout.CENTER);		//面板1放在中部
		this.add(mb2, BorderLayout.SOUTH);		//将面板2放在南部

		//定义聊天界面
		wby = new JTextArea();
		gd = new JScrollPane(wby);
		an3 = new JButton("发送");
		wbk2 = new JTextField(20);
		wbk2.requestFocus();
		mb3 = new JPanel();
		mb3.add(wbk2);
		mb3.add(an3);

		this.setTitle("浪迹天涯聊天室");		 //设置界面标题
		this.setIconImage(new ImageIcon("images/title.jpg").getImage());  //界面log
		this.setSize(350, 450);	   //界面大小
		this.setResizable(true);	//不允许扩大界面
		this.setLocation(500, 120);		//打开位置坐标
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);  //允许关闭界面
		this.setVisible(true);
		an3.addActionListener(this);	//发送按钮添加事件
		wbk2.addActionListener(this);	//发送内容添加事件
	}

	public void actionPerformed(ActionEvent arg0) {
		String s = wby.getText();		//获取已经发送内容
		String str = wbk2.getText();	//获取发送框的内容
		if (str.charAt(0)!=' ') {      //发送内容不为空
			s = s  + " 我  :   " + str + "\n"; //已发送内容+我+发送内容
			wby.setText(s);			//显示内容
			Send.getMsg(str);		//服务器发送内容
			wbk2.setText("");		//发送框置空
			wbk2.requestFocus();
		} else {
			wbk2.requestFocus();
				new ErrorUi("不能发送空消息！");
		}
		

	}

	public static void setText(String s) {  //从 Recice 接收服务器发送的内容
		String str = wby.getText();		//获取已经发送内容
		str += s;
		wby.setText(str);     //显示内容
	}

	public void inmb3() {
		this.remove(bq1);		//将登入界面移除
		this.remove(mb1);
		this.remove(mb2);
		//设置聊天界面
		this.getContentPane().add(gd, "Center"); //添加聊天框
		this.getContentPane().add(mb3, "South");  //添加界面3
		this.setVisible(true);
	}
}
