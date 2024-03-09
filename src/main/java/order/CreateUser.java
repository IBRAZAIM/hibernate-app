package order;

import order.model.Role;
import order.model.User;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Scanner;

public class CreateUser{
    //Регистрация пользователя
    public void addUser(Scanner scanner, EntityManager manager) {
        manager.getTransaction().begin();

        User user = new User();
        user.setRole(Role.USER);

        System.out.print("Введите логин пользователя: ");
        user.setLogin(scanner.nextLine());

        System.out.print("Введите пароль пользователя: ");
        user.setPassword(scanner.nextLine());

        System.out.print("Введите имя пользователя: ");
        user.setName(scanner.nextLine());

        System.out.print("Введите фамилию пользователя: ");
        user.setSurname(scanner.nextLine());

        user.setRegistrationDate(LocalDateTime.now());

        manager.persist(user);

        manager.getTransaction().commit();

        // Вывод информации о добавленном пользователе
        System.out.println("Добавлен пользователь:");
        System.out.println("ID: " + user.getId());
        System.out.println("Логин: " + user.getLogin());
        System.out.println("Имя: " + user.getName());
        System.out.println("Фамилия: " + user.getSurname());
    }
}
