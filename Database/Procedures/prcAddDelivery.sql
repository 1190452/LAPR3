create or replace procedure prcAddDeliveryByDrone( p_necessaryEnergy delivery.necessaryenergy%type,
                                            p_distance delivery.distance%type,
                                            p_weight delivery.weight%type, p_licensePlate delivery.licensePlateVehicle%type) IS 

BEGIN

    INSERT INTO Delivery(id, necessaryEnergy, distance, weight, licensePlateVehicle)
    VALUES (seq_delivery.nextval, p_necessaryEnergy, p_distance, p_weight, p_licensePlate);
    
END;
/

create or replace procedure prcAddDeliveryByScooter( p_necessaryEnergy delivery.necessaryenergy%type,
                                            p_distance delivery.distance%type,
                                            p_weight delivery.weight%type, p_idcourier delivery.idcourier%type) IS 

BEGIN
    INSERT INTO Delivery(id, necessaryEnergy, distance, weight, idcourier)
    VALUES (seq_delivery.nextval, p_necessaryEnergy, p_distance, p_weight, p_idcourier);
    
END;
/
