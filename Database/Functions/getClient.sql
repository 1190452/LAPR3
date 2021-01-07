CREATE OR REPLACE FUNCTION getClient(p_id client.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM client WHERE client.id = p_id; 
  
  RETURN c; 
END;
/

