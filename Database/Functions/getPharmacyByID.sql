CREATE OR REPLACE FUNCTION getPharmacyByID(p_id pharmacy.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM pharmacy WHERE id = p_id; 
  RETURN c; 
END;