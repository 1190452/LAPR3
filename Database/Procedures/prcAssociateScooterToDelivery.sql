create or replace PROCEDURE prcAssociateScooterToDelivery (p_id delivery.id%type,
                                                 p_scooter delivery.licenseplatescooter%type) IS 
BEGIN

    UPDATE delivery SET  licenseplatescooter = p_scooter WHERE id = p_id; 

END;


declare
begin
prcAssociateScooterToDelivery(1,'AB-10-VB');
end;