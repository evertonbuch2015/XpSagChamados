package br.com.sintech.view.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import br.com.sintech.core.entity.Empresa;
import br.com.sintech.core.entity.GrupoUsuario;
import br.com.sintech.core.entity.Usuario;
import br.com.sintech.core.service.ServiceUsuario;
import br.com.sintech.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class UsuarioBean extends GenericBean<Usuario, ServiceUsuario>implements Serializable{

	private static final long serialVersionUID = -3175517834044022637L;

	public enum TipoFiltro{
		CODIGO("Código"), 
		NOME("Nome Usuário");
		
		private String label;

		TipoFiltro(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}
	
	private TipoFiltro filtro;	
	private Integer usuarioId;
	
	
	public UsuarioBean() {
		super(new ServiceUsuario());
	}
		
	// =======================METODOS DO USUARIO=================================================
	
	public void filtrar(){
		try {
			this.entidades = service.filtrarTabela(filtro, valorFiltro);
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}
	
	
	@Override
	public Usuario criarEntidade() {		
		return new Usuario();
	}
	
	
	public void excluirEmpresa(Empresa empresa){
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
	}	
		
	
	public void empresaSelecionada(SelectEvent event){
		Empresa empresa = (Empresa) event.getObject();
		adicionarEmpresa(empresa);
	}
	
	
	// =============================GET AND SET=================================
	
	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	
	public TipoFiltro getFiltro() {
		return filtro;
	}
		
	public void setFiltro(TipoFiltro filtro) {
		this.filtro = filtro;
	}

		
	public GrupoUsuario[] getGrupoUsuarios(){
		return GrupoUsuario.values();
	}	
		
	
	//RETORNA LISTA DE SETORES PARA O COMBO
	public List<String> getSetores(){
		return service.buscarSetores();
	}
	
	
	//RETORNA LISTA DE FILTROS PARA O COMBO
	public TipoFiltro[] tipoFiltros(){
		return TipoFiltro.values();
	}

	@Override
	public List<Usuario> getEntidades() {
		return super.getEntidades();
	}
	
}
