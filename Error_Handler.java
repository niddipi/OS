/**************************************************************************
* Filename = Error_Handler.java
* 
*Description : This file is responsible for handling of all the possible
* 		error and warning Scenarios
*
**************************************************************************/
import java.io.*;
import java.util.*;

public class Error_Handler{
	   Memory_util m_util = new Memory_util();
	   CPU_util util = new CPU_util();

	private static final String newLine = 
		System.getProperty("line.separator");

	public int Error_Handler_func(String error) 
		throws IOException {
			int id = util.Er_id;
			m_util.pcb[id].Err = m_util.pcb[id].Err+error;
			System.out.println("id :"+id+"IError"+m_util.pcb[id].Err);
			util.schedule_type = 2;
			util.value = 4;
			switch(error)
			{

				case "STACK_OVERFLOW":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				case "STACK_UNDERFLOW": 
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "SUSPECTED_INF_LOOP":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "INVALID_INPUT":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "INCORRECT_INST":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "REDUN_INSTRUCTION":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "INVALID_LOADER_FORMAT":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "INVALID_MEMORY_ACCESS":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "MEMORY_FULL":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "FILE_DOES_NOT_EXIST":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "FILE_IS_EMPTY":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "ENTER_FILE":
					m_util.pcb[id].Warn = m_util.pcb[id].Warn+error;
					break;
				case "DIVIDE_BY_ZERO":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "INVALID_OPCODE":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "INVALID_LOADER_SIZE":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "INVALID_LOADER_START_ADDRESS":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "INVALID_TYPE_INST":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "INVALID_LOADER_FILE":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "INSUFFICIENT_AVAILABLE_DISK_SPACE":
					m_util.pcb[id].NATURE = 1;
					
                                        break;
				
				case "INPUT_AND_OUTPUT_MISSING":
					m_util.pcb[id].NATURE = 1;
					
                                        break;
				
				case "OUTPUT_MISSING":
					m_util.pcb[id].NATURE = 1;
					
                                        break;
				case "INVALID_TRACE_FLAG":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "**INPUT_**OUTPUT_**FIN_Missing":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "**INPUT_Is_Missing":
					m_util.pcb[id].NATURE = 1;
					
					break;
				
				case "**FIN_Is_Missing":
					m_util.pcb[id].NATURE = 1;
					
					break;
				
				case "INPUT_WORDS_MISSING":
					m_util.pcb[id].NATURE = 1;
					
					break;
				
				case "INVALID_INPUT_FORMAT":
					m_util.pcb[id].NATURE = 1;
					
					break;
				
				case "INVALID_OUTPUT_SEG_SIZE":
					m_util.pcb[id].NATURE = 1;
					
					break;

				
				case "INVALID_INPUT_SEG_SIZE":
					m_util.pcb[id].NATURE = 1;
					
					break;
				case "INVALID_JOBID_GIVEN":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				
				case "INVALID_LOAD_ADDRESS_GIVEN":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				case "INVALID_START_ADDRESS_GIVEN":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				
				case "INVALID_PROGRAM_SIZE_GIVEN":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				
				case "MULTIPLE_**FIN_PRESENT":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				
				case "MULTIPLE_**JOB_PRESENT":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				case "MULTIPLE_**INPUT_PRESENT":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				
				case "MULTIPLE_**OUTPUT_PRESENT":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				
				case "INVALID_OUTPUT":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				
				case "READING_BEYOND_END_OF_FILE":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				case "WRITING_BEYOND_END_OF_FILE":
					m_util.pcb[id].NATURE = 1;
                			
					break;

				case "UNNECESSARY_BLANK_LINES_PRESENT":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				case "INFINITE_LOOP_DETECTED":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				
				case "**JOB_Is_Missing":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				
				case "CONFLICT_NO_OF_WORDS_FOR_PROGRAM_SEGMENT":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				
				case "CONFLICT_NO_OF_WORDS_FOR_INPUT_SEGMENT":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				case "OUT_OF_RANGE_MEMORY_ACCESS":
					m_util.pcb[id].NATURE = 1;
                			
					break;
				
				default:
					break;
			}		

			return 0;	
		}
}
