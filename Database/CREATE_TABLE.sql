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
	id			INTEGER		constraint pkidclient PRIMARY KEY,
	email		VARCHAR(30) constraint nn_emailclient		NOT NULL UNIQUE,
	name		VARCHAR(50)	constraint nn_nameclient	NOT NULL,
	NIF			INTEGER		constraint nn_nifclient	UNIQUE NOT NULL,
	password	VARCHAR(40)	constraint nn_passwordclient	NOT NULL,
	credits		INTEGER      constraint nn_creditsclient  DEFAULT 0
);

CREATE TABLE Courier (
	id			INTEGER	 pkidcourier		PRIMARY KEY
	email		VARCHAR(30)				NOT NULL UNIQUE,
	name		VARCHAR(50)				NOT NULL,
	maxWeightCapacity	NUMERIC(3,1)	
);

CREATE TABLE Administrator (
	email		VARCHAR(30)		PRIMARY KEY,
	password	VARCHAR(40)		NOT NULL,
);

CREATE TABLE Pharmacy (
	id					INTEGER		PRIMARY KEY,
	name				VARCHAR(50)		NOT NULL UNIQUE,
);

CREATE TABLE Address (
	latitude			NUMERIC(15,7),
	longitude			NUMERIC(15,7),
	street				VARCHAR(50),
    CONSTRAINT pkaddress primary key (latitude, longitude)
);

CREATE TABLE CreditCard (
	numberCC				INTEGER			PRIMARY KEY,
	monthExpiration			INTEGER			NOT NULL,
	yearExpiration			INTEGER			NOT NULL,
	CCV						INTEGER			NOT NULL
);

CREATE TABLE ElectricScooter (
	id						INTEGER		PRIMARY KEY,
	maxBattery				NUMERIC(5,2)	NOT NULL,
	actualBattery			NUMERIC(5,2)	NOT NULL,
);

CREATE TABLE Invoice (
	id			INTEGER			PRIMARY KEY,
	dateInvoice	DATE			NOT NULL,
	finalPrice	NUMBER(5,2)		NOT NULL,
);

CREATE TABLE Cart (
	id					INTEGER			PRIMARY KEY,
	productQuantity		INTEGER			NOT NULL,	
	finalPrice			NUMBER(8,2)		NOT NULL,
	finalWeight			NUMBER(5,2)		NOT NULL,
);

CREATE TABLE ClientOrder (
    id					INTEGER			PRIMARY KEY,
	dateOrder			TIMESTAMP		NOT NULL,
	status				NUMBER(1,0)		NOT NULL	CHECK (value in (0,1)),
);

CREATE TABLE Delivery (
	id					INTEGER			PRIMARY KEY,
	necessaryEnergy		NUMBER(10, 5)	NOT NULL,
	distance			NUMBER(5,4)		NOT NULL,
	weight				NUMBER(5,4)		NOT NULL,
);

CREATE TABLE Stock (
	quantity			INTEGER			NOT NULL,
);

CREATE TABLE Park (
	id						INTEGER		PRIMARY KEY,
	maxCapacity				INTEGER		NOT NULL,
	maxChargingPlaces		INTEGER		NOT NULL,
	actualChargingPlaces	INTEGER		NOT NULL,
);

CREATE TABLE Product (
	id						INTEGER			PRIMARY KEY,
	name					VARCHAR(40)		NOT NULL,
	description				VARCHAR(50)		
	price					NUMBER(4,2)		NOT NULL,
	weight					NUMBER(5,2)		NOT NULL,
);
	

	

