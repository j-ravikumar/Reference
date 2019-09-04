package com.eb.prepaid;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class PrepaidEb  extends JFrame implements ActionListener
{
	JLabel jLabel1;
	JLabel jLabel2;
	JLabel jLabel3;
	JTextField jTextField1;
	JPasswordField jPasswordField1;
	JButton jButton1;
	JButton jButton2;
	private JPanel contentPane;
		
	PrepaidEb()
	{
		setTitle("EB PrePaid Systems");
		contentPane = (JPanel)this.getContentPane();
		
		jLabel1=new JLabel("EB PrePaid - Authentication");
		jLabel1.setFont(new Font("TimesRoman",Font.BOLD,14));
		jLabel1.setForeground(Color.blue);
		
		jLabel2=new JLabel("UserName");
		jLabel3=new JLabel("Password");
		
		jButton1=new JButton("Login");
		jButton2=new JButton("Clear");
		
		jTextField1=new JTextField(20);
		jPasswordField1=new JPasswordField(20);
		
		contentPane.setLayout(null);
		addComponent(contentPane, jLabel1, 150,75,200,20);
		addComponent(contentPane, jLabel2, 100,150,100,20);
		addComponent(contentPane, jTextField1, 250,150,100,20);
		addComponent(contentPane, jLabel3, 100,200,100,20);
		addComponent(contentPane, jPasswordField1, 250,200,100,20);
		addComponent(contentPane, jButton1, 100,250,100,25);
		addComponent(contentPane, jButton2, 250,250,100,25);
		
		setSize(500,500);
		setVisible(true);
		addWindowListener(new WindowClose());
		jButton1.addActionListener(this);
		jButton2.addActionListener(this);
	}
	
	private void addComponent(Container container,Component c,int x,int y,int width,int height)
	{
		c.setBounds(x,y,width,height);
		container.add(c);
	}
	
	public void actionPerformed(ActionEvent e1)
	{
		String cmd=e1.getActionCommand();
		
		if(cmd.equals("Login"))
		{
			if((jTextField1.getText()).equals("prepaid") && (jPasswordField1.getText()).equals("electric"))
			{
				setVisible(false);
				new LinkPrograms();
			}
			else
			{
				jTextField1.setText("");
				jPasswordField1.setText("");
				JOptionPane.showMessageDialog(this,"Username / Password is Incorrect");
				
			}
		}
		
		if(cmd.equals("Clear"))
		{
			jTextField1.setText("");
			jPasswordField1.setText("");
		}
		
	}
	
	class WindowClose extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	}
	
	public static void main(String ar[])
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		new PrepaidEb();
	}
}