create or replace PROCEDURE prcRemoveMedicine(p_id product.id%type) 
IS
BEGIN

    DELETE FROM ProductOrder WHERE idProduct = p_id;
    DELETE FROM RestockOrder WHERE idProduct = p_id;
    DELETE FROM Product where id = p_id;
END;
/
