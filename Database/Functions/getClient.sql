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

