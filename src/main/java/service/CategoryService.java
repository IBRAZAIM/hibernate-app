package service;

import DAO.CategoryDao;
import DAO.ProductDao;
import model.Category;
import model.Product;

import javax.persistence.EntityManager;
import java.util.*;

public class CategoryService {

    private  CategoryDao categoryDao;
    private ProductDao productDao;
    private ProductService productService;

    private EntityManager manager;
    private Scanner scanner = new Scanner(System.in);


    public CategoryService(EntityManager manager) {
        this.categoryDao = new CategoryDao(manager);
        this.manager = manager;
    }

    public List<Product> getProductsInCategory(String categoryName) {
        return categoryDao.findProductsInCategory(categoryName);
    }

    public Map<String, Long> getCountOfProductsInEachCategory() {
        List<Category> categories = categoryDao.getAllCategories();
        Map<String, Long> countMap = new HashMap<>();

        for (Category category : categories) {
            Long count = categoryDao.getCountOfProductsInCategory(category.getName());
            countMap.put(category.getName(), count);
        }

        return countMap;
    }

    public Map<String, Product> getMostExpensiveProductInEachCategory() {
        List<Category> categories = categoryDao.getAllCategories();
        Map<String, Product> mostExpensiveMap = new HashMap<>();

        for (Category category : categories) {
            Product mostExpensiveProduct = categoryDao.findMostExpensiveProductInCategory(category.getName());
            mostExpensiveMap.put(category.getName(), mostExpensiveProduct);
        }

        return mostExpensiveMap;
    }

    public void applyDiscountByCategory(String categoryName, int discountPercentage) {
        List<Product> products = categoryDao.findProductsInCategory(categoryName);

        for (Product product : products) {
            int discountedPrice = (int) (product.getPrice() * (1 - discountPercentage / 100.0));
            product.setPrice(discountedPrice);
            productDao.updateProductPriceByName(categoryName, discountedPrice);
        }
    }
    public List<Product> getProductsByCategory(String categoryIdentifier) {
        Category category = getCategoryByIdOrName(categoryIdentifier);
        if (category != null) {
            return category.getProductList();
        }
        return Collections.emptyList();
    }

    public Category getCategoryByName(String categoryName) {
        return categoryDao.getCategoryByName(categoryName);
    }

    private Category getCategoryByIdOrName(String categoryIdentifier) {
        try {
            int categoryId = Integer.parseInt(categoryIdentifier);
            return categoryDao.getCategoryById(categoryId);
        } catch (NumberFormatException e) {
            // Если не удалось преобразовать в число, ищем по названию
            return getCategoryByName(categoryIdentifier);
        }
    }
    public List<Product> getProductsByCategoryId(int categoryId) {
        return categoryDao.getProductsByCategoryId(categoryId);
    }
    public void printCategories() {
        List<Category> categories = manager
                .createQuery("SELECT c FROM Category c ORDER BY id", Category.class)
                .getResultList();

        if (categories.isEmpty()) {
            System.out.println("Нет доступных категорий.");
            return;
        }
        System.out.println("Список категорий:");
        for (Category category : categories) {
            System.out.println(category.getId() + ". " + category.getName());
        }
    }

    public void categoryFunc() {
//        String categoryChoice = scanner.nextLine();
//
//        switch (categoryChoice) {
//            case "a":
//                // Найти список товаров в категории
//                System.out.println("Введите ID категории:");
//                String categoryIdentifier = scanner.nextLine();
//
//                int categoryId;
//                try {
//                    categoryId = Integer.parseInt(categoryIdentifier);
//                    // Ищем по ID
//                    List<Product> productsInCategory = getProductsByCategoryId(categoryId);
//                } catch (NumberFormatException e) {
//                    System.out.println("Попробуйте ввести название: ");
//                    Category category = getCategoryByName(categoryIdentifier);
//
//                }
//            case "b":
//                // Найти количество товаров в каждой категории
//                Map<String, Long> countMap = getCountOfProductsInEachCategory();
//                for (Map.Entry<String, Long> entry : countMap.entrySet()) {
//                    System.out.println(entry.getKey() + ": " + entry.getValue());
//                }
//                break;
//            case "c":
//                // Найти самый дорогой товар в каждой категории
//                Map<String, Product> mostExpensiveMap = getMostExpensiveProductInEachCategory();
//                for (Map.Entry<String, Product> entry : mostExpensiveMap.entrySet()) {
//                    System.out.println(entry.getKey() + ": " + entry.getValue());
//                }
//                break;
//            case "d":
//                // Предоставить скидку товарам в категории
//                System.out.println("Введите название категории для скидки:");
//                String discountCategory = scanner.nextLine();
//                System.out.println("Введите процент скидки:");
//                int categoryDiscountPercentage = Integer.parseInt(scanner.nextLine());
//                applyDiscountByCategory(discountCategory, categoryDiscountPercentage);
//                System.out.println("Скидка применена");
//                break;
//            case "0":
//                // Вернуться в предыдущее меню
//                break;
//            default:
//                System.out.println("Некорректная команда. Пожалуйста, введите корректную команду.");
//        }
        System.out.println("В доработке...");
    }

}

