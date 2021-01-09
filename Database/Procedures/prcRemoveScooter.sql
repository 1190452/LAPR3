create or replace PROCEDURE prcRemoveScooter(p_id_vehicle electricscooter.licensePlate%type) 
IS
BEGIN
    DELETE FROM electricscooter where licensePlate = p_id_vehicle;
END;
/
