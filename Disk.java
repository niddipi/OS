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
	static int available_space=256;	
	static int[] disk_fmbv = new int[256];
	Memory_util m_util = new Memory_util();
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
			if(disk[i] != null){
			System.out.println("index i: "+i+" "+disk[i]);}	
			i++;
		}	
	}	


/****This Function returns the immediate 
     available memory Frame******/
        public int Free()
        {
                int loc = 0;
                while(loc !=256)
                {
                        if(disk_fmbv[loc] == 0){
                                disk_fmbv[loc] =1;
                                break;
                        }
                        loc++;
                }
                return loc;
        }
	
/***********************************
Check Available Page functions 
checks for available page in memory
************************************/

int Check_avialable_page(int id1){
        int no_of_pages = m_util.pcb[id1].Total_no_of_pages;
        int k =0;
        int value = -1;
	
        while(k<no_of_pages){

		if(m_util.pcb[id1].disk_frame_no[k] != -1){
			value =m_util.pcb[id1].disk_frame_no[k];
			m_util.pcb[id1].disk_frame_no[k] = -1;
			break;
		}
        	k++;
        }
        return value;
}

/***Initializes FMBV Vector****/
public void Init_diskfmbv()
        {
                int i = 0;
                while(i<256)
                {
                        disk_fmbv[i] = 0;
                        i++;
                }
        }

void display_disk(int id)
	{
		int i = 0;
		while(i<m_util.pcb[id].Total_no_of_pages){
		if(m_util.pcb[id].Disk_PMT[i] >= 0){
		System.out.println("i :"+i+" "+"index"+m_util.pcb[id].Disk_PMT[i]+
							disk[m_util.pcb[id].Disk_PMT[i]]);
		}
		i++;
		}

	}
}
