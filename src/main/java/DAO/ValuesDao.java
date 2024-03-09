package DAO;

import model.Value;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ValuesDao extends EntityDao<Value> {
    public ValuesDao(EntityManager entityManager) {
        super(entityManager, Value.class);
    }

    public List<Value> findValuesByProductId(int productId) {
        String query1 = "SELECT v FROM Value v WHERE v.product.id = :productId";
        TypedQuery<Value> query = manager.createQuery(query1, Value.class);
        query.setParameter("productId", productId);
        return query.getResultList();
    }

    // Дополнительные методы, если необходимо
}
