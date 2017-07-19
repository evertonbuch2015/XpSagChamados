package br.com.sintech.view.managedBean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Session;

import br.com.sintech.core.dao.ChamadoDao;
import br.com.sintech.view.util.GeradorRelatorio;

@ManagedBean
@ViewScoped
public class RelChamadoBean {

	private Date dataInicial;
	private Date dataFinal;
	
	private ChamadoDao dao;
	
	public RelChamadoBean() {
		this.dao = new ChamadoDao();
	}
	
	public void gerarRelatorio(){
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DATA_INICIAL", new java.sql.Timestamp(dataInicial.getTime()));
		parametros.put("DATA_FINAL", new java.sql.Timestamp(dataFinal.getTime()));
				
		GeradorRelatorio geradorRelatorio = new GeradorRelatorio("/relatorios/report_chamados.jrxml", parametros);
				
		Session session = dao.getEntityManager().unwrap(Session.class);
		session.doWork(geradorRelatorio);
		
		FacesContext.getCurrentInstance().responseComplete();
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}	
}
