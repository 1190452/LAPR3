# UC 7 - Add electric scooter

## 1. Requirements Engineering

### Brief Format

The administrator chooses to create an electric scooter on the system. The system requests the necessary (i.e. id and the maximum battery). The administrator enters the requested data. The system asks for confirmation. The administrator confirms. The system informs the administrator of the success of the operation.


### SSD
![UC7_SSD](UC7_SSD.svg)


### Full format

#### Main actor
Administrator

#### Stakeholders and their interests

**Administrator:** intends to manage the pharmacies.

**Pharmacy:** intends to ship products by having scooters.

**Couriers:** intends to have a vehicle so that he can transport the medicines.

#### Preconditions

* The scooter cannot exist on the system (id should be different).

#### Postconditions

* The scooter is saved on the system and is now usable to make deliveries.
* By default, the actual battery of the scooter is defined as the maximum battery of the scooter


#### Main success scenario (or basic flow)

1. The administrator starts by creating a scooter.
2. The system requests the necessary (i.e. id and the maximum battery).
3. The administrator enters the requested data. 
4. The system validates and asks for confirmation.
5. The administrator confirms the data.
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

This use case happens everytime the administrator wants to add a new electric scooter on the system


## 2. Object Oriented Analysis

### Excerpt from the Relevant Domain Model for UC

![UC7_MD](UC7_MD.svg)


## 3. Design - Use Case Realization

### Rational

| Main Flow | Question: What Class... | Answer | Justification  |
|:--------------  |:---------------------- |:----------|:---------------------------- |
|1. The administrator starts creating a scooter |... interact with administrator?| CreateScooterUI |Pure Fabrication|
| |... coordinate the UC?| CreateScooterController |Controller|
| |... create instances of electric scooters?|RegisterScooter|Creator (rule1) combined with HC + LC on Platform: Platform delegates responsibilities for RegisterScooter|
| |... knows RegisterScooter?| Platform | IE: Platform contains RegisterScooter
|2. The system requests the necessary data|... interact with administrator?| CreateScooterUI |Pure Fabrication|
|3. The Administrator enters the requested data|... save the entered data?|ElectricScooter|IE: ElectricScooter knows the attributes|
|4. The system validates and asks for confirmation |... validates scooter data( Global Validation ) | RegisterScooter | IE: RegisterScooter contains ElectricScooter and knows them all|
| |... validates scooter data( local Validation )|Scooter| IE: ElectricScooter knows the attributes |
|5. The administrator confirms the data |||  |
|6. The system informs the administrator the success of the operation||| |


### Systematization ##

It follows from the rational that the conceptual classes promoted to software classes are:

 * RegisterScooter
 * Administrator
 * Platform

Other software classes (i.e. Pure Fabrication) identified:  

 * CreateScooterUI  
 * CreateScooterController



### Sequence Diagram





### Class Diagram



