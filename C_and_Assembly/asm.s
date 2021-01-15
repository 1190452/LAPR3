.section .text
	.global estimateTime

estimateTime:

#prologue
pushl %ebp # save previous stack frame pointer
movl %esp, %ebp # the stack frame pointer for our function

#pushl %esi
#pushl %edi
pushl %ebx

#Body of the function
movl 8(%ebp), %ebx		# pointer to struct
addl $4, %ebx			# %ebx now points to charging_place_potency
movl (%ebx), %ecx			# %ecx has the value stored in %ebx
movl 12(%ebp), %eax		# ah_battery

cdq		#extende o sinal para %edx
idivl %ecx	#realiza a divisão sendo que o resultado fica em %eax

imull $3600, %eax	#multiplica o valor em %eax por 3600 para obtermos o tempo de carregamento em segundos

movl 16(%ebp), %ecx		#maxBattery
movl 20(%ebp), %edx		#actualBattery

subl %edx, %ecx		#subtrai a actual battery à max battery (ex: 100-70=30), ou seja, precisamos de saber quanto tempo demora a carregar os 30%
			#neste exemplo %ecx tem o valor 30
imull %ecx, %eax	#multiplica a percentagem pelo número de segundos

movl 16(%ebp), %ecx	#move outra vez maxBateery para ecx, uma vez que o valor já foi alterado

cdq			#extende o sinal para %edx
idivl %ecx	#divide pelo valor em %ecx (maxBattery) e o resultado fica em %eax


# epilogue
popl %ebx
movl %ebp, %esp # restore the previous stack pointer ("clear" the stack)
pop %ebp #restore the previous stack frame pointer

ret

