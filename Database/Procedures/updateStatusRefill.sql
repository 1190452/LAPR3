CREATE OR REPLACE PROCEDURE updateStatusRefill (p_id refillstock.id%type) IS 
v_courier refillstock.idcourier%type;
v_energy refillstock.necessaryEnergy%type;
v_licensePlate refillstock.licensePlateVehicle%type;
BEGIN
    SELECT idcourier, necessaryEnergy,licensePlateVehicle INTO v_courier, v_energy, v_licensePlate
    FROM refillstock WHERE id = p_id;
    
    UPDATE courier SET status = 0 WHERE id = v_courier;
    
    UPDATE refillstock SET status = 1 WHERE id = p_id;
    
    UPDATE vehicle SET actualBattery = actualBattery - v_energy WHERE licensePlate = v_licensePlate;
    
    FOR c1 IN (SELECT p.name, ro.productQuantity, ro.idPharmReceiver, ro.idPharmSender 
                FROM RestockOrder ro INNER JOIN refillstock rs ON ro.idrefillstock = p_id 
                INNER JOIN Product p ON p.id = ro.idProduct)LOOP
    
         UPDATE product SET stock = stock + c1.productQuantity WHERE name = lower(c1.name) AND idPharmacy = c1.idPharmReceiver;
    
         UPDATE product SET stock = stock - c1.productQuantity WHERE name = lower(c1.name) AND idPharmacy = c1.idPharmSender; 
    
    END LOOP;
    
END;
/

