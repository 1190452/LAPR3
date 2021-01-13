create or replace PROCEDURE prcAddScooter(p_licensePlate Vehicle.licensePlate%type, p_maxBattery Vehicle.maxBattery%type, p_actualBattery Vehicle.actualBattery%type,
                                        p_status Vehicle.status%type, p_ahBattery Vehicle.ah_battery%type, p_vBattery Vehicle.v_battery%type,
                                        p_enginePower Vehicle.enginepower%type, p_weight Vehicle.weight%type,
                                        p_idPharmacy Vehicle.idpharmacy%type, p_idTypeVehicle vehicle.idtypevehicle%type)
IS
BEGIN
      
    INSERT INTO Vehicle(id,licensePlate, maxbattery, actualBattery, status, ah_Battery, v_Battery, enginePower, weight, idPharmacy, idTypeVehicle ) 
    VALUES(seq_vehicle.nextval, p_licensePlate, p_maxbattery, p_actualBattery,p_status, p_ahBattery, p_vBattery, p_enginePower, p_weight, p_idPharmacy, p_idTypeVehicle); 
    
    UPDATE park SET actualcapacity = actualcapacity - 1 WHERE idpharmacy = p_idPharmacy AND idtypepark = 1 ;
    
END;
/

create or replace PROCEDURE prcAddDrone(p_licensePlate Vehicle.licensePlate%type, p_maxBattery Vehicle.maxBattery%type, p_actualBattery Vehicle.actualBattery%type,
                                        p_status Vehicle.status%type, p_ahBattery Vehicle.ah_battery%type, p_vBattery Vehicle.v_battery%type,
                                        p_enginePower Vehicle.enginepower%type, p_weight Vehicle.weight%type,
                                        p_maxWeightCapacity vehicle.maxWeightCapacity%type,
                                        p_idPharmacy Vehicle.idpharmacy%type, p_idTypeVehicle vehicle.idtypevehicle%type)
IS
BEGIN
      
    INSERT INTO Vehicle(id,licensePlate, maxbattery, actualBattery, status, ah_Battery, v_Battery, enginePower, weight,maxWeightCapacity, idPharmacy, idTypeVehicle ) 
    VALUES(seq_vehicle.nextval, p_licensePlate, p_maxbattery, p_actualBattery,p_status, p_ahBattery, p_vBattery, p_enginePower, p_weight,p_maxWeightCapacity, p_idPharmacy, p_idTypeVehicle); 
    
    UPDATE park SET actualcapacity = actualcapacity - 1 WHERE idpharmacy = p_idPharmacy AND idtypepark = 2 ;
    
END;
/
