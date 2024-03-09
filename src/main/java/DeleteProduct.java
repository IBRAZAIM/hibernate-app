import model.Option;
import model.Product;
import model.Value;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Scanner;

public class DeleteProduct {
    public void deleteProduct(Scanner scanner, EntityManager manager) {
        System.out.println("Удаление товара DeleteProduct");
        System.out.println("Введите id товара");
        Long productId = Long.parseLong(scanner.nextLine());
        try {
            Product product = manager.find(Product.class, productId);
            if (product != null) {
                manager.getTransaction().begin();
                // Удаляем все связанные элементы из таблицы "order_products"
                Query query = manager.createQuery("DELETE FROM OrderItem op WHERE op.product.id = :productId");
                query.setParameter("productId", productId);
                query.executeUpdate();

                // Удаляем все значения товара
                for (Value value : product.getValues()) {
                    manager.remove(value);
                }

                // Удаляем сам товар
                manager.remove(product);
                manager.getTransaction().commit();
                System.out.println("Товар удален");
            } else {
                System.out.println("Товар с указанным id не найден");
            }
        } catch (Exception ex) {
            System.out.println("Ошибка при удалении товара: " + ex.getMessage());
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
        }
    }

}
