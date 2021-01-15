CREATE OR REPLACE PROCEDURE prcAddMedicine(p_name product.name%type, 
                            p_description product.description%type,
                            p_price product.price%type, p_weight product.weight%type,
                            p_stock product.stock%type, p_idpharmacy product.idpharmacy%type) IS 

BEGIN

    INSERT INTO Product(id,name,description,price,weight,stock, idpharmacy) 
    VALUES (seq_product.nextval,p_name, p_description, p_price, p_weight,p_stock, p_idpharmacy);
   
END;
/

