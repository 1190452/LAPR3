create or replace FUNCTION getProduct(p_name product.name%type, p_id product.idpharmacy%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Product WHERE idpharmacy = p_id AND name = p_name; 
  RETURN c; 
END;
/

create or replace FUNCTION getProductList
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT id, name FROM Product; 
  RETURN c; 
END;
/

create or replace FUNCTION getProductListByPharmacy(p_id product.idpharmacy%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Product WHERE idpharmacy = p_id ; 
  RETURN c; 
END;
/

