package com.eb.prepaid;


import java.io.*;
public interface SimpleSerial {

    // These are copied right out of WINBASE.H
    // Most applications should be fine with the defaults.
    
    public static final int NOPARITY            = 0;
    public static final int ODDPARITY           = 1;
    public static final int EVENPARITY          = 2;
    public static final int MARKPARITY          = 3;
    public static final int SPACEPARITY         = 4;

    public static final int ONESTOPBIT          = 0;
    public static final int ONE5STOPBITS        = 1;
    public static final int TWOSTOPBITS         = 2;
      
    /*
    Returns the number of bytes available at the time of this call.
    It's possible there are more bytes by the time readByte() or
    readBytes() is executed.  But there will never be fewer.
    */
    public int available();
        
    /*
    returns TRUE if port is fully operationsal
    returns FALSE if there is a problem with the port
    */
    public boolean isValid();
    
    /*
    Be sure to close your serial port when done.  Note that port is
    automatically closed on exit if you don't close it yourself
    */
    public void close();
        
    /*
    Reads a single byte from the input stream.
    Return value will be from -128 to 127 (inclusive)
    If no data at serial port, waits in routine for data to arrive.
    Use available() to check how much data is available.
    If error, returns 256.
    */
    public int readByte();
    
    /*
    Reads all the bytes in the serial port.
    If no bytes availble, returns array with zero elements. 
    Never waits for data to arrive
    */
    public byte[] readBytes();
    
    /*
    Reads bytes from serial port and converts to a text string.
    DO NOT use this routine to read data.  Char->Byte converstion
    does strange things when the values are negative.  For non-
    text values, use readBytes() above
    */    
    public String readString();
   
    /*
    Writes a single byte to the serial port.
    This writes the data, whether the PIC is ready to receive or not.
    Be careful not to overwhelm the PIC chip with data.
    On pics without a hardware UART, the data will be ignored.
    On pics with a hardware UART, overflowing will loose data AND require
    the UART on the PIC to be reset.  You can reset the UART in PIC code,
    or manually turn the PIC off and then on.
    
    NOTE:  A byte has a value in the range of -128 to 127
    NOTE:  If you want to write a character, you need to cast it to a byte,
            for example:  simpleSerial.writeByte((char)'b');
    */
    public boolean writeByte(byte val);
        
    /*
    For more advanced use.  Gets the input stream associated with serial port
    */
    public InputStream getInputStream();   

    /*
    For more advanced use.  Gets the output stream associated with serial port
    */
    public OutputStream getOutputStream();   
    
}