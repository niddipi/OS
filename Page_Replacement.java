public class Page_Replacement{
	
   public static int  Entry = 0;

   int Page_Replacement_func(int Address){
      int i  	  = 0;
      int prev_old  = 0;
      int page_add  = -1;
      int page  = 0;
      int count = 0;

      Memory_util m_util = new Memory_util();
      Address = 0;
      int no_of_pages = m_util.pcb[0].no_of_pages;
      while(i < no_of_pages)	
      {
	
	 if(m_util.pcb[0].Page_Mem_order[Address].Ref_bit ==0)	
	 {
	    if(m_util.pcb[0].Page_Mem_order[Address].Page_loc == -1)
	    {
		return Address;

	    }
	    if(count == 0)
	    {
	       prev_old=m_util.pcb[0].Page_Mem_order[Address].old;
	       page_add=Address;
	    }
	    else if(prev_old>m_util.pcb[0].Page_Mem_order[Address].old)
	    {
	       prev_old = m_util.pcb[0].Page_Mem_order[Address].old;
	       page_add=Address;
	    }	
	    count = count+1; 
	 }
	 Address++;
	 i++;
      }
     i        = 0;
     prev_old = 0;
     count    = 0;
     Address  = 0;
    
      if(page_add == -1)
      {
	 while(i < no_of_pages)
      	 {

            if(count == 0)
            {
               prev_old=m_util.pcb[0].Page_Mem_order[Address].old;
               page_add=Address;
            }
            else if(prev_old>m_util.pcb[0].Page_Mem_order[Address].old)
            {
               prev_old = m_util.pcb[0].Page_Mem_order[Address].old;
               page_add=Address;
            }
            count = count+1;
         Address++;
         i++;
	 }	

      }
      return page_add;
   }
   public void update_entry_value(int B_address,int address){

      Memory_util m_util = new Memory_util();	
   
      m_util.pcb[0].Page_Mem_order[address].old = Entry + 1;
      Entry = Entry +1;

   }

   public void update_Ref_bit(int no_of_pages,int address){
            int i =0;
	
      	    Memory_util m_util = new Memory_util();	
	
	if(Entry > no_of_pages)
        {
	    while(i < 6)
	    {
		if(m_util.pcb[0].Page_Mem_order[i].Frame_base_address > 0){

			m_util.pcb[0].Page_Mem_order[i].Ref_bit = 0;
		}
			i++;
	    } 
	}
	    m_util.pcb[0].Page_Mem_order[address].Ref_bit = 1;

	}
	
}