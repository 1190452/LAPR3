create or replace FUNCTION getScooter(p_id electricscooter.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM electricscooter WHERE electricscooter.id = p_id; 
  RETURN c; 
END;