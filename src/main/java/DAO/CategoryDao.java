package DAO;

import model.Category;
import model.Product;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoryDao extends EntityDao<Category> {

    public CategoryDao(EntityManager manager) {
        super(manager, Category.class);
    }

    // Найти продукты в указанной категории
    public List<Product> findProductsInCategory(String categoryName) {
        TypedQuery<Product> query = manager.createQuery(
                "select p from Product p where p.category.name = :categoryName", Product.class);
        query.setParameter("categoryName", categoryName);
        return query.getResultList();
    }

    // Получить список всех категорий
    public List<Category> getAllCategories() {
        TypedQuery<Category> query = manager.createQuery("select c from Category c", Category.class);
        return query.getResultList();
    }

    // Получить количество продуктов в указанной категории
    public Long getCountOfProductsInCategory(String categoryName) {
        TypedQuery<Long> query = manager.createQuery(
                "select count(p) from Product p where p.category.name = :categoryName", Long.class);
        query.setParameter("categoryName", categoryName);
        return query.getSingleResult();
    }

    // Найти самый дорогой продукт в указанной категории
    public Product findMostExpensiveProductInCategory(String categoryName) {
        TypedQuery<Product> query = manager.createQuery(
                "select p from Product p where p.category.name = :categoryName order by p.price desc", Product.class);
        query.setMaxResults(1);
        query.setParameter("categoryName", categoryName);
        List<Product> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    // Обновить цену продукта по его имени
    public void updateProductPriceByName(String productName, int newPrice) {
        try {
            manager.getTransaction().begin();
            Query query = manager.createQuery("UPDATE Product p SET p.price = :newPrice WHERE p.name = :productName");
            query.setParameter("newPrice", newPrice);
            query.setParameter("productName", productName);
            query.executeUpdate();
            manager.getTransaction().commit();
        } catch (Exception ex) {
            manager.getTransaction().rollback();
            throw ex;
        }
    }



    // Получить категорию по ID
    public Category getCategoryById(int categoryId) {
        return manager.find(Category.class, categoryId);
    }

    // Получить категорию по названию
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

    // Получить список продуктов по ID категории
    public List<Product> getProductsByCategoryId(Long categoryId) {
        TypedQuery<Product> query = manager.createQuery(
                "select p from Product p where p.category.id = :categoryId", Product.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }
}
