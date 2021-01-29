create or replace FUNCTION getChargingCourierList (p_idpark park.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 

    SELECT DISTINCT c.email, v.* FROM courier c
    INNER JOIN delivery d  ON d.idCourier = c.id
    INNER JOIN vehicle v ON d.licenseplateVehicle = v.licenseplate 
    INNER JOIN pharmacy ph ON v.idPharmacy = ph.id
    INNER JOIN park p ON p.idpharmacy = ph.id
    WHERE d.status = 1 AND v.isCharging = 1 AND p.id = p_idpark ;

  RETURN c; 

END;
/