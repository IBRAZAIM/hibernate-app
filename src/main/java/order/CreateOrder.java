package order;

import model.Product;
import order.model.Order;
import order.model.Status;
import order.model.User;
import service.ProductService;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateOrder {
    // Оформление заказа
    //Произведен рефакторинг кода улучшена структура кода
    public void createOrder(Scanner scanner, EntityManager entityManager) {
        try (scanner) {
            System.out.print("Введите идентификатор пользователя: ");
            Long userId = Long.parseLong(scanner.nextLine());
            User user = entityManager.find(User.class, userId);

            System.out.print("Введите адрес доставки: ");
            String address = scanner.nextLine();

            List<OrderItem> orderItemList = new ArrayList<>();
            Order order = new Order();
            //Заполняем значения Order
            order.setUser(user);
            order.setStatus(Status.CREATED);
            order.setAddress(address);
            order.setDate(LocalDateTime.now());

            boolean continueAddingProducts = true;
            while (continueAddingProducts) {
                OrderItem item = new OrderItem();
                System.out.print("Введите идентификатор продукта: ");
                Long productId = Long.parseLong(scanner.nextLine());
                Product product = entityManager.find(Product.class, productId);

                System.out.print("Введите количество: ");
                int quantity = Integer.parseInt(scanner.nextLine());

                //Заполняем значения OrderItem
                item.setOrder(order);
                item.setProduct(product);
                item.setQuantity(quantity);
                orderItemList.add(item);

                System.out.print("Хотите добавить еще продукты? (y/n): ");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("y")) {
                    continueAddingProducts = false;
                }
            }
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(order);
                for (OrderItem orderItem: orderItemList){
                    entityManager.persist(orderItem);
                }

                entityManager.getTransaction().commit();
                System.out.println("Заказ успешно оформлен.");
            } catch (Exception e) {
                System.out.println("Ошибка при оформлении заказа: " + e.getMessage());
                entityManager.getTransaction().rollback();
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        }
    }

}
