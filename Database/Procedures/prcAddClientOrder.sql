CREATE OR REPLACE PROCEDURE prcAddClientOrder( p_finalprice clientorder.finalprice%type,
                            p_finalweight clientorder.finalweight%type, 
                            p_idclient clientorder.idclient%type) IS 

BEGIN

    INSERT INTO ClientOrder(id, dateorder, finalprice, finalweight, idclient) 
    VALUES (seq_clientorder.nextval,sysdate, p_finalprice, p_finalweight, p_idclient);
   
END;
/

