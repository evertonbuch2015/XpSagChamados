package br.com.sintech.core.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.sintech.core.dao.ChamadoDao;
import br.com.sintech.core.entity.Chamado;
import br.com.sintech.core.entity.ChamadoMovimento;
import br.com.sintech.core.entity.GrupoUsuario;
import br.com.sintech.core.entity.SituacaoChamado;
import br.com.sintech.core.util.NegocioException;
import br.com.sintech.core.util.PersistenciaException;
import br.com.sintech.core.util.UtilErros;
import br.com.sintech.view.managedBean.ChamadoBean.TipoFiltro;
import br.com.sintech.view.util.SessionContext;

public class ServiceChamado implements GenericService<Chamado> {

	private ChamadoDao dao;
	
	
	public ServiceChamado() {
		this.dao = new ChamadoDao();
	}
	
	
	@Override
	public String salvar(Chamado entidate) throws Exception {
		if (entidate.getIdChamado() == null) {
			
			try {
				
				ChamadoMovimento movimento = new ChamadoMovimento();
				movimento.setChamado(entidate);
				movimento.setData(new Date());
				movimento.setDescricao("Chamado Aberto");
				movimento.setSituacao(SituacaoChamado.ABERTO);
				
				entidate.getMovimentos().add(movimento);
				entidate.setUsuario(SessionContext.getInstance().getUsuarioLogado());
				
				dao.save(entidate);
				return "Cadastro de Chamado Realizado com Sucesso";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Chamado!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
			
		} else {
			
			try {
				dao.update(entidate);
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
		String jpql = "Select c From Chamado c left JOIN FETCH c.programa left JOIN FETCH c.usuario left JOIN FETCH c.movimentos where c.idChamado = ?1";
		try {
			return dao.findOne(jpql, entidade.getIdChamado());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Chamado!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));		
		}
	}

	
	@Override
	public List<Chamado> buscarTodos() throws PersistenciaException {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		
		Date d = c.getTime();
		
		List<Chamado> lista = null;
		try {
			
			if(SessionContext.getInstance().getUsuarioLogado().getGrupoUsuario() == GrupoUsuario.ADMIN){
				String jpql = "Select c From Chamado c where c.dataSolicitacao >= ?";				
				lista = dao.find(jpql, d);
			}else{
				String jpql = "Select c From Chamado c where c.dataSolicitacao >= ? and c.empresa = ?";
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
		// TODO Auto-generated method stub
		
	}

	
	public List<Chamado> filtrarTabela(TipoFiltro tipoFiltro , Object valorFiltro)throws Exception{
		List<Chamado> lista = null;
		
		try {
			
			if(tipoFiltro.equals(TipoFiltro.PROTOCOLO)){
				String jpql = "Select c From Chamado c where c.protocolo = ?";
				lista = dao.find(jpql,valorFiltro);
			}
			
			else if(tipoFiltro.equals(TipoFiltro.TITULO)){
				if(SessionContext.getInstance().getUsuarioLogado().getGrupoUsuario() == GrupoUsuario.ADMIN){
					String jpql = "Select c From Chamado c where c.titulo like ?";
					lista = dao.find(jpql, valorFiltro);
				}else{
					String jpql = "Select c From Chamado c where c.titulo like ? and c.empresa = ?";
					lista = dao.find(jpql, valorFiltro, SessionContext.getInstance().getEmpresaUsuarioLogado());
				}				
			}
			
			else if(tipoFiltro.equals(TipoFiltro.SITUACAO)){
				if(SessionContext.getInstance().getUsuarioLogado().getGrupoUsuario() == GrupoUsuario.ADMIN){
					String jpql = "Select c From Chamado c where c.situacao = ?";
					lista = dao.find(jpql, valorFiltro);
				}else{
					String jpql = "Select c From Chamado c where c.situacao = ? and c.empresa = ?";
					lista = dao.find(jpql, valorFiltro, SessionContext.getInstance().getEmpresaUsuarioLogado());					
				}
			}
			
			else if(tipoFiltro.equals(TipoFiltro.DATA_SOLICITACAO)){
				try{
					if(SessionContext.getInstance().getUsuarioLogado().getGrupoUsuario() == GrupoUsuario.ADMIN){
						String jpql = " Select c From Chamado c where c.dataSolicitacao = ? ";					
						lista = dao.find(jpql,valorFiltro);
					}else{
						String jpql = " Select c From Chamado c where c.dataSolicitacao = ? and c.empresa = ?";					
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
}
