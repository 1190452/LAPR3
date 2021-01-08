<<<<<<< HEAD
create or replace FUNCTION getProductList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT id, name FROM Product; 
  RETURN c; 
=======
create or replace FUNCTION getProductList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT id, name FROM Product; 
  RETURN c; 
>>>>>>> 0732e975e1b66f7885a9d37bc7b94ebefdcbd217
END;