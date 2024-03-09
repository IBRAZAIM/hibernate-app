package order;

import model.Product;
import order.model.Review;
import order.model.User;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Scanner;

public class CreateReview {
    // Добавление отзыва
    public void addReview(Scanner scanner, EntityManager entityManager) {
        try {

            System.out.print("Введите идентификатор пользователя: ");
            Long userId = Long.parseLong(scanner.nextLine());
            User user = entityManager.find(User.class, userId);
            if (user == null) {
                return;
            }
            System.out.print("Введите идентификатор продукта: ");
            Long productId = Long.parseLong(scanner.nextLine());
            Product product = entityManager.find(Product.class, productId);
            if (product == null) {
                return;
            }
            System.out.print("Введите рейтинг (от 1 до 5): ");
            int rating = Integer.parseInt(scanner.nextLine());

            if (!(rating >= 1 && rating <= 5)) {
                return;
            }
            System.out.print("Введите текст отзыва: ");
            String text = scanner.nextLine();

            try {
                entityManager.getTransaction().begin();
                Review review = new Review();
                review.setUser(user);
                review.setProduct(product);
                review.setPublished(true);
                review.setRating(rating);
                review.setText(text);
                review.setPublicationDate(LocalDateTime.now());
                // Сохранение отзыва в базу данных
                entityManager.persist(review);
                entityManager.getTransaction().commit();
                System.out.println("Отзыв успешно добавлен.");
            } catch (Exception e) {
                System.out.println("Ошибка при добавлении отзыва: " + e.getMessage());
                entityManager.getTransaction().rollback();
            }
        }catch (Exception e) {
            System.out.println("Ошибка" + e.getMessage());
        }
    }
}
