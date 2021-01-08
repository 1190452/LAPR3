create or replace FUNCTION getCourier(p_id courier.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Courier WHERE courier.id = p_id; 
  RETURN c; 
END;