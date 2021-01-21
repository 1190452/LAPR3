
# UC 10 - Add Medicine

## 1. Requirements Engineering

### Brief Format

The Administrator initiates the adding of a medicine to the system. The system asks for the necessary data (id, name, description, price, weight). The administrator writes the necessary data. The System shows the data and asks for confirmation. The Administrator confirms. The System registers the data and informs about the success of the operation.

### SSD
![UC10_SSD](UC10_SSD.svg)


### Full Format

#### Main Actor

Administrator

#### Stakeholders and their interests

* **Administrator:** want to register medicines so that the pharmacy can sell them
* **Pharmacy:** intends to sell medicines.

#### Preconditions

The Pharmacy has to be registered in the platform.

#### Post-conditions

The medicine should be added to the system.

#### Main success scenario (or basic flow)

1. The Administrator initiates the adding of a medicine to the system.
2. The system asks for the data (id, name, description, price, weight).
3. The administrator writes the necessary data. 
4. The System shows the data and asks for confirmation.
5. The Administrator confirms.
6. The System registers the data and informs about the success of the operation.


#### Extensions (or alternative flow)

*a. The Administrator requests to cancel the adding of a medicine

> The use case ends.

4a. Missing minimum required data.
>    1. The system informs you which data is missing.
>    2. The system allows the entry of missing data (step 3)
>
    >    2a. The Administrator does not change the data. The use case ends.

4b. The System detects that the data (or some subset of the data) entered must be unique and that it already exists in the system.
>    1. The system alerts the collaborator to the fact.
>    2. The system allows its modification (step 3)
>
    >    2a.  The Administrator does not change the data. The use case ends.

4c. The system detects that the data entered (or some subset of the data) is invalid.
> 1. The system alerts the collaborator to the fact.
> 2. The system allows its modification (step 3).
>
    > 2a.  The Administrator does not change the data. The use case ends.

-----------------

## 2. OO Analysis

### Excerpt from the Relevant Domain Model for UC

![UC10_MD](UC10_MD.svg)

## 3. Design - Use Case Realization


###	Sequence Diagram

![UC10_SD.svg](UC10_SD.svg)

###	Class Diagram

![UC10_CD.svg](UC10_CD.svg)
