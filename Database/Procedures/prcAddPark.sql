CREATE OR REPLACE PROCEDURE prcAddPark(p_maxcapacity park.maxcapacity%type, 
                            p_maxchargingplaces park.maxchargingplaces%type,
                            p_actualchargingplaces park.actualchargingplaces%type,
                            p_power park.power%type,
                            p_idpharmacy park.idpharmacy%type) IS 

BEGIN

    INSERT INTO Park(id, maxcapacity, actualcapacity,maxchargingplaces, actualchargingplaces,power, idpharmacy) 
    VALUES (seq_park.nextval,p_maxcapacity,p_maxcapacity, p_maxchargingplaces, p_actualchargingplaces, p_power, p_idpharmacy);
   
END;

--test 

declare
begin
prcAddPark(12,2,1,21,1);
end;

select * from park;