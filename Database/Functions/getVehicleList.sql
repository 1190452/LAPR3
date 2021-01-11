create or replace FUNCTION getVehicleList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM vehicle; 
  RETURN c; 
  
END;
/

create or replace FUNCTION getScooterList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM vehicle WHERE idTypeVehicle = 1; 
  RETURN c; 
  
END;
/

create or replace FUNCTION getDroneList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM vehicle WHERE idTypeVehicle = 2; 
  RETURN c; 
  
END;