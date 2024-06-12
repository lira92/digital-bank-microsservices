package br.com.biopark.dtos;

import java.util.Objects;

public class MovimentacaoDTO {

	public String nome;
	public Long numero;
	public Double valor;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	@Override
	public int hashCode() {
		return Objects.hash(nome, numero, valor);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovimentacaoDTO other = (MovimentacaoDTO) obj;
		return Objects.equals(nome, other.nome) && Objects.equals(numero, other.numero)
				&& Double.doubleToLongBits(valor) == Double.doubleToLongBits(other.valor);
	}
}
