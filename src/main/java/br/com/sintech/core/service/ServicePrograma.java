package br.com.sintech.core.service;

import java.util.ArrayList;
import java.util.List;

import br.com.sintech.core.dao.ProgramaDao;
import br.com.sintech.core.entity.Programa;
import br.com.sintech.core.util.NegocioException;
import br.com.sintech.core.util.PersistenciaException;
import br.com.sintech.core.util.UtilErros;

public class ServicePrograma implements GenericService<Programa> {

	private ProgramaDao dao; 
	
	
	public ServicePrograma() {
		this.dao = new ProgramaDao();
	}
	
	
	@Override
	public String salvar(Programa entidate) throws Exception {
		if (entidate.getIdPrograma() == null) {
			
			try {
				dao.save(entidate);
				return "Cadastro de Programa Realizado com Sucesso";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir o Programa!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
			
		} else {
			
			try {
				dao.update(entidate);
				return "Cadastro de Programa Alterado com Sucesso";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar o Programa!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}			
		}
	}

	
	@Override
	public String excluir(Programa entidade) throws Exception {
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
	public Programa carregarEntidade(Programa entidade) throws PersistenciaException {
		String jpql = "Select e From Programa e where e.idPrograma = ?1";
		try {
			return dao.findOne(jpql, entidade.getIdPrograma());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados do Programa!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));		
		}
	}

	
	@Override
	public List<Programa> buscarTodos() throws PersistenciaException {
		
		try {
			return dao.findAll();
		} catch (Exception e) {
			return new ArrayList<Programa>();
		}
	}

	
	@Override
	public void consisteAntesEditar(Programa entidade) throws NegocioException {
		// TODO Auto-generated method stub
		
	}

}
