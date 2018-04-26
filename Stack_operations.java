/**************************************************************************
* Filename = Stack_operations.java
* 
*Description : This file is responsible for execution of all the Stack
* 	 	related operations
*
**************************************************************************/
import java.io.*;

public class Stack_operations{
	
	public static int[] Stack = new int[7];
	public static int TOS = -1;
	public Error_Handler Er = new Error_Handler();
	public CPU_util util = new CPU_util();
	
	int PUSH(int data) throws IOException
	{
		if (TOS >= 6)
		{
			Er.Error_Handler_func("STACK_OVERFLOW");
			return 1;
		}
		else if(TOS < 6)
		{
			TOS++;
		}
		Stack[TOS] = data;
		return 0;
	}

	int POP() throws IOException
	{
		int value = 0;

		 if (TOS < 0)
                {
			Er.Error_Handler_func("STACK_UNDERFLOW");
                        return 1;
                }
		
		value = Stack[TOS];
		TOS--;
		return  value;
		
	}
}
