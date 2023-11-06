create or replace function fun_actualiza_sku(
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
    update mae_sku_general_abcc set articulo = articulo_param, marca = marca_param, modelo = modelo_param, id_departamento = id_depto_parm,
	id_clase = id_clase_param, id_familia = id_fam_param, stock = stock_param, cantidad = cantidad_param where sku = sku_param;    
END;
$BODY$
LANGUAGE plpgsql
