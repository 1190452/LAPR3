CREATE OR REPLACE PROCEDURE updateStatusToParked (p_licensePlate vehicle.licensePlate%type) IS 
BEGIN
    
        UPDATE vehicle SET status = 0 WHERE licensePlate = p_licensePlate; 
    
END;
/

CREATE OR REPLACE PROCEDURE updateStatusToFree (p_licensePlate vehicle.licensePlate%type) IS 
BEGIN
    
        UPDATE vehicle SET status = 1 WHERE licensePlate = p_licensePlate; 
    
END;
/


CREATE OR REPLACE PROCEDURE updateIsChargingY (p_licensePlate vehicle.licensePlate%type) IS 
BEGIN
    
        UPDATE vehicle SET ischarging = 1 WHERE licensePlate = p_licensePlate; 
    
END;
/

CREATE OR REPLACE PROCEDURE updateIsChargingN (p_licensePlate vehicle.licensePlate%type) IS 
BEGIN
    
        UPDATE vehicle SET ischarging = 0 WHERE licensePlate = p_licensePlate; 
    
END;
/