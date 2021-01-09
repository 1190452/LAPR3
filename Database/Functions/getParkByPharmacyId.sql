create or replace FUNCTION getParkByPharmacyId(p_id park.idpharmacy%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM park WHERE idpharmacy = p_id; 
  RETURN c; 
END;
/
