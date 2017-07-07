package br.com.sintech.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="CHAMADO_ANEXO")
public class ChamadoAnexo implements Serializable {

	
	@Id
    @SequenceGenerator(name="G_CHAMADO_ANEXO", sequenceName="\"G_CHAMADO_ANEXO\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_CHAMADO_ANEXO")
    @Column(name = "COD_CHAMADOANEXO")
    private Integer idChamadoAnexo;
	
	
	
	@Lob
	@Column(name="ARQUIVO")
	private byte[] arquivo;
	
	
	@Column(name="EXTENSAO", length =5)
	private String extensao;


	@ManyToOne
    @JoinColumn(name ="COD_CHAMADO")
    private Chamado chamado;
	
	
	//-------------------------------	GETs and SETs------------------------------//
	
	public Integer getIdChamadoAnexo() {
		return idChamadoAnexo;
	}


	public void setIdChamadoAnexo(Integer idChamadoAnexo) {
		this.idChamadoAnexo = idChamadoAnexo;
	}


	public byte[] getArquivo() {
		return arquivo;
	}


	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}


	public String getExtensao() {
		return extensao;
	}


	public void setExtensao(String extensao) {
		this.extensao = extensao;
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
		result = prime * result + ((idChamadoAnexo == null) ? 0 : idChamadoAnexo.hashCode());
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
		ChamadoAnexo other = (ChamadoAnexo) obj;
		if (idChamadoAnexo == null) {
			if (other.idChamadoAnexo != null)
				return false;
		} else if (!idChamadoAnexo.equals(other.idChamadoAnexo))
			return false;
		return true;
	}	
}
