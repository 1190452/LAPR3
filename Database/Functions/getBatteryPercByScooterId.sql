create or replace FUNCTION getBatteryPercByScooterId(p_id electricscooter.id%type)
RETURN NUMBER AS
v_battery number;
BEGIN
  SELECT actualBattery INTO v_battery FROM electricscooter WHERE id = p_id; 

    RETURN v_battery;
END;

select getBatteryPercByScooterId(2) from dual;

select * from electricscooter;