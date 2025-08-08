package projeto.bancario.poo.facade;

import java.math.BigDecimal;

import projeto.bancario.poo.exception.ContaNaoEncontradaException;
import projeto.bancario.poo.exception.DataBaseException;
import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.model.Cliente;
import projeto.bancario.poo.model.Conta;
import projeto.bancario.poo.model.TipoTransacao;

@SuppressWarnings("rawtypes")
public class BancarioFacade {

	private final ClienteFacade clienteFacade;
    private final ContaFacade contaFacade;
    private final TransacaoFacade transacaoFacade;

    public BancarioFacade() {
        this.clienteFacade = new ClienteFacade();
        this.contaFacade = new ContaFacade();
        this.transacaoFacade = new TransacaoFacade();
    }

    // Exemplo: Transferência entre contas (unifica chamadas a múltiplos DAOs)
	public void transferir(String cpfOrigem, String cpfDestino, BigDecimal valor) throws DataBaseException, OperacaoBancariaException, ContaNaoEncontradaException {
    	    
    	    Cliente origem = clienteFacade.buscarClientePorCpf(cpfOrigem);
    	    Cliente destino = clienteFacade.buscarClientePorCpf(cpfDestino);
    	    
    	    Conta contaOrigem = contaFacade.buscarContaPorCliente(origem.getId());
    	    Conta contaDestino = contaFacade.buscarContaPorCliente(destino.getId());
    	    
    	    // Validações adicionais (ex.: contas ativas)
    	    if (!contaOrigem.isStatus() || !contaDestino.isStatus()) {
    	        throw new OperacaoBancariaException("Conta origem ou destino inativa");
    	    }
    	    
    	    // Realiza a transferência
    	    contaOrigem.sacarQuantia(contaDestino, valor);  // Chama o método da classe Conta
    	    contaDestino.depositarQuantia(contaDestino, valor);
    	    
    	    // Registra a transação
    	    transacaoFacade.registrarTransacao(contaOrigem, contaDestino, valor, TipoTransacao.CREDITO);
    	    transacaoFacade.registrarTransacao(contaOrigem, contaDestino, valor, TipoTransacao.DEBITO);
    	    transacaoFacade.registrarTransacao(contaOrigem, contaDestino, valor, TipoTransacao.TRANSACAO_CREDITO);
    	    transacaoFacade.registrarTransacao(contaOrigem, contaDestino, valor, TipoTransacao.TRANSACAO_DEBITO);
    }
}
