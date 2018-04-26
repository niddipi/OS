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
	Output_Spooling output_spool = new Output_Spooling();

	private static final String newLine = 
		System.getProperty("line.separator");

	public int Error_Handler_func(String error) 
		throws IOException {

			output_spool.Err = output_spool.Err+error;
			switch(error)
			{

				case "STACK_OVERFLOW":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				case "STACK_UNDERFLOW": 
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "SUSPECTED_INF_LOOP":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "INVALID_INPUT":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "INCORRECT_INST":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "REDUN_INSTRUCTION":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "INVALID_LOADER_FORMAT":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "INVALID_MEMORY_ACCESS":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "MEMORY_FULL":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "FILE_DOES_NOT_EXIST":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "FILE_IS_EMPTY":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "ENTER_FILE":
					output_spool.Warn = output_spool.Warn+error;
					break;
				case "DIVIDE_BY_ZERO":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "INVALID_OPCODE":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "INVALID_LOADER_SIZE":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "INVALID_LOADER_START_ADDRESS":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "INVALID_TYPE_INST":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "INVALID_LOADER_FILE":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "INSUFFICIENT_AVAILABLE_DISK_SPACE":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
                                        System.exit(1);
                                        break;
				
				case "INPUT_AND_OUTPUT_MISSING":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
                                        System.exit(1);
                                        break;
				
				case "OUTPUT_MISSING":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
                                        System.exit(1);
                                        break;
				case "INVALID_TRACE_FLAG":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "**INPUT_**OUTPUT_**FIN_Missing":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "**INPUT_Is_Missing":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "**FIN_Is_Missing":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "INPUT_WORDS_MISSING":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "INVALID_INPUT_FORMAT":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "INVALID_OUTPUT_SEG_SIZE":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;

				
				case "INVALID_INPUT_SEG_SIZE":
					output_spool.NATURE = 1;
					output_spool.Output_spool();
					System.exit(1);
					break;
				case "INVALID_JOBID_GIVEN":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "INVALID_LOAD_ADDRESS_GIVEN":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				case "INVALID_START_ADDRESS_GIVEN":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "INVALID_PROGRAM_SIZE_GIVEN":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "MULTIPLE_**FIN_PRESENT":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "MULTIPLE_**JOB_PRESENT":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				case "MULTIPLE_**INPUT_PRESENT":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "MULTIPLE_**OUTPUT_PRESENT":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "INVALID_OUTPUT":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "READING_BEYOND_END_OF_FILE":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				case "WRITING_BEYOND_END_OF_FILE":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;

				case "UNNECESSARY_BLANK_LINES_PRESENT":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				case "INFINITE_LOOP_DETECTED":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "**JOB_Is_Missing":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "CONFLICT_NO_OF_WORDS_FOR_PROGRAM_SEGMENT":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				
				case "CONFLICT_NO_OF_WORDS_FOR_INPUT_SEGMENT":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				case "OUT_OF_RANGE_MEMORY_ACCESS":
					output_spool.NATURE = 1;
                			output_spool.Output_spool();
					System.exit(1);
					break;
				
				default:
					break;
			}		

			return 0;	
		}
}
