create or replace FUNCTION getAllPaths (p_idTypePath path.idTypePath%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Path WHERE idTypePath = p_idTypePath ; 
  RETURN c; 
END;








