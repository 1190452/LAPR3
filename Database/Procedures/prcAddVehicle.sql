create or replace PROCEDURE prcAddVehicle(p_licensePlate Vehicle.licensePlate%type, p_maxBattery Vehicle.maxBattery%type,
                                        p_ahBattery Vehicle.ah_battery%type, p_vBattery Vehicle.v_battery%type,
                                        p_enginePower Vehicle.enginepower%type,p_weight Vehicle.weight%type,p_frontalArea vehicle.frontalarea%type,
                                        p_idPharmacy Vehicle.idpharmacy%type, p_idTypeVehicle vehicle.idtypevehicle%type)
IS
BEGIN

    IF p_idtypevehicle = 1 THEN

        INSERT INTO Vehicle(id,licensePlate, maxbattery, ah_Battery, v_Battery, enginePower, weight,frontalArea, idPharmacy, idTypeVehicle ) 
        VALUES(seq_vehicle.nextval, p_licensePlate, p_maxbattery, p_ahBattery, p_vBattery, p_enginePower,p_weight,p_frontalarea, p_idPharmacy, p_idTypeVehicle); 

        UPDATE park SET actualcapacity = actualcapacity - 1 WHERE idpharmacy = p_idPharmacy AND idtypepark = 1 ;

    ELSE
        INSERT INTO Vehicle(id,licensePlate, maxbattery, ah_Battery, v_Battery, enginePower, weight,maxWeightCapacity,frontalArea, idPharmacy, idTypeVehicle ) 
        VALUES(seq_vehicle.nextval, p_licensePlate, p_maxbattery, p_ahBattery, p_vBattery, p_enginePower, p_weight,10,p_frontalarea, p_idPharmacy, p_idTypeVehicle); 

        UPDATE park SET actualcapacity = actualcapacity - 1 WHERE idpharmacy = p_idPharmacy AND idtypepark = 2 ;

    END IF;

END;
/

