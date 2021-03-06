package br.com.sintech.core.util;

import java.util.ArrayList;
import java.util.List;

import br.com.sintech.core.entity.Empresa;
import br.com.sintech.core.entity.Programa;
import br.com.sintech.core.entity.SituacaoChamado;
import br.com.sintech.core.entity.Usuario;
import br.com.sintech.core.service.ServiceEmpresa;
import br.com.sintech.core.service.ServicePrograma;
import br.com.sintech.core.service.ServiceSituacaoChamado;


public class Constantes {

	private static Constantes instance;
	private List<Programa> listaProgramas;
	private List<Empresa> listaEmpresasAtivas;
	private List<Usuario> usuariosLogados;
	private List<SituacaoChamado> listaSituacaoChamado;
	private List<String> listaMenusSistema; 
	
	
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
			listaEmpresasAtivas = new ServiceEmpresa().buscarTodosAtivas();
			listaSituacaoChamado = new ServiceSituacaoChamado().buscarTodos();
			listaMenusSistema = new ServicePrograma().getMenusSistema();
			
			usuariosLogados = new ArrayList<Usuario>();			
		} catch (PersistenciaException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Empresa> getEmpresasAtivas(){		
		return listaEmpresasAtivas;
	}
	
	
	public List<Programa> getProgramas(){
		return listaProgramas;
	}
	
	
	public List<SituacaoChamado> getListaSituacaoChamado() {
		return listaSituacaoChamado;
	}
	
	
	public SituacaoChamado getSituacaoChamadoInicial(){
		for (SituacaoChamado situacaoChamado : listaSituacaoChamado) {
			if(situacaoChamado.isEventoInicial()){
				return situacaoChamado;
			}
		}
		return null;
	}
	
	
	public void addUsuarioLogado(Usuario usuario){
		if(!this.usuariosLogados.contains(usuario)){
			this.usuariosLogados.add(usuario);
		}
	}
	
	
	public void removeUsuarioLogado(Usuario usuario){
		if(this.usuariosLogados.contains(usuario)){
			this.usuariosLogados.remove(usuario);
		}
	}
	
	
	public List<Usuario> getUsuariosLogados() {
		return usuariosLogados;
	}
	
	
	public List<String> getListaMenusSistema() {
		return listaMenusSistema;
	}
}
