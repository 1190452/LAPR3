create or replace FUNCTION getCourierList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Courier; 
  RETURN c; 
END;

