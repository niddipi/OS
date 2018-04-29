/**************************************************************************
* Filename   : File_writer.java
* 
*Description : This file is responsible for writing trace file and other
* 	 	file related operattions
*
**************************************************************************/
import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class File_writer{

CPU_util util = new CPU_util();
Error_Handler e = new Error_Handler();
	private static final String newLine = 
			System.getProperty("line.separator");
	
	public synchronized void write_tracefile(String fileName){
		//String fileName = "trace_file.txt";
		PrintWriter printwriter = null;
		int len = 0;
		CPU_util util = new CPU_util();
		Stack_operations SO = new Stack_operations();
		Memory memory = new Memory();	
		Memory_util m_util = new Memory_util();
 		int id = util.id;
		File file = new File(fileName);
		try {
			if (!file.exists())
			{
				file.createNewFile();
				printwriter = new PrintWriter(
					new FileOutputStream(fileName, true));
				String msg1 = "PC       BR        IR"+
						"   BEx_TOS"+
						"  BEx_S[TOS]"+
						"  BEx_EA"+
						"  BEx_(EA)"+
					"   TOS   S[TOS]      EA       (EA)";
				String msg2 = "HEX      HEX       HEX"+
						"     HEX"+
						"   HEX"+
						"         HEX"+
						"         HEX"+
					"     HEX      HEX     HEX       HEX";
				printwriter.write(newLine + msg1+newLine);
				printwriter.write(newLine + msg2+newLine);
				printwriter.flush();
				printwriter.close();
			}
			printwriter = new PrintWriter(
				new FileOutputStream(fileName, true));
			String PC = Integer.toHexString(util.PC);
			while (PC.length() < 2){
                                PC = "0"+PC;
                        }
			
			int decimal = Integer.parseInt(util.IR,2);
			String IR = Integer.toString(decimal,16);
			while (IR.length() < 4){
                                IR = "0"+IR;
                        }

			String BR = Integer.toHexString(memory.BR);	
			while (BR.length() < 4){
                                BR = "0"+BR;
                        }

			/****************After Execution******************/
			String TOS = "NA";
			String TOS_val = "NA ";
			if(SO.TOS >= 0)	
			{
				
				TOS = Integer.toHexString(SO.TOS);
				TOS_val = 
					Integer.toHexString(SO.Stack[SO.TOS]);
				while (TOS_val.length() < 4){
					TOS_val = "0"+TOS_val;
				}
				len = TOS_val.length();
				if(len > 4)
				{
					TOS_val=TOS_val.substring(len-4,len);
				}
			}
			String EA = " NA ";
			String EA_val = " NA ";
			if(util.EA >= 0)
			{
				EA = Integer.toHexString(util.EA);
				while (EA.length() < 4){
					EA = "0"+EA;
				}
				m_util.pcb[id].Trace_page = 1;
				EA_val = memory.Memory_func("READ",util.EA,EA);
				if(!EA_val.equals("  NA")){
				int decimal1 = Integer.parseInt(EA_val,2);
				EA_val = Integer.toHexString(decimal1);
				
				while (EA_val.length() < 4){
					EA_val = "0"+EA_val;
				}
				len = EA_val.length();
				if(len > 4)
				{
					EA_val = EA_val.substring(len-4,len);
				}}
			}
			/****Before Execution*****/
			String TOS_prev = "NA";
			String TOS_val_prev = "NA ";
			if(util.TOS_Prev >= 0)	
			{
				
				TOS_prev = Integer.toHexString(util.TOS_Prev);
				TOS_val_prev = 
					Integer.toHexString(util.TOS_Val_Prev);
				while (TOS_val_prev.length() < 4){
					TOS_val_prev = "0"+TOS_val_prev;
				}
				len = TOS_val_prev.length();
				if(len > 4)
				{
					TOS_val_prev=TOS_val_prev.substring(len-4,len);
				}
			}
			String EA_prev = " NA ";
			String EA_val_prev = " NA ";
			if(util.EA_Prev >= 0)
			{

				EA_prev = Integer.toHexString(util.EA_Prev);
				while (EA_prev.length() < 4){
					EA_prev = "0"+EA_prev;
				}
				m_util.pcb[id].Trace_page = 1;
				EA_val_prev = memory.Memory_func("READ",util.EA_Prev,"Prog");
				if(!EA_val_prev.equals("  NA")){
				int decimalv = Integer.parseInt(EA_val_prev,2);
				EA_val_prev = Integer.toHexString(decimalv);
				while (EA_val_prev.length() < 4){
					EA_val_prev = "0"+EA_val_prev;
				}}
			}
			
			String msg = PC;		
			msg = msg+"      "+BR;		
			msg = msg+"      "+IR;		
			msg = msg+"      "+TOS_prev;		
			msg = msg+"      "+TOS_val_prev;		
			msg = msg+"      "+EA_prev;		
			msg = msg+"      "+EA_val_prev;		
			msg = msg+"      "+TOS;		
			msg = msg+"      "+TOS_val;		
			msg = msg+"      "+EA;		
			msg = msg+"      "+EA_val;		

			printwriter.write(newLine + msg);
		} catch (IOException ioex) {
		} finally {
			//Error Handler
			if (printwriter != null) {
				printwriter.flush();
				printwriter.close();
			}
		}
	}


}
