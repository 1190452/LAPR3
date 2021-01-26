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
	char *initFile;
	
	int arr[11];
	
	glob_t initConfig;
	int retval4;
    
		initConfig.gl_pathc = 0;
		initConfig.gl_pathv = NULL;
		initConfig.gl_offs = 0;

		retval4 = glob("configurable.txt", GLOB_NOCHECK | GLOB_NOSORT,
                   NULL, &initConfig );
		if( retval4 == 0 ) {
			int idx;
        
			for( idx = 0; idx < initConfig.gl_pathc; idx++ ) {
				if(initConfig.gl_pathv[idx] != 0){
					initFile =  initConfig.gl_pathv[idx];
					break;
				}	       
			}  
		}else{
			puts( "glob() failed" );
		}
	
	
	int n = 0;
	int initLine;
	int initCounter = 0;
	int initArray[15];	
	FILE * initPointer;
	initPointer = fopen(initFile, "r"); //pointer to configurable.txt
	
	if (initPointer != NULL){
		  while(!feof(initPointer)){		//while pointer != null
			  fscanf(initPointer, "%d", &initLine);	// value read by the file
			  initCounter++;
			  initArray[n] = initLine;
			  n++;  
		  } 
	}
	
	park_charger *arrayPtr;
	arrayPtr =( park_charger *) malloc (initCounter/3 * sizeof ( park_charger ));	
	
	
	
//do...while(1) so that the application never ends 
do{	
	glob_t configs;
	glob_t flag;
	glob_t paths;
	
	
//---------------------------------------------------------------------------------------------------  
				
		
	while (access(flagFile, F_OK) != 0){ //while file does not exist
	 printf("Waiting for FLAG...\n"); 
	 sleep(3);
	 
	
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
	


//--------------------------------------------------------------------------------//	
	
//repeat the reading of a configurable file to see if there are any changes
	do{
		printf("Waiting for a configurable file to fill the data about the park chargers\n\n");
		sleep(1);
		
		int retval3;
    
		configs.gl_pathc = 0;
		configs.gl_pathv = NULL;
		configs.gl_offs = 0;

		retval3 = glob( "configurable.txt", GLOB_NOCHECK | GLOB_NOSORT,
                   NULL, &configs );
		if( retval3 == 0 ) {
			int idx;
        
			for( idx = 0; idx < configs.gl_pathc; idx++ ) {
				if(configs.gl_pathv[idx] != 0){
					configFile =  configs.gl_pathv[idx];
					break;
				}	       
			}  
		}else{
			puts( "glob() failed" );
		} 
	 }while(access(configFile, F_OK) != 0); 
	 
	 
	 
 
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
	
	park_charger *ptrvec;
	if(count > initCounter){
		ptrvec = (park_charger *) realloc (arrayPtr, (count/3) * sizeof(park_charger));
		initCounter = count;	
	}else if (count == initCounter){
		ptrvec = (park_charger *) malloc (initCounter/3 * sizeof(park_charger));
	}
	
	
	int k=0;

	do{
		ptrvec->parkID = arrConfig[k];
		ptrvec->charging_place_potency = arrConfig[k + 1]; 
		ptrvec->ocupied_charging_places = arrConfig[k + 2];
		k = k+3;
		if(k < count){
			ptrvec++;
		}else{
			break;
		}	  
	}while(k <= count && arrConfig[k] != 0);
 
  
  
  
//----------------------------Search for the data file in the directory--------------------------------//
		
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
		  int busy_chargers = arr[10];
		  
		  int result;
		  fclose(fPointer1);
		  
		  while(ptrvec != NULL){
			  if(ptrvec->parkID == id){
				  ptrvec->ocupied_charging_places = busy_chargers;
				 result = estimateTime(ptrvec, ah_battery, max_battery, actual_battery); 
				 break;
			  }else{
				ptrvec--;  
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
	  sprintf(finalStr2, "estimate_%d_%02d_%02d_%02d_%02d_%02d.data.flag", year, month, day, hour, minute, second);
	  
	  fPointer3 = fopen(finalStr2, "w");	//writes to the file finalStr2
	  
	  fprintf(fPointer3, "This file exists to confirm that the data file is correct and can be read by other applications!!!");
	  
	  fclose(fPointer3);	//closes the file estimate_[datetime].flag
	  
	  
	  printf("The files were created with success!!\n\n");
  
	
	

   }
   
   }while(1);
	
	return 0;
}
	  





