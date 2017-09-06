package br.com.sintech.core.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.sintech.core.dao.JDBCUtil.UrlConexao;
import br.com.sintech.core.entity.Autorizacao;
import br.com.sintech.core.entity.Empresa;
import br.com.sintech.core.util.PersistenciaException;

public class AutorizacaoDao {
	
	private String BUSCAR_POR_EMPRESA = 
			" SELECT A.COD_RUDAUTORIZACAO, A.MES, A.ANO, A.AUTORIZACAO, C.CODIGO, C.FANTASIA, P.DOCUMENTO" + 
			" FROM RUD_AUTORIZACAO A "+
			" INNER JOIN CAD_COLABORADOR C ON A.COD_CADCOLABORADOR = C.COD_CADCOLABORADOR"+
			" INNER JOIN CAD_PESSOA P ON P.CODIGO_PESSOA = c.CODIGO_PESSOA "+
			" WHERE C.CODIGO = %s";
	
	Connection conn = null;
	
	public AutorizacaoDao() {

	}
    
	
    private ResultSet executaSQL(String sql)throws SQLException{
    	if(this.conn == null || this.conn.isClosed()){
    		conn = JDBCUtil.getInstance().getConnection(UrlConexao.URL_SINTECH);
    	}
    	
    	Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    	return stm.executeQuery(sql);
    }
    
    

    public List<Autorizacao> findAutorizacaoByEmpresa(Empresa empresa)throws Exception{
        List<Autorizacao> lista = new ArrayList<>();
        
        try {        	
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
        	throw new PersistenciaException(ex);
        }finally{
        	this.conn.close();
	    }
        
        return lista;
    }
}
