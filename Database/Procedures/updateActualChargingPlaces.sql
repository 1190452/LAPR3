CREATE OR REPLACE PROCEDURE updateActualChargingPlacesR (p_id park.id%type) IS 
v_places int;
BEGIN
    SELECT actualChargingPlaces INTO v_places
    FROM park WHERE id = p_id;
    
    IF v_places not like 0 THEN
    UPDATE park SET actualChargingPlaces = actualChargingPlaces - 1 WHERE id = p_id; 
    END IF;
    
END;
/


CREATE OR REPLACE PROCEDURE updateActualChargingPlacesA (p_id park.id%type) IS 
v_places int;
v_maxplaces int;
BEGIN
    SELECT actualChargingPlaces, maxChargingPlaces INTO v_places, v_maxplaces
    FROM park WHERE id = p_id;
    
    IF v_places < v_maxplaces THEN
    UPDATE park SET actualChargingPlaces = actualChargingPlaces + 1 WHERE id = p_id; 
    END IF;
    
END;
/

