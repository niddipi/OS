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
		Error_Handler Er = new Error_Handler();
		Memory m = new Memory();
		Memory_util m_util = new Memory_util();
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
		inp_spool.InputSpool(args[0]);
		new Disk().Disk_display();
		//loader.Loader_func(args[0],1);
		//new Memory().display_mem();
		/*
		int id = util.id;
		while(true)
		{
			cpu.CPU_func(m_util.pcb[id].PC,trace_switch);
			if(util.value == 4)
			{
				break;
			}
			System.out.println("SYSTEM :"+util.address);	
			util.address = m.Page_not_found(util.address,(util.value));
			if(util.value ==1)
			{
				m_util.pcb[0].Input_seg_info = util.address;

			}
			else if(util.value ==2)
			{
				m_util.pcb[0].Output_seg_info =util.address;

			}
			m_util.pcb[id].PC = m_util.pcb[id].PC - 1;
			util.value = -1;
		}	
		Output_Spooling output_spool = new Output_Spooling();
		output_spool.Output_spool(); 	
	}*/


}
