import model.Category;
import model.Option;
import model.Product;
import model.Value;
import service.CategoryService;
import service.OptionService;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class CreateProduct {
    public void createProduct() {
        Scanner scanner = new Scanner(System.in);
        EntityManager manager = Persistence.createEntityManagerFactory("default").createEntityManager();
        CategoryService categoryService = new CategoryService(manager);
        OptionService optionService = new OptionService(manager);
        try {
            System.out.println("Введите название товара:");
            String productName = scanner.nextLine();
            System.out.println("Введите стоимость товара:");
            int productPrice = Integer.parseInt(scanner.nextLine());
            categoryService.printCategories();
            System.out.println("Введите id категории товара");
            int categoryId = Integer.parseInt(scanner.nextLine());

            Category category = manager.find(Category.class, categoryId);
            if (category == null) {
                return;
            }
            Product product = new Product();
            product.setName(productName);
            product.setPrice(productPrice);
            product.setCategory(category);

            manager.getTransaction().begin();
            manager.persist(product);

            // Получаем список характеристик для выбранной категории
            List<Option> optionsList = category.getOptionsList();

            System.out.println("Список характеристик для категории " + categoryId + ": " + optionsList);

            if (optionsList.isEmpty()) {
                System.out.println("Для выбранной категории отсутствуют характеристики.");
                manager.getTransaction().commit();
                return;
            }
            // Добавляем значения для каждой характеристики
            System.out.println("Введите значения для характеристик:");

            for (Option option : optionsList) {
                while (true) {
                    System.out.println("Характеристика: " + option.getName());
                    System.out.println("Введите значение:");
                    String valueName = scanner.nextLine();

                    if (valueName.isBlank()) {
                        System.out.println("Значение не может быть пустым. Пожалуйста, введите значение.");
                        continue;
                    }

                    Value value = new Value();
                    value.setOption(option);
                    value.setProduct(product);
                    value.setName(valueName);
                    manager.persist(value);
                    break;
                }
            }

            System.out.println("Товар добавлен");
            manager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Ошибка при добавлении товара: " + ex.getMessage());
            manager.getTransaction().rollback();
        }
    }
}

