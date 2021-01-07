create or replace FUNCTION fncAddPharmacy   (p_name pharmacy.name%type, p_latitude address.latitude%type, p_longitude address.longitude%type,
                                            p_emailAdmin pharmacy.emailadministrator%type, p_street address.street%type,
                                            p_doorNumber address.doornumber%type, p_zipcode address.zipcode%type, p_locality address.zipcode%type) RETURN INTEGER
IS
v_lat address.latitude%type;
v_lon address.longitude%type;

BEGIN
    
    INSERT INTO Address VALUES (p_latitude, p_longitude, p_street,p_doorNumber, p_zipcode, p_locality );
    
    select latitude, longitude into v_lat, v_lon from address where latitude = p_latitude and longitude = p_longitude;
      
    INSERT INTO pharmacy VALUES(SEQ_PHARMACY.nextval, p_name, v_lat ,v_lon, p_emailAdmin);
    
    RETURN SEQ_PHARMACY.currval;
END;
/

--Teste
declare 
v_ret2 int;
begin 
  v_ret2 :=  fncAddPharmacy('Farmácia Zé do Pipo', 41.15874371509202, -8.633733820238406 , 'admin@isep.ipp.pt', 'Rua da Venezuela',855, '4100-128', 'Porto') ;
end;
/

select * from pharmacy;

select * from address;

select * from client;
