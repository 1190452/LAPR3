CREATE OR REPLACE FUNCTION getPharmacyByName(p_name pharmacy.name%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM pharmacy WHERE name = p_name; 
  RETURN c; 
END;