create or replace FUNCTION getCreditCard(p_numberCC creditcard.numberCC%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM creditcard WHERE creditcard.numberCC = p_numberCC; 
  RETURN c; 
END;