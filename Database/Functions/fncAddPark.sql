CREATE OR REPLACE FUNCTION fncAddPark(p_maxcapacity park.maxcapacity%type, 
                            p_maxchargingplaces park.maxchargingplaces%type,
                            p_power park.power%type, p_idpharmacy park.idpharmacy%type, 
                            p_typepark park.idtypepark%type)RETURN INT IS 

BEGIN

    INSERT INTO Park(id, maxcapacity, actualcapacity,maxchargingplaces, actualchargingplaces,power, idpharmacy, idtypepark) 
    VALUES (seq_park.nextval,p_maxcapacity,p_maxcapacity, p_maxchargingplaces, p_maxchargingplaces, p_power, p_idpharmacy,p_typepark);
    
    RETURN seq_park.currval;
   
END;
/
