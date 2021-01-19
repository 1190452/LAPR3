create or replace function fncAddRefillByDrone( p_necessaryEnergy RefillStock.necessaryenergy%type,
                                            p_distance RefillStock.distance%type,
                                            p_weight RefillStock.weight%type, p_licensePlate RefillStock.licensePlateVehicle%type) RETURN INTEGER IS 

BEGIN

    INSERT INTO RefillStock(id, necessaryEnergy, distance, weight, licensePlateVehicle)
    VALUES (seq_refillstock.nextval, p_necessaryEnergy, p_distance, p_weight, p_licensePlate);
    
    RETURN seq_refillstock.currval;
    
END;
/

create or replace function fncAddRefillByScooter( p_necessaryEnergy RefillStock.necessaryenergy%type,
                                            p_distance RefillStock.distance%type,
                                            p_weight RefillStock.weight%type,p_licensePlate RefillStock.licensePlateVehicle%type,  
                                            p_idcourier RefillStock.idcourier%type)RETURN INTEGER IS 

BEGIN
    INSERT INTO RefillStock(id, necessaryEnergy, distance, weight,licensePlateVehicle, idcourier)
    VALUES (seq_refillstock.nextval, p_necessaryEnergy, p_distance, p_weight,p_licensePlate, p_idcourier);
    
    RETURN seq_refillstock.currval;
    
END;
/

