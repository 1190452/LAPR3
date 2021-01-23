create or replace FUNCTION fncAddClientOrder( p_finalprice clientorder.finalprice%type,
                            p_finalweight clientorder.finalweight%type, 
                            p_complete clientorder.complete%type,
                            p_idclient clientorder.idclient%type,
                            p_productsprice clientorder.finalprice%type) RETURN INTEGER IS 

v_cred INTEGER;
BEGIN

    INSERT INTO ClientOrder(id, dateorder, finalprice, finalweight,complete, idclient) 
    VALUES (seq_clientorder.nextval,sysdate, p_finalprice, p_finalweight,p_complete, p_idclient);


    select credits into v_cred from client where id = p_idclient;

    UPDATE Client SET credits = v_cred + (p_productsprice * 0.2) where id = p_idclient;

    RETURN seq_clientorder.currval;

END;
/