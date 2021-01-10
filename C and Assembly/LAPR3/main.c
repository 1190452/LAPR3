#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "asm.h"
int main(void) {
  int result;
  int intData;
  s1 struc;
  s1* s = &struc;
  
  int year = 2020;
  int month = 2;
  int day = 25;
  int hour = 12;
  int minute = 8;
  int second = 36;
  
  FILE * fPointer1;
  fPointer1 = fopen("lock_*.data", "r");
  
	
  if (fPointer1 != NULL){
	  fscanf(fPointer1, "%2d", intData);
	  fclose(fPointer1);
  }else{
	  printf("Could not open the file");	
  
  

  result = estimateTime(&s);
  
  FILE *fPointer2;
  char finalStr1[40];
  sprintf(finalStr1, "estimate_%d_%02d_%02d_%02d_%02d_%02d.data", year, month, day, hour, minute, second);
  
  
  fPointer2 = fopen(finalStr1, "w");	//writes to the file finalStr1
  
  fprintf(fPointer2, result);
  
  fclose(fPointer2);	//closes the file estimate_[datetime].data
  
  
  FILE *fPointer3;
  
  char finalStr2[40];
  sprintf(finalStr2, "estimate_%d_%02d_%02d_%02d_%02d_%02d.flag", year, month, day, hour, minute, second);
  
  fPointer = fopen(finalStr2, "w");	//writes to the file finalStr2
  
  fprintf(fPointer3, "This file exists to confirm that the data file is correct and can be read by other applications!!!");
  
  fclose(fPointer3);	//closes the file estimate_[datetime].flag
  

  return 0;
}
