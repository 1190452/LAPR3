create or replace FUNCTION getScooterList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM electricscooter; 
  RETURN c; 
  
END;