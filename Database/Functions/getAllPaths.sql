create or replace FUNCTION getAllPaths
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Path; 
  RETURN c; 
END;








