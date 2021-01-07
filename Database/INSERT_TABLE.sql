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

--select * from user_sequences;


INSERT INTO Address VALUES (41.15833, -8.62908, 'Praça Mouzinho de Albuquerque');
INSERT INTO address VALUES (41.18200, -8.60119, 'Hospital São João');
INSERT INTO Address(latitude, longitude, street) VALUES(41.17734 ,-8.65741,'Estrada da Circunvalação');

INSERT INTO creditcard VALUES(1234567891011121, 08, 2021, 456);
INSERT INTO creditcard VALUES(1234567891011122, 09, 2021, 567);

INSERT INTO AppUser(email,password,role) VALUES('client1@isep.ipp.pt', 'qwerty', 'client');
INSERT INTO AppUser(email,password,role) VALUES('client2@isep.ipp.pt', 'qwerty', 'client');

INSERT INTO AppUser(email,password,role) VALUES('courier1@isep.ipp.pt', 'qwerty', 'courier');
INSERT INTO AppUser(email,password,role) VALUES('courier2@isep.ipp.pt', 'qwerty', 'courier');

INSERT INTO AppUser(email,password,role) VALUES('admin@isep.ipp.pt', 'qwerty', 'administrator');

INSERT INTO Administrator(email,name) VALUES('admin@isep.ipp.pt', 'Antonio');

INSERT INTO Pharmacy(id,name, Addresslatitude, Addresslongitude, emailAdministrator)
VALUES(1, 'Farmácia Porto',41.17734 ,-8.65741,'admin@isep.ipp.pt');

INSERT INTO Client(id, email, name, NIF, credits, Addresslatitude, Addresslongitude,numberCreditCard) 
VALUES(1,'client1@isep.ipp.pt', 'Joaquim Alberto', 123456789, 0, 41.15833, -8.62908, 1234567891011121);
INSERT INTO Client(id, email, name, NIF, credits, Addresslatitude, Addresslongitude,numberCreditCard) 
VALUES(2, 'client2@isep.ipp.pt', 'Hernani Carvalho', 134568795,0, 41.18200, -8.60119, 1234567891011122);

INSERT INTO Courier(id, name, email, NIF, NSS, maxWeightCapacity,weight, idPharmacy)
VALUES(1, 'Manuel', 'courier1@isep.ipp.pt', 196547823, 23456987265, 10 ,80, 1);
INSERT INTO Courier(id, name, email, NIF, NSS, maxWeightCapacity, weight, idPharmacy)
VALUES(2, 'João','courier2@isep.ipp.pt', 165478923, 21453269857, 23, 75, 1);



