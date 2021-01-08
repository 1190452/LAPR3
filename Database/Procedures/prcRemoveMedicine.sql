create or replace PROCEDURE prcRemoveMedicine(p_id product.id%type) 
IS
BEGIN
    DELETE FROM Product where id = p_id;
END;
/
