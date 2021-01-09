CREATE OR REPLACE PROCEDURE updateActualCapacity (p_id park.id%type) IS 

BEGIN
    UPDATE park SET actualcapacity = actualcapacity - 1 WHERE id = p_id; 
END;
/

declare
begin
updateActualCapacity(22);
end;

select * from park;