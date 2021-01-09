create or replace FUNCTION getDeliveryByCourierId(p_id courier.id%type)
RETURN SYS_REFCURSOR
AS
  c SYS_REFCURSOR;
BEGIN

  OPEN c FOR 
  SELECT * FROM delivery WHERE idcourier = p_id order by id DESC 
  FETCH FIRST ROW ONLY;
  RETURN c; 
  
END;



SELECT getDeliveryByCourierId(1) FROM DUAL;


  
insert into delivery (id, necessaryEnergy, distance, weight, idorder, idelectricscooter, idcourier)
values (1, 10, 3, 5, 1, 2, 1);
insert into delivery (id, necessaryEnergy, distance, weight, idorder, idelectricscooter, idcourier)
values (2, 10, 3, 4,24 , 2, 2);



  