CREATE OR REPLACE PROCEDURE prcAddClient(p_email client.email%type,p_name client.name%type,
                                    p_NIF client.nif%type,
                                    p_latitude client.addresslatitude%type, 
                                    p_longitude client.addresslongitude%type,
                                    p_altitude client.addressaltitude%type,
                                    p_numberCreditCard client.numbercreditcard%type) IS 

BEGIN

    INSERT INTO Client(id, email, name, NIF, Addresslatitude, Addresslongitude,Addressaltitude,numberCreditCard) 
    VALUES (seq_client.nextval,p_email, p_name, p_NIF,  p_latitude, p_longitude,p_altitude, p_numberCreditCard);
   
END;
/
