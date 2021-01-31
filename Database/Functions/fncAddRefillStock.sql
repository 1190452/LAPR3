
create or replace function fncAddRefill( p_necessaryEnergy RefillStock.necessaryenergy%type,
                                            p_distance RefillStock.distance%type,
                                            p_weight RefillStock.weight%type,p_licensePlate RefillStock.licensePlateVehicle%type,  
                                            p_idcourier RefillStock.idcourier%type)RETURN INTEGER IS 

BEGIN

IF p_idcourier = 0 THEN
    INSERT INTO RefillStock(id, necessaryEnergy, distance, weight,licensePlateVehicle)
    VALUES (seq_refillstock.nextval, p_necessaryEnergy, p_distance, p_weight,p_licensePlate);
ELSE
    INSERT INTO RefillStock(id, necessaryEnergy, distance, weight,licensePlateVehicle, idcourier)
    VALUES (seq_refillstock.nextval, p_necessaryEnergy, p_distance, p_weight,p_licensePlate, p_idcourier);
END IF;
    RETURN seq_refillstock.currval;
    
END;
/
