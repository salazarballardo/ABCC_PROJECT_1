package Model;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cmodeloabccproject
{
	private InputStream propertiesFile;
	
	public Cmodeloabccproject(InputStream propertiesFile)
	{
		this.propertiesFile = propertiesFile;
	}
	
	/**
	 * Consulta en la db y valida si el sku ya esta dado de alta
	 * @param String sku_value
	 * @return String	 
	 */
	public String valida_existesku(String sku_value)
	{
		String strret = "";
		Dbcontext dbcontext = null;
		ResultSet resultset = null;				
		
		try
		{
		    dbcontext = new Dbcontext(propertiesFile);
			dbcontext.connect();
			resultset = dbcontext.getStatement().executeQuery(Cconstantes.querycount_sku + sku_value);
			resultset.next();
			strret = String.valueOf(resultset.getInt("registros"));									
		}
		catch (Exception ex)       
		{		
			inserta_error_endb("valida_existesku", ex.getMessage());
		}
		finally
		{
			try {
				dbcontext.disconnect();      // cerramos la conexion
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return strret;
	}
	
	/**
	 * Consulta en la db y obtiene los departamentos	  
	 * @return String	 
	 */
	public String get_departamentos()
	{
		String strretdeptos = "";
		String strretdeptosret = "";
				
		Dbcontext dbcontext = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;		
		
		try
		{
			dbcontext = new Dbcontext(propertiesFile);
			dbcontext.connect();
			preparedstatement = dbcontext.getPreparedStatemtent(Cconstantes.fungetdepartamentos);
			resultset = preparedstatement.executeQuery();
			
			while (resultset.next())
			{				
				strretdeptos += resultset.getString(1) + ",";
			}
			
			strretdeptosret = strretdeptos.substring(0, strretdeptos.length() - 1);			
		}
		catch (Exception ex)        // en caso de error la respuesta se ira vacia
		{		
			inserta_error_endb("get_departamentos", ex.getMessage());
		}
		finally
		{
			try {
				dbcontext.disconnect();      // cerramos la conexion
				preparedstatement.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}          
		}
		
		return strretdeptosret;
	}
	
	/**
	 * Consulta en la db y obtiene las clases	  
	 * @return String	 
	 */
	public String get_clases(String depto)
	{
		String strretclases = "";
		String strretclasesret = "";

		Dbcontext dbcontext = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		
		try
		{
			dbcontext = new Dbcontext(propertiesFile);
			dbcontext.connect();
			preparedstatement = dbcontext.getPreparedStatemtent(Cconstantes.fungetclases);
			preparedstatement.setInt(1, Cconstantes.get_intvalue_deptos(depto));
			resultset = preparedstatement.executeQuery();
						
			while (resultset.next())
			{				
				strretclases += resultset.getString(1) + ",";
			}
			
			strretclasesret = strretclases.substring(0, strretclases.length() - 1);
		}
		catch (Exception ex)
		{
			System.out.println("Error get_clases : " + ex.getMessage());
			inserta_error_endb("get_clases", ex.getMessage());
		}
		finally
		{
			try {
				dbcontext.disconnect();      // cerramos la conexion
				preparedstatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return strretclasesret;
	}
	
	/**
	 * Consulta en la db y obtiene las familias correspondientes 
	 * @param String deptov
	 * @param String clase
	 * @return String	 
	 */
	public String get_familias(String depto, String clase)
	{
		String strfamilias = "";
		String strfamiliasret = "";
		Dbcontext dbcontext = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		
		try
		{
			dbcontext = new Dbcontext(propertiesFile);
			dbcontext.connect();
			preparedstatement = dbcontext.getPreparedStatemtent(Cconstantes.fungetfamilias);
			preparedstatement.setInt(1, Cconstantes.get_intvalue_deptos(depto));
			preparedstatement.setInt(2, Cconstantes.get_intvalue_clase(clase));
			resultset = preparedstatement.executeQuery();
			
			while (resultset.next())
			{				
				strfamilias += resultset.getString(1) + ",";
			}
			
			strfamiliasret = strfamilias.substring(0, strfamilias.length() - 1);
		}
		catch (Exception ex)
		{	
			inserta_error_endb("get_familias", ex.getMessage());
		}
		finally
		{
			try {
				dbcontext.disconnect();      // cerramos la conexion
				preparedstatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return strfamiliasret;
	}
	
	/**
	 * Consulta en la db y obtiene los datos del sku
	 * @param String skuparam
	 * @return String	 
	 */
	public Csku get_datos_skudb(String skuparam)
	{
		Csku skuobj = new Csku();
		
		Dbcontext dbcontext = null;
		PreparedStatement preparedstatement = null;
		ResultSet resultset = null;
		
		try 
		{
			dbcontext = new Dbcontext(propertiesFile);
			dbcontext.connect();
			preparedstatement = dbcontext.getPreparedStatemtent(Cconstantes.fungetdatossku);
			preparedstatement.setInt(1, Integer.parseInt(skuparam));
			resultset = preparedstatement.executeQuery();								
			resultset.next();      // la funcion solo regresa 1 registro
			
			skuobj.setArticulo(resultset.getString("articulo_ret"));
			skuobj.setMarca(resultset.getString("marca_ret"));
			skuobj.setModelo(resultset.getString("modelo_ret"));
			skuobj.setDepartamento(resultset.getString("departamento_ret"));
			skuobj.setClase(resultset.getString("clase_ret"));
			skuobj.setFamilia(resultset.getString("familia_ret"));
			skuobj.setStock(resultset.getInt("stock_ret"));
			skuobj.setCantidad(resultset.getInt("cantidad_ret"));
			skuobj.setFechaalta(resultset.getString("fechaalta_ret"));
			skuobj.setFechabaja(resultset.getString("fechabaja_ret"));
			skuobj.setDescontinuado(resultset.getString("descontinuado_ret"));
		}
		catch (Exception ex)
		{		
			inserta_error_endb("get_datos_sku", ex.getMessage());
		}
		finally 
		{
			try {
				dbcontext.disconnect();      // cerramos la conexion
				preparedstatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return skuobj;
	}
	
	/**
	 * Se da de alta un sku en la bd
	 * @param Csku skuparam
	 * @return Boolean	 
	 */
	public Boolean model_sku_alta(Csku skuparam)
	{
		Boolean ret = true;
		
		Dbcontext dbcontext = null;
		PreparedStatement preparedstatement = null;		
		
		try
		{
			dbcontext = new Dbcontext(propertiesFile);
			dbcontext.connect();
			preparedstatement = dbcontext.getPreparedStatemtent(Cconstantes.funaltaskus);
			preparedstatement.setInt(1, skuparam.getSku());    // select fun_alta_sku(4579, 'BRAVIA 48', 'SONY', 'bravia', 2, 6, 6, 32, 12)
			preparedstatement.setString(2, skuparam.getArticulo());
			preparedstatement.setString(3, skuparam.getMarca());
			preparedstatement.setString(4, skuparam.getModelo());
			preparedstatement.setInt(5, Cconstantes.get_intvalue_deptos(skuparam.getDepartamento()));
			preparedstatement.setInt(6, Cconstantes.get_intvalue_clase(skuparam.getClase()));
			preparedstatement.setInt(7, Cconstantes.get_intvalue_familia(skuparam.getFamilia()));
			preparedstatement.setInt(8, skuparam.getStock());
			preparedstatement.setInt(9, skuparam.getCantidad());
			preparedstatement.executeQuery();			
		}
		catch (Exception ex)
		{
			ret = false;           // se indicara que hubo algun error al insertar
			inserta_error_endb("model_alta_sku", ex.getMessage());
		}
		finally
		{
			try {
				dbcontext.disconnect();      // cerramos la conexion
				preparedstatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	/**
	 * Se da de baja un sku en la bd
	 * @param Csku skuparam
	 * @return Boolean	 
	 */
	public Boolean model_borra_sku(String skupram)
	{
		Boolean ret = true;
		
		Dbcontext dbcontext = null;
		PreparedStatement preparedstatement = null;		
		
		try
		{
			dbcontext = new Dbcontext(propertiesFile);
			dbcontext.connect();
			preparedstatement = dbcontext.getPreparedStatemtent(Cconstantes.funborrasku);
			preparedstatement.setInt(1, Integer.parseInt(skupram));
			preparedstatement.executeQuery();			
		}
		catch (Exception ex)
		{
			ret = false;
			inserta_error_endb("model_borra_sku", ex.getMessage());
		}
		finally 
		{
			try {
				dbcontext.disconnect();      // cerramos la conexion
				preparedstatement.close();			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	/**
	 * Se realiza un update sobre un sku
	 * @param Csku skuparam
	 * @return Boolean	 
	 */
	public Boolean model_update_sku(Csku skuparam)
	{
		Boolean ret = true;
		
		Dbcontext dbcontext = null;
		PreparedStatement preparedstatement = null;
		
		try
		{
			dbcontext = new Dbcontext(propertiesFile);
			dbcontext.connect();
			preparedstatement = dbcontext.getPreparedStatemtent(Cconstantes.funactualizasku);
			preparedstatement.setInt(1, skuparam.getSku());    // select fun_alta_sku(4579, 'BRAVIA 48', 'SONY', 'bravia', 2, 6, 6, 32, 12)
			preparedstatement.setString(2, skuparam.getArticulo());
			preparedstatement.setString(3, skuparam.getMarca());
			preparedstatement.setString(4, skuparam.getModelo());
			preparedstatement.setInt(5, Cconstantes.get_intvalue_deptos(skuparam.getDepartamento()));
			preparedstatement.setInt(6, Cconstantes.get_intvalue_clase(skuparam.getClase()));
			preparedstatement.setInt(7, Cconstantes.get_intvalue_familia(skuparam.getFamilia()));
			preparedstatement.setInt(8, skuparam.getStock());
			preparedstatement.setInt(9, skuparam.getCantidad());
			preparedstatement.setString(10, skuparam.getDescontinuado());
			preparedstatement.executeQuery();								
		}
		catch (Exception ex)
		{
			ret = false;
			System.out.println("Error model_update_sku : " + ex.getMessage());
			// inserta_error_endb("model_update_sku", ex.getMessage());
		}
		finally
		{
			try {
				dbcontext.disconnect();      // cerramos la conexion
				preparedstatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	/**
	 * En caso de error se guarda el error en la tabla de errores
	 * @param Csku skuparam
	 * @return Boolean	 
	 */
	private void inserta_error_endb(String metodo, String error)
	{
		Dbcontext dbcontext = null;
		PreparedStatement preparedstatement = null;
		
		try
		{
			dbcontext = new Dbcontext(propertiesFile);
			dbcontext.connect();
			preparedstatement = dbcontext.getPreparedStatemtent(Cconstantes.funinsertaerror);
			preparedstatement.setString(1, metodo);    
			preparedstatement.setString(2, error);
			preparedstatement.executeQuery();						
		}
		catch (Exception ex)
		{	
			ex.printStackTrace();
		}
		finally
		{
			try {
				dbcontext.disconnect();      // cerramos la conexion
				preparedstatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
