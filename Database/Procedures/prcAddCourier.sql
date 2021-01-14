CREATE OR REPLACE procedure prcAddCourier(p_name courier.name%type, p_email courier.email%type,
                                            p_nif courier.nif%type, p_nss courier.nss%type, 
                                            p_weight courier.weight%type,
                                            p_idpharmacy courier.idpharmacy%type) 
IS 

BEGIN

    INSERT INTO Courier(id, name, email,nif,nss,weight,idpharmacy) 
    VALUES (seq_courier.nextval,p_name, p_email, p_nif, p_nss, p_weight, p_idpharmacy);
   
END;
/


