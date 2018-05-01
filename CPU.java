/**************************************************************************
* Filename = CPU.java
* 
*Description : This file is responsible for execution of Fetch,Decode
* 		and Execute continously.
*
**************************************************************************/
import java.io.*;
import java.util.*;

public class CPU {
	
	Memory_util m_util = new Memory_util();
	Error_Handler Er = new Error_Handler();
	public static int prev_index = 0;
	public static int current_index = 0;
	public static int index_count = 0;		
	String Fetch(int PC)
	{
		Memory memory = new Memory();
		String Z = null;
		return memory.Memory_func("READ",PC,"Prog");
	}
       	/***Decodes and Executes the instruction*****/ 
	int Decode_Execute(String Instruction) throws IOException
	{
		char [] new_inst = Instruction.toCharArray();
		char Type = new_inst[0];
		Stack_operations SO = new Stack_operations();
		CPU_util util = new CPU_util();
		int value = 0;	
		int index = 0;
		int id = util.id;
		String Z =null;
		Memory memory = new Memory();
		Memory_util m_util = new Memory_util();
		String opcode = null ;
		//Zero address
		if(Type == '0')
		{
			ZeroAddressInstruction Exec_zero_inst =
				new ZeroAddressInstruction();
			int i = 0;
			int start = 0;
			int end = 0;
			while(index < 2)
			{ 
				start = i+3;
				end   = i+7;
				util.EA = -1;
				util.EA_Prev = util.EA;
				opcode = new String(new_inst,start,
							end - start + 1);
				value = 
				Exec_zero_inst.ZeroAddressInstruction(opcode);
				
				i = i+8;
				
				util.CLOCK = util.CLOCK+1;	
				
				util.CPUCLOCK = util.CPUCLOCK+1; 
				if((util.CLOCK - util.OLD_CLOCK) >= 15)
				{
					util.OLD_CLOCK = util.CLOCK;	
					m_util.Display_Mem_Pages();
				}
				util.OP = opcode;
				if(opcode == "10101")
				{
					break;
				}
				index++;
			}
		}
		else if(Type == '1')
		{
			opcode = new String(new_inst, 1, 5 - 1 + 1);		
			util.OP = opcode;
			String DADDR  = new String(new_inst, 9, 15 - 9 + 1);
			if (new_inst[6] == '1')
			{
				util.EA = Integer.parseInt(DADDR, 2);
				util.EA = util.EA + 
				Integer.parseInt(
				memory.Memory_func("READ",SO.Stack[SO.TOS],"Prog"),2);
			}
			else
			{
				util.EA = Integer.parseInt(DADDR, 2);
			}

			OneAddressInstruction Exec_One_inst = 
						new OneAddressInstruction();

			value = 
			   Exec_One_inst.OneAddressInstruction(opcode,util.EA);
				util.CLOCK = util.CLOCK+4; 
				util.CPUCLOCK = util.CPUCLOCK+4; 
					
				 if((util.CLOCK - util.OLD_CLOCK) >= 15)
                                {
					util.OLD_CLOCK= util.CLOCK; 	
                                        m_util.Display_Mem_Pages();
                                }

		}
		else
		{
			Er.Error_Handler_func("INVALID_TYPE_INST");
		}
		return value;
	}	

	String CPU_func(int cpu_PC,int trace_switch) throws IOException
	{
		CPU_util util = new CPU_util();
		String Instruction=null;
		int value = 0;
		int TOS =-1;
		int id  = util.id;
		Stack_operations SO = new Stack_operations();
		File_writer FWrite = new File_writer(); 
		Memory memory = new Memory();
		String filename = "";
		while(true)
		{
			util.Er_id = util.id;
			System.out.printf("\n\n\n");
			System.out.println("*********************************");
                        System.out.println("id "+id);
                        System.out.printf("PC %d\n",util.PC);
                        System.out.printf("TOS %d\n",SO.TOS);
                        TOS = SO.TOS;
                        while(TOS >=0)
                        {
                                System.out.printf("Stackvalue of %d:%d\n",TOS,SO.Stack[TOS]);
                                TOS--;
                        }

			
			util.value = -1;
			cpu_PC = util.PC;
			if(m_util.pcb[id].CLOCK > 1200)
			{
				util.CLOCK = 0;
				Er.Error_Handler_func("INFINITE_LOOP_DETECTED");
			}
			m_util.pcb[id].CLOCK++;
			util.IR = Fetch(cpu_PC);
			System.out.println("util.OP "+util.IR+" cpu_PC"+cpu_PC);
			if(util.IR.equals("stop"))
			{
				util.PC = util.PC+1;
				util.value =0;
				break;
			}
			util.TOS_Prev = SO.TOS;
			if(SO.TOS >= 0){
				util.TOS_Val_Prev = SO.Stack[SO.TOS];
			}
			if(id == 6){
				new Memory().Display_Mem(m_util.pcb[id].Page_Mem_order[0].Frame_base_address);

			}
			util.EA_Prev = util.EA;

			util.PC = util.PC+1;
			value = Decode_Execute(util.IR);
			if(id == 0){
				System.out.println("id == 4"+"Pages :"+m_util.pcb[id].no_of_pages);
				m_util.Display_Mem_Pages();
			}
			if(m_util.id > 12){
				new Disk().display_disk(12);
			}
			System.out.println("*********************************");
                        System.out.printf("\n\n\n");
			 if(((util.CPUCLOCK - util.OLD_CPUCLOCK) >= 20)&& util.value == -1)
			{
				util.value = 5;
				util.OLD_CPUCLOCK= util.CPUCLOCK; 	
			}
			if(util.value >=0 && util.value <4 )
			{
				util.CPUCLOCK = util.OLD_CPUCLOCK;
				break;
			}
			filename = "trace_file"+id+m_util.pcb[id].JOBID+".txt";
			System.out.println("filename :"+filename);
			if(m_util.pcb[id].trace_flag ==1)
			{
				FWrite.write_tracefile(filename);
			}
			if(util.value >=4){
				System.out.println("util.value :"+util.value);
				util.CPUCLOCK = util.OLD_CPUCLOCK;
				break;
			}
			
		}
		return util.IR;		
	}

}
