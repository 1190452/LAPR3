create or replace FUNCTION getUndoneOrders
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM clientorder WHERE status = 0; 
  RETURN c; 
END;