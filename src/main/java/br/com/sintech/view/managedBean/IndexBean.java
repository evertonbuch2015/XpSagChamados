package br.com.sintech.view.managedBean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.sintech.core.entity.Usuario;
import br.com.sintech.view.util.SessionContext;


@ManagedBean
@SessionScoped
public class IndexBean implements Serializable {
	
	private static final long serialVersionUID = 201405150723L;
	
	private String localeCode;
	private static Map<String, Locale> countries;
	private Usuario usuarioLogado;
	private boolean permissaoAdmin,permissaoProgramador,permissaoSuporte,permissaoUsuario;
	
	static {
		countries = new LinkedHashMap<String, Locale>();
		countries.put("English", new Locale("en"));
		countries.put("Português", new Locale("pt"));
	}

	
	public IndexBean() {
		usuarioLogado = SessionContext.getInstance().getUsuarioLogado();
		atribuirPermissoes();
	}

	// =======================METODOS DO USUARIO=====================================	
	
	public void localeCodeChanged(AjaxBehaviorEvent e) {
		//String newLocaleValue = ((SelectOneMenu)e.getSource()).getValue() + "";

		for (Entry<String, Locale> entry : countries.entrySet()) {
			if (entry.getValue().toString().equals(localeCode)) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
			}
		}
	}
	
	
	public String deslogar() {
		
		SessionContext.getInstance().encerrarSessao();
	    
	    return "/login?faces-redirect=true";
	}
	
	// =============================GET AND SET======================================
	
	public Map<String, Locale> getCountries() {
		return countries;
	}

	
	public String getLocaleCode() {
		if ((this.localeCode == null) && (FacesContext.getCurrentInstance().getViewRoot() != null) &&
				(FacesContext.getCurrentInstance().getViewRoot().getLocale() != null)) {
			this.localeCode = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		}

		return this.localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}


	public Usuario getUsuarioLogado(){
		return usuarioLogado;
	}
	
	
	private void atribuirPermissoes() {
		switch (usuarioLogado.getGrupoUsuario()) {
		case ADMIN:
			permissaoAdmin = true; 
			permissaoProgramador = true;
			permissaoSuporte = true;
			permissaoUsuario = true;
			break;
		case PROGRAMADOR:
			permissaoAdmin = false; 
			permissaoProgramador = true;
			permissaoSuporte = true;
			permissaoUsuario = true;
			break;
		case SUPORTE:
			permissaoAdmin = false;
			permissaoProgramador = false;
			permissaoSuporte = true;
			permissaoUsuario = true;
			break;
		case USUARIO:
			permissaoAdmin = false;
			permissaoProgramador = false;
			permissaoSuporte = false;
			permissaoUsuario = true;
			break;
		}
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isPermissaoAdmin() {
		return permissaoAdmin;
	}

	public boolean isPermissaoProgramador() {
		return permissaoProgramador;
	}

	public boolean isPermissaoSuporte() {
		return permissaoSuporte;
	}

	public boolean isPermissaoUsuario() {
		return permissaoUsuario;
	}	
}
