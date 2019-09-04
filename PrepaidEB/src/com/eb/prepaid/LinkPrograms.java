package com.eb.prepaid;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class LinkPrograms extends JFrame implements ActionListener
{
	JButton jButton1;
	JButton jButton2;
	JButton jButton3;
	JLabel jLabel1;
	private JPanel contentPane;
	
	LinkPrograms()
	{
		setTitle("EB PrePaid : Linking Programs");
		contentPane = (JPanel)this.getContentPane();
		
		jLabel1=new JLabel("EB PrePaid - Linking Programs");
		jLabel1.setFont(new Font("TimesRoman",Font.BOLD,14));
		jLabel1.setForeground(Color.blue);
		
		jButton1=new JButton("Run Project");
		jButton2=new JButton("Insert Records");
		jButton3=new JButton("Verify Records");
		
		jButton1.addActionListener(this);
		jButton2.addActionListener(this);
		jButton3.addActionListener(this);
		
		contentPane.setLayout(null);
		addComponent(contentPane, jLabel1, 110,75,200,20);
		addComponent(contentPane, jButton1, 110,150,150,25);
		addComponent(contentPane, jButton2, 110,200,150,25);
		addComponent(contentPane, jButton3, 110,250,150,25);
		
		setSize(400,400);
		setVisible(true);
		addWindowListener(new WindowClose());
	}
	
	private void addComponent(Container container,Component c,int x,int y,int width,int height)
	{
		c.setBounds(x,y,width,height);
		container.add(c);
	}
	
	public void actionPerformed(ActionEvent ev)
	{
		String cmd=ev.getActionCommand();
		if(cmd.equals("Run Project"))
		{
			new PrepaidMain();
			jButton1.setEnabled(false);
			jButton2.setEnabled(false);
			jButton3.setEnabled(false);
		}
		
		if(cmd.equals("Insert Records"))
		{
			setVisible(false);
			new InsertRecord();
		}
			
		if(cmd.equals("Verify Records"))
		{
			setVisible(false);
			new VerifyRecords();
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
		new LinkPrograms();
	}
}