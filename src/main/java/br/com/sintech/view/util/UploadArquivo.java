package br.com.sintech.view.util;

import java.io.File;
import java.io.FileOutputStream;

import br.com.sintech.core.entity.ChamadoAnexo;

public class UploadArquivo {
	
	public UploadArquivo() {
	
	}

	
	public void gravar(ChamadoAnexo anexo) {		
		try {						
			if(!new File(anexo.getCaminho() + anexo.getNome()).exists()){
			
				File file = new File(anexo.getCaminho());			
				file.mkdirs();
				
				FileOutputStream fos;
				fos = new FileOutputStream(anexo.getCaminho() + anexo.getNome());
				fos.write(anexo.getArquivo());
				fos.close();
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}














/*public void fileUpload(FileUploadEvent event, String type, String diretorio) {
try {
	
	
	this.nome = 
		new SimpleDateFormat("ddMMyyyy").format(new java.util.Date()) + type;
	
	this.caminho = diretorio + nome;
	this.arquivo = event.getFile().getContents();			
				
	File file = new File(diretorio);
	file.mkdirs();

} catch (Exception ex) {
	System.out.println("Erro no upload do arquivo" + ex);
}
}


public void gravar() {

try {

	FileOutputStream fos;
	fos = new FileOutputStream(this.caminho);
	fos.write(this.arquivo);
	fos.close();

} catch (Exception ex) {
	System.out.println(ex);
}

}*/


/*public String getRealPath() {
FacesContext aFacesContext = FacesContext.getCurrentInstance();
ServletContext context = (ServletContext) aFacesContext.getExternalContext().getContext();
return context.getRealPath("/");
}*/
