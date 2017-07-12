package br.com.sintech.core.service;

import java.util.ArrayList;
import java.util.List;

import br.com.sintech.core.dao.SituacaoChamadoDao;
import br.com.sintech.core.entity.SituacaoChamado;
import br.com.sintech.core.util.NegocioException;
import br.com.sintech.core.util.PersistenciaException;
import br.com.sintech.core.util.UtilErros;

public class ServiceSituacaoChamado implements GenericService<SituacaoChamado> {

	private SituacaoChamadoDao dao; 
	
	
	public ServiceSituacaoChamado() {
		this.dao = new SituacaoChamadoDao();
	}
	
	
	@Override
	public String salvar(SituacaoChamado entidate) throws Exception {
		if (entidate.getIdSituacaoChamado() == null) {
			
			try {
				dao.save(entidate);
				return "Cadastro de Situação Realizado com Sucesso";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir a Situação!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
			
		} else {
			
			try {
				dao.update(entidate);
				return "Cadastro de Situação Alterado com Sucesso";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar a Situação!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}			
		}
	}

	
	@Override
	public String excluir(SituacaoChamado entidade) throws Exception {
		try {
			dao.delete(entidade);
			return "Programa removido com Sucesso!";
        }catch (Exception ex) {
        	ex.printStackTrace();            
        	throw new PersistenciaException("Ocorreu uma exceção ao Remover o Programa!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}

	
	@Override
	public SituacaoChamado carregarEntidade(SituacaoChamado entidade) throws PersistenciaException {
		String jpql = "Select e From SituacaoChamado e where e.idSituacaoChamado = ?1";
		try {
			return dao.findOne(jpql, entidade.getIdSituacaoChamado());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados da Situação!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));		
		}
	}

	
	@Override
	public List<SituacaoChamado> buscarTodos() throws PersistenciaException {
		
		try {
			return dao.findAll();
		} catch (Exception e) {
			return new ArrayList<SituacaoChamado>();
		}
	}

	
	@Override
	public void consisteAntesEditar(SituacaoChamado entidade) throws NegocioException {
		
	}
}
