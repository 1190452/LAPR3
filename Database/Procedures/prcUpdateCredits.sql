CREATE OR REPLACE PROCEDURE prcUpdateCredits(p_id clientorder.id%type ) IS 
v_idClient int;
v_finalPrice int;
BEGIN
  
        SELECT idClient, finalPrice INTO v_idclient, v_finalPrice
        FROM ClientOrder WHERE id = p_id;
    
        UPDATE client SET credits = credits - v_finalPrice WHERE id = v_idclient;

END;
/