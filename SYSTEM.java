 /* a. Neelesh Iddipilla 
    b. CS 5323
    c. Memory Mamangement (Phase II)
    d. 04/03/2018
    e. GLOBAL Variables:
       LOADER CLASS: TRACE_FLAG,JOB_ID, LOCAL_ADDRESS,
       BASE_ADDRESS.
       CPU CLASS:TOS_Prev,PC,EA,CLOCK,IO_CLOCK
       ERROR_HANDLER: NATURE, ERROR.
       MEMORY CLASS: Memory
       LOADER CLASS: TRACE_FLAG,JOB_ID, LOCAL_ADDRESS, BASE_ADDRESS,
       INITIAL_PC, PC, INST_REGISTER, PREVIOUS_EA, EFFECTIVE_ADDR;
       CPU    CLASS: prev_index, current_index, index_total, CLOCK,
       IOCLOCK, opcode, OUPUT.
       ERROR_HANDLER: NATURE, ERROR.
	Memory_util : public static int  Prog_seg_size ,Input_seg_size
	Output_seg_size
    f. The SYSTEM CLASS is the driver of the project.
        Input_Spooling and CPU classes were called.
       Input_Spooling  loads the input file to disk and
       then CPU will execute the Instructions in the Memory.
    g. The SYSTEM class is the starting step of the porject.
       It handles the segment fault and page faults.
       I divide each module into a seperate class. It checks
       the trace file and the output files were already existing.
       It will delete the existing tracefile and output_file.
    */

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class SYSTEM
{
	public static int trace_switch = 0;
	public static void main(String[] args) throws IOException {
	
		CPU_util util = new CPU_util();
		Scheduler sched = new Scheduler();
		Output_Spooling output_spool = new Output_Spooling();
		Error_Handler Er = new Error_Handler();
		Memory m = new Memory();
		Memory_util m_util = new Memory_util();
	    	m_util.Init_fmbv();	
		Disk disk = new Disk();
		disk.Init_diskfmbv();
		int val  = 0;
		File f = new File("output_file.txt");
		if(f.exists())
		{
			f.delete();
		}
		
		f = new File("trace_file.txt");
		if(f.exists())
		{
			f.delete();
		}
		
		CPU cpu = new CPU();
		Loader loader = new Loader();
		if(args.length == 0)
		{
			Er.Error_Handler_func("ENTER_FILE");	
		}
		File file = new File(args[0]);
		if(!file.exists())
		{
			Er.Error_Handler_func("FILE_DOES_NOT_EXIST");	
		}	
		
		Input_spooling inp_spool = new Input_spooling(); 
		m_util.filename = args[0];
		System.out.println("END");
		val = inp_spool.InputSpool(args[0]);
		while(val != 0)
		{
			System.out.println("Neels");
                        output_spool.Output_spool();
			val = inp_spool.InputSpool(args[0]);
		}
		new Disk().Disk_display();
		if(util.JOBS_FINISH == 1)
		{
			System.exit(0);
		}
		//m_util.Display_PCB(0);
		int value =0;
		int id = util.id;
		util.schedule_type = -1;
		m_util.display_fmbv();
		while(true)
		{
			value = sched.schedule_nextjob();
			id = util.id;
			System.out.println("JOB Scheduled :"+util.id);
			if(value <0){
				break;
			}
			cpu.CPU_func(util.PC,trace_switch);
			if(util.value == 4)
			{
				System.out.println("idddddddddddddd :"+id);
			        sched.Save_pcb();	
				output_spool.Output_spool(); 	
				val = inp_spool.InputSpool(args[0]);
				while(val != 0)
				{
					System.out.println("Neelesh");
					//Output_Spooling output_spool = new Output_Spooling();
					output_spool.Output_spool();
					val = inp_spool.InputSpool(args[0]);
				}
				 value =0;
				util.schedule_type = 2;
				m_util.display_fmbv();
			}
			else if(util.value == 5){
				util.schedule_type = 1;
			}
			else{
			util.schedule_type = 0;
			util.address = m.Page_not_found(util.address,(util.value));
			if(util.value ==1)
			{
				m_util.pcb[id].Input_seg_info = util.address;

			}
			else if(util.value ==2)
			{
				m_util.pcb[id].Output_seg_info =util.address;

			}
			else if(util.value == 4)
                        {
                                System.out.println("Hello :"+id);
                                sched.Save_pcb();       
                                output_spool.Output_spool();  
                                inp_spool.InputSpool(args[0]);
                                util.schedule_type = 2;
                                m_util.display_fmbv();
                        }
			
                        }
			if(util.schedule_type != 1){
			util.PC = util.PC - 1;
			}
			util.value = -1;
		}	
	}


}
