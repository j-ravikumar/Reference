package com.eb.prepaid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class InsertRecord extends JFrame implements ActionListener
{
	JLabel jLabel1;
	JLabel jLabel2;
	JLabel jLabel3;
	
	JTextField jTextField2;
	JTextField jTextField3;
	
	JButton jButton1;
		
	JPanel contentPane;
	
	InsertRecord()
	{
		setTitle("EB-Prepaid : InsertRecords");
		contentPane = (JPanel)this.getContentPane();
		
		jLabel1=new JLabel("EB PrePaid - InsertRecords");
		jLabel1.setFont(new Font("TimesRoman",Font.BOLD,14));
		jLabel1.setForeground(Color.blue);
		
		jLabel2=new JLabel("SecretCode");
		jLabel3=new JLabel("Amount");
		
		jTextField2=new JTextField(20);
		jTextField3=new JTextField(20);
		
		jButton1=new JButton("Insert");
		jButton1.addActionListener(this);
		
		contentPane.setLayout(null);
		addComponent(contentPane, jLabel1, 150,75,200,20);
		addComponent(contentPane, jLabel2, 100,150,100,20);
		addComponent(contentPane, jTextField2, 250,150,120,20);
		addComponent(contentPane, jLabel3, 100,200,100,20);
		addComponent(contentPane, jTextField3, 250,200,120,20);
		addComponent(contentPane, jButton1, 200,275,120,25);
		
		setSize(500,500);
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
		Statement stmt=null;
		Connection con=null;
		if(cmd.equals("Insert"))
		{
			try
			{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				con=DriverManager.getConnection("jdbc:odbc:prepaid","","");
				stmt=con.createStatement();
			    stmt.executeUpdate("insert into AccountTable values('"+jTextField2.getText()+"','"+jTextField3.getText()+"')");
				JOptionPane.showMessageDialog(this,"Record Inserted Successfully");
				stmt.close();
				con.close();
			}
			catch(Exception er)
			{
				JOptionPane.showMessageDialog(this,"Secret Code Already Exists.\nDon't Insert Duplicate Records");
				try
				{
					stmt.close();
					con.close();
				}
				catch(Exception ernt)
				{
				}
			}
		}
	}
	
	class WindowClose extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
		    new LinkPrograms();
		}
	}
	
	public static void main(String ar[])
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		new InsertRecord();
	}
}