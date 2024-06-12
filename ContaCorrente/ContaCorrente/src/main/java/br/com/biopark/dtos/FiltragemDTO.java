package br.com.biopark.dtos;

import java.util.Objects;

import br.com.biopark.enums.Tipo;

public class FiltragemDTO {

	public Long numero;
	public String nome;
	public Tipo tipo;
	public String data_maior_que;
	public String data_menor_que;
	public String data_igual;
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public String getData_maior_que() {
		return data_maior_que;
	}
	public void setData_maior_que(String data_maior_que) {
		this.data_maior_que = data_maior_que;
	}
	public String getData_menor_que() {
		return data_menor_que;
	}
	public void setData_menor_que(String data_menor_que) {
		this.data_menor_que = data_menor_que;
	}
	public String getData_igual() {
		return data_igual;
	}
	public void setData_igual(String data_igual) {
		this.data_igual = data_igual;
	}
	public FiltragemDTO(Long numero, String nome, Tipo tipo, String data_maior_que, String data_menor_que,
			String data_igual) {
		this.numero = numero;
		this.nome = nome;
		this.tipo = tipo;
		this.data_maior_que = data_maior_que;
		this.data_menor_que = data_menor_que;
		this.data_igual = data_igual;
	}
	public FiltragemDTO() {
	}
	@Override
	public int hashCode() {
		return Objects.hash(data_igual, data_maior_que, data_menor_que, nome, numero, tipo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FiltragemDTO other = (FiltragemDTO) obj;
		return Objects.equals(data_igual, other.data_igual) && Objects.equals(data_maior_que, other.data_maior_que)
				&& Objects.equals(data_menor_que, other.data_menor_que) && Objects.equals(nome, other.nome)
				&& Objects.equals(numero, other.numero) && tipo == other.tipo;
	}
}
