create or replace FUNCTION getClientOrder(p_id clientorder.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM clientorder WHERE clientorder.id = p_id; 
  RETURN c; 
  
END;
/
