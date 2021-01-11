create or replace FUNCTION getVehicle(p_licensePlate vehicle.licensePlate%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM vehicle WHERE licensePlate = p_licensePlate AND idTypeVehicle = 1; 
  RETURN c; 
END;
/

create or replace FUNCTION getScooter(p_licensePlate vehicle.licensePlate%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM vehicle WHERE licensePlate = p_licensePlate AND idTypeVehicle = 1; 
  RETURN c; 
END;
/

create or replace FUNCTION getDrone(p_licensePlate vehicle.licensePlate%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM vehicle WHERE licensePlate = p_licensePlate AND idTypeVehicle = 2; 
  RETURN c; 
END;
/

