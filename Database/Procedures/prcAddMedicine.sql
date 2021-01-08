CREATE OR REPLACE PROCEDURE prcAddMedicine(p_name product.name%type, 
                            p_description product.description%type,
                            p_price product.price%type, p_weight product.weight%type, 
                            p_idpharmacy product.idpharmacy%type) IS 

BEGIN

    INSERT INTO Product(id,name,description,price,weight,idpharmacy) 
    VALUES (seq_product.nextval,p_name, p_description, p_price, p_weight,p_idpharmacy);
   
END;
/


--Test

declare
begin
prcAddMedicine('xarope', 'xarope para a tosse', 12, 0.5, 3);
end;

select * from product;