package Model;

public class Csku 
{
	private int sku;	
	private String articulo;
	private String marca;
	private String modelo;
	private String departamento;
	private String clase;
	private String familia;
	private int stock;
	private int cantidad;
	private String descontinuado;
	private String fechaalta;
	private String fechabaja;
		
	public Csku(String articulo, String marca, String modelo, String departamento, String clase, String familia,
			int stock, int cantidad, String fechaalta, String fechabaja) 
	{
		super();
		this.articulo = articulo;
		this.marca = marca;
		this.modelo = modelo;
		this.departamento = departamento;
		this.clase = clase;
		this.familia = familia;
		this.stock = stock;
		this.cantidad = cantidad;
		this.fechaalta = fechaalta;
		this.fechabaja = fechabaja;
	}
	
	public Csku()
	{
		
	}
	
	public int getSku() 
	{
		return sku;
	}
	
	public void setSku(int sku) 
	{
		this.sku = sku;
	}
	
	public String getArticulo() 
	{
		return articulo;
	}

	public void setArticulo(String articulo) 
	{
		this.articulo = articulo;
	}

	public String getMarca() 
	{
		return marca;
	}

	public void setMarca(String marca) 
	{
		this.marca = marca;
	}

	public String getModelo() 
	{
		return modelo;
	}

	public void setModelo(String modelo) 
	{
		this.modelo = modelo;
	}

	public String getDepartamento() 
	{
		return departamento;
	}

	public void setDepartamento(String departamento) 
	{
		this.departamento = departamento;
	}

	public String getClase() 
	{
		return clase;
	}

	public void setClase(String clase) 
	{
		this.clase = clase;
	}

	public String getFamilia() 
	{
		return familia;
	}

	public void setFamilia(String familia) 
	{
		this.familia = familia;
	}

	public int getStock() 
	{
		return stock;
	}

	public void setStock(int stock) 
	{
		this.stock = stock;
	}

	public int getCantidad() 
	{
		return cantidad;
	}

	public void setCantidad(int cantidad) 
	{
		this.cantidad = cantidad;
	}

	public String getFechaalta() 
	{
		return fechaalta;
	}

	public void setFechaalta(String fechaalta) 
	{
		this.fechaalta = fechaalta;
	}

	public String getFechabaja() 
	{
		return fechabaja;
	}

	public void setFechabaja(String fechabaja) 
	{
		this.fechabaja = fechabaja;
	}
	
	public String getDescontinuado() 
	{
		return descontinuado;
	}

	public void setDescontinuado(String descontinuado) 
	{
		this.descontinuado = descontinuado;
	}
}	
