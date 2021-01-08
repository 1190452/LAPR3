<<<<<<< HEAD
create or replace FUNCTION getScooterList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT id, ah_battery, v_battery,enginepower, weight, idpharmacy FROM electricscooter; 
  RETURN c; 
=======
create or replace FUNCTION getScooterList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT id, ah_battery, v_battery,enginepower, weight, idpharmacy FROM electricscooter; 
  RETURN c; 
>>>>>>> 0732e975e1b66f7885a9d37bc7b94ebefdcbd217
END;