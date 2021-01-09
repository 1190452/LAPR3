DROP TABLE Client CASCADE CONSTRAINTS PURGE;
DROP TABLE Administrator CASCADE CONSTRAINTS PURGE;
DROP TABLE Courier CASCADE CONSTRAINTS PURGE;
DROP TABLE ElectricScooter CASCADE CONSTRAINTS PURGE;
DROP TABLE Pharmacy CASCADE CONSTRAINTS PURGE;
DROP TABLE Address CASCADE CONSTRAINTS PURGE;
DROP TABLE Product CASCADE CONSTRAINTS PURGE;
DROP TABLE Park CASCADE CONSTRAINTS PURGE;
DROP TABLE Invoice CASCADE CONSTRAINTS PURGE;
DROP TABLE ClientOrder CASCADE CONSTRAINTS PURGE;
DROP TABLE Delivery CASCADE CONSTRAINTS PURGE;
DROP TABLE CreditCard CASCADE CONSTRAINTS PURGE;
DROP TABLE AppUser CASCADE CONSTRAINTS PURGE;
DROP TABLE ProductOrder CASCADE CONSTRAINTS PURGE;


CREATE TABLE AppUser (
	email			    VARCHAR(40) PRIMARY KEY,
	password			VARCHAR(40) NOT NULL,
	role				VARCHAR(40) NOT NULL
);

CREATE TABLE Address (
	latitude			NUMBER (20, 15),
	longitude			NUMBER (20,15),
	street				VARCHAR(50) NOT NULL,
    doorNumber          INTEGER     NOT NULL,
    zipCode             VARCHAR(10) NOT NULL,
    locality            VARCHAR(40) NOT NULL,
    CONSTRAINT pkaddress PRIMARY KEY (latitude, longitude)
);

CREATE TABLE Client (
	id		    INTEGER		        CONSTRAINT pk_idclient PRIMARY KEY,
    name		VARCHAR(50)	        CONSTRAINT nn_nameclient	NOT NULL,
	email		VARCHAR(50),
	NIF			NUMBER(9)		    CONSTRAINT nn_nifclient	UNIQUE NOT NULL,
	credits		INTEGER  default 0  CONSTRAINT nn_creditsclient NOT NULL, 
    Addresslatitude  NUMBER(20,15),
    Addresslongitude NUMBER(20,15),
    numberCreditCard NUMBER(16)
);

CREATE TABLE Courier (
	id			INTEGER		CONSTRAINT pk_idCourier	   PRIMARY KEY,
	name		VARCHAR(50)	CONSTRAINT nn_nameCourier  NOT NULL,
    email		VARCHAR(40)	CONSTRAINT nn_emailCourier NOT NULL UNIQUE,
    NIF         NUMBER(9)   CONSTRAINT nn_nifcourier   NOT NULL,
    NSS         NUMBER(11)  CONSTRAINT nn_ssncourier   NOT NULL,
	maxWeightCapacity	NUMBER(3,1)	CONSTRAINT nn_maxWeightCapacity  NOT NULL,
    weight      NUMBER              CONSTRAINT nn_weightcourier      NOT NULL,
    idPharmacy  INTEGER		        CONSTRAINT nn_idPharmacyCourier	 NOT NULL
);

CREATE TABLE Administrator (
	email		VARCHAR(40)		PRIMARY KEY CONSTRAINT fk_emailAdmin REFERENCES AppUser(email),
    name        VARCHAR(30)     CONSTRAINT nn_nameAdmin NOT NULL
);

CREATE TABLE Pharmacy (
	id					    INTEGER		     CONSTRAINT pk_idPharmacy    PRIMARY KEY,
	name				    VARCHAR(50)		 CONSTRAINT nn_namePharmacy  NOT NULL,
    Addresslatitude         NUMBER(20,15),
    Addresslongitude        NUMBER(20,15),
    emailAdministrator      VARCHAR(30)
);



CREATE TABLE CreditCard (
	numberCC				NUMBER(16)		CONSTRAINT pk_numberCC  PRIMARY KEY,
	monthExpiration			NUMBER(2)		CONSTRAINT chk_monthExpiration  CHECK(monthExpiration between 1 and 12) NOT NULL,
	yearExpiration			NUMBER(4)		CONSTRAINT nn_yearExpiration    NOT NULL,
	CCV						NUMBER(3)		CONSTRAINT nn_CCV   NOT NULL
);

CREATE TABLE ElectricScooter (
	id						INTEGER	        CONSTRAINT pk_idElectricScooter	PRIMARY KEY,
	maxBattery				NUMBER(5,2)	    CONSTRAINT nn_maxBattery        NOT NULL,
	actualBattery			NUMBER(5,2)     CONSTRAINT nn_actualBattery	    NOT NULL,
    status      			NUMBER(1,0)	 DEFAULT 0   CONSTRAINT chkstatusscooter CHECK (status in (0,1))	NOT NULL,
    ah_battery              NUMBER(7,2)     CONSTRAINT nn_ahbattery         NOT NULL,
    v_battery               NUMBER(7,2)     CONSTRAINT nn_vbattery          NOT NULL,
    enginePower             NUMBER(7,2)     CONSTRAINT nn_enginepower       NOT NULL,
    weight                  NUMBER(7,2)     CONSTRAINT nn_weightScooter     NOT NULL,
    idPharmacy              INTEGER		    NOT NULL
);

CREATE TABLE Invoice (
	id			INTEGER			            CONSTRAINT pk_idInvoice     PRIMARY KEY,
	dateInvoice	DATE			            CONSTRAINT nn_dateInvoice   NOT NULL,
	finalPrice	NUMBER(5,2)		            CONSTRAINT nn_finalPrice    NOT NULL,
    idClient    INTEGER		                CONSTRAINT nn_idClientInvoice   NOT NULL,
    idOrder     INTEGER		                CONSTRAINT nn_idOrderInvoice    NOT NULL
);


CREATE TABLE ClientOrder (
    id					INTEGER			CONSTRAINT pk_idClientOrder PRIMARY KEY,
	dateOrder			TIMESTAMP		CONSTRAINT nn_ddateOrder    NOT NULL,
    finalPrice          NUMBER(5)       CONSTRAINT nn_finalPriceOrder   NOT NULL,
    finalWeight         NUMBER(5)       CONSTRAINT nn_finalweightOrder  NOT NULL,
	status				NUMBER(1,0)	DEFAULT 0    CONSTRAINT chkStstus CHECK (status in (0,1)) NOT NULL,
    idClient            INTEGER			CONSTRAINT nn_idClientOrder NOT NULL
);

CREATE TABLE Delivery (
	id					INTEGER			PRIMARY KEY,
	necessaryEnergy		NUMBER(10, 5)	NOT NULL,
	distance			NUMBER(5,4)		NOT NULL,
	weight				NUMBER(5,4)		NOT NULL,
    idOrder             INTEGER         CONSTRAINT nn_idOrder    NOT NULL,
    idElectricScooter   INTEGER         CONSTRAINT nn_idElectricScooter    NOT NULL,
    idCourier           INTEGER         CONSTRAINT nn_idCourier  NOT NULL
);

CREATE TABLE Product (
	id						INTEGER			CONSTRAINT pk_idProductProduct  PRIMARY KEY,
	name					VARCHAR(40)		CONSTRAINT nn_nameProduct   NOT NULL UNIQUE,
	description				VARCHAR(50),
	price					NUMBER		CONSTRAINT nn_priceProduct  NOT NULL,
	weight					NUMBER(5,2)		CONSTRAINT nn_weightProduct NOT NULL,
    stock                   INTEGER     CONSTRAINT nn_stockProduct  NOT NULL,
    idPharmacy              INTEGER
);


CREATE TABLE Park (
	id						INTEGER		PRIMARY KEY,
	maxCapacity				NUMBER		NOT NULL,
    actualCapacity          NUMBER      NOT NULL,
	maxChargingPlaces		NUMBER		NOT NULL,
	actualChargingPlaces	NUMBER		NOT NULL,
    idPharmacy              INTEGER		NOT NULL
);

CREATE TABLE ProductOrder(
    idOrder         INTEGER  PRIMARY KEY CONSTRAINT fk_clientorder REFERENCES ClientOrder(id),
    idProduct       INTEGER,
    productQuantity NUMBER
);



ALTER TABLE Client ADD CONSTRAINT fk_addressLatitudeClient FOREIGN KEY (Addresslatitude, Addresslongitude) REFERENCES Address(latitude, longitude);
ALTER TABLE Client ADD CONSTRAINT fk_creditCardNumberClient FOREIGN KEY (numberCreditCard) REFERENCES CreditCard(numberCC);

ALTER TABLE Invoice ADD CONSTRAINT fk_clientIDInvoice FOREIGN KEY (idClient) REFERENCES Client(id);
ALTER TABLE Invoice ADD CONSTRAINT fk_orderIDInvoice FOREIGN KEY (idOrder) REFERENCES ClientOrder(id);

ALTER TABLE Product ADD CONSTRAINT fk_productIDPharmacyProduct FOREIGN KEY (idPharmacy) REFERENCES Pharmacy(id);


ALTER TABLE ClientOrder ADD CONSTRAINT fk_clientIDClientOrder  FOREIGN KEY (idClient)  REFERENCES Client(id);

ALTER TABLE Courier ADD CONSTRAINT fk_pharmacyIDCourier  FOREIGN KEY (idPharmacy) REFERENCES Pharmacy(id);

ALTER TABLE Pharmacy ADD CONSTRAINT fk_addressLatitudePharmacy FOREIGN KEY (Addresslatitude, Addresslongitude) REFERENCES Address(latitude, longitude);
ALTER TABLE Pharmacy ADD CONSTRAINT fk_administratorIDPharmacy  FOREIGN KEY (emailAdministrator) REFERENCES Administrator(email);

ALTER TABLE Park ADD CONSTRAINT fk_IDPharmacyPark FOREIGN KEY (idPharmacy) REFERENCES Pharmacy(id);

ALTER TABLE ElectricScooter ADD CONSTRAINT fk_IDPharmacyScooter FOREIGN KEY (idPharmacy) REFERENCES Pharmacy(id);

ALTER TABLE Delivery ADD CONSTRAINT fk_IDOrderDelivery FOREIGN KEY (idOrder) REFERENCES ClientOrder(id);
ALTER TABLE Delivery ADD CONSTRAINT fk_IDScooterDelivery FOREIGN KEY (idElectricScooter) REFERENCES ElectricScooter(id);
ALTER TABLE  Delivery ADD CONSTRAINT fk_IDCourierDelivery FOREIGN KEY (idCourier) REFERENCES Courier(id);

ALTER TABLE Client ADD CONSTRAINT fk_emailCliente FOREIGN KEY (email) REFERENCES AppUser(email);

ALTER TABLE Courier ADD CONSTRAINT fk_emailCourier FOREIGN KEY (email) REFERENCES AppUser(email);

ALTER TABLE ProductOrder ADD CONSTRAINT fk_idProduct FOREIGN KEY (idProduct) REFERENCES Product(id);



	

	

