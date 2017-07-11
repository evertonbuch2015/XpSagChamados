package br.com.sintech.core.util;

import java.util.ArrayList;
import java.util.List;

import br.com.sintech.core.dao.ParametroDao;
import br.com.sintech.core.entity.Empresa;
import br.com.sintech.core.entity.Parametro;
import br.com.sintech.core.entity.Programa;
import br.com.sintech.core.entity.Usuario;
import br.com.sintech.core.service.ServiceEmpresa;
import br.com.sintech.core.service.ServicePrograma;


public class Constantes {

	private static Constantes instance;
	private List<Programa> listaProgramas;
	private List<Empresa> listaEmpresas;
	private List<Usuario> usuariosLogados;
	private Parametro parametro;
	
	
	
	private Constantes() {
		refresh();
	}
	
	
	public static synchronized Constantes getInstance(){
		if(instance == null){
			instance = new Constantes(); 
		}
		return instance;		
	}
		
	
	
	public void refresh(){
		try {
			listaProgramas = new ServicePrograma().buscarTodos();
			listaEmpresas = new ServiceEmpresa().buscarTodos();
			
			parametro = new ParametroDao().findAll().get(0);
			
			usuariosLogados = new ArrayList<Usuario>();
			
		} catch (PersistenciaException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public List<Empresa> getEmpresas(){		
		return listaEmpresas;
	}
	
	
	public List<Programa> getProgramas(){
		return listaProgramas;
	}
	
	public Parametro getParametro() {
		return parametro;
	}


	
	public void addUsuarioLogado(Usuario usuario){
		this.usuariosLogados.add(usuario);		
	}
	
	public void removeUsuarioLogado(Usuario usuario){
		this.usuariosLogados.remove(usuario);		
	}
	
	public List<Usuario> getUsuariosLogados() {
		return usuariosLogados;
	}
}
