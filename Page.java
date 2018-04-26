/*****************************************************************
* Filename   : Page.java
* 
*Description : This file is responsible for 
*		execution of all the Page data
*
*****************************************************************/
public class Page{

	int Frame_base_address = 0;
	int Dirty_bit = 0;
	int Ref_bit = 0;
	int no_of_pages= 0;
	int Page_loc= -1;
	int old = 0;
	int word_count = 0;

 public static Page[] PageSet(int size) {

        Page[] p= new Page[size];
        for(int i=0; i<size; i++)
            p[i] = new Page();
        return p;

    }
} 
