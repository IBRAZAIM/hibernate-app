package order;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Scanner;

public class OrderTest {
    public static void main(String[] args) {
        EntityManager manager = Persistence.createEntityManagerFactory("default").createEntityManager();
        Scanner scanner = new Scanner(System.in);
        UserAuthenticator userAuthenticator = new UserAuthenticator();

        boolean isAuthenticated = false;
        int attempts = 0;
        while (!isAuthenticated && attempts < 3) {
            System.out.println("Введите логин:");
            String username = scanner.nextLine();
            System.out.println("Введите пароль:");
            String password = scanner.nextLine();

            isAuthenticated = userAuthenticator.authenticateUser(username, password, manager);
            if (!isAuthenticated) {
                System.out.println("Неверный логин или пароль. Попробуйте еще раз.");
                attempts++;
            }
        }

        if (isAuthenticated) {
            System.out.println("Авторизация успешна!");
            System.out.println("------------------------------------------");
            showMenu(scanner, manager);
        } else {
            System.out.println("Превышено количество попыток ввода. Попробуйте позже.");
        }
    }

    private static void showMenu(Scanner scanner, EntityManager manager) {
        System.out.println(
                "Выберите функционал:\n" +
                        "(1)Создание пользователя\n" +
                        "(2)Заказ товара\n" +
                        "(3)Оставить отзыв\n"
        );
        int num = Integer.parseInt(scanner.nextLine());
        switch (num) {
            case 1:
                CreateUser createUser = new CreateUser();
                createUser.addUser(scanner, manager);
                break;
            case 2:
                CreateOrder createOrder = new CreateOrder();
                createOrder.createOrder(scanner, manager);
                break;
            case 3:
                CreateReview review = new CreateReview();
                review.addReview(scanner, manager);
                break;
            default:
                System.out.println("Некорректный выбор функционала: " + num);
                break;
        }
    }
}

