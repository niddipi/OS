/**************************************************************************
* Filename = OneAddressInstruction.java
* 
*Description : This file is responsible for execution of all the One 
* 		Address instructions
*
**************************************************************************/
import java.util.Scanner;
import java.io.*;

public class OneAddressInstruction{

   int OneAddressInstruction(String opcode,int EA) throws IOException
   {
      int value = 0;
      String Z = "gg";
      Error_Handler Er = new Error_Handler();
      Stack_operations SO = new Stack_operations();
      CPU_util util  = new CPU_util();
          Memory_util m_util = new Memory_util();
      Memory memory    = new Memory();
      String temp = null;
      int id = util.id;
      short EA_value = 0;
      util.OP=opcode;
      if(!(opcode.equals("10001") || opcode.equals("00000") || 
			     opcode.equals("01111") || 
			     opcode.equals("10000") ||
			     opcode.equals("10010")))
      {

         temp    = memory.Memory_func("READ",EA,Z);
         if(temp == null)
         {
            Er.Error_Handler_func("INVALID_MEMORY_ACCESS");

         }
         if(temp.equals("stop"))
         {
            util.value =0;
            return 0;

         }
         EA_value = (short)Integer.parseInt(temp, 2);
      }
      Arithmetic_and_LogicalOP AL = new Arithmetic_and_LogicalOP();

      switch(opcode){

         case "00000":
            //NOP Opcode
	    System.out.println("NOP");
            break;

         case "00001":
            //Add Opcode
	    System.out.println("Add");
            SO.Stack[SO.TOS] = AL.OR(SO.Stack[SO.TOS],EA_value);
            break;

         case "00010":
            //AND Opcode
	    System.out.println("AND");
            SO.Stack[SO.TOS] = AL.AND(SO.Stack[SO.TOS],EA_value);
            break;

         case "00011":
            //NOT Opcode
	    System.out.println("NOT");
            break;

         case "00100":
            //XOR Opcode
	    System.out.println("XOR");
            SO.Stack[SO.TOS] = AL.XOR(SO.Stack[SO.TOS],EA_value);
            break;

         case "00101":
            //ADD Opcode;
	    System.out.println("ADD");

            SO.Stack[SO.TOS] = AL.ADD(SO.Stack[SO.TOS],EA_value);
            break;

         case "00110":
            //SUB Opcode
	    System.out.println("SUB");
            SO.Stack[SO.TOS] = AL.SUB(SO.Stack[SO.TOS],EA_value);
            break;

         case "00111":
            //MUL Opcode
	    System.out.println("MUL");
            SO.Stack[SO.TOS] = AL.MUL(SO.Stack[SO.TOS],EA_value);
            break;
         case "01000":
            //DIV Opcode
	    System.out.println("DIV");
            if(EA_value==0)
            {
               Er.Error_Handler_func("DIVIDE_BY_ZERO");	
            }
            SO.Stack[SO.TOS] = AL.DIV(SO.Stack[SO.TOS],EA_value);
            break;
         case "01001":
            //MOD Opcode
	    System.out.println("MOD");
            SO.Stack[SO.TOS] = AL.MOD(SO.Stack[SO.TOS],EA_value);
            break;
         case "01010":
            //SL Opcode
	    System.out.println("SL");
            break;
         case "01011":
            //SR Opcode
	    System.out.println("SR");
            break;
         case "01100":
            //CPG Opcode
	    System.out.println("CPG");
            if(SO.TOS >= 6)
            {
               Er.Error_Handler_func("STACK_OVERFLOW");
            }
            SO.Stack[SO.TOS+1] = AL.CPG(SO.Stack[SO.TOS],EA_value);
            SO.TOS = SO.TOS+1;
            break;
         case "01101":
            //CPL Opcode
	    System.out.println("CPL");
            if(SO.TOS >= 6)
            {
               Er.Error_Handler_func("STACK_OVERFLOW");
            }
            SO.Stack[SO.TOS+1] = AL.CPL(SO.Stack[SO.TOS],EA_value);
            SO.TOS = SO.TOS+1;
            break;
         case "01110":
            //CPE Opcode
	    System.out.println("CPE");
            if(SO.TOS >= 6)
            {
               Er.Error_Handler_func("STACK_OVERFLOW");
            }
            SO.Stack[SO.TOS+1] = AL.CPE(SO.Stack[SO.TOS],EA_value);
            SO.TOS = SO.TOS+1;
            break;
         case "01111":
            //BR Opcode
	    System.out.println("BR");
            m_util.pcb[id].PC = m_util.pcb[id].EA;
            break;
         case "10000":
            //BRT Opcode
	    System.out.println("BRT");
            if(SO.Stack[SO.TOS] == 1)
            {
               m_util.pcb[id].PC = m_util.pcb[id].EA;
	       System.out.println("m_util.pcb[id].EA :"+m_util.pcb[id].EA);
            }
            SO.TOS = SO.TOS-1;
            break;
         case "10001":
            //BRF Opcode
	    System.out.println("BRF");

            if(SO.Stack[SO.TOS] == 0)
            {
               m_util.pcb[id].PC = m_util.pcb[id].EA;
            }
            SO.TOS = SO.TOS-1;
            break;
         case "10010":
            //CALL Opcode
	    System.out.println("CALL");
            SO.PUSH(m_util.pcb[id].PC);
            m_util.pcb[id].PC = m_util.pcb[id].EA; 
            break;
         case "10011":
            //RD Opcode
            break;
         case "10100":
            //WR Opcode
            break;
         case "10101":
            //RTN Opcode
            break;
         case "10110":
            //PUSH Opcode
	    System.out.println("PUSH");
            SO.PUSH(EA_value);
            break;
         case "10111":
            //POP Opcode
	    System.out.println("POP");
            int val = SO.POP();
            String bin = util.twos_compl(val);
            memory.Memory_func("WRITE",m_util.pcb[id].EA,util.twos_compl(val));
            break;
         case "11000":
            //HLT Opcode
	    System.out.println("HLT");
            util.value = 4;
            value = 2;
            break;
         default:
            //Error Handler for INVALID_OPCODE
            Er.Error_Handler_func("INVALID_OPCODE");
            break;
      }
      return value;
   }
}
