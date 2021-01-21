
# UC 7 - Add Vehicle

## 1. Requirements Engineering

### Brief Format

The administrator chooses to create an electric scooter on the system. The system requests the necessary (i.e. licencePlate, maximumBattery, actualBattery, enginePower, ampereHour, voltage, weight, pharmacyID, typeVehicle). The administrator enters the requested data. The system asks for confirmation. The administrator confirms. The system informs the administrator of the success of the operation.


### SSD
![UC7_SSD](UC7_SSD.svg)


### Full format

#### Main actor
Administrator

#### Stakeholders and their interests

**Administrator:** intends to manage the pharmacies.

**Pharmacy:** intends to ship products by having vehicles.

**Couriers:** intends to have a scooter so that he can transport the medicines.

#### Preconditions

* The vehicle cannot exist on the system (id should be different).

#### Postconditions

* The vehicle is saved on the system and is now usable to make deliveries.
* By default, the id, the actual battery, the status and the isCharging state of the vehicle is defined previously.


#### Main success scenario (or basic flow)

1. The administrator logins in the system
2. The administrator is now logged in and has administrator privileges.
3. The administrator chooses to create a new Vehicle. 
4. The system asks for information to be inserted.
5. The administrator inserts the information
6. the system saves it and informs the administrator the success of the operation.


#### Extensions (or alternative flows)

1a. The administrator requests to cancel the creation.
>    1. The use case ends.

4a. The administrator does not enter all the requested data
>    1. The system informs which data is missing.
>    2. The system allows the administrator to enter the missing data (step 3).
>
  > 2a. The administrator does not change the data. The use case ends.

4b. The system detects that the data (or some subset of the data) entered (i.e. id) must be unique and that it already exists in the system.
>    1. The system alerts the administrator to the fact.
>    2. The system allows the administrator to change it (step 3)
>
  > 2a. The administrator does not change the data. The use case ends.



#### Special requirements
--------------------

#### List of Technologies and Data Variations
--------------------

#### Frequency of Occurrence
--------------------

This use case happens everytime the administrator wants to add a new vehicle on the system


## 2. Object Oriented Analysis

### Excerpt from the Relevant Domain Model for UC

![UC7_MD](UC7_MD.svg)


## 3. Design - Use Case Realization



### Sequence Diagram

![UC7_SD](UC7_SD.svg)



### Class Diagram

![UC7_CD](UC7_CD.svg)


