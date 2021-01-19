CREATE OR REPLACE PROCEDURE prcUpdateStockAfterPayment(p_id clientorder.id%type ) IS 
v_idClient int;
v_finalPrice int;
BEGIN

    FOR c1 IN (SELECT po.idproduct, po.productquantity FROM ProductOrder po 
                INNER JOIN ClientOrder co ON po.idOrder = co.id
                WHERE co.id = p_id) LOOP
    
        UPDATE product SET stock = stock - c1.productquantity
        WHERE product.id = c1.idproduct; 
    
    END LOOP;
    
        SELECT idClient, finalPrice INTO v_idclient, v_finalPrice
        FROM ClientOrder WHERE id = p_id;
    
        UPDATE client SET credits = credits + 0.2 * v_finalPrice WHERE id = v_idclient;

END;
/