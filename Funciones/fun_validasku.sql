create function fun_validasku(sku_param int)
returns int
language plpgsql
as
$$
DECLARE
   iRegreso int;
begin 
   iRegreso := 1;
   IF NOT EXISTS(select 1 from mae_sku_general_abcc where sku = sku_param) THEN	
         iRegreso := 0;
   END IF;
   return iRegreso;	
end
$$;