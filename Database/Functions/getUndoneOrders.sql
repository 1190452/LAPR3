create or replace FUNCTION getUndoneOrders
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM clientorder WHERE status = 0 AND complete = 1; 
  RETURN c; 
END;
/

create or replace FUNCTION getUndoneOrdersByPharmacy(p_idpharmacy Product.idpharmacy%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
  v_idPharmacy int;
  v_idProduct int;
BEGIN

    OPEN c FOR 
        SELECT co.* FROM Product p INNER JOIN ProductOrder po ON p.id= po.idProduct 
        INNER JOIN ClientOrder co ON po.idOrder = co.id
        WHERE p.idPharmacy = p_idpharmacy AND co.status = 0 AND complete = 1;
    RETURN c; 
END;
/