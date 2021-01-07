<<<<<<< HEAD
CREATE OR REPLACE FUNCTION fncAddMedicine(f_name product.name%type, f_description product.description%type,
                                            f_price product.price%type, f_weight product.weight%type, 
                                            f_idpharmacy product.idpharmacy%type) 
RETURN INTEGER IS 

BEGIN

    INSERT INTO Product VALUES (seq_product.nextval,f_name, f_description, f_price, f_weight,f_idpharmacy);

    RETURN seq_product.currval;
   
END;
/


--Test

declare
v_ret int;
begin
v_ret := fncAddMedicine('xarope', 'xarope para a tosse', 12, 0.5, 1);
end;

=======
CREATE OR REPLACE FUNCTION fncAddMedicine(f_name product.name%type, f_description product.description%type,
                                            f_price product.price%type, f_weight product.weight%type, 
                                            f_idpharmacy product.idpharmacy%type) 
RETURN INTEGER IS 

BEGIN

    INSERT INTO Product VALUES (seq_product.nextval,f_name, f_description, f_price, f_weight,f_idpharmacy);

    RETURN seq_product.currval;
   
END;
/


--Test

declare
v_ret int;
begin
v_ret := fncAddMedicine('xarope', 'xarope para a tosse', 12, 0.5, 1);
end;

>>>>>>> bf606c70ae69c8711be013a31e79666d7b87a7c7
select * from product;