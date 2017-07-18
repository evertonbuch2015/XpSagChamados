package br.com.sintech.view.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.sintech.core.entity.Empresa;
import br.com.sintech.core.entity.Usuario;


public class SessionContext {
	 private static SessionContext instance;
     
     public static SessionContext getInstance(){
        if (instance == null){instance = new SessionContext();}        
        return instance;
     }
     
     
     private SessionContext(){
        
     }
     
     
     private ExternalContext currentExternalContext(){
        if (FacesContext.getCurrentInstance() == null){
            throw new RuntimeException("O FacesContext não pode ser chamado fora de uma requisi��o HTTP");
        }else{
            return FacesContext.getCurrentInstance().getExternalContext();
        }
     }
     
     
     public FacesContext currentFacesContext(){
         if (FacesContext.getCurrentInstance() == null){
             throw new RuntimeException("O FacesContext não pode ser chamado fora de uma requisião HTTP");
         }else{
             return FacesContext.getCurrentInstance();
         }
     }    
     
     
     public void encerrarSessao(){        
        ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().invalidate();
        deleteAttribute("usuarioLogado");
        deleteAttribute("empresaUsuarioLogado");
     }
     
     //-------------------Metodos auxiliares para manipilar atributos de sessao---------------//
     
     public Object getAttribute(String nome){
        return currentExternalContext().getSessionMap().get(nome);
     }
     
     public void setAttribute(String nome, Object valor){
        currentExternalContext().getSessionMap().put(nome, valor);
     }
     
     public void deleteAttribute(String key){
    	 currentExternalContext().getSessionMap().remove(key);
     }
     
     
     //-------------------Metodos auxiliares do Usuario Logado no Sistema--------------------//
     
     public Usuario getUsuarioLogado(){    	 
    	 return (Usuario) currentExternalContext().getSessionMap().get("usuarioLogado");
     }
     
     public boolean usuarioLogadoIsADMIN(){
    	 Usuario usuarioLogado = getUsuarioLogado();
    	 switch (usuarioLogado.getGrupoUsuario()) {
    	 	case ADMIN:
    	 		return true;
    	 	case PROGRAMADOR:
    	 		return false;
    	 	case SUPORTE:
    	 		return false;
    	 	case USUARIO:
    	 		return false;
    	 	default:
    	 		return false;
		}
     }
     
     public boolean usuarioLogadoIsPROGRAMADOR(){
    	 Usuario usuarioLogado = getUsuarioLogado();
    	 switch (usuarioLogado.getGrupoUsuario()) {
    	 	case ADMIN:
    	 		return true;
    	 	case PROGRAMADOR:
    	 		return true;
    	 	case SUPORTE:
    	 		return false;
    	 	case USUARIO:
    	 		return false;
    	 	default:
    	 		return false;
		}
     }
          
     public boolean usuarioLogadoIsSUPORTE(){
    	 Usuario usuarioLogado = getUsuarioLogado();
    	 switch (usuarioLogado.getGrupoUsuario()) {
    	 	case ADMIN:
    	 		return true;
    	 	case PROGRAMADOR:
    	 		return true;
    	 	case SUPORTE:
    	 		return true;
    	 	case USUARIO:
    	 		return false;
    	 	default:
    	 		return false;
		}
     }
          
     public boolean usuarioLogadoIsUSUARIO(){
    	 Usuario usuarioLogado = getUsuarioLogado();
    	 switch (usuarioLogado.getGrupoUsuario()) {
    	 	case ADMIN:
    	 		return true;
    	 	case PROGRAMADOR:
    	 		return true;
    	 	case SUPORTE:
    	 		return true;
    	 	case USUARIO:
    	 		return true;
    	 	default:
    	 		return false;
		}
     }
       
     public Empresa getEmpresaUsuarioLogado(){
    	 Empresa empresaUsuarioLogado = 
    			 (Empresa) currentExternalContext().getSessionMap().get("empresaUsuarioLogado");
    	 
    	 return empresaUsuarioLogado;
     }

}
