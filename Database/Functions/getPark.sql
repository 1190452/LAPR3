create or replace FUNCTION getParkByPharmacyId(p_id park.idpharmacy%type, p_type park.idtypepark%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM park WHERE idpharmacy = p_id AND idtypepark= p_type; 
  RETURN c; 
END;
/

create or replace FUNCTION getParkWithCPlaces(p_type park.idtypepark%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM park WHERE actualChargingPlaces > 0 AND idtypepark= p_type; 
  RETURN c; 
END;
/

create or replace FUNCTION getParkWithNPlaces(p_type park.idtypepark%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM park WHERE actualCapacity > 0 AND idtypepark= p_type; 
  RETURN c; 
END;
/