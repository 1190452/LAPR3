create or replace PROCEDURE prcUpdateStockAfterPayment(p_id clientorder.id%type, p_stockMissing number,
                                                       p_finalPrice clientorder.finalPrice%type ) IS 
v_idClient int;
BEGIN

    FOR c1 IN (SELECT po.idproduct, po.productquantity FROM ProductOrder po 
                INNER JOIN ClientOrder co ON po.idOrder = co.id
                WHERE co.id = p_id) LOOP

        UPDATE product SET stock = stock - (c1.productquantity-p_stockMissing)
        WHERE product.id = c1.idproduct; 

    END LOOP;

        SELECT idClient INTO v_idclient
        FROM ClientOrder WHERE id = p_id;
        
        IF p_finalPrice > 5  THEN

            UPDATE client SET credits = credits + (0.2 * p_finalPrice) WHERE id = v_idclient;
        
        END IF;

END;