package br.com.sintech.core.service;

import java.util.ArrayList;
import java.util.List;

import br.com.sintech.core.dao.ProgramaDao;
import br.com.sintech.core.entity.Programa;
import br.com.sintech.core.util.NegocioException;
import br.com.sintech.core.util.PersistenciaException;
import br.com.sintech.core.util.UtilErros;
import br.com.sintech.view.managedBean.ProgramaBean.TipoFiltro;

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
		
	}

	
	public List<Programa> filtrarTabela(TipoFiltro filtro, String valorFiltro) throws PersistenciaException{
		List<Programa> lista = null;
		
		try {
		
			if(filtro.equals(TipoFiltro.CODIGO)){			
				String jpql = "Select p From Programa p where p.idPrograma in (" + valorFiltro + ")";
				lista = dao.find(jpql);				
			}
			else if(filtro.equals(TipoFiltro.NOME)){							
				lista = dao.find("Select p From Programa p where p.nome like ?",valorFiltro);						
			}
			else if(filtro.equals(TipoFiltro.PROGRAMA)){							
				lista = dao.find("Select p From Programa p where p.programa like ?",valorFiltro);						
			}
			
			return lista;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados do Programa!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}
	
	
	public List<String> getMenusSistema(){
		return dao.findMenusSistema();
	}

}
