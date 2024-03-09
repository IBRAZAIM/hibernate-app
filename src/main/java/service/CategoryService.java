package service;

import DAO.CategoryDao;
import model.Category;
import model.Product;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class CategoryService {

    private CategoryDao categoryDao;
    private EntityManager manager;
    private Scanner scanner = new Scanner(System.in);

    public CategoryService(EntityManager manager) {
        this.categoryDao = new CategoryDao(manager);
        this.manager = manager;
    }

    // Получить список продуктов в указанной категории
    public List<Product> getProductsInCategory(String categoryName) {
        return categoryDao.findProductsInCategory(categoryName);
    }

    // Получить количество продуктов в каждой категории
    public Map<String, Long> getCountOfProductsInEachCategory() {
        List<Category> categories = categoryDao.getAllCategories();
        Map<String, Long> countMap = new HashMap<>();

        for (Category category : categories) {
            Long count = categoryDao.getCountOfProductsInCategory(category.getName());
            countMap.put(category.getName(), count);
        }

        return countMap;
    }

    // Получить самый дорогой продукт в каждой категории
    public Map<String, Product> getMostExpensiveProductInEachCategory() {
        List<Category> categories = categoryDao.getAllCategories();
        Map<String, Product> mostExpensiveMap = new HashMap<>();

        for (Category category : categories) {
            Product mostExpensiveProduct = categoryDao.findMostExpensiveProductInCategory(category.getName());
            mostExpensiveMap.put(category.getName(), mostExpensiveProduct);
        }

        return mostExpensiveMap;
    }

    // Применить скидку к товарам в указанной категории
    public void applyDiscountByCategory(Scanner scanner, EntityManager manager) {
        System.out.println("Введите название категории:");
        String categoryName = scanner.nextLine();
        System.out.println("Введите процент скидки:");
        int discountPercentage = Integer.parseInt(scanner.nextLine());
        List<Product> products = categoryDao.findProductsInCategory(categoryName);
        try {
            for (Product product : products) {
                int discountedPrice = (int) (product.getPrice() * (1 - discountPercentage / 100.0));
                product.setPrice(discountedPrice);
                categoryDao.updateProductPriceByName(product.getName(), discountedPrice);
            }
            System.out.println("Скидка применена.");
        } catch (Exception ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        }
    }


    // Вывести список категорий
    public void printCategories() {
        List<Category> categories = categoryDao.getAllCategories();

        if (categories.isEmpty()) {
            System.out.println("Нет доступных категорий.");
            return;
        }
        System.out.println("Список категорий:");
        for (Category category : categories) {
            System.out.println(category.getId() + ". " + category.getName());
        }
    }

    public void categoryFunc(Scanner scanner, EntityManager manager) {
        // Выводим сообщение о выборе категории
        System.out.println("Введите ваш выбор:");
        String categoryChoice = scanner.nextLine();

        try {
            switch (categoryChoice) {
                case "a":
                    // Выводим сообщение о вводе названия категории
                    System.out.println("Введите название категории:");
                    String categoryName = scanner.nextLine();
                    // Получаем список продуктов в категории и выводим их
                    List<Product> productsInCategory = getProductsInCategory(categoryName);
                    try {
                        for (Product product : productsInCategory) {
                            System.out.println(product.getName() + " - " + product.getPrice());
                        }
                    } catch (Exception ex) {
                        System.out.println("Ошибка: " + ex.getMessage());
                    }
                    break;
                case "b":
                    // Получаем и выводим количество продуктов в каждой категории
                    Map<String, Long> countMap = getCountOfProductsInEachCategory();
                    try {
                        for (Map.Entry<String, Long> entry : countMap.entrySet()) {
                            System.out.println(entry.getKey() + ": " + entry.getValue());
                        }
                    } catch (Exception ex) {
                        System.out.println("Ошибка: " + ex.getMessage());
                    }
                    break;
                case "c":
                    // Получаем и выводим самый дорогой продукт в каждой категории
                    Map<String, Product> mostExpensiveMap = getMostExpensiveProductInEachCategory();
                    try {
                        for (Map.Entry<String, Product> entry : mostExpensiveMap.entrySet()) {
                            Product product = entry.getValue();
                            if (product != null) {
                                System.out.println(entry.getKey() + ": " + product.getName() + " - " + product.getPrice());
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println("Ошибка: " + ex.getMessage());
                    }
                    break;
                case "d":
                    //Предоставление скидки на товар по категории
                    applyDiscountByCategory(scanner, manager);
                    break;
                case "0":
                    break;
                default:
                    // Выводим сообщение об ошибке при некорректном выборе
                    System.out.println("Некорректная команда. Пожалуйста, введите корректную команду.");
            }
        } catch (NumberFormatException e) {
            // Выводим сообщение об ошибке при некорректном вводе числа
            System.out.println("Ошибка: введите корректное число.");
        }
    }

}




