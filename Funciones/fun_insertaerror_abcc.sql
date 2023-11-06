create or replace function fun_insertaerror_abcc(
    in character varying,
    in text)    
  RETURNS void AS
$BODY$
DECLARE
    metodo_param        ALIAS FOR $1;
    error_param         ALIAS FOR $2;    	  
BEGIN  
    insert into ctl_error_abcc (metodo, error, fecha) values (metodo_param, error_param, now());	
END;
$BODY$
LANGUAGE plpgsql