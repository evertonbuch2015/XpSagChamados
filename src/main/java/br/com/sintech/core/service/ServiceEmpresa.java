package br.com.sintech.core.service;

import java.util.ArrayList;
import java.util.List;

import br.com.sintech.core.dao.EmpresaDao;
import br.com.sintech.core.entity.Empresa;
import br.com.sintech.core.util.NegocioException;
import br.com.sintech.core.util.PersistenciaException;
import br.com.sintech.core.util.UtilErros;
import br.com.sintech.view.managedBean.EmpresaBean.TipoFiltro;

public class ServiceEmpresa implements GenericService<Empresa>{

	private EmpresaDao empresaDao;
	
	
	public ServiceEmpresa() {
		empresaDao = new EmpresaDao();
	}
	
	
	public String salvar(Empresa entidate)throws Exception {
		if (entidate.getIdEmpresa() == null) {
			
			try {
				empresaDao.save(entidate);
				return "Cadastro de Empresa Realizado com Sucesso";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao inserir a Empresa!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}
			
		} else {
			
			try {
				empresaDao.update(entidate);
				return "Cadastro de Empresa Alterado com Sucesso";
			} catch (Exception e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocorreu uma exceção ao alterar a Empresa!" + 
	            		" \nErro: " + UtilErros.getMensagemErro(e));
			}			
		}
	}

	
	public String excluir(Empresa entidade)throws Exception {
		try {
			empresaDao.delete(entidade);
			return "Empresa removida com Sucesso!";
        }catch (Exception ex) {
        	ex.printStackTrace();            
        	throw new PersistenciaException("Ocorreu uma exceção ao Excluir a Empresa!" + 
            		" \nErro: " + UtilErros.getMensagemErro(ex));
		}
	}

	
	public Empresa carregarEntidade(Empresa Empresa) throws PersistenciaException{
		String jpql = "Select e From Empresa e left JOIN FETCH e.endereco where e.idEmpresa = ?1";
		try {
			return empresaDao.findOne(jpql, Empresa.getIdEmpresa());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao buscar os dados da Empresa!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));		
		}
	}

	
	public List<Empresa> buscarTodos() {
		try {
			return empresaDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Empresa>();
		}
	}

	
	public List<Empresa> filtrarTabela(TipoFiltro tipoFiltro , String valorFiltro)throws Exception{
		List<Empresa> lista = null;
		
		try {
			if(tipoFiltro.equals(TipoFiltro.CODIGO)){						
				String jpql = "Select e From Empresa e where e.codigo in (" + valorFiltro + ")";
				lista = empresaDao.find(jpql);					
			}
			else if(tipoFiltro.equals(TipoFiltro.NOME)){					
					lista = empresaDao.find("Select e From Empresa e where e.nomeRazao like ?",valorFiltro);				
			}
			
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenciaException("Ocorreu uma exceção ao Filtrar os dados da Empresa!" + 
            		" \nErro: " + UtilErros.getMensagemErro(e));
		}
	}

	
	public void consisteAntesEditar(Empresa entidade) throws NegocioException{

	}

}
