create or replace FUNCTION getProductList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT id, name FROM Product; 
  RETURN c; 

END;