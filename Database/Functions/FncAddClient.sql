CREATE OR REPLACE FUNCTION fncAddClient(f_email client.email%type,f_name client.name%type,
                                    f_NIF client.nif%type, f_credits client.credits%type,
                                    f_numberCreditCard client.numbercreditcard%type,
                                    f_latitude address.latitude%type, 
                                    f_longitude address.longitude%type,
                                    f_street address.street%type) 
RETURN INTEGER IS 

BEGIN
    
    INSERT INTO Address VALUES (f_latitude, f_longitude,f_street);

    INSERT INTO Client VALUES (seq_client.nextval,f_name, f_email, f_NIF, f_credits,f_latitude,f_longitude,f_numberCreditCard);

    RETURN seq_client.currval;
   
END;

declare
v_ret int;
begin
v_ret := fncAddClient('client1@isep.ipp.pt', 'Joaquim Alberto', 123456789, 0, 41.15833, -8.62908, 1234567891011121, 'Rua dfghj');
end;

select * from client;


CREATE OR REPLACE procedure fncAddAddress( f_latitude address.latitude%type, 
                                    f_longitude address.longitude%type,
                                    f_street address.street%type) IS 

BEGIN
    
    INSERT INTO Address VALUES (f_latitude, f_longitude,f_street);
   
END;

declare
begin
fncAddAddress( 41.15843, -8.62909, 'Rua dfghj');
end;

select * from address;