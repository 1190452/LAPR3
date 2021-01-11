create or replace PROCEDURE prcAssociateVehicleToDelivery (p_id delivery.id%type,
                                                 p_vehicle delivery.licenseplatevehicle%type) IS 
BEGIN

    UPDATE delivery SET  licenseplatevehicle = p_vehicle WHERE id = p_id; 

END;
/

