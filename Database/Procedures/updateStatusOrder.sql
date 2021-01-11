create or replace PROCEDURE updateStatusOrder (p_id clientorder.id%type,
                                            p_delivery clientorder.iddelivery%type) IS 
BEGIN

        UPDATE clientorder SET status = 1, iddelivery = p_delivery WHERE id = p_id; 

END;
/