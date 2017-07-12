package br.com.sintech.core.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="UPD_SUPORTE_ANEXOS")
public class ChamadoAnexo implements Serializable {

	private static final long serialVersionUID = -7361524004280774623L;


	@Id
    @SequenceGenerator(name="G_UPD_SUPORTE_ANEXOS", sequenceName="\"G_UPD_SUPORTE_ANEXOS\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_UPD_SUPORTE_ANEXOS")
    @Column(name = "COD_UPDSUPORTEANEXOS")
    private Integer idChamadoAnexo;
	
	
	@Column(name="NOME")
	private String nome;
	
	
	@Lob
	@Column(name="ARQUIVO", updatable=false)	
	@Basic(fetch=FetchType.LAZY)
	private byte[] arquivo;
	
	
	@Column(name="CAMINHO", length =100)
	private String caminho;
	
	
	@Column(name="EXTENSAO", length =5)
	private String extensao;

	
	@Column(name="TAMANHO")
	private Integer tamanho;
	
	
	@ManyToOne
    @JoinColumn(name ="COD_UPDSUPORTE")
    private Chamado chamado;
	

	@Column(name ="CONTENT_TYPE")
	private String contentType;

	
	public ChamadoAnexo() {

	}
	
	
	
	
	public ChamadoAnexo(Integer idChamadoAnexo, String nome, String caminho, String extensao, Integer tamanho,
			Chamado chamado) {
		this.idChamadoAnexo = idChamadoAnexo;
		this.nome = nome;
		this.caminho = caminho;
		this.extensao = extensao;
		this.tamanho = tamanho;
		this.chamado = chamado;
	}



	public ChamadoAnexo(Integer idChamadoAnexo, String nome, String caminho, String extensao, Integer tamanho,
			byte[] arquivo, Chamado chamado, String contentType) {
		this.idChamadoAnexo = idChamadoAnexo;
		this.nome = nome;
		this.caminho = caminho;
		this.extensao = extensao;
		this.tamanho = tamanho;
		this.arquivo = arquivo;
		this.chamado = chamado;
		this.contentType = contentType;
	}



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

	
	public String getCaminho() {
		return caminho;
	}
	
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	

	public String getExtensao() {
		return extensao;
	}


	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public String getTamanhoFormatado() {
		if(tamanho > 0){
			if(tamanho >= 1024 && tamanho < 1048576){
				return (tamanho / 1024)+" KB";
			}else if(tamanho >= 1048576){
				return (tamanho / 1048576)+" MB";
			}else{
				return tamanho + "Bytes";
			}
		}else{
			return "0 Bytes";
		}		
	}
	
	public Integer getTamanho() {
		return tamanho;
	}

	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}


	public Chamado getChamado() {
		return chamado;
	}


	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
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
