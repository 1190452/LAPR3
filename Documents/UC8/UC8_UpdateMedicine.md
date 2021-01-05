
# UC 8 - Update Medicine

## 1. Requirements Engineering

### Brief Format

The Administrator initiates the update of the stock of a medicine that exists in the system. The system asks for the id to update. The administrator writes the id. The System asks for the quantity to add to the stock. The administrator enters the quantity. The system shows the changes and asks for confirmation. The Administrator confirms. The System registers the data and informs about the success of the operation.

### SSD
![UC18_SSD](UC18_SSD.svg)


### Full Format

#### Main Actor

Administrator

#### Stakeholders and their interests

* **Administrator:** want to update the stock of a medicine.
* **Pharmacy:** intends to have all its stocks updated.

#### Preconditions

The Pharmacy has to be registered in the platform.
The medicine that will be updated should already exist in the platform.

#### Post-conditions

The stock of medicines should have its information updated in the system.

#### Main success scenario (or basic flow)

1. The Administrator initiates the update of the stock of a medicine that exists in the system.
2. The system asks for the id to update.
3. The administrator writes the id.
4. The System asks for the quantity to add to the stock.
5. The administrator enters the quantity.
6. The system shows the changes and asks for confirmation. 
7. The Administrator confirms.
8. The System registers the data and informs about the success of the operation.


#### Extensions (or alternative flow)

*a. The Administrator requests to cancel the update of a medicine

> The use case ends.

5a. The System detects that the quantity of stocks didn't change.

>    1. The system alerts the administrator to the fact.
>    2. The system allows its modification (step 3)
>
    >    2a.  The Administrator does not change the data. The use case ends.

5b. The system detects that the id entered is invalid.

> 1. The system alerts the collaborator to the fact.
> 2. The system allows its modification (step 3).
>
    > 2a.  The Collaborator does not change the data. The use case ends.

-----------------

## 2. OO Analysis

### Excerpt from the Relevant Domain Model for UC

![UC18_MD](UC18_MD.svg)

## 3. Design - Use Case Realization


###	Sequence Diagram

![UC18_SD.svg](UC18_SD.svg)

###	Class Diagram

![UC18_CD.svg](UC18_CD.svg)
