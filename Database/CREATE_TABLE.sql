DROP TABLE Client CASCADE CONSTRAINTS PURGE;
DROP TABLE Administrator CASCADE CONSTRAINTS PURGE;
DROP TABLE Courier CASCADE CONSTRAINTS PURGE;
DROP TABLE ElectricScooter CASCADE CONSTRAINTS PURGE;
DROP TABLE Pharmacy CASCADE CONSTRAINTS PURGE;
DROP TABLE Address CASCADE CONSTRAINTS PURGE;
DROP TABLE Product CASCADE CONSTRAINTS PURGE;
DROP TABLE Stock CASCADE CONSTRAINTS PURGE;
DROP TABLE Park CASCADE CONSTRAINTS PURGE;
DROP TABLE Invoice CASCADE CONSTRAINTS PURGE;
DROP TABLE ClientOrder CASCADE CONSTRAINTS PURGE;
DROP TABLE Delivery CASCADE CONSTRAINTS PURGE;
DROP TABLE CreditCard CASCADE CONSTRAINTS PURGE;
drop table AppUser CASCADE CONSTRAINTS PURGE;

CREATE TABLE AppUser (
	email			VARCHAR(40) PRIMARY KEY,
	password			VARCHAR(40) NOT NULL,
	role				VARCHAR(40) NOT NULL
);

CREATE TABLE Address (
	latitude			number,
	longitude			number,
	street				VARCHAR(50) NOT NULL,
    CONSTRAINT pkaddress primary key (latitude, longitude)
);

CREATE TABLE Client (
	id			INTEGER		constraint pk_idclient PRIMARY KEY,
    name		VARCHAR(50)	constraint nn_nameclient	NOT NULL,
	email		VARCHAR(30),
	NIF			number(9)		constraint nn_nifclient	UNIQUE NOT NULL,
	credits		INTEGER  default 0    constraint nn_creditsclient NOT NULL, 
    Addresslatitude number,
    Addresslongitude number,
    numberCreditCard integer
);

CREATE TABLE Courier (
	id			INTEGER		constraint pk_idCourier	PRIMARY KEY,
	name		VARCHAR(50)	constraint nn_nameCourier			NOT NULL,
    email		VARCHAR(40)	constraint nn_emailCourier			NOT NULL UNIQUE,
    NIF         NUMBER(9) constraint nn_nifcourier      NOT NULL,
    NSS         NUMBER(11) constraint nn_ssncourier NOT NULL,
	maxWeightCapacity	number(3,1)	constraint nn_maxWeightCapacity     NOT NULL,
    weight      number  constraint nn_weightcourier     NOT NULL,
    idPharmacy  INTEGER		constraint nn_idPharmacyCourier	NOT NULL
);

CREATE TABLE Administrator (
	email		VARCHAR(40)		PRIMARY KEY constraint fk_emailAdmin REFERENCES AppUser(email),
    name        VARCHAR(30)     CONSTRAINT nn_nameAdmin NOT NULL
);

CREATE TABLE Pharmacy (
	id					    INTEGER		        constraint pk_idPharmacy    PRIMARY KEY,
	name				    VARCHAR(50)		    constraint nn_namePharmacy  NOT NULL,
    Addresslatitude         number,
    Addresslongitude        number,
    emailAdministrator      varchar(30)
);



CREATE TABLE CreditCard (
	numberCC				INTEGER			constraint pk_numberCC  PRIMARY KEY,
	monthExpiration			INTEGER			constraint nn_monthExpiration   NOT NULL,
	yearExpiration			INTEGER			constraint nn_yearExpiration    NOT NULL,
	CCV						INTEGER			constraint nn_CCV   NOT NULL
);

CREATE TABLE ElectricScooter (
	id						INTEGER	CONSTRAINT pk_idElectricScooter	PRIMARY KEY,
	maxBattery				number(5,2)	    CONSTRAINT nn_maxBattery    NOT NULL,
	actualBattery			number(5,2)     CONSTRAINT nn_actualBattery	NOT NULL,
    status      			NUMBER(1,0)	    constraint chkstatusscooter CHECK (status in (0,1))	    NOT NULL,
    ah_battery              number(7,2)     constraint nn_ahbattery NOT NULL,
    v_battery               number(7,2)     constraint nn_vbattery NOT NULL,
    enginePower             number(7,2)     constraint nn_enginepower NOT NULL,
    weight                  number(7,2)     constraint nn_weightScooter NOT NULL,
    idPharmacy              INTEGER		NOT NULL
);

CREATE TABLE Invoice (
	id			INTEGER			            constraint pk_idInvoice     PRIMARY KEY,
	dateInvoice	DATE			            constraint nn_dateInvoice   NOT NULL,
	finalPrice	NUMBER(5,2)		            constraint nn_finalPrice    NOT NULL,
    idClient integer		                constraint nn_idClientInvoice    NOT NULL,
    idOrder integer		                constraint nn_idOrderInvoice    NOT NULL
);


CREATE TABLE ClientOrder (
    id					INTEGER			constraint pk_idClientOrder PRIMARY KEY,
	dateOrder			TIMESTAMP		constraint nn_ddateOrder    NOT NULL,
    finalPrice          NUMBER(5)       constraint nn_finalPriceOrder   NOT NULL,
    finalWeight         NUMBER(5)       constraint nn_finalweightOrder  NOT NULL,
	status				NUMBER(1,0)	    constraint chkStstus CHECK (status in (0,1))	    NOT NULL,
    idClient            INTEGER			constraint nn_idClientOrder NOT NULL
);

CREATE TABLE Delivery (
	id					INTEGER			PRIMARY KEY,
	necessaryEnergy		NUMBER(10, 5)	NOT NULL,
	distance			NUMBER(5,4)		NOT NULL,
	weight				NUMBER(5,4)		NOT NULL,
    idOrder            INTEGER     constraint nn_idOrder    NOT NULL,
    idElectricScooter        INTEGER        constraint nn_idElectricScooter    NOT NULL,
    idCourier            INTEGER        constraint nn_idCourier        NOT NULL
);

CREATE TABLE Product (
	id						INTEGER			constraint pk_idProductProduct     PRIMARY KEY,
	name					VARCHAR(40)		constraint nn_nameProduct   NOT NULL,
	description				VARCHAR(50),
	price					NUMBER(4,2)		constraint nn_priceProduct  NOT NULL,
	weight					NUMBER(5,2)		constraint nn_weightProduct NOT NULL,
    idPharmacy              INTEGER
);

CREATE TABLE Stock (
    idProduct           INTEGER         constraint pk_idProductStock PRIMARY KEY constraint fk_idProductStock REFERENCES Product(id),
	quantity			INTEGER			constraint nn_quantity  NOT NULL
);

CREATE TABLE Park (
	id						INTEGER		PRIMARY KEY,
	maxCapacity				INTEGER		NOT NULL,
	maxChargingPlaces		INTEGER		NOT NULL,
	actualChargingPlaces	INTEGER		NOT NULL,
    idPharmacy              INTEGER		NOT NULL
);



ALTER TABLE Client ADD CONSTRAINT fk_addressLatitudeClient FOREIGN KEY (Addresslatitude, Addresslongitude) REFERENCES Address(latitude, longitude);
ALTER TABLE Client ADD CONSTRAINT fk_creditCardNumberClient FOREIGN KEY (numberCreditCard) REFERENCES CreditCard(numberCC);

ALTER TABLE Invoice ADD CONSTRAINT fk_clientIDInvoice FOREIGN KEY (idClient) REFERENCES Client(id);
ALTER TABLE Invoice ADD CONSTRAINT fk_orderIDInvoice FOREIGN KEY (idOrder) REFERENCES ClientOrder(id);

ALTER TABLE Product ADD CONSTRAINT fk_productIDPharmacyProduct FOREIGN KEY (idPharmacy) REFERENCES Pharmacy(id);


ALTER TABLE ClientOrder ADD CONSTRAINT fk_clientIDClientOrder  FOREIGN KEY (idClient)  REFERENCES Client(id);

ALTER TABLE Courier ADD CONSTRAINT fk_pharmacyIDCourier  FOREIGN KEY (idPharmacy) REFERENCES Pharmacy(id);

ALTER TABLE Pharmacy ADD CONSTRAINT fk_addressLatitudePharmacy  FOREIGN KEY (Addresslatitude, Addresslongitude) REFERENCES Address(latitude, longitude);
ALTER TABLE Pharmacy ADD CONSTRAINT fk_administratorIDPharmacy  FOREIGN KEY (emailAdministrator) REFERENCES Administrator(email);

ALTER TABLE park ADD CONSTRAINT fk_IDPharmacyPark FOREIGN KEY (idPharmacy) REFERENCES Pharmacy(id);

ALTER TABLE ElectricScooter ADD CONSTRAINT fk_IDPharmacyScooter FOREIGN KEY (idPharmacy) REFERENCES Pharmacy(id);

ALTER TABLE Delivery ADD CONSTRAINT fk_IDOrderDelivery FOREIGN KEY (idOrder) REFERENCES ClientOrder(id);
ALTER TABLE Delivery ADD CONSTRAINT fk_IDScooterDelivery FOREIGN KEY (idElectricScooter) REFERENCES ElectricScooter(id);
ALTER TABLE  Delivery ADD CONSTRAINT fk_IDCourierDelivery FOREIGN KEY (idCourier) REFERENCES Courier(id);

ALTER TABLE Client ADD CONSTRAINT fk_emailCliente FOREIGN KEY (email) REFERENCES AppUser(email);

ALTER TABLE Courier ADD CONSTRAINT fk_emailCourier FOREIGN KEY (email) REFERENCES AppUser(email);



	

	

