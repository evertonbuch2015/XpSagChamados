package br.com.sintech.core.entity;



public class Autorizacao {

	
	private Integer idAutorizacao;	
	private String mes;
	private String ano;
	private Integer autorizacao;
	
	private Integer codigoEmpresa;
	private String nomeEmpresa;
	private String documentoEmpresa;
	
	public Autorizacao() {
	
	}


	


	public Integer getIdAutorizacao() {
		return idAutorizacao;
	}


	public void setIdAutorizacao(Integer idAutorizacao) {
		this.idAutorizacao = idAutorizacao;
	}


	public String getMes() {
		return mes;
	}


	public void setMes(String mes) {
		this.mes = mes;
	}


	public String getAno() {
		return ano;
	}


	public void setAno(String ano) {
		this.ano = ano;
	}


	public Integer getAutorizacao() {
		return autorizacao;
	}


	public void setAutorizacao(Integer autorizacao) {
		this.autorizacao = autorizacao;
	}


	public Integer getCodigoEmpresa() {return codigoEmpresa;}
	public void setCodigoEmpresa(Integer codigoEmpresa) {this.codigoEmpresa = codigoEmpresa;}

	public String getNomeEmpresa() {return nomeEmpresa;}
	public void setNomeEmpresa(String nomeEmpresa) {this.nomeEmpresa = nomeEmpresa;}

	public String getDocumentoEmpresa() {return documentoEmpresa;}
	public void setDocumentoEmpresa(String documentoEmpresa) {this.documentoEmpresa = documentoEmpresa;}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAutorizacao == null) ? 0 : idAutorizacao.hashCode());
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
		Autorizacao other = (Autorizacao) obj;
		if (idAutorizacao == null) {
			if (other.idAutorizacao != null)
				return false;
		} else if (!idAutorizacao.equals(other.idAutorizacao))
			return false;
		return true;
	}
}
