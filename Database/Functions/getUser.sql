create or replace FUNCTION getUser(p_email appuser.email%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM appUser WHERE appUser.email = p_email; 
  RETURN c; 
END;




