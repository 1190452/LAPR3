create or replace function fncAddRefillByDrone( p_necessaryEnergy delivery.necessaryenergy%type,
                                            p_distance delivery.distance%type,
                                            p_weight delivery.weight%type, p_licensePlate delivery.licensePlateVehicle%type) RETURN INTEGER IS 

BEGIN

    INSERT INTO RefillStock(id, necessaryEnergy, distance, weight, licensePlateVehicle)
    VALUES (seq_refillstock.nextval, p_necessaryEnergy, p_distance, p_weight, p_licensePlate);
    
    RETURN seq_refillstock.currval;
    
END;
/

create or replace function fncAddRefillByScooter( p_necessaryEnergy delivery.necessaryenergy%type,
                                            p_distance delivery.distance%type,
                                            p_weight delivery.weight%type, p_idcourier delivery.idcourier%type)RETURN INTEGER IS 

BEGIN
    INSERT INTO RefillStock(id, necessaryEnergy, distance, weight, idcourier)
    VALUES (seq_refillstock.nextval, p_necessaryEnergy, p_distance, p_weight, p_idcourier);
    
    RETURN seq_refillstock.currval;
    
END;
/