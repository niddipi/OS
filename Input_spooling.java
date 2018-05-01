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
   static  int check_size =0;
   static  int count_val =0;
   Loader loader = new Loader(); 
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
   void check_available_disk(int size){

      int r = size;	
      Disk disk = new Disk();
      if((256 - save_load_address)<r)
	{
		save_load_address =0;
	}
  }

   int InputSpool(String filename)
         throws IOException,NumberFormatException,NoSuchElementException{
         try{
	    
            int x =0;
	    int size = 0;
            int id =0;
	    int value =0;
            String binary = null;
            String line   = null;
            CPU_util util = new CPU_util();
            boolean isNumeric = false;
            Disk disk = new Disk();
	    if(count_val == 0){
            fstream = new FileInputStream(filename);
            br = 
               new BufferedReader(new InputStreamReader(fstream));
		count_val = count_val +1;
	    }
	    System.out.println("count_val :"+count_val);
	    
	    value = m_util.Memory_Available;	
            while(value > 0){
	    
	    value = m_util.Memory_Available;	
            x =0;
	    int index = 0;
	    id = m_util.id;
	    util.Er_id = m_util.id;
	    m_util.pcb[id].int_jobid = m_util.id;
            binary = null;
            line   = null;
            isNumeric = false;
            if(value < Page_Allocation(check_size)){
		break;
	    }
	    m_util.pcb[id].Arrival_time =util.CPUCLOCK;
	  
            line = br.readLine();
	    if(line == null)
            {
		System.out.println("All jobs Done");
		if(m_util.pcb[m_util.id - 1].valid_pcb == -1){
		util.JOBS_FINISH = 1;
		}
		break;	       
            }
	    m_util.id++;
            String line1 = line;
            Scanner scan = new Scanner(line);
            scan.useDelimiter(" ");
            String job = scan.next();
            if(!(job.equals("**JOB")))
            {
               Er.Error_Handler_func("**JOB_Is_Missing");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
            }		
            if(scan.hasNext() == false)
            {
               Er.Error_Handler_func("INPUT_AND_OUTPUT_Size_MISSING");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
            }
            int Input_seg_size = 0;
            try{
               Input_seg_size = Integer.parseInt(scan.next(), 16);
            }catch(NumberFormatException e)
            {	
               Er.Error_Handler_func("INVALID_INPUT_SEG_SIZE");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
	       return -1;
            }
            if(scan.hasNext() == false)
            {
               Er.Error_Handler_func("OUTPUT_SIZE_MISSING");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
            }
            int Output_seg_size = 0;
            try{
               Output_seg_size = Integer.parseInt(scan.next(), 16);
            }catch(NumberFormatException e)
            {	
               Er.Error_Handler_func("INVALID_OUTPUT_SEG_SIZE");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
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
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
            }


            Scanner scan1 = new Scanner(line);
            scan1.useDelimiter(" ");
            check_size = 0; 
            int JOBID         = 0;
            int load_address  = save_load_address;
            int Start_address = 0;
            int SIZE          = 0;
            try{
               JOBID         = Integer.parseInt(scan1.next(), 16);
            }catch(NumberFormatException e)
            {	
               Er.Error_Handler_func("INVALID_JOBID_GIVEN");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
            }
            try{
               load_address  = Integer.parseInt(scan1.next(), 16);
            }catch(NumberFormatException e)
            {	
               Er.Error_Handler_func("INVALID_LOAD_ADDRESS_GIVEN");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
            }
            try{	
               Start_address = Integer.parseInt(scan1.next(), 16);
            }catch(NumberFormatException e)
            {	
               Er.Error_Handler_func("INVALID_START_ADDRESS_GIVEN");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
            }
            try{
               SIZE          = Integer.parseInt(scan1.next(), 16);

            }catch(NumberFormatException e)
            {	
               Er.Error_Handler_func("INVALID_PROGRAM_SIZE_GIVEN");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
            }
	    size = SIZE/8;
	    m_util.pcb[id].int_jobid = id;
            m_util.pcb[id].Prog_seg_size  = SIZE;
            m_util.pcb[id].Input_seg_size = Input_seg_size;
            m_util.pcb[id].Output_seg_size= Output_seg_size;

	    /***********************************Disk Management*******************/
	      int no_of_pages = m_util.pcb[id].no_of_pages;
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
	      m_util.pcb[id].Total_no_of_pages = (prog_pages + Inp_pages + output_pages);
	      m_util.pcb[id].Init_Disk_PMT(m_util.pcb[id].Total_no_of_pages);
	    		int k = 0;
                        int val =0;
                        while(k<m_util.pcb[id].Total_no_of_pages)
                        {
                                val = disk.Free();
                                m_util.pcb[id].disk_frame_no[k] = val;
				System.out.println("m_util.pcb[id].disk_frame_no[k] :"+val);
                                k++;
                        }
			disk.Disk_display();
			System.out.println("********************************");
			

	    /************************************************************************/
	    load_address  = disk.Check_avialable_page(id);
	    m_util.pcb[id].Disk_PMT[index] =load_address;
	    index++;

            m_util.pcb[id].JOBID         = JOBID;
            m_util.pcb[id].Start_address = Start_address;	

      	    m_util.pcb[id].PC = Start_address;
	    System.out.println("Start_address :"+Start_address);

            String trace_flag   = scan1.next();
            if((!trace_flag.equals("0")) && 
                  (!trace_flag.equals("1")))
            {
               Er.Error_Handler_func("INVALID_TRACE_FLAG");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
            }
	    try{
	    m_util.pcb[id].trace_flag = Integer.parseInt(trace_flag); 
	    if(id == 12){
		System.out.println("id :"+id+" "+m_util.pcb[id].trace_flag);
	    }
	    }
	    catch(Exception e){
		Er.Error_Handler_func("INVALID_TRACE_FLAG");	
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
	    }
	    int base =0;

            while ((line = br.readLine()) != null){
               try{
                  line1 = line;
                  scan = new Scanner(line1);
                  scan.useDelimiter(" ");
                  job = scan.next();
                  if(job.equals("**JOB"))
                  {
                     Er.Error_Handler_func("MULTIPLE_**JOB_PRESENT");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
			return -1;
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
		while(!line.equals("**FIN")){
		
			line = br.readLine();
		}
			System.out.println("line :"+line);
			return -1;
                  }

                  if (x==2)
                  {
                     load_address = disk.Check_avialable_page(id);
	    m_util.pcb[id].Disk_PMT[index] =load_address;
	    index++;
                     x = 0;
                  }
                  check_size = check_size+(line.length()/4);
		
		  if(base == 0)
                  {
			  System.out.println("id :"+id+"Disk_base :"+load_address);
			  System.out.println("line :"+line);
                          m_util.pcb[id].Disk_base = load_address;
                  }
                  disk.Disk_func("Write",load_address,line);
                  line1 = line;
                  x++;
		  base++;
               }catch(NoSuchElementException e)
               {
                  Er.Error_Handler_func("UNNECESSARY_BLANK_LINES_PRESENT_IN_LOADER_FORMAT");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		  return -1;
               }	
            }
            if(line.equals("**OUTPUT")||
                  line.equals("**FIN"))
            {
               Er.Error_Handler_func("**INPUT_Is_Missing");		
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
            }	
            if(check_size > SIZE)
            {
               Er.Error_Handler_func("CONFLICT_NO_OF_WORDS_FOR_PROGRAM_SEGMENT");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
            }
	    if(check_size < SIZE){
			Memory_util.pcb[id].Warn =
				"READ_LESS_NO_OF_WORDS_FOR_PROGRAM_SEGMENT";

	    }
	    m_util.pcb[id].Prog_seg_size=check_size;
	    System.out.println("load_address :"+load_address);
            String Prog_seg_last = disk.disk[load_address]; 
            m_util.pcb[id].DISK_FRAG = m_util.pcb[id].DISK_FRAG+(Prog_seg_last.length()/4);
            int word_count = check_size;
            if(line == null)
            {
               Er.Error_Handler_func("**INPUT_**OUTPUT_**FIN_Missing");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;

            }
            m_util.pcb[id].DISK_PAGE_COUNT = index;
            x = 0;
            load_address = disk.Check_avialable_page(id);
	    m_util.pcb[id].Disk_PMT[index] =load_address;
	    index++;

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
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		  return -1;
               }
		
               if ((x>2) && (line.length() >4))
               {
                  load_address = disk.Check_avialable_page(id);
	    	   m_util.pcb[id].Disk_PMT[index] =load_address;
	    index++;
                  m_util.pcb[id].DISK_PAGE_COUNT = m_util.pcb[id].DISK_PAGE_COUNT+1;
                  x = 0;
               }
               if(line.equals("**INPUT"))
               {
                  if(count > 0)
                  {
                     Er.Error_Handler_func("MULTIPLE_**INPUT_PRESENT");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		     return -1;
                  }
                  count++;
               	  line2 = line;
               }
	       String temp = line;
               line = br.readLine();	
	       if(line == null)
		{
			Er.Error_Handler_func("**FIN_Is_Missing");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
			return -1;
		}
               if(line.equals("**INPUT"))
               {
                  if(count > 0)
                  {
                     Er.Error_Handler_func("MULTIPLE_**INPUT_PRESENT");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
			return -1;
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
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		  return -1;
               }
               if(job.equals("**INPUT"))
               {
                  Er.Error_Handler_func("MULTIPLE_**INPUT_PRESENT");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		  return -1;
               }

               if(temp.equals("**INPUT") && (line ==null | line.equals("**FIN")))
               {
                  Er.Error_Handler_func("INPUT_WORDS_MISSING");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		  return -1;
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
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
	   	     return -1;
                  }
                  check_size = check_size+(line.length()/4);
		  if(check_size > 8){
			load_address = disk.Check_avialable_page(id);
	    		m_util.pcb[id].Disk_PMT[index] =load_address;
	    		index++;
		}
               }

               disk.Disk_func("Write",load_address,line);
               if(line.equals("**FIN")){
                  break;
               }
               x++;

            }
            word_count = word_count + check_size;
               if(check_size > Input_seg_size)
               {
                  Er.Error_Handler_func("CONFLICT_NO_OF_WORDS_FOR_INPUT_SEGMENT");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		  return -1;
               }

 	    if(check_size > Input_seg_size)
               {
			Memory_util.pcb[id].Warn =
                                "READ_LESS_NO_OF_WORDS_FOR_INPUT_SEGMENT";


		}	
	      
            String Input_seg_last = disk.disk[load_address]; 
            	check_size = 5; 
            if(line == null)
            {	
               Er.Error_Handler_func("**FIN_IS_MISSING");
		while(!line.equals("**FIN")){
			line = br.readLine();
		}
		return -1;
            }	
            x = 0;
            loader.Loader_func(filename,1,id);	    
	    disk.display_disk(id);
	    System.out.println("load_address :"+load_address);
	    if(Input_seg_last == null){
		Input_seg_last = disk.disk[load_address-1];
	   }
            m_util.pcb[id].DISK_FRAG = m_util.pcb[id].DISK_FRAG+Input_seg_last.length()/4;
            m_util.pcb[id].DISK_FRAG = m_util.pcb[id].DISK_FRAG+Output_seg_size%8;
            m_util.pcb[id].DISK_FRAG = (24 - m_util.pcb[id].DISK_FRAG)/3;
            m_util.pcb[id].TOTAL_DISK_USAGE = word_count;
		
	    m_util.Memory_Available = m_util.Memory_Available-Page_Allocation(size); 
		
		 }
		 }catch(IOException e){

		    Er.Error_Handler_func("FILE_IS_EMPTY");
			return -1;
		 }

		return 0;
	   }

}
