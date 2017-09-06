package br.com.sintech.view.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sintech.core.dao.AutorizacaoDao;
import br.com.sintech.core.entity.Autorizacao;
import br.com.sintech.core.entity.Empresa;
import br.com.sintech.core.util.Constantes;
import br.com.sintech.view.util.SessionContext;
import br.com.sintech.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class AutorizacaoBean implements Serializable{

	private static final long serialVersionUID = -7788910464093760104L;
	private Empresa empresa;
	private AutorizacaoDao autorizacaoDao;
	private List<Autorizacao> listaAutorizacoes;
	private Autorizacao autorizacao;
	private boolean mostrarPanel;
	
	public AutorizacaoBean() {		
		autorizacaoDao = new AutorizacaoDao();
		
		mostrarPanel = true;
		//se for um usuario traz somente da empresa logada.
		if(!SessionContext.getInstance().usuarioLogadoIsSUPORTE()){
			mostrarPanel = false;
			this.empresa = SessionContext.getInstance().getEmpresaUsuarioLogado();
			buscarAutorizacoes();
		}
	}
	
		
	public void buscarAutorizacoes(){
		try {
			listaAutorizacoes = autorizacaoDao.findAutorizacaoByEmpresa(empresa);
		} catch (Exception e) {
			UtilMensagens.mensagemErro("Ocorreu um Erro ao Buscar as Autorizações. Erro Nº 100, contate o Suporte.");
			e.printStackTrace();
		}
	}
	
	
	
	public List<Empresa> getEmpresas(){
		if(SessionContext.getInstance().usuarioLogadoIsSUPORTE()){
			return Constantes.getInstance().getEmpresasAtivas();
		}else{
			List<Empresa> lista = new ArrayList<>();
			lista.add(SessionContext.getInstance().getEmpresaUsuarioLogado());
			return lista;
					
		}
	}
	
	
	public List<Autorizacao> getListaAutorizacoes() {
		return listaAutorizacoes;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public Autorizacao getAutorizacao() {
		return autorizacao;
	}
	
	public void setAutorizacao(Autorizacao autorizacao) {
		this.autorizacao = autorizacao;
	}
	
	public boolean isMostrarPanel() {
		return mostrarPanel;
	}
}
