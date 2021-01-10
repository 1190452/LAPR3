#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "asm.h"
int main(void) {
  int result;
  char plate[100];
  int intData;
  int arr[11];
  int i =0;
  s1 struc;
  s1* s = &struc;
 
  
  FILE * fPointer1;
  fPointer1 = fopen("zezoca.txt", "r");
  
	
  if (fPointer1 != NULL){
	  fscanf(fPointer1, "%s", plate);
	  while(!feof(fPointer1)){
	      fscanf(fPointer1, "%d", &intData);
	      arr[i] = intData;
	      i++;  
	  }
	  fclose(fPointer1);
  }else{
	  printf("Could not open the file");	
	  
  }
  
  s->parkID = arr[0];
  s->charging_place_potency = arr[1];
  s->ah_battery = arr[2];
  s->maxBattery = arr[3];
  s->actualBattery = arr[4];
  s->year = arr[5];
  s->month = arr[6];
  s->day = arr[7];
  s->hour = arr[8];
  s->minute = arr[9];
  s->second = arr[10];
  
  

  result = estimateTime(s);
  
  FILE *fPointer2;
  char finalStr1[40];
  sprintf(finalStr1, "estimate_%d_%02d_%02d_%02d_%02d_%02d.data", s->year, s->month, s->day, s->hour, s->minute, s->second); //name of the file
  
  
  fPointer2 = fopen(finalStr1, "w");	//writes to the file finalStr1
  
  fprintf(fPointer2, "%d\n", result);
  
  fclose(fPointer2);	//closes the file estimate_[datetime].data
  
  
  FILE *fPointer3;
  
  char finalStr2[40];
  sprintf(finalStr2, "estimate_%d_%02d_%02d_%02d_%02d_%02d.flag", s->year, s->month, s->day, s->hour, s->minute, s->second);
  
  fPointer3 = fopen(finalStr2, "w");	//writes to the file finalStr2
  
  fprintf(fPointer3, "This file exists to confirm that the data file is correct and can be read by other applications!!!");
  
  fclose(fPointer3);	//closes the file estimate_[datetime].flag
  

  return 0;
}
