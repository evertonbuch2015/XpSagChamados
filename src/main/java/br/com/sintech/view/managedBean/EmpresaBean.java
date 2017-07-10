package br.com.sintech.view.managedBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sintech.core.entity.Empresa;
import br.com.sintech.core.entity.Endereco;
import br.com.sintech.core.entity.Estados;
import br.com.sintech.core.service.ServiceEmpresa;
import br.com.sintech.view.util.UtilMensagens;


@ManagedBean
@ViewScoped
public class EmpresaBean extends GenericBean<Empresa, ServiceEmpresa>implements Serializable {
	
	private static final long serialVersionUID = -5293574908725828017L;

	public enum TipoFiltro{
		CODIGO("Código"), NOME("Nome");
		
		private String label;

		TipoFiltro(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}
	
	private TipoFiltro filtro;	
	private Integer idHotel;

	
	public EmpresaBean() {
		super(new ServiceEmpresa());
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
	public Empresa criarEntidade() {
		this.entidade = new Empresa();
		this.entidade.setEndereco(new Endereco());
		entidade.setDataCadastro(new Date());
		return entidade;
	}	
	
	// =============================GET AND SET=====================================

	public Integer getIdHotel() {
		return idHotel;
	}
	
	public void setIdHotel(Integer idHotel) {
		this.idHotel = idHotel;
	}
	
	
	public Estados[] getEstados(){
		return Estados.values();
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
	public List<Empresa> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
}
