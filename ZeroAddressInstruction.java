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
   public static int inp_count =0 ;
   public static int in_count =8 ;
   public static int out_count =-1 ;
   public static String OUTPUT;
   int ZeroAddressInstruction(String opcode) throws IOException
   {
      Stack_operations SO = new Stack_operations();
      CPU_util util = new CPU_util();
   	int id =util.id;
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
		/*
               }*/
		//int id = util.id;
               if(m_util.pcb[id].Input_seg_info == -1)
               {
                  util.value =1;
		  util.address = 0;
                  return 0;	
               }
               if(inp_count >= m_util.pcb[id].Input_seg_size)
               {
                  Er.Error_Handler_func("READING_BEYOND_END_OF_FILE");
		  return 4;
	       }
		int count = m_util.pcb[id].inp_count;
		count     = m_util.pcb[id].Input_seg_info + count;
               if(m_util.pcb[id].inp_count > in_count)
               {
                  util.value =1;
		  util.address = 0;
   		  in_count = in_count+8 ;
                  return 0;	
               }
               String bin = new Memory().Memory_func("READ",
						 count,"Input"); 
		System.out.println("bin : "+bin);	
               input = (short)Integer.parseInt(bin, 2); 
		if(m_util.pcb[id].inp_count == 0){
               m_util.pcb[id].INPUT = m_util.pcb[id].INPUT+bin+"\n";
		}
		else
		{
               		m_util.pcb[id].INPUT = m_util.pcb[id].INPUT+"\t\t     "+bin+"\n";
		}
               int er = SO.PUSH(input);	
	      if(er == -1)
		{
			return 4;
		}
               m_util.pcb[id].inp_count++;
            }catch(InputMismatchException ex){
               Er.Error_Handler_func("INVALID_INPUT");
		return 4;
            }
            util.CLOCK = util.CLOCK+15;

            util.IO_CLOCK = util.IO_CLOCK+15;
            value =1;
            break;

         case "10100":
            //WRITE Opcode Exection
            String temp = "";
	    	System.out.println("WR");
	   	 
            if(m_util.pcb[id].Output_seg_info == -1)
            {
               util.value =2;
               util.address =0;
		util.out_value = -1;
               return 0;	
            }
            else
            {
		util.out_value = 0;
               if(m_util.pcb[id].out_count > m_util.pcb[id].Output_seg_size)
               {
                  Er.Error_Handler_func("WRITING_BEYOND_END_OF_FILE");
		  return 4;
               }
               int val = (int) SO.POP();
	       if(util.value == 4)
		{
			return 4;
		}
	
               temp = Integer.toBinaryString(val);
               temp = "0000000000000000"+temp;
               temp = temp.substring(temp.length()-16,
                     temp.length()); 	
               OUTPUT = temp;	
	    	System.out.println("id :"+id+"WR val :"+val+" OUTPUT:"+OUTPUT);

               int address = m_util.pcb[id].Output_seg_info;
               //new Memory().Write(address,OUTPUT); 
               m_util.pcb[id].Output_seg_info = m_util.pcb[id].Output_seg_info;
              }

            if(m_util.pcb[id].OUTPUT != null)
            {
               m_util.pcb[id].OUTPUT = m_util.pcb[id].OUTPUT+"\t\t      "+temp+"\n";
            }
            else
            {
               m_util.pcb[id].OUTPUT = temp+"\n";
            }
            util.CLOCK = util.CLOCK+15;
            util.IO_CLOCK = util.IO_CLOCK+15;
            value = 1;
            m_util.pcb[id].out_count++;
            break;

         case "10101":
            //RTN Opcode Exection
	    System.out.println("RTN");
            util.PC = SO.POP();
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
	   if(util.out_value != -1){
            util.value = 4;
	 }
            break;
         default:
            //Error Handler for Invalid Opcode Exection
            Er.Error_Handler_func("INVALID_OPCODE");
	    return 4;
      }
      return value;
   }
}
