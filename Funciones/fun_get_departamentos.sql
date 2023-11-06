create or replace function fun_get_departamentos(
    in int,
    out id_clase_ret smallint,
    out clase_ret character varying)
RETURNS SETOF record AS
$BODY$
DECLARE
   departamento    ALIAS FOR $1;   
   rTabla RECORD;
   cSql VARCHAR;
BEGIN
   cSql := 'select id_clase, nombreclase clase from cen_clase_abcc where departamento = '||departamento||' order by id_clase';
   FOR rTabla IN EXECUTE(cSql)
   LOOP
      id_clase_ret := rTabla.id_clase;
      clase_ret := rTabla.clase;
   RETURN NEXT;
   END LOOP;	
END;
$BODY$
LANGUAGE plpgsql