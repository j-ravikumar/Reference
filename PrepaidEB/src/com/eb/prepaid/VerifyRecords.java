package com.eb.prepaid;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.swing.table.*;

class VerifyRecords extends JFrame 
{
	private JPanel contentPane;
	private JTable jTable2;
	private JScrollPane jScrollPane2;
	
	VerifyRecords()
	{
		setTitle("EB Prepaid : Verify Records");
		contentPane = (JPanel)this.getContentPane();
		jTable2 = new JTable();
		jTable2.setTableHeader(null);
		jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(jTable2);
		contentPane.setLayout(null);
		addComponent(contentPane, jScrollPane2, 50,40,600,260);
		addWindowListener(new myWindowClose());
		setSize(700,500);
		setVisible(true);
		try
		{
		setValuesToTable();
		}catch(Exception ew){}
		
	}
	
	private void addComponent(Container container,Component c,int x,int y,int width,int height)
	{
		c.setBounds(x,y,width,height);
		container.add(c);
	}
	
	void setValuesToTable()throws Exception
	{
		
		java.util.List arry1=new ArrayList();
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection cn=DriverManager.getConnection("jdbc:odbc:prepaid","","");
		Statement st=cn.createStatement();
		ResultSet rs=st.executeQuery("select * from AccountTable");
		int row=0;
		while(rs.next())
		{
			arry1.add(rs.getString(1));
			arry1.add(rs.getString(2));
			arry1.add("$");
			row+=1;
		}
		
		st.close();
		cn.close();

		
		//detecting number of columns
		String detectcol="";
		boolean detect=false;
		int col=0;
		for(int det=0;det<arry1.size();det++)
		{
			if(!detect)
			{
				detectcol=(String)arry1.get(det);
				if(detectcol.equals("$"))
				{
					col=det;
					detect=true;
					break;
				}
			}
		}
		
		//detecting number of rows
		int rows=(arry1.size())/(col+1);
	
		// setting rows and column for table
		jTable2.setModel(new DefaultTableModel(rows+1, col));
		// Setting header
		jTable2.setValueAt("SECRET CODE",0,0);
		jTable2.setValueAt("AMOUNT",0,1);
		// Setting values to table
		int r=1,c=0;
		String setTableValue="";
		for(int i=0;i<arry1.size();i++)
		{
			setTableValue=(String)arry1.get(i);
			if(!(setTableValue.equals("$")))
			{
				jTable2.setValueAt(setTableValue,r,c);
				c=c+1;
			}
			else
			{
				r=r+1;
				c=0;
			}
		}
		
	}
	
	private class myWindowClose extends WindowAdapter
    {
    	public void windowClosing(WindowEvent e)
    	{
    		try{
	    	   	    setVisible(false);
		    		new LinkPrograms();
	    	   }
	    	catch(Exception e1)
	    	   {
	    	   }
    	}
    }
    
	public static void main(String ar[])
	{
		new VerifyRecords();
	}
}