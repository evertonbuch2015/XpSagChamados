package br.com.sintech.view.managedBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.SelectEvent;

import br.com.sintech.core.entity.SituacaoChamado;
import br.com.sintech.core.service.ServiceChamado;
import br.com.sintech.core.util.Constantes;
import br.com.sintech.view.util.SessionContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean
@ViewScoped
public class RelChamadoBean {

	private Date dataInicial;
	private Date dataFinal;
	private boolean datasIsNull;
	private SituacaoChamado situacaoChamado;
	
	//private ChamadoDao dao;
	private ServiceChamado service;
	
	
	public RelChamadoBean() {
		//this.dao = new ChamadoDao();
		this.service = new ServiceChamado();
	}
	
	// =======================METODOS DO USUARIO=================================================
	
	public void gerarRelatorio(){
		HttpServletResponse response = 
				(HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DATA_INICIAL", new java.sql.Timestamp(dataInicial.getTime()));
		parametros.put("DATA_FINAL", new java.sql.Timestamp(dataFinal.getTime()));
		
				
		if(situacaoChamado != null){
			parametros.put("P_SITUACAO", situacaoChamado.getIdSituacaoChamado());
		}
		
		//se for um usuario traz somente da empresa logada.
		if(!SessionContext.getInstance().usuarioLogadoIsSUPORTE()){
			parametros.put("P_COD_UPDCLIENTES", SessionContext.getInstance().getEmpresaUsuarioLogado().getIdEmpresa());
		}
				
		
		try {
			
			JasperPrint print = service.getReportByDataSolicitacaoSituacao("/relatorios/report_chamados.jrxml", parametros);
			
			byte[] b = JasperExportManager.exportReportToPdf(print);
			response.setContentType("application/pdf");
			response.getOutputStream().write(b);
			response.getCharacterEncoding();
			
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}catch (IOException e) {		
			e.printStackTrace();
		} 
		
		
		
		/*GeradorRelatorio geradorRelatorio = new GeradorRelatorio("/relatorios/report_chamados.jrxml", parametros);
				
		Session session = dao.getEntityManager().unwrap(Session.class);
		session.doWork(geradorRelatorio);*/
		
		
	}
	
	
	public void onDataSelect(SelectEvent event) {
		datasIsNull = (dataInicial != null && dataFinal != null);
    }
	
	// =============================GET AND SET=================================
	
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

	
	public SituacaoChamado getSituacaoChamado() {
		return situacaoChamado;
	}	
	public void setSituacaoChamado(SituacaoChamado situacaoChamado) {
		this.situacaoChamado = situacaoChamado;
	}
	
	
	public boolean isDatasIsNull() {
		return datasIsNull;
	}
	
	
	public List<SituacaoChamado> getSituacoesChamado(){
		return Constantes.getInstance().getListaSituacaoChamado();
	}	
}
