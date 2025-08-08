package projeto.bancario.poo.facade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import projeto.bancario.poo.dao.TransacaoDAO;
import projeto.bancario.poo.exception.DataBaseException;
import projeto.bancario.poo.model.Conta;
import projeto.bancario.poo.model.TipoTransacao;
import projeto.bancario.poo.model.Transacao;

@SuppressWarnings("rawtypes")
public class TransacaoFacade {

	private final TransacaoDAO transacaoDAO;

    public TransacaoFacade() {
        this.transacaoDAO = new TransacaoDAO();
    }

    // Registra uma transação genérica
	public void registrarTransacao(Conta origem, Conta destino, BigDecimal valor, TipoTransacao tipo) 
    	    throws DataBaseException {
    	    
    	    Transacao transacao = new Transacao();
    	    transacao.setContaOrigem(origem);
    	    transacao.setContaDestino(destino);
    	    transacao.setQuantia(valor);
    	    transacao.setTipo(tipo);
    	    transacao.setData(LocalDateTime.now());
    	    
    	    transacaoDAO.save(transacao);
    }

    // Lista transações de uma conta
    public List<Transacao> listarTransacoesPorConta(String numeroConta) throws DataBaseException {
        return transacaoDAO.findByConta(numeroConta);
    }
}
