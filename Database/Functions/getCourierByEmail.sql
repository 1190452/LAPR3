CREATE OR REPLACE FUNCTION getCourierByEmail(p_email courier.email%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM courier WHERE courier.email = p_email; 
  
  RETURN c; 
END;