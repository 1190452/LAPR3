--SEQUENCES
declare
  v_cmd varchar(2000);
begin
  for r in (select sequence_name from user_sequences)
  loop
    v_cmd := 'drop sequence ' || r.sequence_name;
    execute immediate(v_cmd);
  end loop;
  --
  for r in (select table_name from user_tables)
  loop
    v_cmd := 'create sequence seq_' || r.table_name;
    execute immediate(v_cmd);
  end loop;
end;
/


INSERT INTO AppUser(email,password,role) VALUES('admin@isep.ipp.pt', 'qwerty', 'administrator');
INSERT INTO Administrator(email,name) VALUES('admin@isep.ipp.pt', 'Antonio');

INSERT INTO TypeVehicle(id, name) VALUES (1, 'Electric Scooter');
INSERT INTO TypeVehicle(id, name) VALUES (2, 'Drone');

INSERT INTO TypePark(id, name) VALUES (1, 'Park Electric Scooter');
INSERT INTO TypePark(id, name) VALUES (2, 'Park Drone');


---------------------------------------------------------------------------------------------------------------

INSERT INTO Address VALUES (41.15833, -8.62908,10, 'Praça Mouzinho de Albuquerque', 0, '4100-369', 'Porto');
INSERT INTO Address VALUES (41.18200, -8.60119,10, 'Alameda Prof. Hernâni Monteiro', 0, '4200-319', 'Porto');
INSERT INTO Address VALUES (41.15213, -8.62908,10, 'Praça das Flores', 0, '4000-364', 'Porto');
INSERT INTO Address VALUES (41.12300, -8.67770,10, 'Travessa do Paço', 0, '4001-329', 'Porto');
INSERT INTO Address VALUES (41.98520, -8.14520,10, 'Praça D.João I', 0, '4520-025', 'Porto');
INSERT INTO Address VALUES (41.22500, -8.68897,10, 'Travessa da Santa Barbara', 0, '9820-319', 'Porto');
INSERT INTO Address VALUES (41.15833, -8.60328,10, 'Praça dos Poveiros', 0, '4500-360', 'Porto');
INSERT INTO Address VALUES (41.20000, -8.77819,10, 'Avenida Fernão Magalhães ', 0, '4580-023', 'Porto');
 

INSERT INTO creditcard(numbercc,monthexpiration,yearexpiration,ccv) VALUES(1234567891011121, 08, 2021, 456);
INSERT INTO creditcard(numbercc,monthexpiration,yearexpiration,ccv)VALUES(1234567891011122, 09, 2021, 567);

INSERT INTO AppUser(email,password,role) VALUES('client1@isep.ipp.pt', 'qwerty', 'client');
INSERT INTO AppUser(email,password,role) VALUES('client2@isep.ipp.pt', 'qwerty', 'client');
INSERT INTO AppUser(email,password,role) VALUES('client3@isep.ipp.pt', 'qwerty', 'client');

INSERT INTO AppUser(email,password,role) VALUES('courier1@isep.ipp.pt', 'qwerty', 'courier');
INSERT INTO AppUser(email,password,role) VALUES('courier2@isep.ipp.pt', 'qwerty', 'courier');

INSERT INTO Pharmacy(id,name, Addresslatitude, Addresslongitude,emailpharmacy, emailAdministrator)
VALUES(SEQ_PHARMACY.nextval, 'Farmácia Porto',41.15833 ,-8.62908,'pharmacy1@isep.ipp.pt','admin@isep.ipp.pt');

INSERT INTO Pharmacy(id,name, Addresslatitude, Addresslongitude,emailpharmacy, emailAdministrator)
VALUES(SEQ_PHARMACY.nextval, 'Farmácia da Avenida',41.20000, -8.77819,'pharmacy2@isep.ipp.pt','admin@isep.ipp.pt');

INSERT INTO Client(id, email, name, NIF, credits, Addresslatitude, Addresslongitude,numberCreditCard) 
VALUES(SEQ_CLIENT.nextval,'client1@isep.ipp.pt', 'Joaquim Alberto', 123456789, 0, 41.20000, -8.77819, 1234567891011121);
INSERT INTO Client(id, email, name, NIF, credits, Addresslatitude, Addresslongitude,numberCreditCard) 
VALUES(SEQ_CLIENT.nextval, 'client2@isep.ipp.pt', 'Hernani Carvalho', 134568795,0, 41.18200, -8.60119, 1234567891011122);

INSERT INTO Courier(id, name, email, NIF, NSS, weight, idPharmacy)
VALUES(SEQ_COURIER.nextval, 'Manuel', 'courier1@isep.ipp.pt', 196547823, 23456987265, 80, 1);
INSERT INTO Courier(id, name, email, NIF, NSS,  weight, idPharmacy)
VALUES(SEQ_COURIER.nextval, 'João','courier2@isep.ipp.pt', 165478923, 21453269857,  75, 1);

INSERT INTO Product(id, name, description, price, weight,stock, idpharmacy)
VALUES(SEQ_PRODUCT.nextval, 'Ben-u-ron', 'Para as dores de cabeça', 5, 0.5,70, 1);
INSERT INTO Product(id, name, description, price, weight, stock, idpharmacy)
VALUES(SEQ_PRODUCT.nextval, 'Brufen', 'Para as dores de coto', 4, 0.7,5, 1);
INSERT INTO Product(id, name, description, price, weight, stock, idpharmacy)
VALUES(SEQ_PRODUCT.nextval, 'Brufen', 'Para as dores de coto', 4, 0.7,15, 2);
INSERT INTO Product(id, name, description, price, weight, stock, idpharmacy)
VALUES(SEQ_PRODUCT.nextval, 'Dulcolax', 'Para as voltas ao intestino', 10, 1,23, 2);
INSERT INTO Product(id, name, description, price, weight,stock, idpharmacy)
VALUES(SEQ_PRODUCT.nextval, 'SARS-COV2', 'Para curar o BIXO', 100, 0.2, 10, 2);

INSERT INTO ClientOrder(id, dateorder, finalprice, finalweight, idclient, iddelivery) 
VALUES (seq_clientorder.nextval,sysdate, 12, 0.5, 1,3);
INSERT INTO ClientOrder(id, dateorder, finalprice, finalweight, idclient) 
VALUES (seq_clientorder.nextval,sysdate, 10, 1, 2);

INSERT INTO Vehicle(id,licensePlate, maxbattery, actualBattery, status,ischarging, ah_Battery, v_Battery, enginePower, weight, idPharmacy, idTypeVehicle ) 
VALUES(seq_vehicle.nextval, 'AB-10-VB',100,70, 0,0, 120, 300, 500, 500, 1, 1);
INSERT INTO Vehicle(id,licensePlate, maxbattery, actualBattery, status,ischarging, ah_Battery, v_Battery, enginePower, weight, idPharmacy,idTypeVehicle ) 
VALUES(seq_vehicle.nextval, 'MN-21-14',100,70, 0,0, 120, 300, 500, 500, 1,1);
INSERT INTO Vehicle(id,licensePlate, maxbattery, actualBattery, status,ischarging, ah_Battery, v_Battery, enginePower, weight,maxWeightCapacity, idPharmacy, idTypeVehicle ) 
VALUES(seq_vehicle.nextval, 'AA-15-BB',100,70, 1,0, 120, 300, 500, 500, 20,1, 2);
INSERT INTO Vehicle(id,licensePlate, maxbattery, actualBattery, status,ischarging, ah_Battery, v_Battery, enginePower, weight, maxWeightCapacity,idPharmacy,idTypeVehicle ) 
VALUES(seq_vehicle.nextval, 'MM-20-15',100,70, 0,0, 120, 300, 500, 500,15, 1,2);

INSERT INTO ClientOrder (id, dateorder,finalprice,finalweight,status,idclient)
VALUES (seq_ClientOrder.nextval, sysdate, 12,23,0,1);
INSERT INTO ClientOrder (id, dateorder,finalprice,finalweight,status,idclient)
VALUES (seq_ClientOrder.nextval, sysdate, 12,23,0,1);

INSERT INTO ProductOrder (idOrder, idProduct, ProductQuantity) VALUES (1, 1, 2);
INSERT INTO ProductOrder (idOrder, idProduct, ProductQuantity) VALUES (1, 2, 2);
INSERT INTO ProductOrder (idOrder, idProduct, ProductQuantity) VALUES (2, 4, 1);

insert into delivery (id, necessaryEnergy, distance, weight, idcourier)
values (seq_delivery.nextval, 10, 3, 5, 61);
insert into delivery (id, necessaryEnergy, distance, weight, idcourier)
values (seq_delivery.nextval, 10, 3, 4, 2);

INSERT INTO park (id, maxcapacity, actualcapacity, maxchargingplaces, actualChargingPlaces,power, idpharmacy, idtypepark)
VALUES(seq_park.nextval,1,1,1,1,300,1,1);


