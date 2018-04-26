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
				m_util.pcb[id].EA = -1;
				m_util.pcb[id].EA_Prev = m_util.pcb[id].EA;
				opcode = new String(new_inst,start,
							end - start + 1);
				value = 
				Exec_zero_inst.ZeroAddressInstruction(opcode);
				
				i = i+8;
				
				util.CLOCK = util.CLOCK+1;	
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
				m_util.pcb[id].EA = Integer.parseInt(DADDR, 2);
				m_util.pcb[id].EA = m_util.pcb[id].EA + 
				Integer.parseInt(
				memory.Memory_func("READ",SO.Stack[SO.TOS],"Prog"),2);
			}
			else
			{
				m_util.pcb[id].EA = Integer.parseInt(DADDR, 2);
			}
			m_util.pcb[id].EA_Prev = m_util.pcb[id].EA;

			OneAddressInstruction Exec_One_inst = 
						new OneAddressInstruction();

			value = 
			   Exec_One_inst.OneAddressInstruction(opcode,m_util.pcb[id].EA);
				util.CLOCK = util.CLOCK+4; 
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
		int TOS =0;
		int id  = 0;
		m_util.pcb[id].PC = cpu_PC;
		Stack_operations SO = new Stack_operations();
		File_writer FWrite = new File_writer(); 
		Memory memory = new Memory();
		while(true)
		{
			System.out.printf("\n\n\n");
                        System.out.printf("PC %d\n",m_util.pcb[id].PC);
                        System.out.printf("TOS %d\n",SO.TOS);
                        TOS = SO.TOS;
                        while(TOS >=0)
                        {
                                System.out.printf("Stackvalue of %d:%d\n",TOS,SO.Stack[TOS]);
                                TOS--;
                        }
                        System.out.printf("\n\n\n");


			if(util.CLOCK > 1200)
			{
				Er.Error_Handler_func("INFINITE_LOOP_DETECTED");
			}
			util.value = -1;
			cpu_PC = m_util.pcb[id].PC;
			util.IR = Fetch(cpu_PC);
			System.out.println("util.OP "+util.IR+" cpu_PC"+cpu_PC);
			if(util.IR.equals("stop"))
			{
				m_util.pcb[id].PC = m_util.pcb[id].PC+1;
				util.value =0;
				break;
			}
			util.TOS_Prev = SO.TOS;
			if(SO.TOS >= 0){
				util.TOS_Val_Prev = SO.Stack[SO.TOS];
			}
			
			m_util.pcb[id].PC = m_util.pcb[id].PC+1;
			value = Decode_Execute(util.IR);
			if(util.value >=0)
			{
				break;
			}
			
			if(m_util.pcb[id].trace_flag ==1)
			{
				FWrite.write_tracefile();
			}
		}
		return util.IR;		
	}

}
