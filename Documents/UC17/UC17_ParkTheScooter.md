# UC 17 - Park the scooter

## 1.Requirement Engineering

### Brief format
The courier requests the parking of the scooter. The system requests the data from the scooter(e.g id). The courier enters the data of the scooter. The system verifies that the scooter is from that park and authotrizes the parking.

### SSD
![UC4_SSD.svg](UC4_SSD.svg)


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
2. The system requests the data from the scooter (e.g id).
3. The courier enters the data requested of the scooter.
4. The system verifies that the scooter is from that park and authotrizes the parking.

-----------

#### Extensions

*a. The Courier cancels the parking of the scooter.
> The use case ends.

3a. The data enter is not valid.
>  1. The system informs the courier of the fact .
>  2. The system allows the courier to introduce the data once again. 
>
    > 2a. The courier does not change the data. The use case ends.

4a. The scooter introduced is not from that park 
>  The system informs the courier .The use case ends.

4b. The scooter introduced is not registered on the system
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

![UC4_MD.svg](UC4_MD.svg)


## 3. Design - Realização do Caso de Uso

### Racional

| Main Flux | Question: Which class... | Answer  | Justification  |
|:--------------  |:---------------------- |:----------|:---------------------------- |
|1. The Collaborator beggins the creation of a payment transaction. |... interacts with te Collaborator?| CreateTransactionUI |Pure Fabrication|
| |... coordinates the UC?| CreateTransactionController |Controller|
| |... creates instances of TransactionExecution |RegisterTransaction|Creator(rule1) combined with HC+LC with Platform.|
| |...knows RegisterTransaction? | Organization | IE: Organization has RegisterTransaction |
|2. The system shows the Collaborator a list of tasks and asks him to choose one.|...has the list of tasks?| TaskList|HC+LC with Organization|
|  |...knows TaskList? | Organization | IE: Organization has TaskList |
|3. The collaborator chooses one task from the list. ||||
|4. The system shows the collaborator a list of freelancers that are present in the system and asks the collaborator once again to choose of of them.|...has the list of Freelancers?| RegisterFreelancer| HC+LC|
| |...knows RegisterFreelancer?| Pla6tform | IE: Platform has RegisterFreelancer |
|5. The collaborator chooses one freelancer from the list. |...
|6. The System asks for the rest of the necessary data(details about the execution of the task(end date, delay, brief description of the quality of the work)).||||
|7. The Collaborator introduces all the necessary data.|...saves all the information introduced? | TransactionExecution | Creator (rule1) |
|                                                      |...creates an instance of Payment? | TransactionExecution | IE: In the MD, TransactionExecution generates a Payment / Creator (rule 1) |
|8. The system shows the data to the Collaborator and asks him to confirm |... validates the TransactionExecution data (Global validation)? | RegisterTransaction | HC+LC combined with IE, because RegisterTransaction has all the TransactionExecutions|
|    |...validates the TransactionExecution data (local validation)? | TransactionExecution | IE: knows its own data |
|9. The collaborator confirms.||||
|10. The system registers the data and informs the Collaborator that the operation was successful. |...saves the created instance of TransactionExecution | RegisterTransaction| HC+LC |


### Sistematization ##

From the racional, the conceptual classes that are promoted to software classes are:

 * Platform
 * Organization
 * TransactionExecution
 * Payment

Other software classes (eg: Pure Fabrication) identified:

 * CreateTransactionUI
 * CreateTransactionController
 * RegisterTransaction
 * RegisterFreelancer
 * TaskList-


### Sequence Diagram

![UC4_SD.svg](UC4_SD.svg)



### Class Diagram

![UC4_CD.svg](UC4_CD.svg)



