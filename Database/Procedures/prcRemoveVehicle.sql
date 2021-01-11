create or replace PROCEDURE prcRemoveVehicle(p_id_vehicle vehicle.licensePlate%type) 
IS
BEGIN
    DELETE FROM vehicle where licensePlate = p_id_vehicle;
END;
/

