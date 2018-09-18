Author of the source code :
Neelesh Iddipilla

This is the source code for an operating system with the following:

A Multiprogramming batch system with process management.

The above project is designed in 3 phases:
phase 1.A simple batch system.
phase 2.A Simple batch system with memory management
phase 3.A Multiprogramming batch system with memory management.

phase 1.A simple batch system.
-------------------------------

In this phase we disgned a system that supports data types of integer and bit.
The range of integer values can span from -2^13 to (2^13)-1

Instruction fromat will be of two types Zero address(8 bit) and one address(16 bit) instructions.

Program Counter : 7bits wide
Architecture supports a stack of 7 registers each of 16 bits size.

S [ 7 : 0 ] <15 : 0>

ZeroAddress Instruction Format:
 ---------------------------
  7  6  5  4  3  2  1   0
 | T | U  |      OPCODE     |
 ---------------------------
 One address Instruction Format:
 -----------------------------------------------
  5   4  3  2  1  0   9   8  7   6 5 4 3 2 1 0
 | T |    OPCODE     | X  |  U  |     DADDR    |
 -----------------------------------------------
 
where,
T - Instruction Type
U - unused
X - index bit (specifies whether to use offset or not)
OPCODE - Specifices what operation to perform.(The opcodes are defined)
DADDR - Displacement address used according to index bit.

phase 2.A Simple batch system with memory management :
-----------------------------------------------------

Before introducing multiprogramming a memory manager has to be implemented.The multiprogramming system will run under a segmentation and paging memeory management scheme.
Each job consists of a program segment, an optional Input-Data segment and output data segement.

Now A disk similar to memory is introduced in the phase2 and memory is treated as virtual memory.

Number of page frames will depend on initail allocation size.

The maximum number of frames allocated in the memory is 6.And a replacement algorithm has to be called if the required displacement address in not in found in the virtual memory.

The Operating system of phase2 does virtual address to physical address mapping

The replacement algorithm used is a second chance algorithm.

Every job has a process context block(pcb).
The pcb contains the following:
JobID
Program segment info
Input segment info
Output segment info
Page Frame 1
Page Frame 2
Page Frame 3
Page Frame 4


Following are the algorithms implemented:

Input spooling from disk
Loading into memory
Page Fault handler
Replacement Algorithm to bring required data into memory
virtual address to physical address mapping
Output Spooling.Writing the caluclated output to disk.

