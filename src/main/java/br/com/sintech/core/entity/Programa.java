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
@Table(name="UPD_CAD_PROGRAMAS")
public class Programa implements Serializable{

	private static final long serialVersionUID = 4264765383244512511L;


	@Id
    @SequenceGenerator(name="G_UPD_CAD_PROGRAMAS", sequenceName="\"G_UPD_CAD_PROGRAMAS\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_UPD_CAD_PROGRAMAS")
    @Column(name = "COD_UPDCADPROGRAMAS")
    private Integer idPrograma;
	
	
	@Column(name = "PROGRAMA",length = 40)
	private String programa;
	
	
	@Column(name = "NOME_MENU",length= 40)
	private String nome;
	
	
	@Column(name = "VERSAO",length= 10)
	private String versao;
	
	
	//@Enumerated(EnumType.STRING)
	@Column(name = "MENU")
	private String menu;

	
	public Integer getIdPrograma() {
		return idPrograma;
	}


	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}


	public String getPrograma() {
		return programa;
	}


	public void setPrograma(String programa) {
		this.programa = programa;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getVersao() {
		return versao;
	}


	public void setVersao(String versao) {
		this.versao = versao;
	}


	public String getMenu() {
		return menu;
	}


	public void setMenu(String menu) {
		this.menu = menu;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPrograma == null) ? 0 : idPrograma.hashCode());
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
		Programa other = (Programa) obj;
		if (idPrograma == null) {
			if (other.idPrograma != null)
				return false;
		} else if (!idPrograma.equals(other.idPrograma))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {	
		return nome + "  /  " + programa;
	}
}
