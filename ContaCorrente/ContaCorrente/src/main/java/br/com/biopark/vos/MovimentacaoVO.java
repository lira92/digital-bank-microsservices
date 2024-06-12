package br.com.biopark.vos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.biopark.enums.Tipo;
import br.com.biopark.models.Conta;

@JsonPropertyOrder({"id", "nome", "valor", "datahora", "tipo", "conta"})
public class MovimentacaoVO extends RepresentationModel<MovimentacaoVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private Long key;
	private String nome;
	private double valor;
	private LocalDateTime datahora;
	private Tipo tipo;
	private Conta conta;

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDateTime getDatahora() {
		return datahora;
	}

	public void setDatahora(LocalDateTime datahora) {
		this.datahora = datahora;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(conta, datahora, key, nome, tipo, valor);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovimentacaoVO other = (MovimentacaoVO) obj;
		return Objects.equals(conta, other.conta) && Objects.equals(datahora, other.datahora)
				&& Objects.equals(key, other.key) && Objects.equals(nome, other.nome) && tipo == other.tipo
				&& Double.doubleToLongBits(valor) == Double.doubleToLongBits(other.valor);
	}
}
