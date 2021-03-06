DROP TABLE Client CASCADE CONSTRAINTS PURGE;
DROP TABLE Administrator CASCADE CONSTRAINTS PURGE;
DROP TABLE Courier CASCADE CONSTRAINTS PURGE;
DROP TABLE TypeVehicle CASCADE CONSTRAINTS PURGE;
DROP TABLE Vehicle CASCADE CONSTRAINTS PURGE;
DROP TABLE Pharmacy CASCADE CONSTRAINTS PURGE;
DROP TABLE Address CASCADE CONSTRAINTS PURGE;
DROP TABLE Product CASCADE CONSTRAINTS PURGE;
DROP TABLE TypePark CASCADE CONSTRAINTS PURGE;
DROP TABLE Park CASCADE CONSTRAINTS PURGE;
DROP TABLE Invoice CASCADE CONSTRAINTS PURGE;
DROP TABLE ClientOrder CASCADE CONSTRAINTS PURGE;
DROP TABLE Delivery CASCADE CONSTRAINTS PURGE;
DROP TABLE CreditCard CASCADE CONSTRAINTS PURGE;
DROP TABLE AppUser CASCADE CONSTRAINTS PURGE;
DROP TABLE ProductOrder CASCADE CONSTRAINTS PURGE;
DROP TABLE RestockOrder CASCADE CONSTRAINTS PURGE;
DROP TABLE RefillStock CASCADE CONSTRAINTS PURGE;
DROP TABLE TypePath CASCADE CONSTRAINTS PURGE;
DROP TABLE Path CASCADE CONSTRAINTS PURGE;


CREATE TABLE AppUser (
	email			    VARCHAR(200) PRIMARY KEY,
	password			VARCHAR(50) NOT NULL,
	role				VARCHAR(50) NOT NULL
);

CREATE TABLE Address (
	latitude			NUMBER,
	longitude			NUMBER,
    altitude            NUMBER,
	street				VARCHAR(250) NOT NULL,
    doorNumber          INTEGER      NOT NULL,
    zipCode             VARCHAR(50)  NOT NULL,
    locality            VARCHAR(250) NOT NULL,
    CONSTRAINT pkaddress PRIMARY KEY (latitude, longitude, altitude)
);

CREATE TABLE Client (
	id		    INTEGER		        CONSTRAINT pk_idclient PRIMARY KEY,
    name		VARCHAR(250)	        CONSTRAINT nn_nameclient	NOT NULL,
	email		VARCHAR(250),
	NIF			NUMBER(9)		    CONSTRAINT nn_nifclient	UNIQUE NOT NULL,
	credits		INTEGER  default 0  CONSTRAINT nn_creditsclient NOT NULL, 
    Addresslatitude  NUMBER,
    Addresslongitude NUMBER,
    AddressAltitude  NUMBER,
    numberCreditCard NUMBER(16)
);

CREATE TABLE Courier (
	id			INTEGER		    CONSTRAINT pk_idCourier	   PRIMARY KEY,
	name		VARCHAR(250)	CONSTRAINT nn_nameCourier  NOT NULL,
    email		VARCHAR(250)	CONSTRAINT nn_emailCourier NOT NULL UNIQUE,
    NIF         NUMBER(9)       CONSTRAINT nn_nifcourier   NOT NULL,
    NSS         NUMBER(11)      CONSTRAINT nn_ssncourier   NOT NULL,
	maxWeightCapacity	NUMBER(7,2)	DEFAULT 20 CONSTRAINT nn_maxWeightCapacity  NOT NULL,
    weight      NUMBER          CONSTRAINT nn_weightcourier      NOT NULL,
    status      NUMBER(1,0)	    DEFAULT 0   CONSTRAINT chkstatusCourier CHECK (status in (0,1))	NOT NULL,
    idPharmacy  INTEGER		    CONSTRAINT nn_idPharmacyCourier	 NOT NULL
);

CREATE TABLE Administrator (
	email		VARCHAR(250)	 PRIMARY KEY CONSTRAINT fk_emailAdmin REFERENCES AppUser(email),
    name        VARCHAR(250)     CONSTRAINT nn_nameAdmin NOT NULL
);

CREATE TABLE Pharmacy (
	id					    INTEGER		     CONSTRAINT pk_idPharmacy    PRIMARY KEY,
	name				    VARCHAR(250)     CONSTRAINT nn_namePharmacy  NOT NULL,
    Addresslatitude         NUMBER,
    Addresslongitude        NUMBER,
    AddressAltitude         NUMBER,
    emailPharmacy           VARCHAR(250)     CONSTRAINT nn_emailPharmacy  NOT NULL UNIQUE,
    emailAdministrator      VARCHAR(250)
);

CREATE TABLE CreditCard (
	numberCC				NUMBER(16)		CONSTRAINT pk_numberCC  PRIMARY KEY,
	monthExpiration			NUMBER(2)		CONSTRAINT chk_monthExpiration  CHECK(monthExpiration between 1 and 12) NOT NULL,
	yearExpiration			NUMBER(4)		CONSTRAINT nn_yearExpiration    NOT NULL,
	CCV						NUMBER(3)		CONSTRAINT nn_CCV   NOT NULL
);

CREATE TABLE TypeVehicle(
    id                      INTEGER         CONSTRAINT pk_idTypeVehicle	   PRIMARY KEY,
    name                    VARCHAR(200)    CONSTRAINT nn_nameTypeVehicle  NOT NULL
);

CREATE TABLE Vehicle (
    id                      INTEGER         CONSTRAINT pk_idVehicle	PRIMARY KEY,
	licensePlate            VARCHAR(10)	    CONSTRAINT nn_licensePlate      NOT NULL UNIQUE,
	maxBattery				NUMBER(10,2)	CONSTRAINT nn_maxBattery        NOT NULL,
	actualBattery			NUMBER(10,2) DEFAULT 100   CONSTRAINT nn_actualBattery	    NOT NULL,
    status      			NUMBER(1,0)	    DEFAULT 0   CONSTRAINT chkstatus        CHECK (status in (0,1))	    NOT NULL,
    ischarging              NUMBER(1,0)     DEFAULT 0   CONSTRAINT chkischarging    CHECK (ischarging in (0,1))	NOT NULL,
    ah_battery              NUMBER(7,2)     CONSTRAINT nn_ahbattery         NOT NULL,
    v_battery               NUMBER(7,2)     CONSTRAINT nn_vbattery          NOT NULL,
    enginePower             NUMBER(7,2)     CONSTRAINT nn_enginepower       NOT NULL,
    weight                  NUMBER(7,2)     CONSTRAINT nn_weight            NOT NULL,
    maxWeightCapacity	    NUMBER(7,2),
    frontalArea             NUMBER(7,2)     CONSTRAINT nn_frontalArea       NOT NULL,
    idPharmacy              INTEGER		    NOT NULL,
    idTypeVehicle           INTEGER         NOT NULL
);

CREATE TABLE Invoice (
	id			INTEGER			            CONSTRAINT pk_idInvoice     PRIMARY KEY,
	dateInvoice	DATE			            CONSTRAINT nn_dateInvoice   NOT NULL,
	finalPrice	NUMBER(15,2)		        CONSTRAINT nn_finalPrice    NOT NULL,
    idClient    INTEGER		                CONSTRAINT nn_idClientInvoice   NOT NULL,
    idOrder     INTEGER		                CONSTRAINT nn_idOrderInvoice    NOT NULL
);


CREATE TABLE ClientOrder (
    id					INTEGER			CONSTRAINT pk_idClientOrder PRIMARY KEY,
	dateOrder			TIMESTAMP		CONSTRAINT nn_ddateOrder    NOT NULL,
    finalPrice          NUMBER(15,2)    CONSTRAINT nn_finalPriceOrder   NOT NULL,
    finalWeight         NUMBER(15,2)    CONSTRAINT nn_finalweightOrder  NOT NULL,
	status				NUMBER(1,0)	    DEFAULT 0    CONSTRAINT chkStatusOrder CHECK (status in (0,1)) NOT NULL,
    complete			NUMBER(1,0)	    CONSTRAINT chkOrderComplete CHECK (complete in (0,1)) NOT NULL,
    idClient            INTEGER			CONSTRAINT nn_idClientOrder NOT NULL,
    idDelivery          INTEGER
);

CREATE TABLE Delivery (
	id					INTEGER			PRIMARY KEY,
	necessaryEnergy		NUMBER(15,2)    NOT NULL,
	distance			NUMBER(15,2)	NOT NULL,
	weight				NUMBER(7,2)		NOT NULL,
    status              NUMBER(1,0)	    DEFAULT 0   CONSTRAINT chkstatusDelivery CHECK (status in (0,1)) NOT NULL,
    licensePlateVehicle VARCHAR(10),
    idCourier           INTEGER         
);

CREATE TABLE Product (
	id						INTEGER			CONSTRAINT pk_idProductProduct  PRIMARY KEY,
	name					VARCHAR(250)	CONSTRAINT nn_nameProduct   NOT NULL,
	description				VARCHAR(250),
	price					NUMBER		    CONSTRAINT nn_priceProduct  NOT NULL,
	weight					NUMBER(7,2)		CONSTRAINT nn_weightProduct NOT NULL,
    stock                   INTEGER         CONSTRAINT nn_stockProduct  NOT NULL,
    idPharmacy              INTEGER
);

CREATE TABLE TypePark(
    id                      INTEGER         CONSTRAINT pk_idTypePark	PRIMARY KEY,
    name                    VARCHAR(200)    CONSTRAINT nn_nameTypePark  NOT NULL
);

CREATE TABLE Park (
	id						INTEGER		PRIMARY KEY,
	maxCapacity				NUMBER		NOT NULL,
    actualCapacity          NUMBER      NOT NULL,
	maxChargingPlaces		NUMBER		NOT NULL,
	actualChargingPlaces	NUMBER		NOT NULL,
    power                   NUMBER      NOT NULL,
    idPharmacy              INTEGER		NOT NULL,
    idTypePark              INTEGER     NOT NULL
);

CREATE TABLE ProductOrder(
    idOrder         INTEGER,
    idProduct       INTEGER,
    productQuantity NUMBER,
    CONSTRAINT pkproductorder PRIMARY KEY (idOrder, idProduct)
);

CREATE TABLE RestockOrder (
    id                  INTEGER     PRIMARY KEY,
    idProduct           INTEGER     constraint nn_restockproductid NOT NULL,
    productQuantity     INTEGER     constraint nn_restockproductquantity NOT NULL,
    status              NUMBER(1,0)	DEFAULT 0   CONSTRAINT chkstatusrestock CHECK (status in (0,1)) NOT NULL,
    idPharmReceiver     INTEGER     constraint nn_restockpharmrid NOT NULL,
    idPharmSender       INTEGER     constraint nn_restockpharmsid NOT NULL,
    idClientOrder       INTEGER     constraint nn_restockclientorderid NOT NULL,
    idRefillStock       INTEGER     
);


CREATE TABLE RefillStock (
	id					INTEGER			PRIMARY KEY,
	necessaryEnergy		NUMBER(10,2)	NOT NULL,
	distance			NUMBER(10,2)	NOT NULL,
	weight				NUMBER(10,2)	NOT NULL,
    status              NUMBER(1,0)	    DEFAULT 0   CONSTRAINT chkstatusRefill CHECK (status in (0,1)) NOT NULL,
    licensePlateVehicle VARCHAR(10),
    idCourier           INTEGER         
);

CREATE TABLE TypePath(
    id                 INTEGER         CONSTRAINT pk_idTypePath PRIMARY KEY,
    name               VARCHAR(200)    CONSTRAINT nn_nameTypePath  NOT NULL
);

CREATE TABLE Path (
    latitudeFrom        NUMBER,
    longitudeFrom       NUMBER,
    altitudeFrom        NUMBER,
    latitudeTo          NUMBER,
    longitudeTo         NUMBER,
    altitudeTo          NUMBER,
    roadRollingResistance NUMBER        NOT NULL,
    windSpeed           NUMBER          NOT NULL,
    windDirection       NUMBER          NOT NULL,
    idTypePath          INTEGER         NOT NULL,
    CONSTRAINT pk_addressFrom PRIMARY KEY (latitudeFrom, longitudeFrom, altitudeFrom,latitudeTo, longitudeTo, altitudeTo, idTypePath)   
);


ALTER TABLE Client ADD CONSTRAINT fk_addressLatitudeClient FOREIGN KEY (Addresslatitude, Addresslongitude, AddressAltitude) REFERENCES Address(latitude, longitude,altitude);
ALTER TABLE Client ADD CONSTRAINT fk_creditCardNumberClient FOREIGN KEY (numberCreditCard) REFERENCES CreditCard(numberCC);

ALTER TABLE Invoice ADD CONSTRAINT fk_clientIDInvoice FOREIGN KEY (idClient) REFERENCES Client(id);
ALTER TABLE Invoice ADD CONSTRAINT fk_orderIDInvoice FOREIGN KEY (idOrder) REFERENCES ClientOrder(id);

ALTER TABLE Product ADD CONSTRAINT fk_productIDPharmacyProduct FOREIGN KEY (idPharmacy) REFERENCES Pharmacy(id);

ALTER TABLE ClientOrder ADD CONSTRAINT fk_deliveryIDClientOrder  FOREIGN KEY (idDelivery)  REFERENCES Delivery(id);
ALTER TABLE ClientOrder ADD CONSTRAINT fk_clientIDClientOrder  FOREIGN KEY (idClient)  REFERENCES Client(id);

ALTER TABLE Courier ADD CONSTRAINT fk_pharmacyIDCourier  FOREIGN KEY (idPharmacy) REFERENCES Pharmacy(id);

ALTER TABLE Pharmacy ADD CONSTRAINT fk_addressLatitudePharmacy FOREIGN KEY (Addresslatitude, Addresslongitude,AddressAltitude) REFERENCES Address(latitude, longitude,altitude);
ALTER TABLE Pharmacy ADD CONSTRAINT fk_administratorIDPharmacy  FOREIGN KEY (emailAdministrator) REFERENCES Administrator(email);

ALTER TABLE Park ADD CONSTRAINT fk_IDPharmacyPark FOREIGN KEY (idPharmacy) REFERENCES Pharmacy(id);
ALTER TABLE Park ADD CONSTRAINT fk_IDTypePark FOREIGN KEY (idTypePark) REFERENCES TypePark(id);

ALTER TABLE Vehicle ADD CONSTRAINT fk_IDPharmacyVehicle FOREIGN KEY (idPharmacy) REFERENCES Pharmacy(id);
ALTER TABLE Vehicle ADD CONSTRAINT fk_IDTypeVehicle FOREIGN KEY (idTypeVehicle) REFERENCES TypeVehicle(id);

ALTER TABLE Delivery ADD CONSTRAINT fk_IDVehicleDelivery FOREIGN KEY (licensePlateVehicle) REFERENCES Vehicle(licensePlate);
ALTER TABLE Delivery ADD CONSTRAINT fk_IDCourierDelivery FOREIGN KEY (idCourier) REFERENCES Courier(id);

ALTER TABLE Client ADD CONSTRAINT fk_emailCliente FOREIGN KEY (email) REFERENCES AppUser(email);

ALTER TABLE Courier ADD CONSTRAINT fk_emailCourier FOREIGN KEY (email) REFERENCES AppUser(email);

ALTER TABLE ProductOrder ADD CONSTRAINT fk_idProduct FOREIGN KEY (idProduct) REFERENCES Product(id);
ALTER TABLE ProductOrder ADD CONSTRAINT fk_idOrder FOREIGN KEY (idOrder) REFERENCES ClientOrder(id);

ALTER TABLE RefillStock ADD CONSTRAINT fk_IDVehicleRefill FOREIGN KEY (licensePlateVehicle) REFERENCES Vehicle(licensePlate);
ALTER TABLE RefillStock ADD CONSTRAINT fk_IDCourierRefill FOREIGN KEY (idCourier) REFERENCES Courier(id);

ALTER TABLE RestockOrder ADD CONSTRAINT fk_idProductRestock FOREIGN KEY (idProduct) REFERENCES Product(id);
ALTER TABLE RestockOrder ADD CONSTRAINT fk_idClientOrder FOREIGN KEY (idClientOrder) REFERENCES ClientOrder(id);
ALTER TABLE RestockOrder ADD CONSTRAINT fk_idPharmReceiver FOREIGN KEY (idPharmReceiver) REFERENCES Pharmacy(id);	
ALTER TABLE RestockOrder ADD CONSTRAINT fk_idPharmSender FOREIGN KEY (idPharmSender) REFERENCES Pharmacy(id);
ALTER TABLE RestockOrder ADD CONSTRAINT fk_idRefillStock FOREIGN KEY (idRefillStock) REFERENCES RefillStock(id);	

ALTER TABLE Path ADD CONSTRAINT fk_addressFrom FOREIGN KEY (latitudeFrom, longitudeFrom, altitudeFrom) REFERENCES Address(latitude, longitude,altitude);
ALTER TABLE Path ADD CONSTRAINT fk_addressTo FOREIGN KEY (latitudeTo, longitudeTo, altitudeTo) REFERENCES Address(latitude, longitude,altitude);
ALTER TABLE Path ADD CONSTRAINT fk_idTypePath FOREIGN KEY (idTypePath) REFERENCES TypePath(id);