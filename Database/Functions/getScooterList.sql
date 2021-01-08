create or replace FUNCTION getScooterList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT id, ah_battery, v_battery,enginepower, weight, idpharmacy FROM electricscooter; 
  RETURN c; 

END;