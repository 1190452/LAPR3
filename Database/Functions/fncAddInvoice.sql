CREATE OR REPLACE FUNCTION fncAddInvoice( p_finalprice invoice.finalprice%type,
                            p_idclient invoice.idclient%type,
                            p_idorder invoice.idorder%type) RETURN INTEGER IS 

BEGIN

    INSERT INTO Invoice(id, dateinvoice, finalprice, idclient, idorder) 
    VALUES (seq_invoice.nextval,sysdate, p_finalprice, p_idclient, p_idorder);
    
    RETURN seq_invoice.currval;
   
END;
/