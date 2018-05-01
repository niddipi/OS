import java.util.ArrayList;
public class Scheduler{

 public static	ArrayList<Integer> READY_QUEUE = new ArrayList<Integer>();
 public static int   RUNNING_QUEUE = -1;
 public static	ArrayList<Integer> BLOCKED_QUEUE = new ArrayList<Integer>();

CPU_util util = new CPU_util();
Stack_operations SO = new Stack_operations();
Memory_util m_util = new Memory_util();



 public int schedule_nextjob(){
	if(util.id == 4)
	{
		System.out.println("util.id :"+util.id+"util.schedule_type :"+util.schedule_type);
	}
	int value = 0;
	if(RUNNING_QUEUE == -1 && util.schedule_type == -1)
	{
		int i =0;
		int no_of_jobs = m_util.id;
		m_util.id_prev = m_util.id;
		
		while(i<no_of_jobs){
		     if(m_util.pcb[i].valid_pcb != -1){					
	             READY_QUEUE.add(i);
			}
		     i++;
		}
		RUNNING_QUEUE = READY_QUEUE.get(0);
		READY_QUEUE.remove(0);
	}
	else if(util.schedule_type == 0){

		if(READY_QUEUE.size()>0){

			BLOCKED_QUEUE.add(RUNNING_QUEUE);
			RUNNING_QUEUE = READY_QUEUE.get(0);
			READY_QUEUE.remove(0);
			//Save_pcb();
		}
			Save_pcb();
	} 
	else if(util.schedule_type == 1)
	{
		int val;
		if(BLOCKED_QUEUE.size() > 0){

			val = BLOCKED_QUEUE.get(0);
			BLOCKED_QUEUE.remove(0);
			READY_QUEUE.add(val);
		}
		if(READY_QUEUE.size()>0){
			READY_QUEUE.add(RUNNING_QUEUE);
			RUNNING_QUEUE = READY_QUEUE.get(0);
			READY_QUEUE.remove(0);
			//Save_pcb();
		}
			Save_pcb();
	}
	else if(util.schedule_type == 2)
	{
		if(m_util.id != m_util.id_prev){
		m_util.id_prev = m_util.id;
		READY_QUEUE.add(m_util.id-1);
		}
		int val = 0;
		if(BLOCKED_QUEUE.size() > 0){
			val = BLOCKED_QUEUE.get(0);
			BLOCKED_QUEUE.remove(0);
			READY_QUEUE.add(val);
		}
		if(READY_QUEUE.size()>0){
			RUNNING_QUEUE = READY_QUEUE.get(0);
			READY_QUEUE.remove(0);
		}
		else
		{
			value = -1;
		}
	}
	util.id = RUNNING_QUEUE;
	if(value != -1){
		value = RUNNING_QUEUE;
	}
	Resume_pcb();
	
	
	return value;
  }
 
  public void Resume_pcb(){ 
    int id = util.id;
    int TOS = 6;
    SO.TOS  = m_util.pcb[id].TOS;
    util.PC = m_util.pcb[id].PC;
    util.EA = m_util.pcb[id].EA;
    util.EA_Prev = m_util.pcb[id].EA_Prev;
    util.TOS_Prev = m_util.pcb[id].TOS_Prev;
    if(util.TOS_Prev >=0){
    util.TOS_Val_Prev = m_util.pcb[id].TOS_Val_Prev;
	}
    while(TOS >=0)
    {
           SO.Stack[TOS] = m_util.pcb[id].Stack[TOS];
           TOS--;
    }

  } 
 
  public void Save_pcb(){

    int id = util.id;
    int TOS = 6;
    if(id == 4)
	{
		System.out.println("SO.TOS save:"+SO.TOS);
	}
    m_util.pcb[id].TOS = SO.TOS;
    m_util.pcb[id].TOS_Prev = util.TOS_Prev;
    if(util.TOS_Prev >=0){
    m_util.pcb[id].TOS_Val_Prev = util.TOS_Val_Prev;
    
	}

    SO.TOS = -1;
    util.TOS_Prev = -1;
    m_util.pcb[id].PC  = util.PC;
    util.PC = -1;
    m_util.pcb[id].EA  = util.EA;
    m_util.pcb[id].EA_Prev  = util.EA_Prev;
   util.EA =-1;
    while(TOS >=0)
    {
	System.out.printf("%d Stackvalueeeee of %d:%d\n",id,TOS,SO.Stack[TOS]);
           m_util.pcb[id].Stack[TOS] = SO.Stack[TOS]; 
	   SO.Stack[TOS] = 0;
           TOS--;
    }	
 }
}
