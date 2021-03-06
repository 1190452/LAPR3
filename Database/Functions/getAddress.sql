create or replace FUNCTION getAddress(p_latitude address.latitude%type,p_longitude address.longitude%type, p_altitude address.altitude%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM address WHERE address.latitude= p_latitude AND address.longitude=p_longitude AND address.altitude=p_altitude; 

  RETURN c; 
END;
/

create or replace FUNCTION getAddressList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Address; 
  RETURN c; 
END;
/