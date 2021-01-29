CREATE OR REPLACE FUNCTION getPharmacy
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM pharmacy; 
  RETURN c; 
END;
/

CREATE OR REPLACE FUNCTION getPharmacyByID(p_id pharmacy.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM pharmacy WHERE id = p_id; 
  RETURN c; 
END;
/

CREATE OR REPLACE FUNCTION getPharmacyByName(p_name pharmacy.name%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM pharmacy WHERE name = lower(p_name); 
  RETURN c; 
END;
/

create or replace FUNCTION getPharmacyByAddress(p_latitude pharmacy.addresslatitude%type, p_longitude pharmacy.addresslongitude%type, p_altitude pharmacy.addressaltitude%type)
RETURN INT
AS
p_id pharmacy.id%type;	
BEGIN

  SELECT id INTO p_id FROM pharmacy WHERE addresslatitude = p_latitude AND addresslongitude = p_longitude AND addressaltitude = p_altitude; 
 
    RETURN p_id;
END;
/