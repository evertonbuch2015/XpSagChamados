package br.com.sintech.view.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.com.sintech.core.entity.Usuario;
import br.com.sintech.core.util.Constantes;

public class SessionControllerListener implements HttpSessionListener{

	
	
	public SessionControllerListener() {
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {		
				
	}
	
	

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		Usuario u = (Usuario) se.getSession().getAttribute("usuarioLogado");
        if (u != null) {
             Constantes.getInstance().removeUsuarioLogado(u);
        }
	}

}
