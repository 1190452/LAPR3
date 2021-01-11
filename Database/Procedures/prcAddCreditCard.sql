CREATE OR REPLACE procedure prcAddCredidCard(p_numbercc creditcard.numbercc%type, 
                                    p_monthexpiration creditcard.monthexpiration%type,
                                    p_yearexpiration creditcard.yearexpiration%type,
                                    p_ccv creditcard.ccv%type) IS 

BEGIN
    
    INSERT INTO creditcard(numbercc,monthexpiration,yearexpiration,ccv) 
    VALUES(p_numbercc, p_monthexpiration, p_yearexpiration, p_ccv);
   
END;
/

