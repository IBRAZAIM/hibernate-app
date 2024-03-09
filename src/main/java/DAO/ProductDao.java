package DAO;

import model.Product;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductDao extends EntityDao<Product>{

    public ProductDao(EntityManager manager) {
        super(manager, Product.class);
    }

    public Product findByID(Long id) {
        return manager.find(Product.class, id);
    }

    public List<Product> findAll() {
        TypedQuery<Product> query = manager.createQuery("select u from Product u", Product.class);
        return query.getResultList();
    }

    public List<Product> findByName(String name) {
        TypedQuery<Product> query = manager.createQuery("select p from Product p where name = ?1", Product.class);
        query.setParameter(1, name);
        return query.getResultList();
    }
    public void updateProductPriceById(Long id, int newPrice) {
        Product product = findByID(id);
        if (product != null) {
            manager.getTransaction().begin();
            product.setPrice(newPrice);
            manager.getTransaction().commit();
        }
    }

    public void updateProductPriceByName(String name, int newPrice) {
        Product product = findByName(name).stream().findFirst().orElse(null);
        if (product != null) {
            manager.getTransaction().begin();
            product.setPrice(newPrice);
            manager.getTransaction().commit();
        }
    }

    public Long getTotalNumberOfProducts() {
        TypedQuery<Long> query = manager.createQuery("select count(p) from Product p", Long.class);
        return query.getSingleResult();
    }

    public List<Product> findProductsInPriceRange(int minPrice, int maxPrice) {
        TypedQuery<Product> query = manager.createQuery("select p from Product p where price between ?1 and ?2", Product.class);
        query.setParameter(1, minPrice);
        query.setParameter(2, maxPrice);
        return query.getResultList();
    }

    public Long getTotalCostOfProducts() {
        TypedQuery<Long> query = manager.createQuery("select sum(p.price) from Product p", Long.class);
        Long totalCost = query.getSingleResult();
        return totalCost != null ? totalCost : 0L;
    }

    public List<Product> findTopNExpensiveProducts(int n) {
        TypedQuery<Product> query = manager.createQuery("select p from Product p order by p.price desc", Product.class);
        query.setMaxResults(n);
        return query.getResultList();
    }

    public Product findMostExpensiveProduct() {
        TypedQuery<Product> query = manager.createQuery("select p from Product p order by p.price desc", Product.class);
        query.setMaxResults(1);
        List<Product> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public void applyDiscountById(Long id, int discountPercentage) {
        Product product = findByID(id);
        if (product != null) {
            manager.getTransaction().begin();
            int discountedPrice = product.getPrice() * (1 - discountPercentage / 100);
            product.setPrice(discountedPrice);
            manager.getTransaction().commit();
        }
    }

}