CREATE OR REPLACE PROCEDURE updateOrderComplete (p_id clientorder.id%type) IS 
BEGIN

    UPDATE clientorder SET complete = 1 WHERE id = p_id;
      
END;
/


CREATE OR REPLACE PROCEDURE updateStatusOrder (p_idDelivery clientorder.idDelivery%type, p_id clientorder.id%type) IS 
BEGIN

    UPDATE clientorder SET idDelivery = p_idDelivery, status = 1 WHERE id = p_id;
      
END;
/