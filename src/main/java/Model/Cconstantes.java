package Model;

import java.util.Dictionary;
import java.util.Hashtable;

public class Cconstantes 
{
	public static String funvalidasku = "SELECT fun_validasku(?)";
	public static String querycount_sku = "select count(*) registros from mae_sku_general_abcc where sku = ";
	public static String fungetdatossku = "select *from fun_getsku_datos(?)";     // sku
	public static String fungetdepartamentos = "select departamento_ret from fun_get_departamentos()";
	public static String fungetclases = "select clase_ret from fun_get_clases(?)";
	public static String fungetfamilias = "select familia_ret from fun_get_familias(?,?)";   // depto, clase
	
	public static String funaltaskus = "select fun_alta_sku(?,?,?,?,?,?,?,?,?)";              // select fun_alta_sku(4579, 'BRAVIA 48', 'SONY', 'bravia', 2, 6, 6, 32, 12)
	public static String funborrasku = "select fun_borra_sku(?)";
	public static String funactualizasku = "select fun_actualiza_sku(?,?,?,?,?,?,?,?,?,?)";   // sku ,articulo, marca, modelo, departamento, clase, familia, stock, cantidad, descontinuado 
	public static String funinsertaerror = "select fun_insertaerror_abcc(?,?)";         // metodo, error
	
	// credenciales db
	
	
	static Dictionary<String, Integer> deptosDictionary = new Hashtable<>();
	static Dictionary<String, Integer> clasesDictionary = new Hashtable<>();
	static Dictionary<String, Integer> familiaDictionary = new Hashtable<>();
	
	/**
	 * Obtenemos la key del depto enviado	  
	 * @return String	 
	 */
	public static int get_intvalue_deptos(String depto)
	{
		int deptovalue = 0;
		
		deptosDictionary.put("DOMESTICOS", 1);
		deptosDictionary.put("ELECTRONICA", 2);
		deptosDictionary.put("MUEBLE SUELTO", 3);
		deptosDictionary.put("SALAS, RECAMARAS, COMEDORES", 4);
		
		deptovalue = deptosDictionary.get(depto);
		
		return deptovalue;
	}
	
	/**
	 * Obtenemos la key de la clase enviada	  
	 * @return String	 
	 */
	public static int get_intvalue_clase(String clase)
	{
		int clasevalue = 0;
		
		clasesDictionary.put("COMESTIBLES", 1);
		clasesDictionary.put("LICUADORAS", 2);
		clasesDictionary.put("BATIDORAS", 3);
		clasesDictionary.put("CAFETERAS", 4);
		clasesDictionary.put("AMPLIFICADORES CAR AUDIO", 5);
		clasesDictionary.put("TELEVISION", 6);
		clasesDictionary.put("COLCHON", 7);
		clasesDictionary.put("JUEGO BOX", 8);
		clasesDictionary.put("SOFA CAMA", 9);
		clasesDictionary.put("SALAS", 10);
		
		clasevalue = clasesDictionary.get(clase);
		
		return clasevalue; 
	}
	
	/**
	 * Obtenemos la key de la familia enviada	  
	 * @return String	 
	 */
	public static int get_intvalue_familia(String familia)
	{
		int famvalue = 0;
		
		familiaDictionary.put("ENLATADOS", 1);
		familiaDictionary.put("CARNES", 2);
		familiaDictionary.put("PESCADOS", 3);
		familiaDictionary.put("LICUADORA ECO", 4);
		familiaDictionary.put("LICUADORA DELUX", 5);
		familiaDictionary.put("TV SONY LCD", 6);
		familiaDictionary.put("TV SONY PLASMA", 7);
		familiaDictionary.put("TV SONY LED", 8);
		familiaDictionary.put("TV LG", 9);
		familiaDictionary.put("HULE ESPUMA", 10);
		familiaDictionary.put("COLCHON ORTOPEDICO", 11);
		familiaDictionary.put("COLCHON AGUA", 12);
		familiaDictionary.put("SALA SENCILLA", 13);
		familiaDictionary.put("SALA ESCUADRA", 14);
		familiaDictionary.put("SOFA CAMA SENCILLO", 15);
		familiaDictionary.put("SOFA CAMA PIEL", 16);
		
		famvalue = familiaDictionary.get(familia);
		
		return famvalue;
	}		
}
