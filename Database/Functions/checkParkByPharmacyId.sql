create or replace FUNCTION checkParkByPharmacyId(p_id pharmacy.id%type) 
RETURN INTEGER IS 
    v_count int;

BEGIN

    SELECT COUNT (*) INTO v_count
    FROM Pharmacy 
    WHERE id = p_id;
    
    IF v_count = 0 THEN
        RETURN 0;
    ELSE
        RETURN 1;
    END IF;
END;