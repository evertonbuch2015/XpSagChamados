package br.com.sintech.view.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.primefaces.context.RequestContext;

import br.com.sintech.core.entity.Programa;
import br.com.sintech.core.util.Constantes;


@ManagedBean
@ViewScoped
public class DialogProgramaBean implements Serializable{
	
	private static final long serialVersionUID = 1448842323669993257L;
	private List<Programa> programas; 
	private String filter;
	
	
	public DialogProgramaBean() {
		programas = new ArrayList<>();
		programas.addAll(Constantes.getInstance().getProgramas());
	}
	
	
	public void filtrarLista(){
		
		if(filter == ""){
			programas.clear();
			programas.addAll(Constantes.getInstance().getProgramas());
		}else{
			CollectionUtils.filter(programas, new Predicate<Programa>() {

				@Override
				public boolean evaluate(Programa arg0) {
					if(((Programa) arg0).getNome() != null){
						return ((Programa) arg0).getNome().contains(filter);
					}else{
						return false;
					}					
				}
			});
		}		
	}
	
	
	public void abrirDialog() {		
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("width", 550);
        options.put("height", 525);
        options.put("position", "center center");        
        options.put("closable", false);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        //options.put("headerElement", "customheader");
        RequestContext.getCurrentInstance().openDialog("/dialogs/dialogProgramas", options, null);
    }
	
	
	public void retornoDialog(Programa entidade){
		RequestContext.getCurrentInstance().closeDialog(entidade);
	}
	
	public void fecharDialog(){
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	//--------------------------------	GETs and SETs------------------------------//
	
	public List<Programa> getProgramas() {
		return programas;
	}
	
	public String getFilter() {
		return filter;
	}
	
	public void setFilter(String filter) {
		this.filter = filter;
	}
}
