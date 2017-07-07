package br.com.sintech.core.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Table(name="CHAMADO")
public class Chamado implements Serializable{

	
	@Id
    @SequenceGenerator(name="G_CHAMADO", sequenceName="\"G_CHAMADO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CHAMADO")
    @Column(name = "COD_CHAMADO")
    private Integer idChamado;
	
	
	@Column(name="PROTOCOLO",length= 20)
	private String protocolo;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_SOLICITACAO")
	private Date dataSolicitacao;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_ENCERRAMENTO")
	private Date dataEncerramento;
	
	
	@Column(name="TITULO")
	private String titulo;
	
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	
	@Column(name="SITUACAO", insertable=false, updatable=false)
	private SituacaoChamado situacao;
	
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="COD_USUARIO")
	private Usuario usuario;
	

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="COD_CADEMPRESA")
	private Empresa empresa;
	
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="COD_CADPROGRAMA")
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


	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	
	public String getDataSolicitacaoFormatada() {
		if(this.dataSolicitacao != null){
			new SimpleDateFormat("dd/MM/yyyy").format(dataSolicitacao);
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
			new SimpleDateFormat("dd/MM/yyyy").format(dataEncerramento);
		}
		return "Em Aberto";
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


	public SituacaoChamado getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoChamado situacao) {
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
		return movimentos;
	}

	public void setMovimentos(List<ChamadoMovimento> movimentos) {
		this.movimentos = movimentos;
	}


	public List<ChamadoAnexo> getAnexos() {
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
