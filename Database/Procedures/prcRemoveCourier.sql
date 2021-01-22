create or replace PROCEDURE prcRemoveCourier(p_id courier.id%type) 
IS
v_email courier.email%type;
BEGIN
    SELECT email INTO v_email
    FROM courier WHERE id = p_id;
    
    DELETE FROM delivery WHERE idCourier = p_id;
    DELETE FROM refillstock WHERE idCourier = p_id;
    DELETE FROM courier where id = p_id;
    DELETE FROM appuser WHERE email = v_email;
END;
/

