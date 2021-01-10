create or replace FUNCTION getAddressList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Address; 
  RETURN c; 
END;
/
