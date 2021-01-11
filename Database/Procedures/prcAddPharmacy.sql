create or replace PROCEDURE prcAddPharmacy (p_name pharmacy.name%type, p_latitude pharmacy.addresslatitude%type,
                                            p_longitude pharmacy.addresslongitude%type,
                                            p_emailAdmin pharmacy.emailadministrator%type) IS

BEGIN
    
    INSERT INTO pharmacy (id, name, addresslatitude, addresslongitude, emailadministrator)
    VALUES(SEQ_PHARMACY.nextval, p_name, p_latitude ,p_longitude, p_emailAdmin);
    
END;
/



