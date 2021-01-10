create or replace PROCEDURE prcAddScooter(p_licensePlate electricscooter.licensePlate%type, p_maxBattery ElectricScooter.maxBattery%type, p_actualBattery ElectricScooter.actualBattery%type,
                                        p_status ElectricScooter.status%type, p_ahBattery electricscooter.ah_battery%type, p_vBattery electricscooter.v_battery%type,
                                        p_enginePower electricscooter.enginepower%type, p_weight ElectricScooter.weight%type, p_idPharmacy electricscooter.idpharmacy%type)
IS
BEGIN
      
    INSERT INTO ElectricScooter(licensePlate, maxbattery, actualBattery, status, ah_Battery, v_Battery, enginePower, weight, idPharmacy ) 
    VALUES(p_licensePlate, p_maxbattery, p_actualBattery,p_status, p_ahBattery, p_vBattery, p_enginePower, p_weight, p_idPharmacy);  
    
    UPDATE park SET actualcapacity = actualcapacity - 1 WHERE idpharmacy = p_idPharmacy;
    
END;
/


--Teste
declare 
begin 
  prcAddScooter('AA-BB-12',100,70, 0, 120, 300, 500, 500, 1);  
end;  

select * from electricscooter;