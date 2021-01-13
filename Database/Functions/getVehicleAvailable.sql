create or replace FUNCTION getVehicleAvailable
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM vehicle WHERE status = 0; 
  RETURN c; 
  
END;
/

create or replace FUNCTION getScooterAvailable
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM vehicle WHERE idTypeVehicle = 1 AND status=0; 
  RETURN c; 
  
END;
/

create or replace FUNCTION getDroneAvailable
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM vehicle WHERE idTypeVehicle = 2 AND status=0; 
  RETURN c; 
  
END;
