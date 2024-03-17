package DAO;

import model.Product;
import model.Value;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

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
    public List<Product> findProductByValues(String value) {
        String query = "SELECT p FROM Product p JOIN p.values v WHERE v.name = :value";
        TypedQuery<Product> jpql = manager.createQuery(query, Product.class);
        jpql.setParameter("value", value);
        return jpql.getResultList();
    }
    public List<Value> printAllValues(){
        //Выводит все значения
        TypedQuery<Value> jpql = manager.createQuery("SELECT v FROM Value v", Value.class);
        return jpql.getResultList();
    }
    public Value getValueByOptionName(String optionName){
        String jpql = "SELECT v FROM Value v WHERE v.option.name = :optionName";
        TypedQuery<Value> query = manager.createQuery(jpql, Value.class);
        query.setParameter("optionName", optionName);
        return query.getSingleResult();
    }


    // Дополнительные методы, если необходимо
}
