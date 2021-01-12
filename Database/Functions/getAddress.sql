create or replace FUNCTION getAddress(p_latitude address.latitude%type,p_longitude address.longitude%type )
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM address WHERE address.latitude= p_latitude AND address.longitude=p_longitude ; 

  RETURN c; 
END;
/