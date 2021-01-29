create or replace FUNCTION getClient(p_nif client.nif%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM client WHERE client.nif = p_nif; 
  RETURN c; 
  
END;
/

CREATE OR REPLACE FUNCTION getClientByEmail(p_email client.email%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM client WHERE client.email = p_email; 
  
  RETURN c; 
END;
/

create or replace FUNCTION getClientById(p_id client.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM client WHERE client.id = p_id; 
  RETURN c; 
  
END;
/

create or replace FUNCTION getClientEmailByDelivery(p_id delivery.id%type)
RETURN  SYS_REFCURSOR
AS
 c SYS_REFCURSOR;	
BEGIN

    OPEN c FOR
        SELECT c.email FROM Client c INNER JOIN ClientOrder co ON c.id = co.idClient
                       INNER JOIN Delivery d ON co.idDelivery = d.id
                       WHERE d.id = p_id;
    RETURN c;
    
END;
/

create or replace FUNCTION getClientByClientOrder(p_id clientorder.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT c.* FROM Client c INNER JOIN ClientOrder co ON co.idClient = c.id WHERE co.id = p_id; 
  RETURN c; 

END;
/

create or replace FUNCTION getClientByAddress(p_latitude client.addresslatitude%type, p_longitude client.addresslongitude%type, p_altitude client.addressaltitude%type)
RETURN INT
AS
p_id client.id%type;	
BEGIN

  SELECT id INTO p_id FROM Client WHERE addresslatitude = p_latitude AND addresslongitude = p_longitude AND addressaltitude = p_altitude; 
 
    RETURN p_id;
END;
/