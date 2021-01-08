create or replace PROCEDURE prcRemoveScooter(p_id_vehicle electricscooter.id%type) 
IS
BEGIN
    DELETE FROM electricscooter where id = p_id_vehicle;
END;
/
