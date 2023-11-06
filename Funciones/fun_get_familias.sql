create or replace function fun_get_familias(
    in int,
    in int,
    out id_familia_ret smallint,
    out familia_ret character varying)
RETURNS SETOF record AS
$BODY$
DECLARE
   departamento    ALIAS FOR $1;   
   familia         ALIAS FOR $2;
   rTabla RECORD;
   cSql VARCHAR;
BEGIN
   cSql := 'select id_familia, nombrefamilia familia from cen_familia_abcc where departamento = '||departamento||' and clase = '||familia||' order by id_familia';
   FOR rTabla IN EXECUTE(cSql)
   LOOP
      id_familia_ret := rTabla.id_familia;
      familia_ret := rTabla.familia;
   RETURN NEXT;
   END LOOP;	
END;
$BODY$
LANGUAGE plpgsql