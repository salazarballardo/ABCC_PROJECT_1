package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import Model.Cmodeloabccproject;
import Model.Csku;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ActionServletSkuAbcc
 */
@WebServlet("/ActionServletSkuAbcc")
public class ActionServletSkuAbcc extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    InputStream contextPropertiesFile;
	Cmodeloabccproject modeloabc;
        
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionServletSkuAbcc() 
    {
        super();
        // TODO Auto-generated constructor stub                
    }
            
	@Override
	public void init() throws ServletException 
	{
		// TODO Auto-generated method stub
		super.init();
		contextPropertiesFile = getServletContext().getResourceAsStream("/WEB-INF/credenciales.properties");
        modeloabc = new Cmodeloabccproject(contextPropertiesFile);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub							
		final String parameterid_pet = request.getParameter("idpet");       // id de la peticion
		
		switch (parameterid_pet)
		{
			case "getdatosku" :
				controller_get_datos_sku(request, response);    
			break;			
			case "getdepto" :
				controller_get_departamentos(request, response);  
			break;
			case "getclases" :
				controller_get_clases(request, response);    
			break;
			case "getfamilias" :
				controller_get_familias(request, response);  
			break;
			case "validaexistesku" :
				controller_validaexiste_sku(request, response);  
			break;
			case "altasku" :
				controller_alta_sku(request, response);    
			break;
			case "borrasku" :
				controller_borra_sku(request, response);   
			break;
			case "cambiosku" :
				controller_cambio_sku(request, response);
			break;			
		}
	}
	
	/**
	 * Manda llamar al modelo y se trae los datos del sku
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void	 
	 */
	private void controller_get_datos_sku(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
        		
		final String parametersku = request.getParameter("sku");		
		Csku skuobj = modeloabc.get_datos_skudb(parametersku);
		
		ObjectMapper objmapper = new ObjectMapper();
		String jsonstr = objmapper.writeValueAsString(skuobj);
		
		jsonstr = "[" + jsonstr + "]";
		JSONArray jsonarray = new JSONArray(jsonstr);
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("articulo_sku", jsonarray);
		
		out.println(jsonobj);
	}
	
	/**
	 * Manda llamar al modelo y se trae los departamentos
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void	 
	 */
	private void controller_get_departamentos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		
		out.println(modeloabc.get_departamentos());
	}
	
	/**
	 * Manda llamar al modelo y se trae las clases
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void	 
	 */
	private void controller_get_clases(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();		
		String respuestamodel = "";
        		
		final String parameterdepto = request.getParameter("departamento");		
		respuestamodel = modeloabc.get_clases(parameterdepto);
		out.println(respuestamodel);
	}
	
	/**
	 * Manda llamar al modelo y se trae las familias
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void	 
	 */
	private void controller_get_familias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		String respuestamodel = "";
        
		final String parameterdepto = request.getParameter("departamento");
		final String parameterclase = request.getParameter("clase");
				
		respuestamodel = modeloabc.get_familias(parameterdepto, parameterclase);
		out.println(respuestamodel);
	}
	
	/**
	 * Manda llamar al modelo y se valida si existe el sku
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void	 
	 */
	private void controller_validaexiste_sku(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		String respuestamodel = "";
        
		final String parameterSku = request.getParameter("sku");				
		respuestamodel = modeloabc.valida_existesku(parameterSku);
		out.println(respuestamodel);
	}
		
	/**
	 * Manda llamar al modelo y se da de alta el sku
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void	 
	 */
	private void controller_alta_sku(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		String strresponse = "0";
        
		// sku, articulo, marca, modelo, id_departamento, id_clase, id_familia, stock, cantidad
		final String paramsku = request.getParameter("sku");
		final String paramarticulo = request.getParameter("articulo");
		final String parammarca = request.getParameter("marca");
		final String parammodelo = request.getParameter("modelo");
		final String paramdeptop = request.getParameter("depto");
		final String paramclase = request.getParameter("clase");
		final String paramfamilia = request.getParameter("familia");
		final String paramstock = request.getParameter("stock");
		final String paramcantidad = request.getParameter("cantidad");
		
		Csku skuobj = new Csku();
		skuobj.setSku(Integer.parseInt(paramsku));
		skuobj.setArticulo(paramarticulo);
		skuobj.setMarca(parammarca);
		skuobj.setModelo(parammodelo);
		skuobj.setDepartamento(paramdeptop);
		skuobj.setClase(paramclase);
		skuobj.setFamilia(paramfamilia);
		skuobj.setStock(Integer.parseInt(paramstock));
		skuobj.setCantidad(Integer.parseInt(paramcantidad));
						
		if (modeloabc.model_sku_alta(skuobj))
		{
			strresponse = "1";
		}
		
		out.println(strresponse);
	}
	
	/**
	 * Manda llamar al modelo y se borra el sku enviado
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void	 
	 */
	private void controller_borra_sku(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();		
		String strresponse = "0";
        
		final String parameterSku = request.getParameter("sku");
		
		if (modeloabc.model_borra_sku(parameterSku))
		{
			strresponse = "1";
		}
		
		out.println(strresponse);
	}
	
	/**
	 * Manda llamar al modelo y realiza un update sobre el sku
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void	 
	 */
	private void controller_cambio_sku(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType( "text/html; charset=iso-8859-1" );
		PrintWriter out = response.getWriter();
		String strresponse = "0";
        
		// sku, articulo, marca, modelo, id_departamento, id_clase, id_familia, stock, cantidad
		final String paramsku = request.getParameter("sku");
		final String paramarticulo = request.getParameter("articulo");
		final String parammarca = request.getParameter("marca");
		final String parammodelo = request.getParameter("modelo");
		final String paramdeptop = request.getParameter("depto");
		final String paramclase = request.getParameter("clase");
		final String paramfamilia = request.getParameter("familia");
		final String paramstock = request.getParameter("stock");
		final String paramcantidad = request.getParameter("cantidad");
		final String paramdesc = request.getParameter("descontinuado");
		
		Csku skuobj = new Csku();
		skuobj.setSku(Integer.parseInt(paramsku));
		skuobj.setArticulo(paramarticulo);
		skuobj.setMarca(parammarca);
		skuobj.setModelo(parammodelo);
		skuobj.setDepartamento(paramdeptop);
		skuobj.setClase(paramclase);
		skuobj.setFamilia(paramfamilia);
		skuobj.setStock(Integer.parseInt(paramstock));
		skuobj.setCantidad(Integer.parseInt(paramcantidad));
		skuobj.setDescontinuado(paramdesc);
						
		if (modeloabc.model_update_sku(skuobj))
		{
			strresponse = "1";
		}
		
		out.println(strresponse);
	}
	
}














































