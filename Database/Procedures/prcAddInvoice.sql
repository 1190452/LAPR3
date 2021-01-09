CREATE OR REPLACE PROCEDURE prcAddInvoice( p_finalprice invoice.finalprice%type,
                            p_idclient invoice.idclient%type,
                            p_idorder invoice.idorder%type) IS 

BEGIN

    INSERT INTO Invoice(id, dateinvoice, finalprice, idclient, idorder) 
    VALUES (seq_invoice.nextval,sysdate, p_finalprice, p_idclient, p_idorder);
   
END;
/