/**************************************************************************
* Filename =Loader.java
* 
* Description : This file loads all the data for input file to memory
*		The buffer size of loader has 4 words.
* 	 	
*
**************************************************************************/
import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;
public class Loader{

	/**Buffer**/
	char[] Buffer = new char[256];
	/****Hex to Binary conversion********/
	String hexToBinary(String hex) {
		String bin = new BigInteger(hex, 16).toString(2);
		while (bin.length() < hex.length()*4){
			bin = "0"+bin;
		}
		if(hex.equals("0000")){

			bin = "0000000000000000";
		}

		return bin;
	}

	int Page_Allocation(int Size)
	{
		if(Size+2 < 6)
		{
			return Size+2;
		}
		else
		{
			return 6;
		}
	}

	int Loader_func(String filename,int trace_switch) throws IOException {

		String line;
		String binary=null;
		int Start_address = 0;
		Memory memory = new Memory();
		Memory_util m_util = new Memory_util();
		Error_Handler Er = new Error_Handler();
		Fault_Handlers FH = new Fault_Handlers();
		int id = 0; 
		Disk DISK = new Disk();	

			m_util.Init_fmbv();	

			CPU_util util = new CPU_util();
			new CPU_util().READY_QUEUE[0] = m_util.pcb[id].JOBID;

			
			int Inp_pages = (m_util.pcb[id].Input_seg_size/8)+1;
			int output_pages = (m_util.pcb[id].Output_seg_size/8)+1;
			int prog_pages = (m_util.pcb[id].Prog_seg_size/8)+1;
			int no_of_pages = Page_Allocation(m_util.pcb[id].Prog_seg_size/8);
			m_util.pcb[0].init(no_of_pages,prog_pages,Inp_pages,output_pages);

			/*********Create Program Segment and Page
			**********and Page map Table 		
			********/
			int temp = FH.Prog_Seg_Fault_Handler(0,no_of_pages);			
			m_util.pcb[0].create_pmt(0,prog_pages);
			
			m_util.pcb[0].Program_seg_info = temp;
			int begin = m_util.pcb[id].Start_address/8;
			line = DISK.disk[begin];
			int loc = 0;
			//int load_address = m_util.Free();
			
			int k = 0;
			while(k<no_of_pages)
			{
				m_util.pcb[0].frame_no[k] = m_util.Free();
				k++;
			}
			
			int load_address =  m_util.check_avialable_page(id);
			 
			binary 	=  hexToBinary(line);

			memory.Memory_func("Write",load_address,binary);
			m_util.pcb[0].Page_frames[temp]      =  line;
			m_util.pcb[0].Page_Mem_order[temp].Frame_base_address = load_address; 
			m_util.pcb[0].Page_Mem_order[temp].Page_loc = begin; 
			m_util.pcb[0].Page_Mem_order[temp].old = 0;
			m_util.pcb[0].Program_PMT[begin] = load_address;

		return Start_address;
	}	
}
