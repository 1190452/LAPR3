INSERT INTO Address VALUES (41.15833, -8.62908, 'Praça Mouzinho de Albuquerque');
INSERT INTO address VALUES (41.18200, -8.60119, 'Hospital São João');

INSERT INTO creditcard VALUES(1234567891011121, 08, 2021, 456);
INSERT INTO creditcard VALUES(1234567891011122, 09, 2021, 567);

INSERT INTO Client VALUES(1, 'client1@isep.ipp.pt', 'Joaquim Alberto', 123456789, 'albertojoaquim', 0, 41.15833, -8.62908, 1234567891011121);
INSERT INTO Client VALUES(2, 'client2@isep.ipp.pt', 'Hernani Carvalho', 134568795, 'carvalhohernani', 0, 41.18200, -8.60119, 1234567891011122);



select * from client;
select * from address;
select * from creditcard;

select * from sailors;