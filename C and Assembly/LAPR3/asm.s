.section .text
	.global fill_s1
fill_s1:

#prologue
pushl %ebp # save previous stack frame pointer
movl %esp, %ebp # the stack frame pointer for sum function

#pushl %esi
#pushl %edi
#pushl %ebx

#Body of the function
movl 16(%ebp), %ecx	#charging place potency
movl 20(%ebp), %eax	#ah_battery

cdq		#extende o sinal para %edx
idivl 	#realiza a divisão sendo que o resultado fica em %eax

imull $3600, %eax	#multiplica o valor em %eax por 3600 para obtermos o tempo de carregamento em segundos

movl 24(%ebp), %ecx		#maxBattery
movl 28(%ebp), %edx		#actualBattery

subl $100, %edx			#subtrai 100 à bateria atual (ex: 100-70=30), ou seja, precisamos de saber quanto tempo demora a carregar os 30%

imull %edx, %eax		#multiplica a percentagem pelo número de segundos

cdq			#extende o sinal para %edx
idivl %ecx	#divide pelo valor em %ecx (maxBattery) e o resultado fica em %eax


# epilogue
#popl %ebx
#popl %edi
#popl %esi
movl %ebp, %esp # restore the previous stack pointer ("clear" the stack)
pop %ebp #restore the previous stack frame pointer

ret

