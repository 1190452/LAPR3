#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <glob.h>
#include <unistd.h>
#include "asm.h"
int main(void) {
  int result;
  char plate[100];
  int intData;
  int arr[11];
  int i =0;
  char *lockFile; 
  char *flagFile;
  s1 struc;
  s1* s = &struc;
  
  
  
  
//------------------------------Search for the flag file in the directory--------------------------------//
  glob_t flag;
    int retval1;
    
    flag.gl_pathc = 0;
    flag.gl_pathv = NULL;
    flag.gl_offs = 0;

    retval1 = glob( "lock*.flag", GLOB_NOCHECK | GLOB_NOSORT,
                   NULL, &flag );
    if( retval1 == 0 ) {
        int idx;
        
        for( idx = 0; idx < flag.gl_pathc; idx++ ) {
			if(flag.gl_pathv[idx] != 0){
				flagFile =  flag.gl_pathv[idx];
				break;
			}
			       
        }
        
    } else {
        puts( "glob() failed" );
		   }
		   
		   
		  
  
   if(access(flagFile, F_OK) != 0){ //while file does not exist
	 printf("Sleeping for 10 seconds...\n"); 
	 sleep(10);
	 main();   
  }
  
  
  
//----------------------------Search for the data file in the directory--------------------------------//
  glob_t paths;
    int retval;
    
    paths.gl_pathc = 0;
    paths.gl_pathv = NULL;
    paths.gl_offs = 0;

    retval = glob( "lock*.data", GLOB_NOCHECK | GLOB_NOSORT,
                   NULL, &paths );
    if( retval == 0 ) {
        int idx;
        
        for( idx = 0; idx < paths.gl_pathc; idx++ ) {
			if(paths.gl_pathv[idx] != 0){
				lockFile =  paths.gl_pathv[idx];
				break;
			}
			       
        }
        
    } else {
        puts( "glob() failed" );
    }
    
//---------------------------------------------------------------------------------------------------//
 
  FILE * fPointer1;
  fPointer1 = fopen(lockFile, "r");
  
	
  if (fPointer1 != NULL){
	  fscanf(fPointer1, "%s", plate);
	  while(!feof(fPointer1)){
	      fscanf(fPointer1, "%d", &intData);
	      arr[i] = intData;
	      i++;  
	  }
	  fclose(fPointer1);
	  
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
  
  
  printf("The files were created with success!!\n\n");
	  
	  
	  
  }else{
	  printf("\n\nCould not open the file!\nMake sure the file exists and/or is in the right directory!\n\n");	
	  
  }
  
  
  globfree( &paths );

  return 0;
}


