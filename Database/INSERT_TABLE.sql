--SEQUENCES
declare
  v_cmd varchar(2000);
begin
  for r in (select sequence_name from user_sequences)
  loop
    v_cmd := 'drop sequence ' || r.sequence_name;
    execute immediate(v_cmd);
  end loop;
  --
  for r in (select table_name from user_tables)
  loop
    v_cmd := 'create sequence seq_' || r.table_name;
    execute immediate(v_cmd);
  end loop;
end;
/


INSERT INTO AppUser(email,password,role) VALUES('bpereira750@gmail.com', 'qwerty', 'administrator');
INSERT INTO Administrator(email,name) VALUES('bpereira750@gmail.com', 'Antonio');

INSERT INTO TypeVehicle(id, name) VALUES (1, 'Electric Scooter');
INSERT INTO TypeVehicle(id, name) VALUES (2, 'Drone');

INSERT INTO TypePark(id, name) VALUES (1, 'Park Electric Scooter');
INSERT INTO TypePark(id, name) VALUES (2, 'Park Drone');

INSERT INTO TypePath(id, name) VALUES (1, 'Land');
INSERT INTO TypePath(id, name) VALUES (2, 'Air');
