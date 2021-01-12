CREATE OR REPLACE PROCEDURE updateStatusCourier (p_id courier.id%type) IS 
BEGIN

    UPDATE courier SET status = 1 WHERE id = p_id;
      
END;
/


CREATE OR REPLACE PROCEDURE updateStatusDelivery (p_id delivery.id%type) IS 
v_courier int;
BEGIN
    SELECT idcourier INTO v_courier
    FROM delivery WHERE id = p_id;
    
    UPDATE courier SET status = 0 WHERE id = v_courier;
    
    UPDATE delivery SET status = 1 WHERE id = p_id;
    
END;
/