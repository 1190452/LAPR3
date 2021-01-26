CREATE OR REPLACE PROCEDURE prcAddPath(p_latitudeFrom path.latitudefrom%type, p_longitudeFrom path.longitudefrom%type, p_altitudeFrom path.altitudefrom%type,
                            p_latitudeTo path.latitudeto%type, p_longitudeTo path.longitudeto%type, p_altitudeTo path.altitudefrom%type,
                            p_roadRollingResistance path.roadRollingResistance%type, p_windSpeed path.windSpeed%type, p_windDirection path.windDirection%type, p_idTypePath path.idTypePath%type) IS

BEGIN

    INSERT INTO Path(latitudeFrom, longitudeFrom, altitudeFrom, latitudeTo, longitudeTo, altitudeTo, roadRollingResistance, windSpeed, windDirection, idTypePath) 
    VALUES (p_latitudeFrom, p_longitudeFrom, p_altitudeFrom, p_latitudeTo, p_longitudeTo, p_altitudeTo, p_roadRollingResistance, p_windSpeed, p_windDirection, p_idTypePath);
   
END;
/
