package br.com.sintech.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.sintech.core.entity.MenuSistema;
import br.com.sintech.core.entity.Programa;
import br.com.sintech.core.service.ServicePrograma;
import br.com.sintech.view.util.UtilMensagens;


@ManagedBean
@SessionScoped
public class ProgramaBean extends GenericBean<Programa, ServicePrograma>implements Serializable {
	
	private static final long serialVersionUID = -5293574908725828017L;

	public enum TipoFiltro{
		CODIGO("Código"), 
		NOME("Nome"),
		PROGRAMA("Programa");
		
		private String label;

		TipoFiltro(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}
	
	
	private TipoFiltro filtro;	

	
	public ProgramaBean() {
		super(new ServicePrograma());
	}
	
	
	// =======================METODOS DO USUARIO=====================================
	
	public void filtrar(){
		try {
			this.entidades = this.service.filtrarTabela(filtro, valorFiltro);
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}

	
	
	@Override
	public Programa criarEntidade() {
		return new Programa();
	}	
	
	// =============================GET AND SET=====================================

	
	public MenuSistema[] getMenusSistema(){
		return MenuSistema.values();
	}

	
	public TipoFiltro[] tipoFiltros(){
		return TipoFiltro.values();
	}
	
	public TipoFiltro getFiltro() {
		return filtro;
	}
	
	public void setFiltro(TipoFiltro filtro) {
		this.filtro = filtro;
	}

	
	@Override
	public List<Programa> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
}
