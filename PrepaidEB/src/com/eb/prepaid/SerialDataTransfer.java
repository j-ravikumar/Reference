package com.eb.prepaid;


import java.io.*;
public class SerialDataTransfer
{
  public static void main(String args[]) 
  {
    SimpleSerial ss; // declare the SimpleSerial object
    ss = new SimpleSerialNative(1); // new up the SimpleSerial object
    //ss.writeByte((byte)'a'); // write a byte to the serial port
    // System.out.println("writed");
    // Give the PIC chip time to digest the byte to make sure it's
    // ready for the next byte.
    try 
    { 
    Thread.sleep(750); 
    } 
    catch (InterruptedException e) 
    {}
    //ss.writeByte((byte)'!'); // write another byte to the serial port
    //System.out.println("writed");
    String inputString = ss.readString(); 
    // read any string from the serial port.
    System.out.println("I read the string: " + inputString);
    for(int jj =1;jj<=500;jj++)
    ss.writeByte((byte)'a');
  
  } 
}