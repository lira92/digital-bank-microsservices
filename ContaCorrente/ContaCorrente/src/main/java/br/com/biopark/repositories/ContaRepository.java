package br.com.biopark.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.biopark.models.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM conta WHERE numero = :numero")
	Conta findByNumero(@Param(value = "numero") Long numero);
	
	@Query(nativeQuery = true, value = "SELECT * FROM conta WHERE email = :email")
	Conta findByEmail(@Param(value = "email") String email);
	
	@Query(nativeQuery = true, value = "SELECT COUNT(id) FROM conta WHERE numero = :numero")
	int existsByNumero(@Param(value = "numero") Long numero);
	
	@Query(nativeQuery = true, value = "SELECT COUNT(id) FROM conta WHERE cpf = :cpf")
	int existsByCpf(@Param(value = "cpf") String cpf);
	
	@Query(nativeQuery = true, value = "SELECT COUNT(id) FROM conta WHERE email = :email")
	int existsByEmail(@Param(value = "email") String email);
	
	@Query(nativeQuery = true, value = "SELECT COUNT(id) FROM conta WHERE telefone = :telefone")
	int existsByTelefone(@Param(value = "telefone") String telefone);
	
	@Query(nativeQuery = true, value = "SELECT * FROM conta ORDER BY id ASC LIMIT 10 OFFSET :page")
	List<Conta> findAllPaged(@Param(value = "page") Integer page);
}
