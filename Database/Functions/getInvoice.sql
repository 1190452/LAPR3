create or replace FUNCTION getInvoice(p_id invoice.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM invoice WHERE invoice.id = p_id; 

  RETURN c; 
END;
/