package br.com.biopark.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.biopark.controllers.MovimentacaoController;
import br.com.biopark.dtos.FiltragemDTO;
import br.com.biopark.exceptions.MinhaException;
import br.com.biopark.mapper.Mapper;
import br.com.biopark.models.Movimentacao;
import br.com.biopark.repositories.MovimentacaoRepository;
import br.com.biopark.vos.MovimentacaoVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class MovimentacaoService {

	@Autowired
	MovimentacaoRepository repository;
	@Autowired
    private EntityManager entityManager;
	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public MovimentacaoVO findById(Long id) {
		if (id == null) throw new MinhaException("Id deve ser preenchido!");
		if (repository.existsById(id) == false) throw new MinhaException("Movimentação não encontrada!");
		MovimentacaoVO movimentacao = Mapper.parseObject(repository.findById(id), MovimentacaoVO.class);
		movimentacao.add(linkTo(methodOn(MovimentacaoController.class).findById(id)).withSelfRel());
		return movimentacao;
	}
	
	public List<MovimentacaoVO> filtragem(FiltragemDTO filtros, Integer page) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movimentacao> cq = cb.createQuery(Movimentacao.class);
        Root<Movimentacao> movimentacao = cq.from(Movimentacao.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filtros.getNumero() != null) {
            predicates.add(cb.equal(movimentacao.get("conta").get("numero"), filtros.getNumero()));
        }
        if (filtros.getNome() != null && !filtros.getNome().isEmpty()) {
            predicates.add(cb.like(movimentacao.get("nome"), "%" + filtros.getNome() + "%"));
        }
        if (filtros.getTipo() != null) {
            predicates.add(cb.equal(movimentacao.get("tipo"), filtros.getTipo()));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (filtros.getData_igual() != null && !filtros.getData_igual().isEmpty()) {
            LocalDateTime dataIgual = LocalDateTime.parse(filtros.getData_igual(), formatter);
            predicates.add(cb.equal(movimentacao.get("datahora"), dataIgual));
        }
        if (filtros.getData_maior_que() != null && !filtros.getData_maior_que().isEmpty()) {
            LocalDateTime dataMaiorQue = LocalDateTime.parse(filtros.getData_maior_que(), formatter);
            predicates.add(cb.greaterThanOrEqualTo(movimentacao.get("datahora"), dataMaiorQue));
        }
        if (filtros.getData_menor_que() != null && !filtros.getData_menor_que().isEmpty()) {
            LocalDateTime dataMenorQue = LocalDateTime.parse(filtros.getData_menor_que(), formatter);
            predicates.add(cb.lessThanOrEqualTo(movimentacao.get("datahora"), dataMenorQue));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Movimentacao> query = entityManager.createQuery(cq);
        query.setFirstResult(page * 10);
        query.setMaxResults(10);

        List<Movimentacao> movimentacoes = query.getResultList();
        List<MovimentacaoVO> vos = Mapper.parseListObjects(movimentacoes, MovimentacaoVO.class);
		vos.stream().forEach(mov -> mov.add(linkTo(methodOn(MovimentacaoController.class).findById(mov.getKey())).withSelfRel()));
		return vos;
    }
}
