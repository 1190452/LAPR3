create or replace FUNCTION getVehicle(p_licensePlate vehicle.licensePlate%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM vehicle WHERE licensePlate = p_licensePlate; 
  RETURN c; 
END;
/


