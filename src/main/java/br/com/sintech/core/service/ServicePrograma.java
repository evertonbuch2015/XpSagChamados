package br.com.sintech.core.service;

import java.util.ArrayList;
import java.util.List;

import br.com.sintech.core.dao.ProgramaDao;
import br.com.sintech.core.entity.Programa;
import br.com.sintech.core.util.NegocioException;
import br.com.sintech.core.util.PersistenciaException;

public class ServicePrograma implements GenericService<Programa> {

	private ProgramaDao dao; 
	
	
	public ServicePrograma() {
		this.dao = new ProgramaDao();
	}
	
	
	@Override
	public String salvar(Programa entidate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String excluir(Programa entidade) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Programa carregarEntidade(Programa entidade) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
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
