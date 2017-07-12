package br.com.sintech.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="UPD_STATUS")
public class SituacaoChamado implements Serializable{

	
	@Id
    @SequenceGenerator(name="G_UPD_STATUS", sequenceName="\"G_UPD_STATUS\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_UPD_STATUS")
    @Column(name = "COD_UPDSTATUS")
    private Integer idSituacaoChamado;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Column(name = "SIGLA")
	private Character sigla;
	
	@Column(name = "SITUACAO_INICIAL")
	private Character eventoInicial;

	//-------------------------------	GETs and SETs------------------------------//
	
	public Integer getIdSituacaoChamado() {return idSituacaoChamado;}
	public void setIdSituacaoChamado(Integer idSituacaoChamado) {this.idSituacaoChamado = idSituacaoChamado;}

	
	public String getDescricao() {return descricao;	}
	public void setDescricao(String descricao) {this.descricao = descricao;	}

	
	public Character getSigla() {return sigla;}
	public void setSigla(Character sigla) {this.sigla = sigla;}

	
	public Character getEventoInicial() {return eventoInicial;}
	public void setEventoInicial(Character eventoInicial) {	this.eventoInicial = eventoInicial;}

	

	public Boolean isEventoInicial() {
		if (this.eventoInicial == null)
			return null;
		
		return eventoInicial.equals('S') ? true : false;
	}
	
	//--------------------------------	Métodos Auxiliares------------------------------//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idSituacaoChamado == null) ? 0 : idSituacaoChamado.hashCode());
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
		SituacaoChamado other = (SituacaoChamado) obj;
		if (idSituacaoChamado == null) {
			if (other.idSituacaoChamado != null)
				return false;
		} else if (!idSituacaoChamado.equals(other.idSituacaoChamado))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return sigla + " - "+ descricao;
	}
}
