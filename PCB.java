/*****************************************************************
* Filename   :  PCB.java
* 
*Description :  This file is responsible for 
*		execution of all the PCB related operations and 
*               data
*
*****************************************************************/
public class PCB{

	int JOBID = -1;
	int int_jobid = -1;
	int Program_seg_info = -1;
	int Input_seg_info =-1;
	int Output_seg_info = -1;
	int no_of_pages = 0;
	int PC =0;
	int[] frame_no;
	int[] Program_PMT;
	int[] Input_PMT;
	int[] Output_PMT;	

	Page[] Page_Mem_order;

	int Start_address = -1;

	public int  Prog_seg_size = -1;
	public int  Input_seg_size = -1;
	public int  Output_seg_size = -1;
	public int  Trace_page = 0;
	public String Page_Frames = "";


	public int EA=0;
	public int EA_Prev=0;

	int trace_flag = 0;
	double TOTAL_DISK_USAGE  = 0;	
	double DISK_PAGE_COUNT   = 0;	
	static String INPUT = "";
	static String OUTPUT = null;
	double DISK_FRAG  	= 0;	
	int output_start_address = 0;	
	int input_start_address  = 0;	
	
/*	
	public double MEM_utilword_ratio = 0.0;
	public  double MEM_utilword_percent = 0.0;
	public  double MEM_utilframe_ratio = 0.0;
	public  double MEM_utilframe_percent = 0.0;
	public  double MEM_frag = 0.0;
*/	
	String[] Page_frames;

	/***Returns a Static PCB****/
	public static PCB[] PCBSet(int size) {
        PCB[] p= new PCB[size];
        for(int i=0; i<size; i++)
            p[i] = new PCB();
        return p;
    	}

	/**Initializes the variables present in PCB***/
	public void init(int no_pages,int Prog_pages,int Inp_pages,int output_pages){

		frame_no = new int[no_pages];
		no_of_pages = no_pages;
		Page_frames = new String[no_pages];
		Page_Mem_order = Page.PageSet(no_pages);
	}

	/**Creates PAge map table for each segment**/
	public void create_pmt(int code,int no_pages)
	{
		if(code == 0)
		{
			Program_PMT = new int[no_pages];
			
		}
		else if(code ==1)
		{
			Input_PMT   = new int[no_pages];
		}
		else
		{
			Output_PMT  = new int[no_pages];	
		}
	}
}