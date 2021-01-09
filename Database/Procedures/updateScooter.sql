CREATE OR REPLACE PROCEDURE updateStatusToParked (p_licensePlate electricscooter.licensePlate%type) IS 
BEGIN
    
        UPDATE electricscooter SET status = 0 WHERE licensePlate = p_licensePlate; 
    
END;
/

CREATE OR REPLACE PROCEDURE updateStatusToDelivery (p_licensePlate electricscooter.licensePlate%type) IS 
BEGIN
    
        UPDATE electricscooter SET status = 1 WHERE licensePlate = p_licensePlate; 
    
END;
/


CREATE OR REPLACE PROCEDURE updateIsChargingY (p_licensePlate electricscooter.licensePlate%type) IS 
BEGIN
    
        UPDATE electricscooter SET ischarging = 1 WHERE licensePlate = p_licensePlate; 
    
END;
/

CREATE OR REPLACE PROCEDURE updateIsChargingN (p_licensePlate electricscooter.licensePlate%type) IS 
BEGIN
    
        UPDATE electricscooter SET ischarging = 0 WHERE licensePlate = p_licensePlate; 
    
END;
/