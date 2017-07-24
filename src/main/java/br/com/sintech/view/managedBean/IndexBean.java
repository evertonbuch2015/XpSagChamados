package br.com.sintech.view.managedBean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.sintech.core.dao.ChamadoDao;
import br.com.sintech.core.entity.GrupoUsuario;
import br.com.sintech.core.entity.Usuario;
import br.com.sintech.core.entityVO.ChamadoDashboardVO;
import br.com.sintech.core.util.PersistenciaException;
import br.com.sintech.view.util.SessionContext;


@ManagedBean
@SessionScoped
public class IndexBean implements Serializable {
	
	private static final long serialVersionUID = 201405150723L;
	
	private String localeCode;
	private static Map<String, Locale> countries;
	private Usuario usuarioLogado;
	private boolean permissaoAdmin, permissaoProgramador, permissaoSuporte, permissaoUsuario;
	private BarChartModel barChartModel;
	
	static {
		countries = new LinkedHashMap<String, Locale>();
		countries.put("English", new Locale("en"));
		countries.put("Português", new Locale("pt"));
	}

	
	public IndexBean(){
		usuarioLogado = SessionContext.getInstance().getUsuarioLogado();				
	}

	
	@PostConstruct
	public void init(){
		atribuirPermissoes();
		
		if(SessionContext.getInstance().getUsuarioLogado().getGrupoUsuario().equals(GrupoUsuario.USUARIO)){
			initChartModel();
		}
	}
	
	// =======================METODOS DO USUARIO=====================================	
	
	public void localeCodeChanged(AjaxBehaviorEvent e) {
		//String newLocaleValue = ((SelectOneMenu)e.getSource()).getValue() + "";

		for (Entry<String, Locale> entry : countries.entrySet()) {
			if (entry.getValue().toString().equals(localeCode)) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
			}
		}
	}
	
	
	public String deslogar() {
		
		SessionContext.getInstance().encerrarSessao();
	    
	    return "/login?faces-redirect=true";
	}
	
	
	private void initChartModel(){
		ChartSeries series;
		try {
			this.barChartModel = new BarChartModel();
			
			series = new ChartSeries();
			series.setLabel("Chamados");
			
			List<ChamadoDashboardVO>	lista = 
					new ChamadoDao().getChamadosVOBySituacao(SessionContext.getInstance().getEmpresaUsuarioLogado());
			
			for (ChamadoDashboardVO entidade : lista) {
				series.set(entidade.getDataSolicitacaoFormatada(), entidade.getQtd());
			}
			

			barChartModel.setBarWidth(25);
			barChartModel.addSeries(series);
			barChartModel.setShowPointLabels(true);
			barChartModel.setAnimate(true);
			barChartModel.setTitle("Histórico de Chamados registrados por Mês/Ano");
			barChartModel.setLegendPosition("ne");
					
			barChartModel.getAxis(AxisType.X).setTickAngle(-50);;

		} catch (PersistenciaException e) {			
			e.printStackTrace();
			this.barChartModel = new BarChartModel();
		}
	}
	
	// =============================GET AND SET======================================
	
	public Map<String, Locale> getCountries() {
		return countries;
	}

	
	public String getLocaleCode() {
		if ((this.localeCode == null) && (FacesContext.getCurrentInstance().getViewRoot() != null) &&
				(FacesContext.getCurrentInstance().getViewRoot().getLocale() != null)) {
			this.localeCode = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		}

		return this.localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}


	public Usuario getUsuarioLogado(){
		return usuarioLogado;
	}
	
	
	private void atribuirPermissoes() {
		permissaoAdmin = SessionContext.getInstance().usuarioLogadoIsADMIN();
		permissaoProgramador = SessionContext.getInstance().usuarioLogadoIsPROGRAMADOR();
		permissaoSuporte = SessionContext.getInstance().usuarioLogadoIsSUPORTE();
		permissaoUsuario = SessionContext.getInstance().usuarioLogadoIsUSUARIO();		
	}

	
	public boolean isPermissaoAdmin() {
		return permissaoAdmin;
	}

	public boolean isPermissaoProgramador() {
		return permissaoProgramador;
	}

	public boolean isPermissaoSuporte() {
		return permissaoSuporte;
	}

	public boolean isPermissaoUsuario() {
		return permissaoUsuario;
	}	

	
	public BarChartModel getBarChartModel() {
		return barChartModel;
	}


	public boolean isRenderizarChart() {
		return SessionContext.getInstance().getUsuarioLogado().getGrupoUsuario().equals(GrupoUsuario.USUARIO);
	}
}
