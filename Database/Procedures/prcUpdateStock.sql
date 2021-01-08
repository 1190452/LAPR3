<<<<<<< HEAD
CREATE OR REPLACE PROCEDURE prcUpdateStock(p_id product.id%type, 
                                            p_stock product.stock%type) IS 

BEGIN

    UPDATE product SET stock = stock + p_stock WHERE product.id = p_id; 
END;
/

=======
CREATE OR REPLACE PROCEDURE prcUpdateStock(p_id product.id%type, 
                                            p_stock product.stock%type) IS 

BEGIN

    UPDATE product SET stock = stock + p_stock WHERE product.id = p_id; 
END;
/

>>>>>>> 0732e975e1b66f7885a9d37bc7b94ebefdcbd217
