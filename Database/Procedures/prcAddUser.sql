create or replace procedure prcAddUser(p_email appuser.email%type, p_pwd appuser.password%type, p_role appuser.role%type) 
 IS
BEGIN
    INSERT INTO AppUser VALUES (p_email, p_pwd, p_role);
END;
