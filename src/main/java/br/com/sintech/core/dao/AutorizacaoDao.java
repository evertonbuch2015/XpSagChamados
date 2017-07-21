package br.com.sintech.core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.sintech.core.entity.Autorizacao;
import br.com.sintech.core.entity.Empresa;

public class AutorizacaoDao {

	private static final String DRIVER 	= "org.firebirdsql.jdbc.FBDriver"; 
	private static final String URL 	= "jdbc:firebirdsql:192.168.1.12/3050:SINTECH"; 
	private static final String USUARIO	= "SYSDBA";
	private static final String SENHA 	= "masterkey";
	
	private String BUSCAR_POR_EMPRESA = 
			" SELECT A.COD_RUDAUTORIZACAO, A.MES, A.ANO, A.AUTORIZACAO, C.CODIGO, C.FANTASIA, P.DOCUMENTO" + 
			" FROM RUD_AUTORIZACAO A "+
			" INNER JOIN CAD_COLABORADOR C ON A.COD_CADCOLABORADOR = C.COD_CADCOLABORADOR"+
			" INNER JOIN CAD_PESSOA P ON P.CODIGO_PESSOA = c.CODIGO_PESSOA "+
			" WHERE C.CODIGO = %s";
	
	private Connection conn;
	
	
	public AutorizacaoDao() {

	}
	
	
	private void conectar() throws SQLException{
		System.setProperty("jdbc.Drivers", DRIVER); 
		conn = DriverManager.getConnection(URL, USUARIO, SENHA);
	}
	
		
    private void desconectar()throws SQLException{      
    	conn.close();
    }
    
    
    private ResultSet executaSQL(String sql)throws SQLException{
    	Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    	return stm.executeQuery(sql);
    }
    
    

    public List<Autorizacao> findByEmpresa(Empresa empresa){
        List<Autorizacao> lista = new ArrayList<>();
        
        try {
        	conectar();        	
        	
        	ResultSet rs = executaSQL(String.format(BUSCAR_POR_EMPRESA, empresa.getCodigo()));        	
        	rs.first();
        	
        	do{
                Autorizacao entidade = new Autorizacao();
                entidade.setIdAutorizacao(rs.getInt("COD_RUDAUTORIZACAO"));
                entidade.setMes(rs.getString("MES"));
                entidade.setAno(rs.getString("ANO"));
                entidade.setAutorizacao(rs.getInt("AUTORIZACAO"));
                
                entidade.setCodigoEmpresa(rs.getInt("CODIGO"));
                entidade.setNomeEmpresa(rs.getString("FANTASIA"));
                entidade.setDocumentoEmpresa(rs.getString("DOCUMENTO"));
                
                lista.add(entidade);
            }while(rs.next());
        	
        	
        } catch (SQLException ex) {

        }finally{
            try {
				desconectar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        
        return lista;
    }
}
