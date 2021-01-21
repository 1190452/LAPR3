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

create or replace FUNCTION getScooterAvailable(p_id vehicle.idpharmacy%type, p_energy vehicle.actualbattery%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM vehicle WHERE idpharmacy = p_id AND idTypeVehicle = 1 AND status=0 AND actualbattery >= p_energy; 
  RETURN c; 

END;
/

create or replace FUNCTION getDroneAvailable(p_id vehicle.idpharmacy%type, p_actualbattery vehicle.actualbattery%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM vehicle WHERE idpharmacy = p_id AND idTypeVehicle = 2 AND status=0 AND actualbattery >= p_actualbattery ; 
  RETURN c; 
  
END;

