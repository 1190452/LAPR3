.section .text
	.global fill_s1
fill_s1:

#prologue
pushl %ebp # save previous stack frame pointer
movl %esp, %ebp # the stack frame pointer for sum function
pushl %esi
pushl %edi
pushl %ebx

#Body of the function
movl 8(%ebp), %esi	#apontador para o início da structure
movl 12(%ebp), %edi	#vi
movb 16(%ebp), %bl	#vc
movl 20(%ebp), %ecx	#vj
movb 24(%ebp), %bh	#vd

movl %edi, (%esi)
addl $4, %esi

movb %bl, (%esi)
addl $4, %esi

movl %ecx, (%esi)
addl $4, %esi

movb %bh, (%esi)


# epilogue
popl %ebx
popl %edi
popl %esi
movl %ebp, %esp # restore the previous stack pointer ("clear" the stack)
pop %ebp #restore the previous stack frame pointer

ret
