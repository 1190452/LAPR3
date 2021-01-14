CREATE OR REPLACE PROCEDURE prcUpdateStockAfterPayment(p_id clientorder.id%type ) IS 

BEGIN

    FOR c1 IN (SELECT po.idproduct, po.productquantity FROM ProductOrder po 
                INNER JOIN ClientOrder co ON po.idOrder = co.id
                WHERE co.id = p_id) LOOP
    
        UPDATE product SET stock = stock - c1.productquantity
        WHERE product.id = c1.idproduct; 
    
    END LOOP;

END;
/


CREATE OR REPLACE PROCEDURE prcUpdateStockA(p_idpharmacyA product.idPharmacy%type, p_idpharmacyR product.idPharmacy%type, 
                                            p_id product.id%type, p_stock product.stock%type) IS 

BEGIN

    UPDATE product SET stock = stock + p_stock WHERE id = p_id AND idPharmacy = p_idpharmacyA ;
    
    UPDATE product SET stock = stock - p_stock WHERE id = p_id AND idPharmacy = p_idpharmacyR ; 
    
END;
/