package br.com.sintech.view.managedBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sintech.core.entity.Usuario;
import br.com.sintech.core.service.ServiceUsuario;
import br.com.sintech.core.util.NegocioException;
import br.com.sintech.view.util.SessionContext;
import br.com.sintech.view.util.UtilMensagens;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1444783038549353503L;
	
	private ServiceUsuario usuarioService;
	
	private Usuario usuario;
	private String fraseSecreta;
	private String email;
	private String senha;
	private String login;
	
	
	public LoginBean() {
		usuario = new Usuario();
		usuarioService = new ServiceUsuario();
	}
	
	
	// ================Métodos do Usuário============================================
	
	public String efetuaLogin() {		
		if(usuarioService.fazerLogin(this.login, this.senha)){
			this.usuario.setNomeUsuario(login);
			this.usuario = usuarioService.buscarPeloNome(this.usuario);
			
			SessionContext.getInstance().setAttribute("usuarioLogado", this.usuario);
			
			return "index?faces-redirect=true";
		}else{
			this.usuario = new Usuario();
			UtilMensagens.mensagemAtencao("Usuário não encontrado");
			return null;
		}		
	}

	
	public String abrirUsuario(){
		
		Usuario usuarioLogado = SessionContext.getInstance().getUsuarioLogado();
		
		if(usuarioLogado != null) {
			return "editarUsuario?faces-redirect=true&usuarioId=" + usuarioLogado.getIdUsusario();
		}
		return "#";
	}
	
	
	public void recuperarSenha(){
		
		try {
			usuarioService.recuperarSenha(email, fraseSecreta);
			UtilMensagens.mensagemInformacao("Enviamos um email de redefinição de senha para a conta: " + email);
			
		} 
		catch (NegocioException e) {			
			UtilMensagens.mensagemAtencao(e.getMessage());			
		}
		finally {
			email = "";
			senha = "";
		}
	}
	
	
	// ================Métodos GET e SET=============================================
		
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	
	public String getFraseSecreta() {
		return fraseSecreta;
	}

	public void setFraseSecreta(String fraseSecreta) {
		this.fraseSecreta = fraseSecreta;
	}

	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}	
}
