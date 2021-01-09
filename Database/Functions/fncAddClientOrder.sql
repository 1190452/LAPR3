CREATE OR REPLACE FUNCTION fncAddClientOrder( p_finalprice clientorder.finalprice%type,
                            p_finalweight clientorder.finalweight%type, 
                            p_idclient clientorder.idclient%type) RETURN INTEGER IS 

BEGIN

    INSERT INTO ClientOrder(id, dateorder, finalprice, finalweight, idclient) 
    VALUES (seq_clientorder.nextval,sysdate, p_finalprice, p_finalweight, p_idclient);
    
    RETURN seq_clientorder.currval;
   
END;
/

