create or replace FUNCTION getDeliveryByCourierId(p_id courier.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;
BEGIN

  OPEN c FOR 
  SELECT * FROM delivery WHERE idcourier = p_id order by id DESC 
  FETCH FIRST ROW ONLY;
  RETURN c; 
  
END;
/


create or replace FUNCTION getCourierDeliveries(p_id courier.id%type) 
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Delivery WHERE idcourier = p_id AND status = 0; 
  RETURN c; 
END;
/


create or replace FUNCTION getDeliveryByDroneId(p_id vehicle.id%type) 
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;
  v_licensePlate vehicle.licensePlate%type;
BEGIN
    SELECT licensePlate INTO v_licensePlate
    FROM vehicle    WHERE id = p_id;
    
    OPEN c FOR 
    SELECT * FROM Delivery WHERE licensePlateVehicle = v_licensePlate
    ORDER BY id DESC
    FETCH FIRST ROW ONLY;
    RETURN c; 
END;
/