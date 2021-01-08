create or replace PROCEDURE prcAddScooter( p_maxBattery ElectricScooter.maxBattery%type, p_actualBattery ElectricScooter.actualBattery%type,
                                        p_status ElectricScooter.status%type, p_ahBattery electricscooter.ah_battery%type, p_vBattery electricscooter.v_battery%type,
                                        p_enginePower electricscooter.enginepower%type, p_weight ElectricScooter.weight%type, p_idPharmacy electricscooter.idpharmacy%type)
IS
BEGIN
      
    INSERT INTO ElectricScooter(id, maxbattery, actualBattery, status, ah_Battery, v_Battery, enginePower, weight, idPharmacy ) 
    VALUES(SEQ_ELECTRICSCOOTER.nextval, p_maxbattery, p_actualBattery,p_status, p_ahBattery, p_vBattery, p_enginePower, p_weight, p_idPharmacy);  
    
END;
/


--Teste
declare 
begin 
  prcAddScooter(100,70, 0, 120, 300, 500, 500, 3);  
end;  

select * from electricscooter;