#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <glob.h>
#include <unistd.h>
#include "asm.h"
int main(void) {
  char *configFile;
  char *lockFile; 
  char *flagFile;
  int arr[10];
 

	glob_t flag;
    int retval3;
    
    flag.gl_pathc = 0;
    flag.gl_pathv = NULL;
    flag.gl_offs = 0;

    retval3 = glob( "configurable.txt", GLOB_NOCHECK | GLOB_NOSORT,
                   NULL, &flag );
    if( retval3 == 0 ) {
        int idx;
        
        for( idx = 0; idx < flag.gl_pathc; idx++ ) {
			if(flag.gl_pathv[idx] != 0){
				configFile =  flag.gl_pathv[idx];
				break;
			}	       
        }  
    } else {
        puts( "glob() failed" );
	  }
	  
	FILE * configPointer;
	configPointer = fopen(configFile, "r"); //pointer to configurable.txt
	int configLine;
	int j = 0;
	int arrConfig[100];
	int count = 0;

	if (configPointer != NULL){
		  do{		//while pointer != null
			  fscanf(configPointer, "%d", &configLine);	// value read by the file
			  count++;
			  arrConfig[j] = configLine;
			  j++;  
		  }while(!feof(configPointer)); 
	}
	
	int k=0;
	park_charger* arrayPtr;
	arrayPtr = (park_charger*) malloc(count/3 * sizeof(park_charger));

	do{
		arrayPtr->parkID = arrConfig[k];
		arrayPtr->charging_place_potency = arrConfig[k + 1]; 
		arrayPtr->ocupied_charging_places = arrConfig[k + 2];
		k = k+3;
		if(k < count){
			arrayPtr++;
		}else{
			break;
		}	  
	}while(k <= count && arrConfig[k] != 0);
	

   while (access(flagFile, F_OK) != 0){ //while file does not exist
	 printf("Sleeping for 10 seconds...\n"); 
	 sleep(3);
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
	  int i =0;
	  int intData;
	  FILE * fPointer1;
	  fPointer1 = fopen(lockFile, "r"); //pointer to lock*.data
	  
	  
	  if (fPointer1 != NULL){
		  while(!feof(fPointer1)){		//while pointer != null
			  fscanf(fPointer1, "%d", &intData);	// value read by the file
			  arr[i] = intData;
			  i++;  
		  }
		  
		  
		  int id = arr[0];
		  int ah_battery = arr[1];
		  int max_battery = arr[2];
		  int actual_battery = arr[3];
		  int year = arr[4];
		  int month = arr[5];
		  int day = arr[6];
		  int hour = arr[7];
		  int minute = arr[8];
		  int second = arr[9];
		  
		  int result;
		  fclose(fPointer1);
		  printf("Zé\n");
		  
		  while(arrayPtr != NULL){
			  if(arrayPtr->parkID == id){
				 result = estimateTime(arrayPtr, ah_battery, max_battery, actual_battery); 
			  }else{
				arrayPtr--;  
			  }
		  }

	 
	  
	  FILE *fPointer2;
	  char finalStr1[40];
	  sprintf(finalStr1, "estimate_%d_%02d_%02d_%02d_%02d_%02d.data", year, month, day, hour, minute, second); //name of the file
	  
	  
	  fPointer2 = fopen(finalStr1, "w");	//writes to the file finalStr1
	  
	  fprintf(fPointer2, "%d\n", result);
	  
	  fclose(fPointer2);	//closes the file estimate_[datetime].data
	  
	  
	  FILE *fPointer3;
	  
	  char finalStr2[40];
	  sprintf(finalStr2, "estimate_%d_%02d_%02d_%02d_%02d_%02d.flag", year, month, day, hour, minute, second);
	  
	  fPointer3 = fopen(finalStr2, "w");	//writes to the file finalStr2
	  
	  fprintf(fPointer3, "This file exists to confirm that the data file is correct and can be read by other applications!!!");
	  
	  fclose(fPointer3);	//closes the file estimate_[datetime].flag
	  
	  
	  printf("The files were created with success!!\n\n");
	  
  
  
	globfree( &paths );

  }
	
	return 0;
}
	  





