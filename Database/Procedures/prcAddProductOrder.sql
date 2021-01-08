CREATE OR REPLACE PROCEDURE prcAddProductOrder( p_idOrder productorder.idorder%type,
                                                p_idProduct productorder.idproduct%type,
                                                p_quantity productorder.productQuantity%type) IS 

BEGIN

    INSERT INTO ProductOrder(idOrder, idProduct, productQuantity) 
    VALUES (p_idOrder,p_idProduct, p_quantity);
   
END;
/
