/*****************************************************************
* Filename = Memory_util.java
* 
*Description : This file contains all required memory utilities 
*		
*
*****************************************************************/
import java.util.*;
import java.io.*;

public class Memory_util{

public static int no_of_jobs = 1;
public static int Memory_Available = 32;

public static int[] fmbv = new int[32]; 
public static int id = 0; 
public static PCB[] pcb = null;


/***Initializes FMBV Vector****/
public void Init_fmbv()
	{
		int i = 0;
	  	while(i<32)
		{
			fmbv[i] = 0;
			i++;
		}
		pcb = PCB.PCBSet(100);
	}
/***Debug Function to display
 FMBV vector*****/
public void display_fmbv()
	{
		int i = 0;
	  while(i<32)
		{
			System.out.println(fmbv[i]);
			i++;
		}
	}

/****This Function returns the immediate 
     available memory Frame******/
	public int Free()
	{
		int loc = 0;
		while(loc !=32)
		{
			if(fmbv[loc] == 0){
				fmbv[loc] =1;
				break;
			}
			loc++;
		}
		return loc;
	}


/***********************************
Check Available Page functions 
checks for available page in memory
************************************/

int check_avialable_page(int id){
	int no_of_pages = pcb[id].no_of_pages;
	int k =0;
	int value = -1;
	while(k<no_of_pages){
	 
	if(pcb[id].frame_no[k] != -1){
		value =pcb[id].frame_no[k];
		pcb[id].frame_no[k] = -1;
		break;
	}
	k++;	
	}
	return value;
}

/***This function is used to print
    Page map table after every 15 
    clock***/
/*
void Display_Mem_Pages(){
	int no_of_pages = pcb[0].no_of_pages;
	
	int Inp_pages = (Input_seg_size/8);
        int output_pages = (Output_seg_size/8);
        int prog_pages = (Prog_seg_size/8);
	int index =0;	
	
	if(Prog_seg_size%8 != 0)
        {
                prog_pages = prog_pages + 1;
        }
        if(Input_seg_size%8 != 0)
        {
                Inp_pages = Inp_pages + 1;
        }
        if(Output_seg_size%8 != 0)
        {
                output_pages = output_pages + 1;
        }
	for(int i =0;i<prog_pages;i++){
		if(pcb[0].Program_PMT[i]>0)
		{
		Page_Frames = Page_Frames+"page : "+i+"   FRAME: "+pcb[0].Program_PMT[i]+"\n";
		System.out.println("page : "+i+"   FRAME: "+pcb[0].Program_PMT[i]+"\n");
		}
	}
	for(int i =0;i<Inp_pages;i++){
		if(pcb[0].Input_seg_info > 0){
		index = (prog_pages+Inp_pages)-1;
                if(pcb[0].Input_PMT[i]>0)
                {
		Page_Frames = Page_Frames+"page : "+index+"   FRAME: "+pcb[0].Input_PMT[i]+"\n";
		System.out.println("page : "+index+"   FRAME: "+pcb[0].Input_PMT[i]+"\n");
                }
        }
}		
	for(int i =0;i<output_pages;i++){
		if(pcb[0].Output_seg_info > 0){
		index = (prog_pages+Inp_pages+output_pages)-1;
                if(pcb[0].Output_PMT[i]>0)
                {
		Page_Frames = Page_Frames+"page : "+index+"   FRAME: "+pcb[0].Output_PMT[i]+"\n";
		System.out.println("page : "+index+"   FRAME: "+pcb[0].Output_PMT[i]+"\n");
                }
        }
}
	Page_Frames = Page_Frames+"=========================="+"\n";
	
}
*/
void Display_Mem_Pages(){
        int no_of_pages = pcb[0].no_of_pages;

        for(int i =0;i<no_of_pages;i++){
        if(pcb[0].Page_Mem_order[i].Page_loc >= 0){
  System.out.println("Page: "+pcb[0].Page_Mem_order[i].Page_loc+"  Frame-->"+pcb[0].Page_Mem_order[i].Frame_base_address);

        }
        }
        System.out.println("======================");
}

}
