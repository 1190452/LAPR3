CREATE OR REPLACE procedure prcAddCourier(f_name courier.name%type, f_email courier.email%type,
                                            f_nif courier.nif%type, f_nss courier.nss%type, 
                                            f_maxweightcapacity courier.maxweightcapacity%type,
                                            f_weight courier.weight%type,
                                            f_idpharmacy courier.idpharmacy%type) 
IS 

BEGIN

    INSERT INTO Courier VALUES (seq_courier.nextval,f_name, f_email, f_nif, f_nss,f_maxweightcapacity, f_weight, f_idpharmacy);
   
END;
/


--Test

declare
v_ret int;
begin
v_ret := fncAddCourier('Fernando', 'courier3@isep.ipp.pt', 120023365,21146698523,10,70,1);
end;

