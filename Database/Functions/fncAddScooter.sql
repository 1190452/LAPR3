create or replace FUNCTION fncAddScooter( p_maxBattery ElectricScooter.maxBattery%type, p_actualBattery ElectricScooter.actualBattery%type,
                                        p_status ElectricScooter.status%type, p_ahBattery electricscooter.ah_battery%type, p_vBattery electricscooter.v_battery%type,
                                        p_enginePower electricscooter.enginepower%type, p_weight ElectricScooter.weight%type, p_idPharmacy electricscooter.idpharmacy%type) RETURN INTEGER
IS
BEGIN
      
    INSERT INTO ElectricScooter VALUES(SEQ_ELECTRICSCOOTER.nextval, p_maxbattery, p_actualBattery,p_status, p_ahBattery, p_vBattery, p_enginePower, p_weight, p_idPharmacy);  
    
    RETURN SEQ_ELECTRICSCOOTER.currval;
END;
/


--Teste
declare 
v_ret int;
begin 
  v_ret :=  fncAddScooter(100,70, 0, 120, 300, 500, 500, 1);  
end;  

  