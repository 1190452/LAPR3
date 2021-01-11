CREATE OR REPLACE PROCEDURE updateActualCapacityR (p_id park.id%type) IS 
v_places int;
BEGIN
    SELECT actualcapacity INTO v_places
    FROM park WHERE id = p_id;
    
    IF v_places not like 0 THEN
        UPDATE park SET actualcapacity = actualcapacity - 1 WHERE id = p_id; 
    END IF;
    
    
END;
/


CREATE OR REPLACE PROCEDURE updateActualCapacityA (p_id park.id%type) IS 
v_places int;
v_maxplaces int;
BEGIN
    SELECT actualcapacity, maxcapacity INTO v_places, v_maxplaces
    FROM park WHERE id = p_id;
    
    IF v_places < v_maxplaces THEN
        UPDATE park SET actualcapacity = actualcapacity + 1 WHERE id = p_id; 
    END IF;
    
    
END;
/