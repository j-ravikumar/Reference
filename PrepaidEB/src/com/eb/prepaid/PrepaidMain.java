package com.eb.prepaid;


import java.io.*;
import java.sql.*;
import java.util.*;

class PrepaidMain implements Runnable
{
	String unit;
	Thread t;
	SimpleSerial ss;
	String secretCode;
	String amountInDatabase;
	String amountForSecretCode;
	String lastDigitOfAmount;
	String netAmount;
	PrepaidMain()
	{
		ss=new SimpleSerialNative(1);
		t=new Thread(this);
		t.start();
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				System.out.println("Waiting");
				readDataFromSerialPort();
				sendnetAmountToSerialPort();
			}
		}
		catch(Exception e2)
		{
		}
	}
	
	void readDataFromSerialPort()throws Exception
	{
		String tempVar1="";
		String tempVar2="";
		String finalVar="";
		secretCode="";
		while(true)
		{
			tempVar1=ss.readString();
			//DataInputStream dis=new DataInputStream(System.in);
			//tempVar1=dis.readLine();
			if(tempVar1.length()!=0)
			{
				secretCode+=tempVar1;
				if(tempVar1.equals("X"))
				{
					break;
				}
				if(secretCode.length()==5)
				{
					break;
				}
			}
		}
	
		System.out.println(secretCode);
		if(!(tempVar1.equals("X")))
		{
			amountInDatabase=getAmountFromDatabase();
			amountForSecretCode=getAmountForSecretCode();
			lastDigitOfAmount="1";
			netAmount=Double.toString(Double.parseDouble(amountInDatabase)+Double.parseDouble(amountForSecretCode));
			updateTable(netAmount);
			netAmount=getAmountFromDatabase();
			netAmount=checkAmount(netAmount);
			netAmount+="2";
		}
		else
		{
			
			addAPulse();
			System.out.println("addpulse");
			amountInDatabase=getAmountFromDatabase();
			calculateAmount();
			lastDigitOfAmount=getLastDigit();
			netAmount=checkAmount(amountInDatabase);
			updateTable(netAmount);
			netAmount=netAmount+lastDigitOfAmount;
		}
		
		
	}

	String getAmountFromDatabase()
	{
		String stringFromDatabase="";
		try
		{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con=DriverManager.getConnection("jdbc:odbc:prepaid","","");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select amount from units where cust='"+"1"+"'");
		rs.next();
		stringFromDatabase=rs.getString(1);
		stmt.close();
		con.close();
		}
		catch(Exception e1)
		{
			System.out.println("Error in phase 1");
		}
		return stringFromDatabase;
	}
	
	String getAmountForSecretCode()
	{
		String stringFromDatabase="";
		try
		{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con=DriverManager.getConnection("jdbc:odbc:prepaid","","");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select Amount from AccountTable where SecretCode='"+secretCode+"'");
		rs.next();
		stringFromDatabase=rs.getString(1);
		stmt.executeUpdate("delete from AccountTable where SecretCode='"+secretCode+"'");
		stmt.close();
		con.close();
		}
		catch(Exception e2)
		{
			stringFromDatabase="0";
		}
		return stringFromDatabase;
	}
	
	void updateTable(String net)throws Exception
	{
		System.out.println(net);
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con=DriverManager.getConnection("jdbc:odbc:prepaid","","");
		Statement stmt=con.createStatement();
		stmt.executeUpdate("update units set amount='"+Double.parseDouble(net)+"' where cust='"+"1"+"'");
		stmt.close();
		con.close();
	}
	
	void addAPulse()
	{
		try
		{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con=DriverManager.getConnection("jdbc:odbc:prepaid","","");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select unit from units where cust='"+"1"+"'");
		rs.next();
		unit=rs.getString(1);
		unit=Long.toString(Long.parseLong(unit)+1);
		stmt.executeUpdate("update units set unit='"+unit+"' where cust='"+"1"+"'");
		stmt.close();
		con.close();
		}
		catch(Exception e3)
		{
			System.out.println("Error in phase 3");
		}
	}
	
	void calculateAmount()
	{
		if(Long.parseLong(unit)<=25)
		{
			amountInDatabase=Double.toString(Double.parseDouble(amountInDatabase)-(double)0.75);
		}
		
		if(Long.parseLong(unit)>25 && Long.parseLong(unit)<=50)
		{
			amountInDatabase=Double.toString(Double.parseDouble(amountInDatabase)-(double)0.85);
		}
		
		if(Long.parseLong(unit)>50 && Long.parseLong(unit)<=100)
		{
			amountInDatabase=Double.toString(Double.parseDouble(amountInDatabase)-(double)1.50);
		}
		
		if(Long.parseLong(unit)>100 && Long.parseLong(unit)<=300)
		{
			amountInDatabase=Double.toString(Double.parseDouble(amountInDatabase)-(double)2.20);
		}
		
		if(Long.parseLong(unit)>300)
		{
			amountInDatabase=Double.toString(Double.parseDouble(amountInDatabase)-(double)3.05);
		}
		if(Double.parseDouble(amountInDatabase)<0)
		{
			amountInDatabase="0.00";
		}
		try
		{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con=DriverManager.getConnection("jdbc:odbc:prepaid","","");
		Statement stmt=con.createStatement();
		stmt.executeUpdate("update units set amount='"+Double.parseDouble(amountInDatabase)+"' where cust='"+"1"+"'");
		stmt.close();
		con.close();
		}
		catch(Exception e4)
		{
			System.out.println("Error in Phase 4");
		}
	}
	
	String getLastDigit()
	{
		String lastBitRet="";
		if(!(Double.parseDouble(amountInDatabase)==0) && Double.parseDouble(amountInDatabase)>20)
		{
			lastBitRet="2";
		}
		
		if(!(Double.parseDouble(amountInDatabase)==0) && Double.parseDouble(amountInDatabase)<=20)
		{
			lastBitRet="4";
		}
		if(Double.parseDouble(amountInDatabase)==0)
		{
			lastBitRet="6";
		}
		return lastBitRet;
	}
	
	
	String checkAmount(String getString1)
	{
		String getString2="";
		int ind=getString1.indexOf(".");
		if(ind<0)
		{
			getString2=getString1+".00";
		    getString1=getString2;
		}
		else
		{
			int len=getString1.length();
			int diff=len-ind;
			if(diff==2)
			{
				getString1+="0";
			}
			else if(diff==3)
			{
				getString1=getString1;
			}
			else
			{
				String newString=getString1.substring(0,ind+3);
				getString1=newString;
			}
		}
		
		int leng=getString1.length();
		String strng=getString1.substring(leng-1,leng);
		if(strng.equals("1")){strng="0";}
		if(strng.equals("4")){strng="5";}
		if(strng.equals("6")){strng="5";}
		if(strng.equals("9")){strng="0";}
		String strng1=getString1.substring(0,leng-1);
		getString1=strng1+strng;
		return getString1;
	}
	
	
	
	void sendnetAmountToSerialPort()
	{
		netAmount=netAmount.replace('.','A');
		System.out.println(netAmount);
		
		char ch;
	    int r,dig;
	    for(int k=0;k<netAmount.length();k++)
	    {
		    ch=netAmount.charAt(k);
		    r=(int)ch;
		    try
		    {
		    	Thread.sleep(300);
		    }
		    catch(Exception occ)
		    {
		    }
		    if(r==65)
		    {
		    	ss.writeByte((byte)ch);
		    	System.out.println(ch);
		    }
		    else
		    {
			    dig=Integer.parseInt(netAmount.substring(k,k+1));
			    System.out.println(dig);
			    ss.writeByte((byte)dig);
			    try
			    {
			    	Thread.sleep(500);
			    }
			    catch(Exception eft)
			    {
			    }
		    }
	    }
		
	}
	
	public static void main(String ar[])
	{
		new PrepaidMain();
	}
}