/**************************************************************************
* Filename = Input_spooling.java
* 
*Description : This file is responsible for execution of all the Input
*              spooling operations such as writing into the disk 
* 	 	
*
**************************************************************************/
import java.util.*;
import java.math.BigInteger;
import java.io.*;

public class Input_spooling{

   static int TOTAL_DISK_WCOUNT = 32*8*8;
   
   static FileInputStream fstream = null;
   static BufferedReader br = null;
   static int save_load_address = 0;
   
   Error_Handler Er = new Error_Handler();
   Memory_util m_util = new Memory_util();
	

   String hexToBinary(String hex) {

      String bin = new BigInteger(hex, 16).toString(2);

      while (bin.length() < hex.length()*4){
         bin = "0"+bin;
      }

      return bin;
   }


   int Page_Allocation(int Size)
   {
      if(Size+2 < 6)
      {
         return Size+2;
      }
      else
      {
         return 6;
      }
   }

   void InputSpool(String filename)
         throws IOException,NumberFormatException,NoSuchElementException{
         try{
	    
            int x =0;
	    int size = 0;
            int id =0;
	    int value =0;
            int check_size =0;
            String binary = null;
            String line   = null;
            CPU_util util = new CPU_util();
            boolean isNumeric = false;
            Disk disk = new Disk();
            size =4;
	    if(save_load_address == 0){
            fstream = new FileInputStream(filename);
            br = 
               new BufferedReader(new InputStreamReader(fstream));
	    }
            Memory_util m_util = new Memory_util();
	    
	    value = m_util.Memory_Available;	
            while(value > 0){
	    
		value = m_util.Memory_Available;	

            x =0;
	    id = m_util.id;
            check_size =0;
            binary = null;
            line   = null;
            isNumeric = false;
            if(value < Page_Allocation(size)){
		break;
	    }
	  
            line = br.readLine();
	    if(line == null)
            {
               Er.Error_Handler_func("INVALID_LOADER_FORMAT");
            }
            String line1 = line;
            Scanner scan = new Scanner(line);
            scan.useDelimiter(" ");
            String job = scan.next();
            if(!(job.equals("**JOB")))
            {
               Er.Error_Handler_func("**JOB_Is_Missing");
            }		
            if(scan.hasNext() == false)
            {
               Er.Error_Handler_func("INPUT_AND_OUTPUT_Size_MISSING");
            }
            int Input_seg_size = 0;
            try{
               Input_seg_size = Integer.parseInt(scan.next(), 16);
            }catch(NumberFormatException e)
            {	
               Er.Error_Handler_func("INVALID_INPUT_SEG_SIZE");
            }
            if(scan.hasNext() == false)
            {
               Er.Error_Handler_func("OUTPUT_SIZE_MISSING");
            }
            int Output_seg_size = 0;
            try{
               Output_seg_size = Integer.parseInt(scan.next(), 16);
            }catch(NumberFormatException e)
            {	
               Er.Error_Handler_func("INVALID_OUTPUT_SEG_SIZE");
            }

            line = br.readLine();
            String line2 = line;
            line1 = line;
            scan = new Scanner(line1);
            scan.useDelimiter(" ");
            job = scan.next();
            if(job.equals("**JOB"))
            {
               Er.Error_Handler_func("MULTIPLE_**JOB_PRESENT");
            }


            Scanner scan1 = new Scanner(line);
            scan1.useDelimiter(" ");
            int JOBID         = 0;
            int load_address  = save_load_address;
            int Start_address = 0;
            int SIZE          = 0;
            try{
               JOBID         = Integer.parseInt(scan1.next(), 16);
            }catch(NumberFormatException e)
            {	
               Er.Error_Handler_func("INVALID_JOBID_GIVEN");
            }
            try{
               load_address  = Integer.parseInt(scan1.next(), 16);
            }catch(NumberFormatException e)
            {	
               Er.Error_Handler_func("INVALID_LOAD_ADDRESS_GIVEN");
            }
            try{	
               Start_address = Integer.parseInt(scan1.next(), 16);
            }catch(NumberFormatException e)
            {	
               Er.Error_Handler_func("INVALID_START_ADDRESS_GIVEN");
            }
            try{
               SIZE          = Integer.parseInt(scan1.next(), 16);

            }catch(NumberFormatException e)
            {	
               Er.Error_Handler_func("INVALID_PROGRAM_SIZE_GIVEN");
            }
	    size = SIZE/8;
	    load_address  = save_load_address;
	    m_util.id++;
	    m_util.pcb[id].int_jobid = id;
            m_util.pcb[id].Prog_seg_size  = SIZE;
            m_util.pcb[id].Input_seg_size = Input_seg_size;
            m_util.pcb[id].Output_seg_size= Output_seg_size;

            m_util.pcb[id].JOBID         = JOBID;
            m_util.pcb[id].int_jobid     = m_util.id;
            m_util.pcb[id].Start_address = Start_address;	

	    if(id == 0){
            	m_util.pcb[id].PC = Start_address;
		}

            String trace_flag   = scan1.next();
            if((!trace_flag.equals("0")) && 
                  (!trace_flag.equals("1")))
            {
               Er.Error_Handler_func("INVALID_TRACE_FLAG");
            }

            m_util.pcb[0].trace_flag = Integer.parseInt(trace_flag); 
	    

            disk.check_avail_space(SIZE);			


            while ((line = br.readLine()) != null){
               try{
                  line1 = line;
                  scan = new Scanner(line1);
                  scan.useDelimiter(" ");
                  job = scan.next();
                  if(job.equals("**JOB"))
                  {
                     Er.Error_Handler_func("MULTIPLE_**JOB_PRESENT");
                  }


                  if (line.equals("**INPUT") |
                        line.equals("**OUTPUT")|
                        line.equals("**FIN"))
                  {
                     break;
                  }
                  isNumeric = line.matches("\\p{XDigit}+");
                  if(!isNumeric | (line.length()%4 != 0))
                  {
                     Er.Error_Handler_func("INVALID_LOADER_FORMAT");
                  }

                  if (x==2)
                  {
                     load_address = load_address+1;
                     x = 0;
                  }
                  check_size = check_size+(line.length()/4);
                  disk.Disk_func("Write",load_address,line);
                  line1 = line;
                  x++;
               }catch(NoSuchElementException e)
               {
                  Er.Error_Handler_func("UNNECESSARY_BLANK_LINES_PRESENT_IN_LOADER_FORMAT");
               }	
            }
            if(line.equals("**OUTPUT")||
                  line.equals("**FIN"))
            {
               Er.Error_Handler_func("**INPUT_Is_Missing");		
            }	
            if(check_size != SIZE)
            {
               Er.Error_Handler_func("CONFLICT_NO_OF_WORDS_FOR_PROGRAM_SEGMENT");
            }
            String Prog_seg_last = disk.disk[load_address]; 
            m_util.pcb[id].DISK_FRAG = m_util.pcb[id].DISK_FRAG+(Prog_seg_last.length()/4);
            int word_count = check_size;
            if(line == null)
            {
               Er.Error_Handler_func("**INPUT_**OUTPUT_**FIN_Missing");

            }
            m_util.pcb[id].DISK_PAGE_COUNT = load_address -1;
            x = 0;
            load_address = load_address+1;

            check_size = 0;
            int count =0;

	    /*Input is read here*/
            while(line != null)
            {

               line1 = line;
               scan = new Scanner(line1);
               scan.useDelimiter(" ");
               job = scan.next();
               if(job.equals("**JOB"))
               {
                  Er.Error_Handler_func("MULTIPLE_**JOB_PRESENT");
               }
		
               if ((x>2) && (line.length() >4))
               {
                  load_address = load_address+1;
                  m_util.pcb[id].DISK_PAGE_COUNT = m_util.pcb[id].DISK_PAGE_COUNT+1;
                  x = 0;
               }
               if(line.equals("**INPUT"))
               {
                  if(count > 0)
                  {
                     Er.Error_Handler_func("MULTIPLE_**INPUT_PRESENT");
                  }
                  count++;
               	  line2 = line;
               }
	       String temp = line;
               line = br.readLine();	
	       if(line == null)
		{
			Er.Error_Handler_func("**FIN_Is_Missing");
		}
               if(line.equals("**INPUT"))
               {
                  if(count > 0)
                  {
                     Er.Error_Handler_func("MULTIPLE_**INPUT_PRESENT");
                  }
                  line2 = line;
                  line = br.readLine();
                  count = count +1;
               }
		/**Error Handling**/
               line1 = line;
               scan = new Scanner(line1);
               scan.useDelimiter(" ");
               job = scan.next();
               if(job.equals("**JOB"))
               {
                  Er.Error_Handler_func("MULTIPLE_**JOB_PRESENT");
               }
               if(job.equals("**INPUT"))
               {
                  Er.Error_Handler_func("MULTIPLE_**INPUT_PRESENT");
               }

               if(temp.equals("**INPUT") && (line ==null | line.equals("**FIN")))
               {
                  Er.Error_Handler_func("INPUT_WORDS_MISSING");
               }
               if(line == null | line.equals("**FIN") | line.equals("**OUTPUT"))
               {
                  break;
               }
		/**Verifying Input**/
               if(line2.equals("**INPUT"))
               {
                  isNumeric = line.matches("\\p{XDigit}+");
                  if(!isNumeric | (line.length()%4 != 0))
                  {
                     Er.Error_Handler_func("INVALID_INPUT_FORMAT");
                  }
                  check_size = check_size+(line.length()/4);
               }

               disk.Disk_func("Write",load_address,line);
               if(line.equals("**FIN")){
                  break;
               }
               x++;

            }
               if(check_size != Input_seg_size)
               {
                  Er.Error_Handler_func("CONFLICT_NO_OF_WORDS_FOR_INPUT_SEGMENT");
               }
            String Input_seg_last = disk.disk[load_address-1]; 
            if(line == null)
            {	
               Er.Error_Handler_func("**FIN_IS_MISSING");
            }	
            x = 0;
            check_size = 0; 
            load_address = load_address+1;
/*
            if(!line.equals("**OUTPUT") && !line.equals("**FIN"))
            {	

               line1 = line;
               scan = new Scanner(line1);
               scan.useDelimiter(" ");
               job = scan.next();
               if(job.equals("**JOB"))
               {
                  Er.Error_Handler_func("MULTIPLE_**JOB_PRESENT");
               }
               if(job.equals("**INPUT"))
               {
                  Er.Error_Handler_func("MULTIPLE_**INPUT_PRESENT");
               }

            }
            if(line.equals("**OUTPUT"))
            {
               while((line = br.readLine()) != null)
               {
                  if (x==2)
                  {
                     load_address = load_address+1;
                     m_util.pcb[id].DISK_PAGE_COUNT = m_util.pcb[id].DISK_PAGE_COUNT+1;
                     check_size = 0;
                     x = 0;
                  }
                  line1 = line;
                  scan = new Scanner(line1);
                  scan.useDelimiter(" ");
                  job = scan.next();
                  if(job.equals("**JOB"))
                  {
                     Er.Error_Handler_func("MULTIPLE_**JOB_PRESENT");
                  }
                  if(job.equals("**INPUT"))
                  {
                     Er.Error_Handler_func("MULTIPLE_**INPUT_PRESENT");
                  }
                  if(job.equals("**OUTPUT"))
                  {
                     Er.Error_Handler_func("MULTIPLE_**OUTPUT_PRESENT");
                  }
                  if(line.equals("**FIN"))
                  {
                     break;
                  }
                  isNumeric = line.matches("\\p{XDigit}+");
                  if(!isNumeric | (line.length()%4 != 0))
                  {
                     Er.Error_Handler_func("INVALID_OUTPUT");
                  }

                  disk.Disk_func("Write",load_address,line);
                  x++;
               }
            }

            if(line == null)
            {	
               Er.Error_Handler_func("**FIN_IS_MISSING");
            }	*/
	    save_load_address = load_address;

            m_util.pcb[id].DISK_FRAG = m_util.pcb[id].DISK_FRAG+Input_seg_last.length()/4;
            m_util.pcb[id].DISK_FRAG = m_util.pcb[id].DISK_FRAG+Output_seg_size%8;
            m_util.pcb[id].DISK_FRAG = (24 - m_util.pcb[id].DISK_FRAG)/3;
            word_count = word_count + check_size;
            m_util.pcb[id].TOTAL_DISK_USAGE = word_count;
            m_util.pcb[id].output_start_address = load_address+1;	
		
	    m_util.Memory_Available = 
					m_util.Memory_Available-Page_Allocation(size); 
	 }
         }catch(IOException e){

            Er.Error_Handler_func("FILE_IS_EMPTY");
         }


   }

}
