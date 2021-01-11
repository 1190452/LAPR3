CREATE OR REPLACE PROCEDURE prcAddClient(p_email client.email%type,p_name client.name%type,
                                    p_NIF client.nif%type, p_credits client.credits%type,
                                    p_latitude client.addresslatitude%type, 
                                    p_longitude client.addresslongitude%type,
                                    p_numberCreditCard client.numbercreditcard%type) IS 

BEGIN

    INSERT INTO Client(id, email, name, NIF, credits, Addresslatitude, Addresslongitude,numberCreditCard) 
    VALUES (seq_client.nextval,p_email, p_name, p_NIF, p_credits, p_latitude, p_longitude, p_numberCreditCard);
   
END;
/
