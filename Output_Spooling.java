/**************************************************************************
* Filename = Output_Spooling.java
* 
*Description : This file is responsible for execution of all the Output_Spooling 
* 	        related operations.Such as writing all the memory stats,
*              Output and Disk stats,errors etc
*
**************************************************************************/
import java.util.*;
import java.math.BigInteger;
import java.io.*;

public class Output_Spooling{

   CPU_util util = new CPU_util();
   public static int NATURE;
   public static String Err="";
   public static String Warn= null;
   Memory_util m_util = new Memory_util();
	Disk disk = new Disk();
   void Output_spool()
         throws IOException{
         try{
            Memory memory = new Memory();
            Memory_util m_util = new Memory_util();
            memory.memory_Stats();
   	 	int id =util.id;

            int i = 0;
            int no_of_pages = m_util.pcb[id].no_of_pages;
            int address = 0;
            int index = 0;
            int dirty_bit = 0;

            while(i<no_of_pages){
	       
              if(m_util.pcb[id].Page_Mem_order[i].Page_loc >= 0){  
               address = m_util.pcb[id].Page_Mem_order[i].Frame_base_address;
               index   = m_util.pcb[id].Page_Mem_order[i].Page_loc; 
               dirty_bit =m_util.pcb[id].Page_Mem_order[i].Dirty_bit;
	       System.out.println("release id :="+id+" address :"+address); 
	       m_util.fmbv[address] = 0;
               if(dirty_bit == 1)
               {
                  memory.Write_back_to_disk(address*8,index);
               }
		}
	    else{
			if(m_util.pcb[id].frame_no[i]>=0)
			{
				m_util.fmbv[m_util.pcb[id].frame_no[i]] = 0;
	       			System.out.println("id :="+id+" address :"+m_util.pcb[id].frame_no[i]); 
				m_util.pcb[id].frame_no[i] = -1;
			}
	    }
               i++;
            }	
	    m_util.Memory_Available = m_util.Memory_Available+no_of_pages;
	    m_util.display_fmbv();
            outputFile();

      int Base = m_util.pcb[id].Disk_base;
      int Inp_pages = (m_util.pcb[id].Input_seg_size/8);
      int output_pages = (m_util.pcb[id].Output_seg_size/8);
      int prog_pages = (m_util.pcb[id].Prog_seg_size/8);

      if(m_util.pcb[id].Prog_seg_size%8 != 0)
      {
         prog_pages = prog_pages + 1;
      }
      if(m_util.pcb[id].Input_seg_size%8 != 0)
      {
         Inp_pages = Inp_pages + 1;
      }
      if(m_util.pcb[id].Output_seg_size%8 != 0)
      {
         output_pages = output_pages + 1;
      }
      no_of_pages = prog_pages+Inp_pages+output_pages;
      
      i = 0;
	/*
      while(i<m_util.pcb[id].Total_no_of_pages){
	   disk.disk[Base+i] = null;	
	}
	*/
	disk.available_space = disk.available_space + prog_pages+Inp_pages;	
        m_util.pcb[id].valid_pcb = -1;
	    
         }catch(IOException e){
         }


   }
   /*****This function writes all required data
     to output device.
    *****/
   public void outputFile() 
         throws IOException,NoSuchElementException{
         try{
            FileWriter fw = 
               new FileWriter("output_file.txt",true);
   	    int id =util.id;
            String str = "";
            if(NATURE == 0)
            {
               str = str+"Normal";
            }
            else
            {
               str = str+"Abnormal";
               str = str+": "+Err;
            }
            String str1 = Memory_util.pcb[id].Page_Frames+"\n"; 
            str1 = str1+"JOBID(DECIMAL): ";
            str1=str1+m_util.pcb[id].JOBID+"\n";
	    
            str1 = str1+"Int_JOBID(DECIMAL): ";
            str1=str1+m_util.pcb[id].int_jobid+"\n";
            if(Warn != null)
            {
               str1 = str1+"Warnings: "+Warn+"\n";
            }
              if(m_util.pcb[id].INPUT != null)
              {
               str1=str1+"INPUT(binary): "+m_util.pcb[id].INPUT+"\n";	
              }
              else
              {
                str1=str1+"INPUT: "+"NO AVAILABLE INPUT"+"\n";	
             }

              if(m_util.pcb[id].OUTPUT != null)
             {
               str1=str1+"OUTPUT(binary): "+m_util.pcb[id].OUTPUT+"\n";	
             }
             else
              {
                str1=str1+"OUTPUT: "+"NO AVAILABLE OUTPUT"+"\n";	
             }
            str1=str1+"Nature of Termination: "+str+"\n";
	/*
            str1=str1+"Clock(HEX)                : ";
            str1=str1+Integer.toHexString(util.CLOCK)+"\n";
            str1=str1+"Run Time(DECIMAL)         : ";
            str1=str1+util.CLOCK+"\n"; 
            int ET = util.CLOCK - util.IO_CLOCK - util.PAGE_FAULT_CLOCK - util.SEG_FAULT_CLOCK;
            str1=str1+"Execution Time(DECIMAL)   : ";
            str1=str1+ET+"\n";
            str1=str1+"I/O Time(HEX)             : ";
            str1=str1+util.IO_CLOCK+"\n";
            str1=str1+"PAGE_FAULT_CLOCK(Decimal) : "+util.PAGE_FAULT_CLOCK+"\n";
            str1=str1+"SEG_FAULT_CLOCK(Decimal)  : "+util.SEG_FAULT_CLOCK+"\n";
            str1=str1+"MEMORY UTILIZATION(word)  : "+util.MEM_utilword_ratio+":"+"256"+" "+
               util.MEM_utilword_percent+"%"+"\n";
            str1=str1+"MEMORY UTILIZATION(frame) : "+util.MEM_utilframe_ratio+": "+"32"+" "+
               util.MEM_utilframe_percent+"%"+"\n";
            str1=str1+"MEMORY FRAGMENTATION(word): "+util.MEM_frag+"\n";
            double x = (double) (m_util.pcb[id].TOTAL_DISK_USAGE/Input_spooling.TOTAL_DISK_WCOUNT);
            double y = x*100;
            str1=str1+"DISK UTILIZATION(word)    : "+x+" "+y+"\n";
            x = (double) (m_util.pcb[id].DISK_PAGE_COUNT/256);
            y = x*100;
            str1=str1+"DISK UTILIZATION(frame)   : "+x+" "+y+"\n";
            str1=str1+"DISK FRAGMENTATION(DEC)   : "+m_util.pcb[id].DISK_FRAG+"\n";
*/
            fw.write(str1);
            fw.close();
         }
         catch(Exception ex){
         }
   }
}
