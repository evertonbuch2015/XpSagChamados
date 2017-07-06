package br.com.sintech.core.service;

import java.io.Serializable;
import java.util.List;

import br.com.sintech.core.util.NegocioException;
import br.com.sintech.core.util.PersistenciaException;

public interface GenericService<T extends Serializable>{

	
	public String salvar(T entidate)throws Exception;
		
	public String excluir(T entidade)throws Exception;
	
	public T carregarEntidade(T entidade)throws PersistenciaException;
	
	public List<T> buscarTodos()throws PersistenciaException;
	
	public void consisteAntesEditar(T entidade)throws NegocioException;
}
