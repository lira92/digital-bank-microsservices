package br.com.biopark.models;

import java.time.LocalDateTime;
import java.util.Objects;

import br.com.biopark.enums.Tipo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimentacao")
public class Movimentacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = true)
	private String nome;
	
	@Column(nullable = false)
	private double valor;
	
	@Column(nullable = false)
	private LocalDateTime datahora;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "conta_id", nullable = false)
	private Conta conta;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Tipo tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return Objects.hash(conta, datahora, id, nome, tipo, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movimentacao other = (Movimentacao) obj;
		return Objects.equals(conta, other.conta) && Objects.equals(datahora, other.datahora)
				&& Objects.equals(id, other.id) && Objects.equals(nome, other.nome) && tipo == other.tipo
				&& Double.doubleToLongBits(valor) == Double.doubleToLongBits(other.valor);
	}

	public Movimentacao(String nome, double valor, Tipo tipo, LocalDateTime datahora, Conta conta) {
		this.nome = nome;
		this.valor = valor;
		this.tipo = tipo;
		this.datahora = datahora;
		this.conta = conta;
	}

	public Movimentacao() {
	}

	public Movimentacao(Long id, String nome, double valor, LocalDateTime datahora, Conta conta, Tipo tipo) {
		this.id = id;
		this.nome = nome;
		this.valor = valor;
		this.datahora = datahora;
		this.conta = conta;
		this.tipo = tipo;
	}
	
	public Movimentacao(Long id, String nome, Double valor, LocalDateTime datahora, Tipo tipo, Conta conta) {
		this.id = id;
		this.nome = nome;
		this.valor = valor;
		this.datahora = datahora;
		this.tipo = tipo;
		this.conta = conta;
	}
}
