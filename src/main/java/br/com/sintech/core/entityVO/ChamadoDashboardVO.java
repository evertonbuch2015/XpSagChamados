package br.com.sintech.core.entityVO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChamadoDashboardVO implements Serializable{

	
	private static final long serialVersionUID = -5637350118549478015L;
	
	private Date dataSolicitacao;
	private Integer qtd;
	
	
	public ChamadoDashboardVO(Date dataSolicitacao, Integer qtd) {
		super();
		this.dataSolicitacao = dataSolicitacao;
		this.qtd = qtd;
	}



	public ChamadoDashboardVO() {	
	}


	public String getDataSolicitacaoFormatada() {return new SimpleDateFormat("MM/yyyy").format(dataSolicitacao);}
	public Date getDataSolicitacao() {return dataSolicitacao;}
	public void setDataSolicitacao(Date dataSolicitacao) {this.dataSolicitacao = dataSolicitacao;}
	
	public Integer getQtd() {return qtd;}
	public void setQtd(Integer qtd) {this.qtd = qtd;}
}
