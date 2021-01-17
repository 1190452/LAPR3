create or replace FUNCTION getRestockRequestList(p_idPharmacy pharmacy.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM RestockRequest WHERE  p_idPharmacy = RestockRequest.pharmSenderID;
  RETURN c; 

END;