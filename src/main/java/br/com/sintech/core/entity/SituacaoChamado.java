package br.com.sintech.core.entity;

public enum SituacaoChamado {	
	ABERTO("Aberto"),
	ANALISE("Análise"),	
	DESENVOLVIMENTO("Desenvolvimento"),	
	FASE_TESTES("Fase de Teste"),
	FASE_ATUALIZACAO("Em Atualização"),
	CONCLUIDO("Concluido"),
	CANCELADO("Cancelado");
	
	private String label;

	SituacaoChamado(String label) {
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
	}
}
