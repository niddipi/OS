/*********************************************
* Filename = Arithmetic_and_LogicalOP.java.java
* 
*Description : This file is responsible for 
*		executing All Arithmetic and
*		Logical Operations
* 		
*
*
********************************************/
public class Arithmetic_and_LogicalOP{

    int OR(int A , int B){

	return A | B;
    }	

    int AND(int A , int B){

	return A & B;
    }


    int NOT(int A){

	return ~A;
    }

    int XOR(int A , int B){
	return A ^ B;
    }

    int ADD(int A , int B){

	return A + B;
    }


    int SUB(int A , int B){

	return A - B;
    }

    int MUL(int A , int B){

	return A * B;
    }


    int DIV(int A , int B){

	return A / B;
    }

    int MOD(int A , int B){

	return A % B;
    }

    int SL(int A){

	return A << 1;
    }

    int SR(int A){

	return A >> 1;
    }


    int CPG(int A , int B){

	if( A > B)
	{
	    return 1;
	}
	else
	{
	    return 0;
	}
    }

    int CPL(int A , int B){

	if(A < B)
	{
	    return 1;
	}
	else
	{
	    return 0;
	}
    }


    int CPE(int A , int B){

	if( A == B)
	{
	    return 1;
	}
	else
	{
	    return 0;
	}
    }
}
	
