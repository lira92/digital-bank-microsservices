package br.com.biopark.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.biopark.models.Movimentacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


@Repository
public class CustomRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Movimentacao> findWithFiltros(String query) {
        return em.createQuery(query, Movimentacao.class).getResultList();
    }

    public <T> TypedQuery<T> createQuery(String query, Class<T> resultClass) {
        return em.createQuery(query, resultClass);
    }
}
