create or replace FUNCTION getScooter(p_licensePlate electricscooter.licensePlate%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM electricscooter WHERE licensePlate = p_licensePlate; 
  RETURN c; 
END;
