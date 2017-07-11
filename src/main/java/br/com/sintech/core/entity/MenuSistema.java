package br.com.sintech.core.entity;

public enum MenuSistema {
	CADASTRO("Cadastro"),
	CAIXA("Caixa"),
	COMPRAS("Compras"),
	COBRANCA_ESCRITURAL("Cobrança Escritural"),
	VENDAS("Vendas"),
	CONTABILIDADE("Contabilidade"),
	CONTAS_PAGAR("Contas a Pagar"),
	CONTAS_RECEBER("Contas a Receber"),
	ESTOQUE("Estoque"),
	FOLHA_RH("Folha e RH"),
	INDUSTRIA("Industria"),
	LAVANDERIA("Lavanderia"),
	LIVROS("Livros Fiscais"),
	SERVICOS("Serviços"),
	TRANSPORTE("Transporte");
	
	
	private String label;

	MenuSistema(String label) {
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
	}

}
