package br.com.sintech.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CHAMADO_MOVIMENTO")
public class ChamadoMovimento implements Serializable{

	
	@Id
    @SequenceGenerator(name="G_CHAMADO_MOVIMENTO", sequenceName="\"G_CHAMADO_MOVIMENTO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CHAMADO_MOVIMENTO")
    @Column(name = "COD_CHAMADOMOVIMENTO")
	private Integer idChamadoMovimento;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA")
	private Date data;
	
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name="SITUACAO")
	private SituacaoChamado situacao;
	
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="COD_USUARIO_RESPONSAVEL")
	private Usuario responsavel;
	
	
	
	@ManyToOne
    @JoinColumn(name ="COD_CHAMADO")
    private Chamado chamado;


	//-------------------------------	GETs and SETs------------------------------//

	public Integer getIdChamadoMovimento() {
		return idChamadoMovimento;
	}



	public void setIdChamadoMovimento(Integer idChamadoMovimento) {
		this.idChamadoMovimento = idChamadoMovimento;
	}



	public Date getData() {
		return data;
	}



	public void setData(Date data) {
		this.data = data;
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



	public Usuario getResponsavel() {
		return responsavel;
	}



	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
	}



	public Chamado getChamado() {
		return chamado;
	}



	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}



	//--------------------------------	Métodos Auxiliares------------------------------//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idChamadoMovimento == null) ? 0 : idChamadoMovimento.hashCode());
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
		ChamadoMovimento other = (ChamadoMovimento) obj;
		if (idChamadoMovimento == null) {
			if (other.idChamadoMovimento != null)
				return false;
		} else if (!idChamadoMovimento.equals(other.idChamadoMovimento))
			return false;
		return true;
	}	
}
