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

               address = m_util.pcb[id].Page_Mem_order[i].Frame_base_address;
               index   = m_util.pcb[id].Page_Mem_order[i].Page_loc; 
               dirty_bit =m_util.pcb[id].Page_Mem_order[i].Dirty_bit;
               if(dirty_bit == 1)
               {
                  memory.Write_back_to_disk(address,index);
               }
               i++;
            }	
            outputFile();
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
            if(Warn != null)
            {
               str1 = str1+"Warnings: "+Warn+"\n";
            }
              if(PCB.INPUT != null)
              {
               str1=str1+"INPUT(binary): "+PCB.INPUT+"\n";	
              }
              else
              {
                str1=str1+"INPUT: "+"NO AVAILABLE INPUT"+"\n";	
             }

              if(PCB.OUTPUT != null)
             {
               str1=str1+"OUTPUT(binary): "+PCB.OUTPUT+"\n";	
             }
             else
              {
                str1=str1+"OUTPUT: "+"NO AVAILABLE OUTPUT"+"\n";	
             }
            str1=str1+"Nature of Termination: "+str+"\n";
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

            fw.write(str1);
            fw.close();
         }
         catch(Exception ex){
         }
   }
}
