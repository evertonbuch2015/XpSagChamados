package br.com.sintech.view.managedBean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.sintech.core.entity.Chamado;
import br.com.sintech.core.entity.ChamadoAnexo;
import br.com.sintech.core.entity.Empresa;
import br.com.sintech.core.entity.Programa;
import br.com.sintech.core.entity.SituacaoChamado;
import br.com.sintech.core.service.ServiceChamado;
import br.com.sintech.core.service.ServiceChamadoAnexo;
import br.com.sintech.core.util.Constantes;
import br.com.sintech.view.util.SessionContext;
import br.com.sintech.view.util.UtilMensagens;


@ManagedBean
@ViewScoped
public class ChamadoBean extends GenericBean<Chamado, ServiceChamado> implements Serializable {

	private static final long serialVersionUID = 905551446067723831L;
	
	public enum TipoFiltro{
		PROTOCOLO("Protocolo"),
		SITUACAO("Situação"),
		DATA_SOLICITACAO("Data Solicitação"),
		TITULO("Titulo");
		
		private String label;

		TipoFiltro(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
	}
	
	private TipoFiltro filtro;
	private SituacaoChamado situacaoFiltro;
	private Date dataFiltro;
	private String nomeProgramaTela;
	private StreamedContent file;
	
	public ChamadoBean() {
		super(new ServiceChamado());
	}
		
	// =======================METODOS DO USUARIO=================================================
	
	public void filtrar(){
		try {
			
			if(filtro == TipoFiltro.SITUACAO){
				this.entidades = service.filtrarTabela(filtro, situacaoFiltro.getDescricao());
			}else if(filtro == TipoFiltro.DATA_SOLICITACAO){
				this.entidades = service.filtrarTabela(filtro, new SimpleDateFormat("dd/MM/yyyy").format(dataFiltro));
			}else{
				this.entidades = service.filtrarTabela(filtro, valorFiltro);
			}
			
		} catch (Exception e) {
			UtilMensagens.mensagemErro(e.getMessage());
		}
	}
	
	
	@Override
	public Chamado criarEntidade() {
		Chamado chamado = new Chamado();
		return chamado;
	}
	
	
	
	public void programaSelecionada(SelectEvent event){
		this.entidade.setPrograma((Programa) event.getObject());
	}
	
	
	public void doUpload(FileUploadEvent fileUploadEvent) {
       UploadedFile uploadedFile = fileUploadEvent.getFile();
       
       String fileNameUploaded = uploadedFile.getFileName();     
       String extencao = fileNameUploaded.substring(fileNameUploaded.lastIndexOf("."), fileNameUploaded.length());       
      
       ChamadoAnexo chamadoAnexo = new ChamadoAnexo();
       chamadoAnexo.setArquivo(uploadedFile.getContents());
       chamadoAnexo.setNome(fileNameUploaded);
       chamadoAnexo.setTamanho(((Long)uploadedFile.getSize()).intValue());
       chamadoAnexo.setExtensao(extencao);       
       chamadoAnexo.setChamado(entidade);
       chamadoAnexo.setContentType(uploadedFile.getContentType());
       
       entidade.getAnexos().add(chamadoAnexo);
    }
	
	
	public void doDownload(ChamadoAnexo anexo){
		InputStream stream = null;
		
		try {
			stream = new FileInputStream(
					anexo.getCaminho() + anexo.getNome());
			
			this.file = new DefaultStreamedContent(stream, anexo.getContentType(), anexo.getNome());
		} catch (FileNotFoundException e) {
			
			try {
				//grava o arquivo antes de chamar novamente o doDownload
				ChamadoAnexo anexoAux = new ServiceChamadoAnexo().carregarEntidade(anexo); 
				
				if (anexo.getCaminho() == null) {
					anexoAux.setCaminho(entidade.getEmpresa().getDiretorioLocal() + "\\" + entidade.getProtocolo()+ "\\");
				}
				
				ServiceChamadoAnexo.gravar(anexoAux);
				doDownload(anexoAux);
				anexoAux = null;
			} catch (Exception e1) {
				InputStream streamAux = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/pdf.png");
		        file = new DefaultStreamedContent(streamAux, "image/jpg", "downloaded_pdf.png");
			}
		}        
	}
	
	// =============================GET AND SET=================================
	
	
	@Override
	public List<Chamado> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
	
	public List<SituacaoChamado> getSituacoesChamado(){
		return Constantes.getInstance().getListaSituacaoChamado();
	}	
	
	public List<Empresa> getEmpresas(){
		if(SessionContext.getInstance().usuarioLogadoIsSUPORTE()){
			return Constantes.getInstance().getEmpresasAtivas();
		}else{
			List<Empresa> lista = new ArrayList<>();
			lista.add(SessionContext.getInstance().getEmpresaUsuarioLogado());
			return lista;
					
		}
	}
		
	
	
	public TipoFiltro getFiltro() {return filtro;}
		
	public void setFiltro(TipoFiltro filtro) {this.filtro = filtro;}
	
	public TipoFiltro[] tipoFiltros(){return TipoFiltro.values();}	
		
	
	public SituacaoChamado getSituacaoFiltro() {return situacaoFiltro;}

	public void setSituacaoFiltro(SituacaoChamado situacaoFiltro) {this.situacaoFiltro = situacaoFiltro;}
	
	
	public Date getDataFiltro() {return dataFiltro;}

	public void setDataFiltro(Date dataFiltro) {this.dataFiltro = dataFiltro;}

	
	public String getNomeProgramaTela() {
		if(entidade.getPrograma() != null){
			this.nomeProgramaTela = entidade.getPrograma().getNome() + "  /  " + entidade.getPrograma().getPrograma(); 
			return nomeProgramaTela;
		}else{
			return null;
		}
	}
	
	public void setNomeProgramaTela(String nomeProgramaTela) {
		this.nomeProgramaTela = nomeProgramaTela;
	}


	public StreamedContent getFile() {
		return file;
	}
}
