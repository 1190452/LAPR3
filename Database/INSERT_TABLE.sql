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


INSERT INTO AppUser(email,password,role) VALUES('bpereira750@gmail.com', 'qwerty', 'administrator');
INSERT INTO Administrator(email,name) VALUES('bpereira750@gmail.com', 'Antonio');

INSERT INTO TypeVehicle(id, name) VALUES (1, 'Electric Scooter');
INSERT INTO TypeVehicle(id, name) VALUES (2, 'Drone');

INSERT INTO TypePark(id, name) VALUES (1, 'Park Electric Scooter');
INSERT INTO TypePark(id, name) VALUES (2, 'Park Drone');

INSERT INTO TypePath(id, name) VALUES (1, 'Land');
INSERT INTO TypePath(id, name) VALUES (2, 'Air');
---------------------------------------------------------------------------------------------------------------

INSERT INTO Address VALUES (41.15833, -8.6290810,10, 'Pra�a Mouzinho de Albuquerque', 0, '4100-369', 'Porto');
INSERT INTO Address VALUES (41.18200, -8.6011920,10, 'Alameda Prof. Hern�ni Monteiro', 0, '4200-319', 'Porto');
INSERT INTO Address VALUES (41.20000, -8.77819,10, 'Pra�a das Flores', 0, '4000-364', 'Porto');
INSERT INTO Address VALUES (41.20500, -8.7781830,10, 'Avenida Fern�o Magalh�es ', 0, '4580-023', 'Porto');

INSERT INTO creditcard(numbercc,monthexpiration,yearexpiration,ccv) VALUES(1234567891011121, 08, 2021, 456);
INSERT INTO creditcard(numbercc,monthexpiration,yearexpiration,ccv)VALUES(1234567891011122, 09, 2021, 567);

INSERT INTO AppUser(email,password,role) VALUES('client1@isep.ipp.pt', 'qwerty', 'client');
INSERT INTO AppUser(email,password,role) VALUES('client2@isep.ipp.pt', 'qwerty', 'client');
INSERT INTO AppUser(email,password,role) VALUES('courier1@isep.ipp.pt', 'qwerty', 'courier');
INSERT INTO AppUser(email,password,role) VALUES('courier2@isep.ipp.pt', 'qwerty', 'courier');
 
INSERT INTO Pharmacy(id,name, Addresslatitude, Addresslongitude,addressaltitude, emailpharmacy, emailAdministrator)
VALUES(SEQ_PHARMACY.nextval, 'Farm�cia Porto',41.15833, -8.6290810,10,'pharmacy1@isep.ipp.pt','admin@isep.ipp.pt');
INSERT INTO Pharmacy(id,name, Addresslatitude, Addresslongitude,addressaltitude,emailpharmacy, emailAdministrator)
VALUES(SEQ_PHARMACY.nextval, 'Farm�cia da Avenida',41.20000, -8.77819,10,'pharmacy2@isep.ipp.pt','admin@isep.ipp.pt');

INSERT INTO park (id, maxcapacity, actualcapacity, maxchargingplaces, actualChargingPlaces,power, idpharmacy, idtypepark)
VALUES(seq_park.nextval,10,10,1,1,300,1,1);
INSERT INTO park (id, maxcapacity, actualcapacity, maxchargingplaces, actualChargingPlaces,power, idpharmacy, idtypepark)
VALUES(seq_park.nextval,10,10,1,1,300,1,2);
INSERT INTO park (id, maxcapacity, actualcapacity, maxchargingplaces, actualChargingPlaces,power, idpharmacy, idtypepark)
VALUES(seq_park.nextval,10,10,1,1,300,2,1);
INSERT INTO park (id, maxcapacity, actualcapacity, maxchargingplaces, actualChargingPlaces,power, idpharmacy, idtypepark)
VALUES(seq_park.nextval,10,10,1,1,300,2,2);

INSERT INTO Client(id, email, name, NIF, credits, Addresslatitude, Addresslongitude,addressaltitude,numberCreditCard) 
VALUES(SEQ_CLIENT.nextval,'client1@isep.ipp.pt', 'Joaquim Alberto', 123456789, 0, 41.20500, -8.7781830,10, 1234567891011121);
INSERT INTO Client(id, email, name, NIF, credits, Addresslatitude, Addresslongitude,addressaltitude, numberCreditCard) 
VALUES(SEQ_CLIENT.nextval, 'client2@isep.ipp.pt', 'Hernani Carvalho', 134568795,0, 41.182, -8.601192, 10, 1234567891011122);
 

INSERT INTO Courier(id, name, email, NIF, NSS, weight, idPharmacy)
VALUES(SEQ_COURIER.nextval, 'Manuel', 'courier1@isep.ipp.pt', 196547823, 23456987265, 80, 1);
INSERT INTO Courier(id, name, email, NIF, NSS,  weight, idPharmacy)
VALUES(SEQ_COURIER.nextval, 'Jo�o','courier2@isep.ipp.pt', 165478923, 21453269857,  75, 2);

INSERT INTO Product(id, name, description, price, weight,stock, idpharmacy)
VALUES(SEQ_PRODUCT.nextval, 'Ben-u-ron', 'Para as dores de cabe�a', 5, 0.5,70, 1);
INSERT INTO Product(id, name, description, price, weight, stock, idpharmacy)
VALUES(SEQ_PRODUCT.nextval, 'Brufen', 'Para as dores de coto', 4, 0.7,5, 1);
INSERT INTO Product(id, name, description, price, weight, stock, idpharmacy)
VALUES(SEQ_PRODUCT.nextval, 'Brufen', 'Para as dores de coto', 4, 0.7,15, 2);
INSERT INTO Product(id, name, description, price, weight, stock, idpharmacy)
VALUES(SEQ_PRODUCT.nextval, 'Ben-u-ron', 'Para as dores de cabe�a', 10, 1,23, 2);
INSERT INTO Product(id, name, description, price, weight,stock, idpharmacy)
VALUES(SEQ_PRODUCT.nextval, 'vacina', 'Para as dores de cabe�a', 5, 0.5,20, 1);
INSERT INTO Product(id, name, description, price, weight,stock, idpharmacy)
VALUES(SEQ_PRODUCT.nextval, 'vacina', 'Para as dores de cabe�a', 5, 0.5,50, 2);

INSERT INTO Vehicle(id,licensePlate, maxbattery, actualBattery, status,ischarging, ah_Battery, v_Battery, enginePower, weight,frontalarea, idPharmacy, idTypeVehicle ) 
VALUES(seq_vehicle.nextval,'AB-10-VB', 2, 2, 0, 0, 120, 300, 500, 500,2, 1, 1);
INSERT INTO Vehicle(id,licensePlate, maxbattery, actualBattery, status,ischarging, ah_Battery, v_Battery, enginePower, weight,frontalarea, idPharmacy,idTypeVehicle ) 
VALUES(seq_vehicle.nextval,'MN-21-14', 2, 2, 0, 0, 120, 300, 500, 500, 2, 2, 1);
INSERT INTO Vehicle(id,licensePlate, maxbattery, actualBattery, status,ischarging, ah_Battery, v_Battery, enginePower, weight,maxWeightCapacity,frontalarea, idPharmacy, idTypeVehicle ) 
VALUES(seq_vehicle.nextval,'AA-15-BB',1.5,1.5, 0, 0, 120, 300, 500, 500, 20, 0.5, 1, 2);
INSERT INTO Vehicle(id,licensePlate, maxbattery, actualBattery, status,ischarging, ah_Battery, v_Battery, enginePower, weight, maxWeightCapacity,frontalarea,idPharmacy,idTypeVehicle ) 
VALUES(seq_vehicle.nextval,'MM-20-15', 1.5, 1.5, 0, 0, 120, 300, 500, 500,15,0.5, 2,2);






