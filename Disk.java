/**************************************************************************
* Filename = Disk.java
* 
*Description : This file is responsible for execution of all the Disk 
* 	 	related operations
*
**************************************************************************/
import java.util.*;
import java.io.*;

public class Disk{

	public static String[] disk = new String[256];
	
	public static int BR = 0;//Base register
	Error_Handler Er = new Error_Handler();
	int available_space=256;	

	void check_avail_space(int required_space){

		if(required_space > available_space)
		{
			try{
				Er.Error_Handler_func("INSUFFICIENT_AVAILABLE_DISK_SPACE");				

			}
			catch(Exception ex){
			}

			
		}	
	}

	//Write Function writes into disk
	int Write(int address,String data){
		try{
			if(disk[address] == null)
			{
				disk[address] = data;
			}
			else if(disk[address].length()<128)
			{
				disk[address] = disk[address]+data;
			}
			
		}
		catch(Exception ex){
		}
		return 0;
	}

	//Read Function reads from disk
	String Read(int address){

		try{

			return disk[address];
		}
		catch(Exception ex){
		}
		return null;
	}

	String Disk_func(String task,int address,String data)
	{
		String line=null;
		if(task.equals("READ"))
		{
			line = Read(address);
		}
		else
		{
			Write(address,data);
		}

		return line;
	}
	
	void Disk_display()
	{
		int i =0;
		while(i<256)
		{
			if(disk[i] == null)
			{
				break;
			}
			System.out.println(disk[i]);	
			i++;
		}	
	}	

}
