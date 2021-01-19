create or replace procedure prcAddRestockOrder( p_idproduct restockorder.idProduct%type,
                                            p_productQuantity  restockorder.productQuantity%type,
                                            p_idPharmReceiver restockorder.idPharmReceiver%type, 
                                            p_idPharmSender restockorder.idPharmSender%type, p_idClientOrder restockorder.idClientOrder%type) IS 

BEGIN

    INSERT INTO RestockOrder(id, idProduct, productQuantity, idPharmReceiver, idPharmSender, idClientOrder)
    VALUES (seq_restockorder.nextval, p_idproduct, p_productQuantity, p_idPharmReceiver, p_idPharmSender,p_idClientOrder);
    
END;
/

CREATE OR REPLACE PROCEDURE updateStatusRestock ( p_id restockorder.id%type, p_idRefill restockorder.idRefillStock%type) IS 
BEGIN

    UPDATE restockorder SET idRefillStock = p_idRefill, status = 1 WHERE id = p_id;
      
END;
/