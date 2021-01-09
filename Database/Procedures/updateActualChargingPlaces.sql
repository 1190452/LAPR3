CREATE OR REPLACE PROCEDURE updateActualChargingPlaces (p_id park.id%type) IS 

BEGIN
    UPDATE park SET actualChargingPlaces = actualChargingPlaces - 1 WHERE id = p_id; 
END;
/

declare
begin
updateActualChargingPlaces(22);
end;

select * from park;