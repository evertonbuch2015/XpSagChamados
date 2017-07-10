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
@Table(name="PARAMETRO")
public class Parametro implements Serializable{

	
	@Id
    @SequenceGenerator(name="G_PARAMETRO", sequenceName="\"G_PARAMETRO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_PARAMETRO")
    @Column(name = "COD_PARAMETRO")
    private Integer idParametro;
	
	@Column(name="SALVAR_ANEXO_BANCO")
	private Character salvarAnexoBanco;
	
	
	
	
	public Integer getIdParametro() {
		return idParametro;
	}
	
	public void setIdParametro(Integer idParametro) {
		this.idParametro = idParametro;
	}
	
	
	public Character getSalvarAnexoBanco() {
		return salvarAnexoBanco;
	}
	
	public void setSalvarAnexoBanco(Character salvarAnexoBanco) {
		this.salvarAnexoBanco = salvarAnexoBanco;
	}
}
