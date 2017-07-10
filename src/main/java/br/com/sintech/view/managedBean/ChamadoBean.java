package br.com.sintech.view.managedBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import br.com.sintech.core.entity.Chamado;
import br.com.sintech.core.entity.Empresa;
import br.com.sintech.core.entity.GrupoUsuario;
import br.com.sintech.core.entity.Programa;
import br.com.sintech.core.entity.SituacaoChamado;
import br.com.sintech.core.service.ServiceChamado;
import br.com.sintech.core.util.Constantes;
import br.com.sintech.view.util.SessionContext;
import br.com.sintech.view.util.UploadArquivo;
import br.com.sintech.view.util.UtilMensagens;


@ManagedBean
//@ViewScoped
@SessionScoped
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
	
	
	
	public ChamadoBean() {
		super(new ServiceChamado());
	}
		
	// =======================METODOS DO USUARIO=================================================
	
	public void filtrar(){
		try {
			
			if(filtro == TipoFiltro.SITUACAO){
				this.entidades = service.filtrarTabela(filtro, situacaoFiltro);
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
		chamado.setDataSolicitacao(new Date());
		return chamado;
	}
	

	
	/*public void excluirEmpresa(Empresa empresa){
		if(this.entidade.getEmpresas().contains(empresa)){
			this.entidade.getEmpresas().remove(empresa);
		}
	}
		
	
	public void adicionarEmpresa(Empresa empresa){		
		if(!this.entidade.getEmpresas().contains(empresa)){
			this.entidade.getEmpresas().add(empresa);
		}else{
			UtilMensagens.mensagemAtencao("Empresa já cadastrada para este Usuário");
		}
	}	*/
		
	
	public void programaSelecionada(SelectEvent event){
		this.entidade.setPrograma((Programa) event.getObject());
	}
	
	
	public void doUpload(FileUploadEvent fileUploadEvent) {
       //UploadedFile uploadedFile = fileUploadEvent.getFile();
        //String fileNameUploaded = uploadedFile.getFileName();
        //long fileSizeUploaded = uploadedFile.getSize();
        //byte[] arquivo = uploadedFile.getContents();
        
        UploadArquivo uploadArquivo = new UploadArquivo();
        uploadArquivo.fileUpload(fileUploadEvent, ".jpg", "/image/");
        uploadArquivo.gravar();
        
        
        /*ChamadoAnexo chamadoAnexo = new ChamadoAnexo();
        chamadoAnexo.setArquivo(arquivo);
        chamadoAnexo.setNome(fileNameUploaded);
        chamadoAnexo.setTamanho(fileSizeUploaded);
        chamadoAnexo.setExtensao(uploadedFile.getContentType());*/
        
        
    
        //entidade.getAnexos().add(chamadoAnexo);
        
        //System.out.println(arquivo);

    }
	
	// =======================GET e SET=================================================
	
	// =============================GET AND SET=================================
	
	
	@Override
	public List<Chamado> getEntidades() {
		if (this.entidades == null) {
			refresh();
		}	
		return entidades;
	}
	

	
	public TipoFiltro getFiltro() {
		return filtro;
	}
	
		
	public void setFiltro(TipoFiltro filtro) {
		this.filtro = filtro;
	}
	
	
	
	public TipoFiltro[] tipoFiltros(){
		return TipoFiltro.values();
	}	
	
	
	public SituacaoChamado[] getSituacoesChamado(){
		return SituacaoChamado.values();
	}	
	
	public List<Empresa> getEmpresas(){
		if(SessionContext.getInstance().getUsuarioLogado().getGrupoUsuario() == GrupoUsuario.ADMIN){
			return Constantes.getInstance().getEmpresas();
		}else{
			List<Empresa> lista = new ArrayList<>();
			lista.add(SessionContext.getInstance().getEmpresaUsuarioLogado());
			return lista;
					
		}
	}
	
	
	
	
	public SituacaoChamado getSituacaoFiltro() {
		return situacaoFiltro;
	}
	

	public void setSituacaoFiltro(SituacaoChamado situacaoFiltro) {
		this.situacaoFiltro = situacaoFiltro;
	}
	
	
	

	public Date getDataFiltro() {
		return dataFiltro;
	}
	

	public void setDataFiltro(Date dataFiltro) {
		this.dataFiltro = dataFiltro;
	}


	
}
