package br.com.sintech.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sintech.core.entity.Empresa;

@FacesConverter(forClass = Empresa.class)
public class EmpresaConverter implements Converter	 {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			Empresa empresa = (Empresa) uiComponent.getAttributes().get(value);
			return empresa;
		}
		return null;
	}	

	@Override	
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof Empresa) {
			Empresa entidade = (Empresa) value;
			if (entidade != null && entidade instanceof Empresa && entidade.getIdEmpresa() != null) {
				uiComponent.getAttributes().put(entidade.getIdEmpresa().toString(), entidade);
				return entidade.getIdEmpresa().toString();
			}
		}
		return "";
	}
}