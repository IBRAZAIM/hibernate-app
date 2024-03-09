package DAO;

import model.Option;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class OptionDao extends EntityDao<Option> {
    public OptionDao(EntityManager entityManager) {
        super(entityManager, Option.class);
    }

    public List<Option> findOptionsByCategoryId(int categoryId) {
        String jpql = "SELECT o FROM Option o WHERE o.category.id = :categoryId";
        TypedQuery<Option> query = manager.createQuery(jpql, Option.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

    // Дополнительные методы, если необходимо
}

