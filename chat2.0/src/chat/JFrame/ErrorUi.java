package chat.JFrame;

/**
 * ����������
 * 1.����������û�������
 * 2.ע��ʱ�����û�������
 * 3.����Ϊ��
 * @author Administrator
 *
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ErrorUi extends JFrame {
	private static final int WIDTH = 300;  
	  
    private static final int HEIGHT = 180;  
    
		public ErrorUi(String str) {
			 final JFrame jf = new JFrame("error");  
		        jf.setSize(WIDTH, HEIGHT);  
		        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		        jf.setLocation(530, 280);	
		        jf.setVisible(true);  
		        JLabel error = new JLabel(str,JLabel.CENTER);
		        jf.add(error); 
		    }  
		
		

}
