CREATE OR REPLACE FUNCTION getClientByEmail(p_email client.email%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM client WHERE client.email = p_email; 
  
  RETURN c; 
END;