package br.com.sintech.core.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="UPD_CLIENTES")
public class Empresa implements Serializable {

	private static final long serialVersionUID = -5693542061510108613L;


	@Id
    @SequenceGenerator(name="G_UPD_CLIENTES", sequenceName="\"G_UPD_CLIENTES\"", allocationSize=1)  
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="G_UPD_CLIENTES")
    @Column(name = "COD_UPDCLIENTES")
    private Integer idEmpresa;
	
	
    @Column(name = "CODIGO" ,nullable = true , length = 2, unique = true)
	private String codigo;
   
    
    @Column(name = "FILIAL" ,nullable = true , length = 2, unique = true)
	private String filial;
    
	
	@NotEmpty(message="O Nome deve ser informado!")
    @Column(name = "NOME_RAZAO" ,nullable = true , length = 70)
    private String nomeRazao;

  
    @Column(name = "NOME" ,nullable = true , length = 70)
    private String nomeFantasia;
    
    
    @Column(name = "DOCUMENTO" ,nullable = true , length = 18, unique = true)
    private String documento;
                
    
    @Column(name = "DATA_CADASTRO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCadastro;
    
    
    @Email(message="Informe um e-mail válido")
    @Column(name = "EMAIL" , length = 60)
    private String email;
    
    
    @Column(name = "HOME_PAGE", length = 50)
    private String site;
    
    
    @Column(name = "TELEFONE1", length = 20)
    private String telefone1;
    
    
    @Column(name = "TELEFONE2", length = 20)
    private String telefone2;
    
            
    @Column(name = "OBSERVACOES" , length = 255)
    private String obs;
    
    
    @Column(name = "INATIVO")
    private Character inativo;
    
     
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name ="COD_UPDCLIENTESENDERECO")
    private Endereco endereco;

    
    @Column(name = "DIRETORIO_LOCAL")
    private String diretorioLocal;
    
    
    //-------------------------------	GETs and SETs------------------------------//
    
    
	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}


	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	
	public String getFilial() {
		return filial;
	}
	
	public void setFilial(String filial) {
		this.filial = filial;
	}
	

	public String getNomeRazao() {
		return nomeRazao;
	}

	public void setNomeRazao(String nomeRazao) {
		this.nomeRazao = nomeRazao;
	}


	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}


	public String getDocumentoFormatado(){
		if(this.documento != null){
			return 	documento.substring(0, 2) + "." + documento.substring(2, 5) + "." + documento.substring(5, 8)
			+ "/" + documento.substring(8,12) +"-"+ documento.substring(12);
		}else{
			return null;
		}
		
	}

	public String getDocumento() {
		return documento;
	}
	
	public void setDocumento(String cnpj) {
		if(cnpj != null){
			this.documento = cnpj.replace(".", "").replace("-", "").replace("/", "");
		}
	}
	

	public String getDataCadastroFormatada(){
		return (this.dataCadastro != null)?new SimpleDateFormat("dd/MM/yyyy").format(dataCadastro):null;
	}
	
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}


	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}


	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}


	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}


	public Character getAtivo() {
		return inativo;
	}
	
	public void setAtivo(Character ativo) {
		this.inativo = ativo;
	}

	
	public Boolean isAtivo() {
		if (this.inativo == null)
			return null;
		
		return inativo.equals('S') ? true : false;
	}
	
	

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	
	public String getDiretorioLocal() {
		return diretorioLocal;
	}
	
	public void setDiretorioLocal(String diretorioLocal) {
		this.diretorioLocal = diretorioLocal;
	}
	
	//--------------------------------	Métodos Auxiliares------------------------------//

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEmpresa == null) ? 0 : idEmpresa.hashCode());
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
		Empresa other = (Empresa) obj;
		if (idEmpresa == null) {
			if (other.idEmpresa != null)
				return false;
		} else if (!idEmpresa.equals(other.idEmpresa))
			return false;
		return true;
	}
	
	@Override
    public String toString() {
        return codigo + " - " + nomeFantasia;
    }
	
}
