import model.Category;
import model.Option;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import java.util.*;

public class CreateCategory {

    public void createCategory(Scanner scanner) {
        EntityManager manager = Persistence.createEntityManagerFactory("default").createEntityManager();

        try {
            Category newCategory = null;
            String categoryName = null; // Перемещаем объявление сюда

            while (newCategory == null || categoryName.isBlank()) {
                System.out.println("Введите название категории:");
                categoryName = scanner.nextLine();

                if (categoryName.isBlank()) {
                    System.out.println("Название категории не может быть пустым.");
                    continue;
                }

                Category existingCategory = manager.createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class)
                        .setParameter("name", categoryName)
                        .getResultList()
                        .stream()
                        .findFirst()
                        .orElse(null);

                if (existingCategory != null) {
                    System.out.println("Такая категория уже существует.");
                    continue;
                }
                manager.getTransaction().begin();

                newCategory = new Category();
                newCategory.setName(categoryName);

                manager.persist(newCategory);
                // Предложите пользователю ввести характеристики для категории
                System.out.println("Введите характеристики категории (разделяйте их запятыми):");
                String optionInput = scanner.nextLine();
                List<String> optionList = Arrays.asList(optionInput.split(","));

                for (String optionName : optionList) {
                    Option option = new Option();
                    option.setName(optionName);
                    option.setCategory(newCategory);
                    manager.persist(option);
                }
            }
            manager.getTransaction().commit();

            System.out.println("Категория добавлена");
        } catch (Exception ex) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
        } finally {
            manager.close();
        }
    }
}

