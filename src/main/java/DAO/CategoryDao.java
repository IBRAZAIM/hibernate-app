package DAO;

import model.Category;
import model.Product;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryDao extends EntityDao<Category>{

    public CategoryDao(EntityManager manager) {
        super(manager, Category.class);
    }

    public List<Product> findProductsInCategory(String categoryName) {
        TypedQuery<Product> query = manager.createQuery(
                "select p from Product p where p.category.name = :categoryName", Product.class);
        query.setParameter("categoryName", categoryName);
        return query.getResultList();
    }

    public List<Category> getAllCategories() {
        TypedQuery<Category> query = manager.createQuery("select c from Category c", Category.class);
        return query.getResultList();
    }

    public Long getCountOfProductsInCategory(String categoryName) {
        TypedQuery<Long> query = manager.createQuery(
                "select count(p) from Product p where p.category.name = :categoryName", Long.class);
        query.setParameter("categoryName", categoryName);
        return query.getSingleResult();
    }

    public Product findMostExpensiveProductInCategory(String categoryName) {
        TypedQuery<Product> query = manager.createQuery(
                "select p from Product p where p.category.name = :categoryName order by p.price desc", Product.class);
        query.setMaxResults(1);
        query.setParameter("categoryName", categoryName);
        List<Product> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
    public Category getCategoryById(int categoryId) {
        return manager.find(Category.class, categoryId);
    }
    public Category getCategoryByName(String categoryName) {
        TypedQuery<Category> query = manager.createQuery(
                "SELECT c FROM Category c WHERE c.name = :name", Category.class);
        query.setParameter("name", categoryName);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<Product> getProductsByCategoryId(int categoryId) {
        TypedQuery<Product> query = manager.createQuery(
                "select p from Product p where p.category.id = :categoryId", Product.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

}