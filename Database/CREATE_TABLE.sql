DROP TABLE Client
DROP TABLE Cart
DROP TABLE Administrator
DROP TABLE Courier
DROP TABLE ElectricScooter
DROP TABLE Pharmacy
DROP TABLE Address
DROP TABLE Product
DROP TABLE Stock
DROP TABLE Park
DROP TABLE Invoice
DROP TABLE OrderClient
DROP TABLE Delivery
DROP TABLE CreditCard


CREATE TABLE Client (
	id			INTEGER		constraint pk_idclient PRIMARY KEY,
	email		VARCHAR(30) constraint nn_emailclient		NOT NULL UNIQUE,
	name		VARCHAR(50)	constraint nn_nameclient	NOT NULL,
	NIF			INTEGER		constraint nn_nifclient	UNIQUE NOT NULL,
	password	VARCHAR(40)	constraint nn_passwordclient	NOT NULL,
	credits		INTEGER      constraint nn_creditsclient  DEFAULT 0
);

CREATE TABLE Courier (
	id			INTEGER		constraint pk_idCourier	PRIMARY KEY
	email		VARCHAR(30)	constraint nn_emailCourier			NOT NULL UNIQUE,
	name		VARCHAR(50)	constraint nn_nameCourier			NOT NULL,
	maxWeightCapacity	NUMERIC(3,1)	constraint nn_maxWeightCapacity     NOT NULL
);

CREATE TABLE Administrator (
	email		VARCHAR(30)		PRIMARY KEY,
	password	VARCHAR(40)		NOT NULL,
);

CREATE TABLE Pharmacy (
	id					INTEGER		constraint pk_idPharmacy    PRIMARY KEY,
	name				VARCHAR(50)		constraint nn_namePharmacy  NOT NULL UNIQUE,
);

CREATE TABLE Address (
	latitude			NUMERIC(15,7),
	longitude			NUMERIC(15,7),
	street				VARCHAR(50),
    CONSTRAINT pkaddress primary key (latitude, longitude)
);

CREATE TABLE CreditCard (
	numberCC				INTEGER			constraint pk_numberCC  PRIMARY KEY,
	monthExpiration			INTEGER			constraint nn_monthExpiration   NOT NULL,
	yearExpiration			INTEGER			constraint nn_yearExpiration    NOT NULL,
	CCV						INTEGER			constraint nn_CCV   NOT NULL
);

CREATE TABLE ElectricScooter (
	id						INTEGER		PRIMARY KEY,
	maxBattery				NUMERIC(5,2)	NOT NULL,
	actualBattery			NUMERIC(5,2)	NOT NULL,
);

CREATE TABLE Invoice (
	id			INTEGER			            constraint pk_idInvoice     PRIMARY KEY,
	dateInvoice	DATE			            constraint nn_dateInvoice   NOT NULL,
	finalPrice	NUMBER(5,2)		            constraint nn_finalPrice    NOT NULL,
);

CREATE TABLE Cart (
	id					INTEGER			PRIMARY KEY,
	productQuantity		INTEGER			NOT NULL,	
	finalPrice			NUMBER(8,2)		NOT NULL,
	finalWeight			NUMBER(5,2)		NOT NULL,
);

CREATE TABLE ClientOrder (
    id					INTEGER			constraint pk_idClientOrder PRIMARY KEY,
	dateOrder			TIMESTAMP		constraint nn_ddateOrder    NOT NULL,
	status				NUMBER(1,0)		constraint nn_status        NULL	CHECK (value in (0,1)),
);

CREATE TABLE Delivery (
	id					INTEGER			PRIMARY KEY,
	necessaryEnergy		NUMBER(10, 5)	NOT NULL,
	distance			NUMBER(5,4)		NOT NULL,
	weight				NUMBER(5,4)		NOT NULL,
);

CREATE TABLE Stock (
	quantity			INTEGER			constraint nn_quantity  NOT NULL,
);

CREATE TABLE Park (
	id						INTEGER		PRIMARY KEY,
	maxCapacity				INTEGER		NOT NULL,
	maxChargingPlaces		INTEGER		NOT NULL,
	actualChargingPlaces	INTEGER		NOT NULL,
);

CREATE TABLE Product (
	id						INTEGER			constraint pk_idProduct     PRIMARY KEY,
	name					VARCHAR(40)		constraint nn_nameProduct   NOT NULL,
	description				VARCHAR(50)		constraint nn_descriptionProduct
	price					NUMBER(4,2)		constraint nn_priceProduct  NOT NULL,
	weight					NUMBER(5,2)		constraint nn_weightProduct NOT NULL
);
	

	

