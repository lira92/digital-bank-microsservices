package br.com.biopark.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.biopark.models.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM movimentacao ORDER BY id ASC LIMIT 10 OFFSET :page")
	List<Movimentacao> findAllPaged(@Param(value = "page") Integer page);
}
