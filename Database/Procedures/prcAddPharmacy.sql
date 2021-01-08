create or replace PROCEDURE prcAddPharmacy (p_name pharmacy.name%type, p_latitude pharmacy.addresslatitude%type,
                                            p_longitude pharmacy.addresslongitude%type,
                                            p_emailAdmin pharmacy.emailadministrator%type) IS

BEGIN
    
    INSERT INTO pharmacy (id, name, addresslatitude, addresslongitude, emailadministrator)
    VALUES(SEQ_PHARMACY.nextval, p_name, p_latitude ,p_longitude, p_emailAdmin);
    
END;
/

--Teste
declare 
v_lat number;
v_lon number;
begin 

v_lat := 41.15833;
v_lon := -8.62908;
prcAddPharmacy('Farmácia Zé do Pipo', v_lat, v_lon , 'admin@isep.ipp.pt') ;
end;
/

select * from pharmacy;

