package br.com.biopark.vos;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "numero", "nome", "cpf", "email", "telefone", "data_de_nascimento", "saldo", "status", "senha"})
public class ContaVO extends RepresentationModel<ContaVO> implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long key;
	private Long numero;
	private String nome;
	private String cpf;
	private String email;
	private double saldo;
	private boolean status;
	private String data_de_nascimento;
	private String senha;
	private String telefone;
	
	public Long getKey() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key;
	}
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
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getData_de_nascimento() {
		return data_de_nascimento;
	}
	public void setData_de_nascimento(String data_de_nascimento) {
		this.data_de_nascimento = data_de_nascimento;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ Objects.hash(cpf, data_de_nascimento, email, key, nome, numero, saldo, senha, status, telefone);
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
		ContaVO other = (ContaVO) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(data_de_nascimento, other.data_de_nascimento)
				&& Objects.equals(email, other.email) && Objects.equals(key, other.key)
				&& Objects.equals(nome, other.nome) && Objects.equals(numero, other.numero)
				&& Double.doubleToLongBits(saldo) == Double.doubleToLongBits(other.saldo)
				&& Objects.equals(senha, other.senha) && status == other.status
				&& Objects.equals(telefone, other.telefone);
	}
}
