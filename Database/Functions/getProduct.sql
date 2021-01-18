create or replace FUNCTION getProduct(p_name product.name%type, p_id product.idpharmacy%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Product WHERE idpharmacy = p_id AND name = lower(p_name); 
  RETURN c; 
END;
/

create or replace FUNCTION getProductById(p_id product.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  SELECT * FROM Product WHERE id = p_id; 
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

create or replace FUNCTION getProductByStock(p_name product.name%type, p_stock product.stock%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;	
BEGIN
  OPEN c FOR 
  
  SELECT pm.* FROM Product p INNER JOIN Pharmacy pm ON p.idpharmacy = pm.id WHERE p.name = lower(p_name) AND p.stock >= p_stock; 
  RETURN c; 
END;
/
