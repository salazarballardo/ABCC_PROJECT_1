create or replace function fun_getsku_datos(
    in int,
    out articulo_ret varchar,
    out marca_ret character varying,
    out modelo_ret character varying,
    out departamento_ret character varying,
    out clase_ret character varying,
    out familia_ret character varying,
    out stock_ret int,
    out cantidad_ret int,
    out fechaalta_ret timestamp,
    out fechabaja_ret timestamp)
  RETURNS SETOF record AS
$BODY$
  DECLARE
    sku_param    ALIAS FOR $1;
    rTabla RECORD;
    cSql VARCHAR;
BEGIN
   cSql := 'select maesku.articulo, maesku.marca, maesku.modelo, deptos.nombredepartamento, clase.nombreclase, fam.nombrefamilia, maesku.stock, maesku.cantidad, maesku.fechaalta, maesku.fechabaja from mae_sku_general_abcc maesku
	inner join cen_departamentos_abcc deptos
	on maesku.id_departamento = deptos.id_departamento
	inner join cen_clase_abcc clase
	on maesku.id_clase = clase.id_clase
	inner join cen_familia_abcc fam 
	on maesku.id_familia = fam.id_familia
	where maesku.sku = '||sku_param||' limit 1';
   FOR rTabla IN EXECUTE(cSql)
   LOOP
	articulo_ret := rTabla.articulo;
        marca_ret := rTabla.marca;
        modelo_ret := rTabla.modelo;
        departamento_ret := rTabla.nombredepartamento;
        clase_ret := rTabla.nombreclase;
        familia_ret := rTabla.nombrefamilia;
        stock_ret := rTabla.stock;
        cantidad_ret := rTabla.cantidad;
        fechaalta_ret := rTabla.fechaalta;
        fechabaja_ret := rTabla.fechabaja;
                
	RETURN NEXT;
   END LOOP;
END;
$BODY$
LANGUAGE plpgsql