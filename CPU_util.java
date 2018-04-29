
/**************************************************************************
* Filename   : CPU_util.java
* 
*Description : This file has all required CPU utilities.Global variable
* 		and functions
*
**************************************************************************/
public class CPU_util{ 
	//All required CPU global
	//variables  
	public static int PC=0;
	public static int EA=0;
	public static int CLOCK=0;
	public static int CPUCLOCK=0;
	public static int OLD_CLOCK=0;
	public static int OLD_CPUCLOCK=0;
	public static int PAGE_FAULT_CLOCK=0;
	public static int SEG_FAULT_CLOCK=0;
	public static double MEM_utilword_ratio = 0.0;
	public static double MEM_utilword_percent = 0.0;
	public static double MEM_utilframe_ratio = 0.0;
	public static double MEM_utilframe_percent = 0.0;
	public static double MEM_frag = 0.0;

	public static String  OP = null;
	public static int value = -1;
	public static int JOBS_FINISH = -1;
	
	public static int schedule_type = -1;
	public static int id =0;
	
	public static int address = 0;
	public static int IO_CLOCK=0;
	public static int IR_val=0;
	public static String IR=null;

		
	public static int TOS_Prev=-1;
	public static int EA_Prev=-1;
	public static int TOS_Val_Prev=0;
	public static int EA_Val_Prev=0;

	
	//Performs twos complement of a number
	public String twos_compl(int num){

		String bin = null;
		if(num>=0)
		{
			bin = Integer.toBinaryString(num);
			while (bin.length() < 16){
				bin = "0"+bin;
			}
			
		}
		else
		{
			bin = Integer.toBinaryString(num);
			char [] new_inst = bin.toCharArray();
			bin = new String(new_inst, 16, 31 - 16 + 1); 
		}	
		return bin;
	}
}
