package br.com.sintech.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sintech.core.entity.SituacaoChamado;

@FacesConverter(forClass = SituacaoChamado.class)
public class SituacaoChamadoConverter implements Converter	 {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			SituacaoChamado entidade = (SituacaoChamado) uiComponent.getAttributes().get(value);
			return entidade;
		}
		return null;
	}	

	@Override	
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof SituacaoChamado) {
			SituacaoChamado entidade = (SituacaoChamado) value;
			if (entidade != null && entidade instanceof SituacaoChamado && entidade.getIdSituacaoChamado() != null) {
				uiComponent.getAttributes().put(entidade.getIdSituacaoChamado().toString(), entidade);
				return entidade.getIdSituacaoChamado().toString();
			}
		}
		return "";
	}
}