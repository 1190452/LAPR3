.section .text
	.global estimateTime

estimateTime:

#prologue
pushl %ebp # save previous stack frame pointer
movl %esp, %ebp # the stack frame pointer for our function

pushl %esi
pushl %edi
pushl %ebx

#Body of the function
movl 8(%ebp), %ebx		# pointer to struct
addl $4, %ebx			# %ebx now points to charging_place_potency
movl (%ebx), %eax		# %eax has the value of the charging place potency
addl $4, %ebx			#%ebx now points to ocupied_charging_places
movl (%ebx), %esi		#%esi has the value of the ocupied charging places
movl 12(%ebp), %ecx		# ah_battery

cdq			#extends the eax signal
idivl %esi		#eax/esi, charging_place_potency/ocupied_charging_places, result in eax

movl %eax, %edi		#moves charging_place_potency/ocupied_charging_places to %edi
movl %ecx, %eax		#moves ecx (which has the ah_battery) to eax to do the division

cdq			#extends the eax signal
idivl %edi		#eax/edi, ah_battery/(charging_place_potency/ocupied_charging_places) to calculate the time to fully charge a 0% battery


movl $100, %ecx		#maxBattery
movl 20(%ebp), %edx	#actualBattery

subl %edx, %ecx		#subtracts the actual battery to the max battery (ex: 100-70=30) we need to know how much time it will take to charge the missing 30%
			#in the example,now ecx has the value 30

imull %ecx, %eax	#multiplies the estimate time for the (maxBattery - actualBattery), result in %eax

movl $100, %ecx		#moves once again 100 to ecx

cdq			#extends eax signal

divl %ecx		#division between eax and ecx (ecx being the maxBattery) 


# epilogue
popl %ebx
popl %edi
popl %esi
movl %ebp, %esp # restore the previous stack pointer ("clear" the stack)
pop %ebp #restore the previous stack frame pointer

ret

