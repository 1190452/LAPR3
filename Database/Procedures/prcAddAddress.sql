CREATE OR REPLACE procedure prcAddAddress( p_latitude address.latitude%type, 
                                    p_longitude address.longitude%type,
                                    p_altitude address.altitude%type,
                                    p_street address.street%type, p_doornumber address.doornumber%type,
                                    p_zipcode address.zipcode%type, p_locality address.locality%type) IS 

BEGIN
    
    INSERT INTO Address(latitude,longitude, altitude,street,doornumber,zipcode,locality) 
    VALUES (p_latitude, p_longitude,p_altitude,p_street, p_doornumber,p_zipcode,p_locality);
   
END;
/
