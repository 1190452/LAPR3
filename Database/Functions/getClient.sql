create or replace FUNCTION getClient(p_nif client.nif%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM client WHERE client.nif = p_nif; 
  RETURN c; 
  
END;
/