package br.com.sintech.core.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.sintech.core.dao.ChamadoAnexoDao;
import br.com.sintech.core.dao.ChamadoDao;
import br.com.sintech.core.dao.JDBCUtil;
import br.com.sintech.core.dao.JDBCUtil.UrlConexao;
import br.com.sintech.core.entity.Chamado;
import br.com.sintech.core.entity.ChamadoAnexo;
import br.com.sintech.core.entity.GrupoUsuario;
import br.com.sintech.core.util.NegocioException;
import br.com.sintech.core.util.PersistenciaException;
import br.com.sintech.core.util.UtilErros;
import br.com.sintech.view.managedBean.ChamadoBean.TipoFiltro;
import br.com.sintech.view.util.SessionContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ServiceChamado implements GenericService<Chamado> {

	private ChamadoDao dao;
	
	
	public ServiceChamado() {
		this.dao = new ChamadoDao();
	}
	
	
	@Override
	public String salvar(Chamado entidade) throws Exception {
		if (entidade.getIdChamado() == null) {
			
			try {
				
				entidade.setUsuario(SessionContext.getInstance().getUsuarioLogado());				

				String protocolo = 
						new SimpleDateFormat("yyyyMM").format(new java.util.Date())+ 
						String.format("%05d", dao.getCodigoProtocolo());
								
				entidade.setProtocolo(protocolo);
				
				
				for (ChamadoAnexo anexo : entidade.getAnexos()) {
					anexo.setCaminho(entidade.getEmpresa().getDiretorioLocal() + "\\" + protocolo+ "\\");
				}				
				
				dao.save(entidade);				
								
				for (ChamadoAnexo anexo : entidade.getAnexos()) {										
					ServiceChamadoAnexo.gravar(anexo);
				}
				
				return "Cadastro de Chamado Realizado com Sucesso";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Chamado!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
			
		} else {
			
			try {
				
				for (ChamadoAnexo anexo : entidade.getAnexos()) {
					if(anexo.getIdChamadoAnexo() == null || anexo.getCaminho() == null){
						anexo.setCaminho(
								entidade.getEmpresa().getDiretorioLocal()+ "\\" + entidade.getProtocolo()+ "\\");
					}											
				}
				
				dao.update(entidade);								
				
				return "Chamado Alterado com Sucesso";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar Chamado!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}			
		}
	}

	
	@Override
	public String excluir(Chamado entidade) throws Exception {
		entidade = carregarEntidade(entidade);
		
		if(entidade.getMovimentos().get(entidade.getMovimentos().size() -1).getSituacao().getSigla() != 'S' ){
			throw new NegocioException("Chamado já está em processo de Análise ou Concluido, não pode ser excluido!");
		}
		
		try {
			dao.delete(entidade);
			return "Chamado removido com Sucesso!";
        }catch (Exception ex) {
        	ex.printStackTrace();            
        	throw new PersistenciaException("Ocorreu uma exceção ao Remover o Chamado!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}

	
	@Override
	public Chamado carregarEntidade(Chamado entidade) throws PersistenciaException {
		String jpql = "Select c From Chamado c left JOIN FETCH c.programa "
				+ " left JOIN FETCH c.usuario "
				+ " left JOIN FETCH c.movimentos "
				+ " left JOIN FETCH c.empresa "
				+ " where c.idChamado = ?1";
		
		try {
			Chamado chamado = dao.findOne(jpql, entidade.getIdChamado());			
			chamado.setAnexos(new ChamadoAnexoDao().findByChamado(chamado));
			
			return chamado;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Chamado!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));		
		}
	}

	
	@Override
	public List<Chamado> buscarTodos() throws PersistenciaException {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -2);
		
		Date d = c.getTime();
		
		List<Chamado> lista = null;
		try {
			
			if(SessionContext.getInstance().getUsuarioLogado().getGrupoUsuario() == GrupoUsuario.ADMIN){
				String jpql = "Select c From Chamado c left JOIN FETCH c.empresa where c.dataSolicitacao >= ? order by c.dataSolicitacao desc";				
				lista = dao.find(jpql, d);
			}else{
				String jpql = "Select c From Chamado c left JOIN FETCH c.empresa where c.dataSolicitacao >= ? and c.empresa = ? order by c.dataSolicitacao desc";
				lista = dao.find(jpql, d, SessionContext.getInstance().getEmpresaUsuarioLogado());
			}
			
			return lista;
		} catch (Exception e) {
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Chamado!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));		
		}
	}

	
	@Override
	public void consisteAntesEditar(Chamado entidade) throws NegocioException {
		try {
			entidade = carregarEntidade(entidade);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		
		if(entidade.getMovimentos().get(entidade.getMovimentos().size() -1).getSituacao().getSigla() != 'S' ){
			throw new NegocioException("Chamado já está em processo de Análise ou já foi Concluido, portanto não pode ser Alterado!");
		}
	}

	
	public List<Chamado> filtrarTabela(TipoFiltro tipoFiltro , Object valorFiltro)throws Exception{
		List<Chamado> lista = null;
		
		try {
			
			if(tipoFiltro.equals(TipoFiltro.PROTOCOLO)){
				String jpql = "Select c From Chamado c left JOIN FETCH c.empresa where c.protocolo = ?";
				lista = dao.find(jpql,valorFiltro);
			}
			
			else if(tipoFiltro.equals(TipoFiltro.TITULO)){
				if(SessionContext.getInstance().getUsuarioLogado().getGrupoUsuario() == GrupoUsuario.ADMIN){
					String jpql = "Select c From Chamado c left JOIN FETCH c.empresa where c.titulo like ?";
					lista = dao.find(jpql, valorFiltro);
				}else{
					String jpql = "Select c From Chamado c left JOIN FETCH c.empresa where c.titulo like ? and c.empresa = ?";
					lista = dao.find(jpql, valorFiltro, SessionContext.getInstance().getEmpresaUsuarioLogado());
				}				
			}
			
			else if(tipoFiltro.equals(TipoFiltro.SITUACAO)){
				if(SessionContext.getInstance().getUsuarioLogado().getGrupoUsuario() == GrupoUsuario.ADMIN){
					String jpql = "Select c From Chamado c left JOIN FETCH c.empresa where c.situacao = ?";
					lista = dao.find(jpql, valorFiltro);
				}else{
					String jpql = "Select c From Chamado c left JOIN FETCH c.empresa where c.situacao = ? and c.empresa = ?";
					lista = dao.find(jpql, valorFiltro, SessionContext.getInstance().getEmpresaUsuarioLogado());					
				}
			}
			
			else if(tipoFiltro.equals(TipoFiltro.DATA_SOLICITACAO)){
				try{
					if(SessionContext.getInstance().getUsuarioLogado().getGrupoUsuario() == GrupoUsuario.ADMIN){
						String jpql = " Select c From Chamado c left JOIN FETCH c.empresa where c.dataSolicitacao = ? ";					
						lista = dao.find(jpql,valorFiltro);
					}else{
						String jpql = " Select c From Chamado c left JOIN FETCH c.empresa where c.dataSolicitacao = ? and c.empresa = ?";					
						lista = dao.find(jpql,valorFiltro, SessionContext.getInstance().getEmpresaUsuarioLogado());
					}
					
				}catch (Exception e) {
					e.printStackTrace();
				}								
			}
			
			return lista;			
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados do Chamado!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}					
	}

	
	public JasperPrint getReportByDataSolicitacaoSituacao(String caminhoRelatorio, Map<String, Object> parametros)throws SQLException, JRException{
		Connection conn = null;
		try {			
			//gerando o jasper design
            JasperDesign desenho = JRXmlLoader.load(this.getClass().getResourceAsStream(caminhoRelatorio));

            //compila o relatório
            JasperReport relatorio = JasperCompileManager.compileReport(desenho);
			
            conn = JDBCUtil.getInstance().getConnection(UrlConexao.URL_SUPORTE);
            
			JasperPrint print = JasperFillManager.fillReport(relatorio, parametros, conn);
			
			return print;
			
		}catch(SQLException ex){		
			throw new SQLException("Erro ao gerar o relatório " + caminhoRelatorio, ex);
		}finally {
			if(conn != null && !conn.isClosed()){
				conn.close();
			}			
		}
	}
	
}
