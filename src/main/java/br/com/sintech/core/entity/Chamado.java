package br.com.sintech.core.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="UPD_SUPORTE")
public class Chamado implements Serializable{

	private static final long serialVersionUID = 8028945669874224625L;


	@Id
    @SequenceGenerator(name="G_UPD_SUPORTE", sequenceName="\"G_UPD_SUPORTE\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_UPD_SUPORTE")
    @Column(name = "COD_UPDSUPORTE")
    private Integer idChamado;
	
	
	@Column(name="PROTOCOLO")
	private Double protocolo;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_SOLICITACAO")
	private Date dataSolicitacao;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_ENCERRAMENTO")
	private Date dataEncerramento;
	
	
	@Column(name="RESUMO_SOLICITACAO")
	private String titulo;
	
	
	@Column(name="DESCRICAO_SOLICITACAO")
	private String descricao;
	
	

	@Column(name="SITUACAO", insertable=false, updatable=false)
	private String situacao;
	
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="CODIGOSISUSUARIO_ABERTURA")
	private Usuario usuario;
	

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="COD_UPDCLIENTES")
	private Empresa empresa;
	
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="COD_UPDCADPROGRAMAS")
	private Programa programa;
	
	
	@OneToMany(mappedBy = "chamado", targetEntity = ChamadoMovimento.class,
			fetch = FetchType.LAZY, cascade = CascadeType.ALL)   
	private List<ChamadoMovimento> movimentos;
	
	
	@OneToMany(mappedBy = "chamado", targetEntity = ChamadoAnexo.class,
			fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ChamadoAnexo> anexos;

	
	//-------------------------------	GETs and SETs------------------------------//
	
	public Integer getIdChamado() {
		return idChamado;
	}

	public void setIdChamado(Integer idChamado) {
		this.idChamado = idChamado;
	}


	public Double getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(Double protocolo) {
		this.protocolo = protocolo;
	}

	
	public String getDataSolicitacaoFormatada() {
		if(this.dataSolicitacao != null){
			return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataSolicitacao);
		}
		return "";
	}
	
	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	
	
	public String getDataEncerramentoFormatada() {
		if(this.dataEncerramento != null){
			return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataEncerramento);
		}
		return "";
	}
	
	public Date getDataEncerramento() {
		return dataEncerramento;
	}
	
	public void setDataEncerramento(Date dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}


	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	
	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}


	public List<ChamadoMovimento> getMovimentos() {
		if(movimentos == null){
			movimentos = new ArrayList<ChamadoMovimento>();
		}
		return movimentos;
	}

	public void setMovimentos(List<ChamadoMovimento> movimentos) {
		this.movimentos = movimentos;
	}


	public List<ChamadoAnexo> getAnexos() {
		if(anexos == null){
			anexos = new ArrayList<ChamadoAnexo>();
		}
		return anexos;
	}

	public void setAnexos(List<ChamadoAnexo> anexos) {
		this.anexos = anexos;
	}


	//--------------------------------	Métodos Auxiliares------------------------------//

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idChamado == null) ? 0 : idChamado.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chamado other = (Chamado) obj;
		if (idChamado == null) {
			if (other.idChamado != null)
				return false;
		} else if (!idChamado.equals(other.idChamado))
			return false;
		return true;
	}
}
