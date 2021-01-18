CREATE OR REPLACE PROCEDURE updateStatusDelivery (p_id delivery.id%type) IS 
v_courier delivery.idcourier%type;
v_energy delivery.necessaryEnergy%type;
v_licensePlate delivery.licensePlateVehicle%type;
BEGIN
    SELECT idcourier, necessaryEnergy,licensePlateVehicle INTO v_courier, v_energy, v_licensePlate
    FROM delivery WHERE id = p_id;
    
    UPDATE courier SET status = 0 WHERE id = v_courier;
    
    UPDATE delivery SET status = 1 WHERE id = p_id;
    
    UPDATE vehicle SET actualBattery = actualBattery - v_energy WHERE licensePlate = v_licensePlate;
    
END;
/
