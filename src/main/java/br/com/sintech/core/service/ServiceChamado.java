package br.com.sintech.core.service;

import java.util.ArrayList;
import java.util.List;

import br.com.sintech.core.dao.ChamadoDao;
import br.com.sintech.core.entity.Chamado;
import br.com.sintech.core.util.NegocioException;
import br.com.sintech.core.util.PersistenciaException;

public class ServiceChamado implements GenericService<Chamado> {

	private ChamadoDao dao;
	
	
	public ServiceChamado() {
		this.dao = new ChamadoDao();
	}
	
	
	@Override
	public String salvar(Chamado entidate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String excluir(Chamado entidade) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chamado carregarEntidade(Chamado entidade) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Chamado> buscarTodos() throws PersistenciaException {		
		try {
			return dao.findAll();
		} catch (Exception e) {
			return new ArrayList<Chamado>();
		}
	}

	@Override
	public void consisteAntesEditar(Chamado entidade) throws NegocioException {
		// TODO Auto-generated method stub
		
	}

}
