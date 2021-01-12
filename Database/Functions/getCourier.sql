create or replace FUNCTION getCourier(p_id courier.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Courier WHERE courier.id = p_id; 
  RETURN c; 
END;
/

CREATE OR REPLACE FUNCTION getCourierByEmail(p_email courier.email%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM courier WHERE courier.email = p_email; 
  
  RETURN c; 
END;
/

create or replace FUNCTION getCourierList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Courier; 
  RETURN c; 
END;
/

create or replace FUNCTION getCourierAvailable
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Courier WHERE courier.status = 0; 
  RETURN c; 
END;
/