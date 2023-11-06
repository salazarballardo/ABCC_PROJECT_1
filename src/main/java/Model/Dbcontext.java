package Model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Dbcontext 
{	
	private String driver;
	private String connectionString;
	private String userdb;
	private String passdb;
	private Connection connection;
	private Statement statement;
	
	public Dbcontext(InputStream propertiesfile) 
	{
		super();
		getCredencialesdb(propertiesfile);
	}
		
	/**
	 * Se conecta a la db	 
	 * @param void 
	 * @return void	 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public void connect() throws ClassNotFoundException, SQLException
	{
		Class.forName(this.driver);
		this.connection = DriverManager.getConnection(this.connectionString, this.userdb, this.passdb);
		this.statement = this.connection.createStatement();		
	}
	
	/**
	 * Se cierra la conexion a la db	 
	 * @param void 
	 * @return void	 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public void disconnect() throws SQLException
	{
		this.connection.close();
		this.statement.close();
	}
	
	/**
	 * Obtiene el PreparedStatemtent 
	 * @param String 
	 * @return PreparedStatement	 
	 * @throws SQLException 
	 */
	public PreparedStatement getPreparedStatemtent(String query) throws SQLException
	{
		return this.connection.prepareStatement(query);
	}
	
	/**
	 * Obtiene las credenciales desde el archivo de configuracion
	 * @param void 
	 * @return void	 
	 */
	private void getCredencialesdb(InputStream credencialesfile)
	{
		Properties properties = null;
		
		try
		{
			properties = new Properties();
			properties.load(credencialesfile);
			this.driver = properties.getProperty("driverposgrest");
			this.connectionString = properties.getProperty("connectionstringservidor");
			this.userdb = properties.getProperty("user");
			this.passdb = properties.getProperty("pass");					
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}		
		/*this.driver = "org.postgresql.Driver";
		this.connectionString = "jdbc:postgresql://10.28.120.4:5432/tienda.0002";
		this.userdb = "syscontrolprogramacion";
		this.passdb = "f45459f3627a6d7372b261324aa4b574";*/
	}
	
	// getters and setters
	public Statement getStatement() 
	{
		return statement;
	}

	public void setStatement(Statement statement) 
	{
		this.statement = statement;
	}
	
	public Connection getConnection() 
	{
		return connection;
	}
	
	public void setConnection(Connection connection) 
	{
		this.connection = connection;
	}
		
	public String getDriver() 	
	{
		return driver;
	}

	public void setDriver(String driver) 
	{
		this.driver = driver;
	}
	
	public String getConnectionString() 
	{
		return connectionString;
	}

	public void setConnectionString(String connectionString) 
	{
		this.connectionString = connectionString;
	}

	public String getUserdb() 
	{
		return userdb;
	}

	public void setUserdb(String userdb) 
	{
		this.userdb = userdb;
	}

	public String getPassdb() 
	{
		return passdb;
	}

	public void setPassdb(String passdb) 
	{
		this.passdb = passdb;
	}
	
}
