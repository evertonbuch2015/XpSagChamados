package br.com.sintech.core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

	public enum UrlConexao{
		URL_SINTECH,
		URL_SUPORTE,
		URL_RUDAR;
	}
	
	
	private static final String DRIVER 	= "org.firebirdsql.jdbc.FBDriver";
	
	private static final String URL_SINTECH 	= "jdbc:firebirdsql:192.168.1.12/3050:SINTECH";
	private static final String URL_SUPORTE 	= "jdbc:firebirdsql:192.168.1.12/3051:SUPORTE";
	private static final String URL_RUDAR	 	= "jdbc:firebirdsql:192.168.1.12/3050:RUDAR";
	
	private static final String USUARIO	= "SYSDBA";
	private static final String SENHA 	= "masterkey";	
	
	
	
	private static JDBCUtil instanceJDBCUtil;	
	
	private JDBCUtil() {
		
	}
	
	
	public static synchronized JDBCUtil getInstance() {
		if (instanceJDBCUtil == null) {
			instanceJDBCUtil = new JDBCUtil();
		}
		return instanceJDBCUtil;
	}

	
	public Connection getConnection(UrlConexao url) throws SQLException{
		switch (url) {
		
			case URL_RUDAR:
				System.setProperty("jdbc.Drivers", DRIVER); 
				return DriverManager.getConnection(URL_RUDAR, USUARIO, SENHA);	
			
			case URL_SINTECH:
				System.setProperty("jdbc.Drivers", DRIVER); 
				return DriverManager.getConnection(URL_SINTECH, USUARIO, SENHA);
			
			case URL_SUPORTE:
				System.setProperty("jdbc.Drivers", DRIVER); 
				return DriverManager.getConnection(URL_SUPORTE, USUARIO, SENHA);
			
			default:
				System.setProperty("jdbc.Drivers", DRIVER); 
				return DriverManager.getConnection(URL_SUPORTE, USUARIO, SENHA);
		}
	}
}
