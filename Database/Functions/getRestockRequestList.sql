create or replace FUNCTION getRestockList(p_idPharmacy pharmacy.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM RestockOrder WHERE  idPharmReceiver = p_idPharmacy ;
  RETURN c; 

END;