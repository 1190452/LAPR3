CREATE OR REPLACE FUNCTION fncLogin(f_email AppUser.email%type, f_password AppUser.password%type) 
RETURN INTEGER IS 
    v_count int;

BEGIN

    SELECT COUNT (*) INTO v_count
    FROM AppUser 
    WHERE email = f_email AND password = f_password;

    IF v_count = 0 THEN
        RETURN 0;
    ELSE
        RETURN 1;
    END IF;
    
END;
/

