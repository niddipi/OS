/*****************************************************************
* Filename = Fault_Handlers.java
* 
*Description : This file is responsible for 
*		execution of all Segement Fault and
*               Page Fault Handlers
*
*****************************************************************/
public class Fault_Handlers{

   int Debug =0;
   Memory_util m_util = new Memory_util();
   CPU_util util = new CPU_util();
  
   
   /**This Function handles Program Segment Faults****/
   public int Prog_Seg_Fault_Handler(int jobid, int no_of_pages)
   {
      int id = util.id;
      m_util.pcb[id].Program_seg_info  = 
	 Prog_Page_Fault_Handler(jobid,no_of_pages); 
      m_util.pcb[id].Program_seg_info=jobid;
      return 0;
   }
   /**This Function handles Program page Faults****/
   public int Prog_Page_Fault_Handler(int jobid , int no_of_pages)
   {
	int Address_of_PMT = -1;
	int id = util.id;
	if(m_util.pcb[id].Program_seg_info == -1)
	{
      		Address_of_PMT = jobid*no_of_pages;
		//m_util.pcb[id].Page_Mem_order[Address_of_PMT].no_of_pages =no_of_pages; 
		
	}
	else
	{
		Address_of_PMT = m_util.pcb[id].Program_seg_info;
		Address_of_PMT = 
			new Page_Replacement().Page_Replacement_func(Address_of_PMT);
	}
      return Address_of_PMT;

   }
   /**This Function handles Input Segment Faults****/
   public int Input_Seg_Fault_Handler(int jobid)
   {
   int id = util.id;	
      m_util.pcb[id].Input_seg_info = Input_Page_Fault_Handler(jobid); 
      util.CLOCK = util.CLOCK + 5;
      util.SEG_FAULT_CLOCK = util.SEG_FAULT_CLOCK +5;

	 if((util.CLOCK - util.OLD_CLOCK) >= 15)
        {
		util.OLD_CLOCK = util.CLOCK;
                m_util.Display_Mem_Pages();
        }

      return jobid;
   }
   /**This Function handles Input page Faults****/
   public int Input_Page_Fault_Handler(int jobid)
   {
	int id = util.id;
	int Address_of_PMT = 0;
        if(m_util.pcb[id].Input_seg_info == -1)
        {
                 Address_of_PMT = jobid;
         
        }
        else
        {
                Address_of_PMT = m_util.pcb[id].Input_seg_info;
                Address_of_PMT = 
                        new Page_Replacement().Page_Replacement_func(Address_of_PMT);
        }
      return Address_of_PMT;

   }

 
   /**This Function handles Output Seg Faults****/
   public int Output_Seg_Fault_Handler(int jobid)
   {
	int id = util.id;
        m_util.pcb[id].Output_seg_info = Output_Page_Fault_Handler(jobid); 
	util.CLOCK = util.CLOCK + 5;
	util.SEG_FAULT_CLOCK = util.SEG_FAULT_CLOCK + 5;
	 if((util.CLOCK - util.OLD_CLOCK) >= 15)
	{
	util.OLD_CLOCK = util.CLOCK;
		m_util.Display_Mem_Pages();
	}
	
      return jobid;
   }

   /**This Function handles Output page Faults****/
   public int Output_Page_Fault_Handler(int jobid)
   {
	int Address_of_PMT = 0;
	int id = util.id;
        if(m_util.pcb[id].Output_seg_info == -1)
        {
                 Address_of_PMT = jobid;
         
        }
        else
        {
                Address_of_PMT = m_util.pcb[id].Output_seg_info;
                Address_of_PMT = 
                        new Page_Replacement().Page_Replacement_func(Address_of_PMT);
         
        }
      return Address_of_PMT;


   }

}
