# UC 7 - Delete electric scooter

## 1. Requirements Engineering

### Brief Format

The administrator chooses to delete an electric scooter on the system. The system requests the id of the electric scooter to remove. The administrator enters the id. The system asks for confirmation. The administrator confirms. The system informs the administrator of the success of the operation.


### SSD
![UC7_SSD](UC7_SSD.svg)


### Full format

#### Main actor
Administrator

#### Stakeholders and their interests

**Administrator:** intends to manage the scooters of a pharmacy.


#### Preconditions

* The scooter must exist on the system (the id provided should be present on the system).

#### Postconditions

* The scooter is deleted on the system and it cannot be usable to make deliveries.


#### Main success scenario (or basic flow)

1. The administrator starts by deleting a scooter.
2. The system requests the necessary data (i.e. id).
3. The administrator enters the requested data. 
4. The system validates and asks for confirmation.
5. The administrator confirms the data.
6. the system saves it and informs the administrator the success of the operation.


#### Extensions (or alternative flows)

1a. The administrator requests to cancel the deletion.
>    1. The use case ends.

4a. The administrator does not enter the id
>    1. The system informs that the id is missing.
>    2. The system allows the administrator to enter the missing data (step 3).
>
  > 2a. The administrator does not change the data. The use case ends.

4b. The system detects that the id entered does not exist in the system.
>    1. The system alerts the administrator to the fact.
>    2. The system allows the administrator to change it (step 3)
>
  > 2a. The administrator does not change the id. The use case ends.
