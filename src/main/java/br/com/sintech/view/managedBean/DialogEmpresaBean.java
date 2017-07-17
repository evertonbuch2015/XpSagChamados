package br.com.sintech.view.managedBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.sintech.core.entity.Empresa;
import br.com.sintech.core.util.Constantes;

@ManagedBean
@ViewScoped
public class DialogEmpresaBean implements Serializable{
	
	private static final long serialVersionUID = 1448842323669993257L;


	public void abrirDialog() {		
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("width", 550);
        options.put("height", 550);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        RequestContext.getCurrentInstance().openDialog("/dialogs/dialogEmpresas", options, null);
    }
	
	
	public void retornoDialog(Empresa entidade){
		RequestContext.getCurrentInstance().closeDialog(entidade);
	}
	
	
	//--------------------------------	GETs and SETs------------------------------//
	
	public List<Empresa> getEmpresas() {
		return Constantes.getInstance().getEmpresasAtivas();
	}
}
