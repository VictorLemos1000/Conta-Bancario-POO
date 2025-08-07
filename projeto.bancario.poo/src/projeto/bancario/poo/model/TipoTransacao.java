package projeto.bancario.poo.model;

public enum TipoTransacao {

	CREDITO(1),
	DEBITO(2),
	TRANSACAO_CREDITO(3),
	TRANSACAO_DEBITO(4);
	
	private final int valor;
    
    TipoTransacao(int valor) {
        this.valor = valor;
    }
	
	private int getValue() {
		// TODO Auto-generated method stub
		return this.valor;
	}
	
	@SuppressWarnings("unused")
	private static TipoTransacao getEnumFromValue(int valor) {
		// TODO Auto-generated method stub
		for (TipoTransacao tipo : TipoTransacao.values()) {
			if (tipo.getValue() == valor) {
				return tipo;
			}
		}
		throw new IllegalArgumentException(" Nenhum tipo de transação encontrada para o valor : " + valor);
	}
}
