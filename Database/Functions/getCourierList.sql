<<<<<<< HEAD
create or replace FUNCTION getCourierList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Courier; 
  RETURN c; 
END;

=======
create or replace FUNCTION getCourierList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Courier; 
  RETURN c; 
END;

>>>>>>> 0732e975e1b66f7885a9d37bc7b94ebefdcbd217
