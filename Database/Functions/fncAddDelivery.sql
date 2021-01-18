create or replace function fncAddDeliveryByDrone( p_necessaryEnergy delivery.necessaryenergy%type,
                                            p_distance delivery.distance%type,
                                            p_weight delivery.weight%type, p_licensePlate delivery.licensePlateVehicle%type) RETURN INTEGER IS 

BEGIN

    INSERT INTO Delivery(id, necessaryEnergy, distance, weight, licensePlateVehicle)
    VALUES (seq_delivery.nextval, p_necessaryEnergy, p_distance, p_weight, p_licensePlate);
    
    RETURN seq_delivery.currval;
    
END;
/

create or replace function fncAddDeliveryByScooter( p_necessaryEnergy delivery.necessaryenergy%type,
                                            p_distance delivery.distance%type,
                                            p_weight delivery.weight%type, p_idcourier delivery.idcourier%type)RETURN INTEGER IS 

BEGIN
    INSERT INTO Delivery(id, necessaryEnergy, distance, weight, idcourier)
    VALUES (seq_delivery.nextval, p_necessaryEnergy, p_distance, p_weight, p_idcourier);
    
     UPDATE courier SET status = 1 WHERE id = p_idcourier;
    
    RETURN seq_delivery.currval;
    
END;
/