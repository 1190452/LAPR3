create or replace PROCEDURE prcRemoveCourier(p_id courier.id%type) 
IS
BEGIN
    DELETE FROM courier where id = p_id;
END;
/

