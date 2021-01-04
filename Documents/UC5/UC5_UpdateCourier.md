
# UC 5 - Update Courier

## 1. Requirements Engineering

### Brief Format

The Administrator initiates the update of a courier that exists in the system. The system asks for the data to update.
The administrator writes the necessary data. The System shows the data and asks for confirmation. The Administrator confirms.
The System registers the data and informs about the success of the operation.

### SSD
![UC5_SSD](UC5_SSD.svg)


### Full Format

#### Main Actor

Administrator

#### Stakeholders and their interests

* **Administrator:** want to update the data about the couriers so that the information about them is always updated.
* **Courier:** wants to have his data updated on the system
* **Pharmacy:** intends to have all its couriers with updated information

#### Preconditions

The Pharmacy has to be registered in the platform.
The Courier that will be updated should already exist in the platform.

#### Post-conditions

The Courier should have its information updated in the system.

#### Main success scenario (or basic flow)

1. The Administrator initiates the update of a courier that exists in the system.
2. The system asks for the courier that is going to be updated.
3. The Administrator enters the courier that will be updated. 
4. The system asks for the information that the administrator wants to update about the courier.
5. The Administrator enters the data that he wants to change.
6. The System shows the altered data and asks the administrator to confirm.
7. The Administrator confirms.
8. The System registers the data and informs about the success of the operation.


#### Extensions (or alternative flow)

*a. The Administrator requests to cancel the update of a courier

> The use case ends.


5a. The System detects that the data (or some subset of the data) didn't change.
>    1. The system alerts the collaborator to the fact.
>    2. The system allows its modification (step 3)
>
    >    2a.  The Collaborator does not change the data. The use case ends.

5b. The system detects that the data entered (or some subset of the data) is invalid.
> 1. The system alerts the collaborator to the fact.
> 2. The system allows its modification (step 3).
>
    > 2a.  The Collaborator does not change the data. The use case ends.

#### Special Requirements

--------------------

#### List of Technologies and Data Variations

-------------------

#### Frequency of Occurrence

------------------

#### Open questions

-----------------

## 2. OO Analysis

### Excerpt from the Relevant Domain Model for UC

![UC5_MD](UC5_MD.svg)

## 3. Design - Use Case Realization


### Rational

| Main Flow | Question: Which Class ... | Answer  | Justification  |
|:--------------  |:---------------------- |:----------|:---------------------------- |
| 1. The Administrator initiates the update of a courier to the system. 		 | ...interacts with the user?						   |   UpdateCourierUI          |       Pure Fabrication     |
|                                                                                | ...coordinates the UC?                              | UpdateCourierController    | Controller |
|                                                                                | ...updates Courier instance?                        | CourierHandler                 | Creator (Rule1) |
| 2. The system asks for the data (name, email, NIF, social security number).    | ...interacts with the user?	                       |   AddCourierUI            |   Pure Fabrication    |
| 3. The administrator writes the necessary data.                                | ...stores the data entered?                         | Courier                | instance created in step 1: it has its own data.                              |
| 4. The System shows the data and asks for confirmation.		                 |	...validates the Courier data (local validation)?  |    Courier             |  IE:has its own data.                            |
|                                                                                | ...validates the Courier data (global validation)?  | CourierHandler               | IE:CourierHandler has the data about all couriers  |
| 5. The Administrator confirms.	                                             | 							                           |                        |                                 |
| 6. The System registers the data and informs about the success of the operation.  |	...keeps the created Courier?		 |  CourierHandler    | IE:the CourierHandler contains all the Courier  |

### Systematization ##

 From the rational the classes that are upgraded into software classes are:

 * Courier

 Other software classes (i.e. Pure Fabrication) identified:

 * AddCourierUI
 * AddCourierController
 * CourierHandler
 
 Other classes of external systems / components:


###	Sequence Diagram

![UC5_SD.svg](UC5_SD.svg)

###	Class Diagram

![UC5_CD.svg](UC5_CD.svg)
