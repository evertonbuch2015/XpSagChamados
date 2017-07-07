package br.com.sintech.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.SelectEvent;

import br.com.sintech.core.entity.Chamado;
import br.com.sintech.core.entity.Empresa;
import br.com.sintech.core.entity.Programa;
import br.com.sintech.core.service.ServiceChamado;
import br.com.sintech.core.util.Constantes;
import br.com.sintech.view.util.UtilMensagens;


@ManagedBean
//@ViewScoped
@SessionScoped
public class ChamadoBean extends GenericBean<Chamado, ServiceChamado> implements Serializable {

	public enum TipoFiltro{
		PROTOCOLO("Protocolo");
		
		private String label;

		TipoFiltro(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}
	
	private TipoFiltro filtro;	
	private String nomePrograma;
	
	
	public ChamadoBean() {
		super(new ServiceChamado());
	}
		
	// =======================METODOS DO USUARIO=================================================
	
	public void filtrar(){
		try {
			//this.entidades = service.filtrarTabela(filtro, valorFiltro);
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}
	
	
	@Override
	public Chamado criarEntidade() {		
		return new Chamado();
	}
	

	
	/*public void excluirEmpresa(Empresa empresa){
		if(this.entidade.getEmpresas().contains(empresa)){
			this.entidade.getEmpresas().remove(empresa);
		}
	}
		
	
	public void adicionarEmpresa(Empresa empresa){		
		if(!this.entidade.getEmpresas().contains(empresa)){
			this.entidade.getEmpresas().add(empresa);
		}else{
			UtilMensagens.mensagemAtencao("Empresa já cadastrada para este Usuário");
		}
	}	*/
		
	
	public void programaSelecionada(SelectEvent event){
		this.entidade.setPrograma((Programa) event.getObject());
		this.nomePrograma = this.entidade.getPrograma().getNome(); 
	}
	
	
	// =============================GET AND SET=================================
	
	
	public TipoFiltro getFiltro() {
		return filtro;
	}
		
	public void setFiltro(TipoFiltro filtro) {
		this.filtro = filtro;
	}
	
	
	public TipoFiltro[] tipoFiltros(){
		return TipoFiltro.values();
	}	
	
	
	public List<Empresa> getEmpresas(){
		return Constantes.getInstance().getEmpresas();
	}
	
	
	@Override
	public List<Chamado> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
	
	
	public String getNomePrograma() {
		return nomePrograma;
	}
	
	public void setNomePrograma(String nomePrograma) {
		this.nomePrograma = nomePrograma;
	}
}
