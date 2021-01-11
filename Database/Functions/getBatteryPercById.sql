create or replace FUNCTION getBatteryPercById(p_id vehicle.licensePlate%type)
RETURN NUMBER AS
v_battery number;
BEGIN
  SELECT actualBattery INTO v_battery FROM vehicle WHERE licensePlate = p_id; 

    RETURN v_battery;
END;
/