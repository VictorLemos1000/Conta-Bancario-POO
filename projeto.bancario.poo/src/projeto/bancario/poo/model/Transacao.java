package projeto.bancario.poo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public class Transacao {

	private Long idTransacao;
	private LocalDateTime data;
	private BigDecimal quantia;
	private TipoTransacao tipo;
	private Conta contaOrigem;
	private Conta contaDestino;

	public Transacao() {
		// TODO Auto-generated constructor stub
		this.data =  LocalDateTime.now();
		this.quantia = BigDecimal.ZERO;
	}
	
	// Métodos geters e seters
	public Long getIdTransacao() {
		return idTransacao;
	}
	public void setIdTransacao(Long idTransacao) {
		this.idTransacao = idTransacao;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public BigDecimal getQuantia() {
		return quantia;
	}
	public void setQuantia(BigDecimal quantia) {
		this.quantia = quantia;
	}
	public TipoTransacao getTipo() {
		return tipo;
	}
	public void setTipo(TipoTransacao tipo) {
		this.tipo = tipo;
	}
	public Conta getContaOrigem() {
		return contaOrigem;
	}
	public void setContaOrigem(Conta<?> contaOrigem) {
		this.contaOrigem = contaOrigem;
	}
	public Conta getContaDestino() {
		return contaDestino;
	}
	public void setContaDestino(Conta<?> contaDestino) {
		this.contaDestino = contaDestino;
	}
	
	// ToString.
	@Override
	public String toString() {
		return "Transacao [idTransacao=" + idTransacao + ", data=" + data + ", quantia=" + quantia + ", tipo=" + tipo
				+ ", contaOrigem=" + contaOrigem + ", contaDestino=" + contaDestino + "]";
	}
	
	// Métodos hashCode & equals.
	@Override
	public int hashCode() {
		return Objects.hash(idTransacao);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transacao other = (Transacao) obj;
		return Objects.equals(idTransacao, other.idTransacao);
	}

	public BigDecimal getValor() {
		// TODO Auto-generated method stub
		return getQuantia();
	}

	
}
