create or replace function fun_alta_sku(
    in int,
    in character varying,
    in character varying,
    in character varying,
    in int,
    in int,
    in int,
    in int,
    in int)
  RETURNS void AS
$BODY$
DECLARE
    sku_param         ALIAS FOR $1;
    articulo_param    ALIAS FOR $2;
	marca_param       ALIAS FOR $3;
	modelo_param      ALIAS FOR $4;
	id_depto_parm     ALIAS FOR $5;
	id_clase_param    ALIAS FOR $6;
	id_fam_param      ALIAS FOR $7;
	stock_param       ALIAS FOR $8;
	cantidad_param    ALIAS FOR $9;	    
BEGIN
    insert into mae_sku_general_abcc (sku, articulo, marca, modelo, id_departamento, id_clase, id_familia, stock, cantidad) 
	     values (sku_param, articulo_param, marca_param, modelo_param, id_depto_parm, id_clase_param, id_fam_param, stock_param, cantidad_param);
END;
$BODY$
LANGUAGE plpgsql