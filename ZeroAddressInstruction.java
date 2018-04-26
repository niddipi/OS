/**************************************************************************
* Filename = ZeroAddressInstruction.java
* 
*Description : This file is responsible for execution of all the zero 
* 		Address instructions
*
**************************************************************************/
import java.util.*;
import java.io.*;
import java.util.InputMismatchException;
import java.util.InputMismatchException;


public class ZeroAddressInstruction {
   Memory_util m_util = new Memory_util();
   public static int inp_count =-1 ;
   public static int out_count =-1 ;
   public static String OUTPUT;
   int id =0;
   int ZeroAddressInstruction(String opcode) throws IOException
   {
      Stack_operations SO = new Stack_operations();
      CPU_util util = new CPU_util();
      Arithmetic_and_LogicalOP AL = new Arithmetic_and_LogicalOP();
      Error_Handler Er = new Error_Handler();
      int value = 0;
      switch(opcode){

         case "00000":
            break;
         case "00001":
            //OR Opcode Execution
	    System.out.println("OR");
            SO.Stack[SO.TOS-1] = 
               AL.OR(SO.Stack[SO.TOS],SO.Stack[SO.TOS-1]);
            SO.TOS = SO.TOS-1;

            break;

         case "00010":
            //AND Opcode Execution
	    System.out.println("AND");
            SO.Stack[SO.TOS-1] = 
               AL.AND(SO.Stack[SO.TOS],SO.Stack[SO.TOS-1]);
            SO.TOS = SO.TOS-1;
            break;

         case "00011":
            //NOT Opcode Execution
	    System.out.println("NOT");
            SO.Stack[SO.TOS] = AL.NOT(SO.Stack[SO.TOS]);
            SO.TOS = SO.TOS;
            break;

         case "00100":
            //XOR Opcode Execution
	    System.out.println("XOR");
            SO.Stack[SO.TOS-1] = 
               AL.XOR(SO.Stack[SO.TOS],SO.Stack[SO.TOS-1]);
            SO.TOS = SO.TOS-1;
            break;

         case "00101":
            //ADD Opcode Execution
	    System.out.println("ADD");
            SO.Stack[SO.TOS-1] = 
               AL.ADD(SO.Stack[SO.TOS],SO.Stack[SO.TOS-1]);
            SO.TOS = SO.TOS-1;
            break;

         case "00110":
            //SUB Opcode Exection
	    System.out.println("SUB");
            SO.Stack[SO.TOS-1] = 
               AL.SUB(SO.Stack[SO.TOS-1],SO.Stack[SO.TOS]);
            SO.TOS = SO.TOS-1;
            break;

         case "00111":
            //MUL Opcode Exection
	    System.out.println("MUL");
            SO.Stack[SO.TOS-1] = 
               AL.MUL(SO.Stack[SO.TOS],SO.Stack[SO.TOS-1]);
            SO.TOS = SO.TOS-1;
            break;
         case "01000":
            //DIV Opcode Exection
	    System.out.println("DIV");

            //Divide by zero Error Handler 
            if(SO.Stack[SO.TOS]==0)
            {
               Er.Error_Handler_func("DIVIDE_BY_ZERO");	
            }
            SO.Stack[SO.TOS-1] = 
               AL.DIV(SO.Stack[SO.TOS-1],SO.Stack[SO.TOS]);
            SO.TOS = SO.TOS-1;
            break;
         case "01001":
            //MOD Opcode Exection
	    System.out.println("MOD");
            SO.Stack[SO.TOS-1] = 
               AL.MOD(SO.Stack[SO.TOS-1],SO.Stack[SO.TOS]);
            SO.TOS = SO.TOS-1;
            break;
         case "01010":
            //SL Opcode Exection
	    System.out.println("SL");
            SO.Stack[SO.TOS] = 
               AL.SL(SO.Stack[SO.TOS]);
            break;
         case "01011":
            //SR Opcode Exection
	    System.out.println("SR");
            SO.Stack[SO.TOS] = AL.SR(SO.Stack[SO.TOS]);
            break;
         case "01100":
            //CPG Opcode Exection
	    System.out.println("CPG");
            SO.Stack[SO.TOS+1] = 
               AL.CPG(SO.Stack[SO.TOS-1],SO.Stack[SO.TOS]);
            SO.TOS = SO.TOS+1;
            break;
         case "01101":
            //CPL Opcode Exection
	    System.out.println("CPL");
            SO.Stack[SO.TOS+1] = 
               AL.CPL(SO.Stack[SO.TOS-1],SO.Stack[SO.TOS]);
            SO.TOS = SO.TOS+1;
            break;
         case "01110":
            //CPE Opcode Exection
	    System.out.println("CPE");
            SO.Stack[SO.TOS+1] = 
               AL.CPE(SO.Stack[SO.TOS-1],SO.Stack[SO.TOS]);
            SO.TOS = SO.TOS+1;
            break;
         case "01111":
            //BR Opcode Exection
            break;
         case "10000":
            //BRT Opcode Exection
            break;
         case "10001":
            //BRF Opcode Exection
            break;
         case "10010":
            //CALL Opcode Exection
            break;
         case "10011":
            //RD Opcode Exection
	    System.out.println("RD");
            short input = 0 ;
            try{
               inp_count++;
               if(inp_count > m_util.pcb[id].Input_seg_size)
               {
                  Er.Error_Handler_func("READING_BEYOND_END_OF_FILE");
               }
               if(m_util.pcb[0].Input_seg_info == -1)
               {
                  util.value =1;
                  return 0;	
               }
	       //System.out.println("util.address"+util.address);
	       //System.out.println("util.pcb[0]"+m_util.pcb[0].Input_seg_info);
		int count = inp_count - 1;
		count  = m_util.pcb[0].Input_seg_info + count;
               String bin  = new Memory().Memory_func("READ",
						 count,"Input"); 
		System.out.println("bin : "+bin);	
	       //System.out.println("Input :"+bin);
               input = (short)Integer.parseInt(bin, 2); 
               PCB.INPUT = PCB.INPUT+bin+"\n";
               SO.PUSH(input);	
            }catch(InputMismatchException ex){
               Er.Error_Handler_func("INVALID_INPUT");
            }
            util.CLOCK = util.CLOCK+15;
            if((util.CLOCK - util.OLD_CLOCK)>= 15)
            {
               util.OLD_CLOCK= util.CLOCK;
               m_util.Display_Mem_Pages();
            }

            util.IO_CLOCK = util.IO_CLOCK+15;
            value =1;
            break;

         case "10100":
            //WRITE Opcode Exection
            String temp = "";
	    	System.out.println("WR");
	   	 
            /*if(m_util.pcb[0].Output_seg_info == -1)
            {
               util.value =2;
               util.address =0;
               return 0;	
            }
            else
            {
               out_count++;
               if(out_count > m_util.Output_seg_size)
               {
                  Er.Error_Handler_func("WRITING_BEYOND_END_OF_FILE");
               }*/
               int val = (int) SO.POP();
	
               temp = Integer.toBinaryString(val);
               temp = "0000000000000000"+temp;
               temp = temp.substring(temp.length()-16,
                     temp.length()); 	
               OUTPUT = temp;	
	    	System.out.println("WR val :"+val+" OUTPUT:"+OUTPUT);

               int address = m_util.pcb[0].Output_seg_info;
               new Memory().Write(address,OUTPUT); 
               m_util.pcb[0].Output_seg_info = m_util.pcb[0].Output_seg_info+1;
            //}

            if(PCB.OUTPUT != null)
            {
               PCB.OUTPUT = PCB.OUTPUT+"\t"+temp;
            }
            else
            {
               PCB.OUTPUT = temp+"\n";
            }
            util.CLOCK = util.CLOCK+15;
            util.IO_CLOCK = util.IO_CLOCK+15;
            if((util.CLOCK - util.OLD_CLOCK)>= 15)
            {
               util.OLD_CLOCK= util.CLOCK;
               m_util.Display_Mem_Pages();
            }
            value = 1;
            break;

         case "10101":
            //RTN Opcode Exection
	    System.out.println("RTN");
            m_util.pcb[id].PC = SO.POP();
            value = 1;
            break;

         case "10110":
            //PUSH Opcode Exection
            break;

         case "10111":
            //POP Opcode Exection
            break;

         case "11000":
            //HLT Opcode Exection
            util.value = 4;
            break;
         default:
            //Error Handler for Invalid Opcode Exection
            Er.Error_Handler_func("INVALID_OPCODE");
            break;
      }
      return value;
   }
}
