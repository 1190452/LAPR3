# UC 17 - Park the scooter

## 1.Requirement Engineering

### Brief format
The courier requests the parking of the scooter. The system checks whether the park has places availables, authorize the parking and update the number of scooters parked.

### SSD
![UC17_SSD.svg](UC17_SSD.svg)


### Full Format

#### Main Actor

Courier

#### Stakeholders and their interests

**Courier**: wants to park the scooter to finalize his delivery.

**Administrator**: wants that the couriers park the scooters of his pharmacy.

#### Preconditions

Its necessary that the courier has one scooter with him.

#### Postconditions
A scooter is parked.

#### Main success scenario

1. The courier requests the parking of the scooter.
2. The system checks whether the park has places available, authorize the parking and update the number of the capacity of the park.


-----------

#### Extensions

*a. The Courier cancels the parking of the scooter.
> The use case ends.


2a. The park don't have free palces
>  The system informs the courier .The use case ends.


#### Special requirements
\-

#### Lista of Data and Techonology variation
\-

#### Frequency of Occurence

Everytime that the courier finish one delivery.

#### Open Questions


## 2. Object oriented analysis

### Excerpt from the Relevant Domain Model for UC

![UC17_MD.svg](UC17_MD.svg)


## 3. Design - Realização do Caso de Uso

### Sistematization ##

From the racional, the conceptual classes that are promoted to software classes are:

 * Pharmacy
 * Park
 * Courier
 * Delivery

Other software classes (eg: Pure Fabrication) identified:

 * ParkTheScooterUI
 * ParkTheScooterController


### Sequence Diagram

![UC17_SD.png](UC17_SD.png)



### Class Diagram

![UC17_CD.svg](UC17_CD.svg)



