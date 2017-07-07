package br.com.sintech.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sintech.core.entity.Programa;

@FacesConverter(forClass = Programa.class)
public class ProgramaConverter implements Converter {
    
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Programa) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Programa) {
        	Programa entity = (Programa) value;
            if (entity != null && entity instanceof Programa && entity.getIdPrograma() != null) {
                uiComponent.getAttributes().put( entity.getIdPrograma().toString(), entity);
                return entity.toString();
            }
        }
        return "";
    }
}