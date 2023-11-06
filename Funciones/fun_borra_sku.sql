create or replace function fun_borra_sku(
    in int
    )
  RETURNS void AS
$BODY$
DECLARE
    sku_param         ALIAS FOR $1;    	    
BEGIN
    delete from mae_sku_general_abcc where sku = sku_param;    
END;
$BODY$
LANGUAGE plpgsql