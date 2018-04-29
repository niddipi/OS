/*****************************************************************
* Filename = Memory.java
* 
*Description : This file is responsible for 
*		execution of all the Memory related operations
*
*****************************************************************/
import java.util.*;
import java.math.BigInteger;
import java.io.*;
public class Memory{


   CPU_util util = new CPU_util();
   public static String[] memory = new String[256];
   public static int BR = 0;//Base register 
   /**Object Declarations****/
   Error_Handler Er = new Error_Handler();
   Memory_util m_util = new Memory_util();
   Fault_Handlers FH = new Fault_Handlers();
   Page_Replacement PR = new Page_Replacement();
   /****Checks For PAge in Memory**/
   int Check_for_page(int address)
   {
  	int id = util.id;
      int no_of_pages = 
         m_util.pcb[id].no_of_pages;
      int  i =0;
      int loc = address/8;
      int value =-1;
      while(i < no_of_pages)
      {
         if(m_util.pcb[id].Page_Mem_order[i].Page_loc==loc)
         {
            value = i;
            break;
         }
         i++;
      }
	//System.out.println("Check_for_page address: "+value);
      return value;
   }

   /*****This function handles if a page is not
     Found in the memory.It takes care of 
     respective Segment Fault or Page Fault
     Handling
    ******/
   int Page_not_found(int address,int code)
   throws IOException{

      util.PAGE_FAULT_CLOCK = util.PAGE_FAULT_CLOCK+10;
  	int id = util.id;
	int Base = m_util.pcb[id].Disk_base;
	//Debugging method
	if(Base == -1){
		System.out.println("Base is not populated id:"+id);
		Integer.parseInt(null, 2);
	}
      util.CLOCK = util.CLOCK+10;	
      if(address ==24)
      {
	System.out.println("code :"+code);	
      }
      if((util.CLOCK - util.OLD_CLOCK) >= 15)
      {
         util.OLD_CLOCK = util.CLOCK;
         m_util.Display_Mem_Pages();
      }

      Fault_Handlers FH = new Fault_Handlers();
      Page_Replacement PR = new Page_Replacement();
      int Address = -1;
      int temp = 0;
      String data = null;
      int index = 0;
      index  = (address/8);

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
	 System.out.println("Code :"+code+"Index "+index+" In"+(Inp_pages+output_pages+prog_pages));
	
     if((index > (Inp_pages+output_pages+prog_pages-1))&&(code !=1))
     {
	     Integer.parseInt(null, 2);
	     Er.Error_Handler_func("OUT_OF_RANGE_MEMORY_ACCESS");
     		
     }

      if(code == 0)
      {
         Address = FH.Prog_Page_Fault_Handler(0,no_of_pages);
	 data = new Disk().disk[Base+index];
         temp = m_util.pcb[id].Page_Mem_order[Address].Page_loc;
         if(temp < (prog_pages+Inp_pages-1) && temp >0){
            m_util.pcb[id].Program_PMT[temp] = -1;}
         m_util.pcb[id].Page_Mem_order[Address].Page_loc = index;
         m_util.pcb[id].Page_Mem_order[Address].word_count = (data.length()/4);
      }
      else if(code == 1)
      {
         index = (prog_pages+Inp_pages)-1; 
         if( m_util.pcb[id].Input_seg_info < 0)
         {
            FH.Input_Seg_Fault_Handler(0);
            m_util.pcb[id].create_pmt(1,Inp_pages);
            Address = FH.Input_Page_Fault_Handler(0);
	    System.out.println("Address :"+Address);
         }
         else
         {
            Address = FH.Input_Page_Fault_Handler(0);
         }
         data = new Disk().disk[Base+index];
		
	 System.out.println("Inputdata "+data+"Index "+index);
	 
         temp = m_util.pcb[id].Page_Mem_order[Address].Page_loc;

         m_util.pcb[id].Page_Mem_order[Address].word_count = 
            m_util.pcb[id].Input_seg_size;
         if(temp < 9 && temp >0){
            m_util.pcb[id].Program_PMT[temp] = -1;
         }

         m_util.pcb[id].Page_Mem_order[Address].Page_loc = index;
      }
      else{

         index = (prog_pages+Inp_pages+output_pages)-1; 
         if(m_util.pcb[id].Output_seg_info < 0)
         {
            FH.Output_Seg_Fault_Handler(0);			
            m_util.pcb[id].create_pmt(2,output_pages);
            Address = FH.Output_Page_Fault_Handler(0);
            m_util.pcb[id].Page_Mem_order[Address].word_count = 
               m_util.pcb[id].Output_seg_size;
         }
         else
         {
            Address = FH.Output_Page_Fault_Handler(0);
         }
         temp = m_util.pcb[id].Page_Mem_order[Address].Page_loc;


         m_util.pcb[id].Page_Mem_order[Address].Page_loc = index;
      }
      if(temp < prog_pages && temp >0){
         m_util.pcb[id].Program_PMT[temp] = -1;
      }
      else if(temp >= prog_pages)
      {
         m_util.pcb[id].Input_PMT[0] = -1;
      }
      else if(temp >= prog_pages && 
            m_util.pcb[id].Output_seg_info>0)
      {

         m_util.pcb[id].Output_PMT[0] = -1;
      }

      int Mem_address = 0;
      int temp_loc = m_util.check_avialable_page(id);  
      //int temp_loc = m_util.Free();  

      if(temp_loc != -1)
      {
         Mem_address = temp_loc;
	System.out.println("temp_loc :"+temp_loc);
         m_util.pcb[id].Page_Mem_order[Address].Frame_base_address = temp_loc;
      }
      else
      {
         Mem_address =		
            m_util.pcb[id].Page_Mem_order[Address].Frame_base_address;

      }

      if(m_util.pcb[id].Page_Mem_order[Address].Dirty_bit == 1)
      {
	System.out.println("Mem_address :"+Mem_address+" Address :"+Address);
         Write_back_to_disk(Mem_address*8,Base+temp);
	 new Disk().Disk_display();
         m_util.pcb[id].Page_Mem_order[Address].Dirty_bit  = 0;
      }
      if(code == 0)
      {
         m_util.pcb[id].Program_PMT[index] = Mem_address;
      }
      else if(code == 1)
      {
         m_util.pcb[id].Input_PMT[0] = Mem_address;	
      }
      else
      {
         m_util.pcb[id].Output_PMT[0] = Mem_address;	
         m_util.pcb[id].Page_Mem_order[Address].Dirty_bit = 1;

      }
      m_util.pcb[id].Page_frames[Address]      =  data;

      if(code == 1)
      {
         Mem_address = Mem_address*8;
	 int count = data.length()/4;
	 String str = null;
         int x = 0;
         int Mem_add = Mem_address;
	System.out.println("Mem_addressssssssssssss :"+Mem_address);
	 while(count != 0)
	{
              str = data.substring(0+(x * 4),4+(x * 4));
              //System.out.println("str "+str);
	      str = hexToBinary(str);
	      Write(Mem_add,str);
              Mem_add++;
	      x++;
	      count--;
	}
      }
      

      if(code ==2)
      {
         return Mem_address*8;
      }
      
     if(code !=1){
	System.out.println("data :"+data+"code :"+code);
	System.out.println("Mwwaddress :"+Mem_address);
      data = hexToBinary(data);
      Write(Mem_address,data);
      }
      m_util.pcb[id].Page_Mem_order[Address].Ref_bit =1;
      PR.update_entry_value(0,Address);
      PR.update_Ref_bit(no_of_pages,Address);
      return Mem_address;
   }

   /***This Function writes back to disk whenever
     it is required
    ****/
   void Write_back_to_disk(int address,int index)
   {
      String data = "";
      String temp = null;	
      int value =0; 
      while(value < 8)
      {
         temp = memory[address];
         if(temp == null)
         {
            break;
         }
         int decimalv = Integer.parseInt(temp,2);
         temp = Integer.toHexString(decimalv);	
	 while(temp.length() < 4)
	 {
	      temp = "0"+temp;	
	 }
         data = data + temp;
         value++;
         address++;

      } 

      new Disk().disk[index] = data;

   }

   /****This function converts hex to string**/
   String hexToBinary(String hex) {

	//System.out.println("hex "+hex);
      String bin = new BigInteger(hex, 16).toString(2);

      while (bin.length() < hex.length()*4){
         bin = "0"+bin;
      }
      if(hex.equals("0000")){

	bin = "0000000000000000";
      }
      return bin;
   }

   /***This Function converts Virtual address to physical 
     Address
    ****/
   int Virtual_to_Physical_addr(int address,int value)
   {
  	int id = util.id;
      int PMT_base_add = 0;
      int no_of_pages  = m_util.pcb[id].no_of_pages;
      int i  = 0;
      int  base_address = -1;
      while(i<no_of_pages)
      {
         if(m_util.pcb[id].Page_Mem_order[PMT_base_add + i].
               Page_loc == address/8){

            base_address = 
               m_util.pcb[id].Page_Mem_order[PMT_base_add + i].
               Frame_base_address;

               }
         i++;
      }
      base_address 	 = m_util.pcb[id].Page_Mem_order[value].Frame_base_address*8;
      int offset         = address%8;
      //System.out.println("base_address :"+base_address+" Offset :"+offset);
      
      return base_address+offset;

   }

   //Write Function writes into memory
   int Write(int address,String data){
      //try{
	/*
	  if(address == 0 && m_util.k == 1 && util.id != 6){
		System.out.println("IIIII Gotcha :id "+util.id);
		String n = null;
		System.out.println(n);
		Integer.parseInt(null, 2);
	}*/

         if(data.length()>16)
         {
            int init_add = address;
            address = address*8;
            int x = 1;
            int Start = 0;
            int End = 0;
            String new_inst = null;
            char [] new_data = data.toCharArray();	
            int value = data.length()/16;

            while (x <= value)
            {
               End = Start+15;
               new_inst = 
                  new String(new_data, Start, End - Start + 1);
               memory[address] = new_inst;
               Start = Start+16;
               address++;
               x++;
            }
            //m_util.fmbv[init_add] = 1;
         }
         else
         {
            memory[address] = data;

         }		
      //}
      //catch(Exception ex){
      //}
      return 0;
   }
   //Read Function reads from memory
   String Read(int address){

      try{
	if(address == 40){
		System.out.println("memory[address] :"+memory[address]);
	}
         return memory[address];
      }
      catch(Exception ex){
      }
      return null;
   }
   //This Function is called to Read or Write in the Memory
   String Memory_func(String task,int address,String data)
   {
  	int id = util.id;

      int temp = address;
      String line=null;
      int no_of_pages = m_util.pcb[id].no_of_pages;
      if(task.equals("READ"))
      {
         if(!data.equals("Input")){
            int value  = Check_for_page(address);
            int offset = address%8;
	    System.out.println("value :"+value);
	    
            if(value == -1)
            {
               if(m_util.pcb[id].Trace_page == 1)
               {
                  m_util.pcb[id].Trace_page = 0;
                  return "  NA";
               }
		if(address/8 == 13){
		Integer.parseInt(null, 2);
		}
               util.address = address;
               util.value =0;
               return "stop";

            }
            else{
               m_util.pcb[id].Page_Mem_order[value].Ref_bit =1;
               address = Virtual_to_Physical_addr(address,value);
	    System.out.println("Memaddress :"+address);
            }
         }
         address = BR + address;
         line = Read(address);
	if(line == null){
	    System.out.println("address :"+address+"id :"+id+" Input_seg_info:"+m_util.pcb[id].Input_seg_info);
		m_util.Display_Mem_Pages();
		Integer.parseInt(null, 2);
	}
	else{
		
	    System.out.println("address :"+address+"id :"+id+" line:"+line);
	}
         m_util.pcb[id].Trace_page = 0;
      }
      else
      {
         if(data.length() == 16){

            int value  = Check_for_page(address);

            int offset = address%8;

            if(value == -1)
            {
               util.address = address;
		if(address/8 == 13){
		Integer.parseInt(null, 2);
		}
               util.value =0;
               return "stop";
            }
            else{
               m_util.pcb[id].Page_Mem_order[value].Ref_bit =1;
               m_util.pcb[id].Page_Mem_order[value].Dirty_bit =1;
               address = Virtual_to_Physical_addr(address,value);
            }
         }
         address = BR + address;
         Write(address,data);

      }
      return line;
   }

   /**Debug Function to see memory contents*/
   void display_mem()
   {
      int i =0;
      while(i<256)	
      {
         if(memory[i] != null)
         {
            BigInteger b = new BigInteger(memory[i], 2);
            System.out.println(memory[i]);
         }
         i++;
      }	
   }

   /********All Memory related stats are calculated
    ********/
   public void memory_Stats()
   {
  	int id = util.id;
      int address = 0;
      int no_of_pages = m_util.pcb[id].no_of_pages;
      int value = 0;
      int count = 0;
      int W_count = 0;
      String data = null;
      for(int i = 0; i<no_of_pages ;i++)
      {
         address = m_util.pcb[id].Page_Mem_order[i].Frame_base_address;
         W_count += m_util.pcb[id].Page_Mem_order[i].word_count;
         address = address*8;
         value =0; 
         while(value <= 7)
         {
            data = memory[address];
            if(data == null)
            {
               break;
            }
            value++;
            address++;
         } 
         if(value != 0&& value != 8)
         {
            value = value +1;
         }
         count = count + value;

      }
      double a = 0;
      util.MEM_frag = (no_of_pages*8)-W_count;
      a = count;
      util.MEM_utilword_ratio  = count;
      a = (count*1.0/(256*1.0)*100);
      util.MEM_utilword_percent = Math.round(a*100)/100D;
      a =  no_of_pages;
      util.MEM_utilframe_ratio = a;
      a = ((no_of_pages*1.0)/(32*1.0))*100;
      util.MEM_utilframe_percent = Math.round(a*100)/100D;;

   }
void Display_Mem(int add)
{
	add = add*8;
	int i = 0;
		System.out.println("$$$$$$$$$$$$$$");
	while(i<8){

		System.out.println(memory[add]);
		add++;
		i++;
	}
		System.out.println("$$$$$$$$$$$$$$");
}
}
