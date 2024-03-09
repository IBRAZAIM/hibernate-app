package service;

import DAO.ProductDao;
import model.Product;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class ProductService {
    private final ProductDao productDao;
    public ProductService(EntityManager entityManager) {
        this.productDao = new ProductDao(entityManager);

    }

    public List<Product> getAllProducts() {
        try {
            if (productDao != null){
                List<Product> allProducts = productDao.findAll();
                for (Product product : allProducts) {
                    System.out.println(product);
                }
            }
        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Product getProductById(Scanner scanner) {
        // Найти товар по id
        System.out.println("Введите id товара:");
        Long productId = Long.parseLong(scanner.nextLine());
        Product findProduct = productDao.findByID(productId);
        if (findProduct != null) {
            System.out.println(findProduct);
            return findProduct;
        } else {
            System.out.println("Товар с указанным id не найден.");
            return null;
        }
    }

    public List<Product> getProductsByName(Scanner scanner) {
        // Найти товар по названию
        System.out.println("Введите название товара:");
        String productNameSearch = scanner.nextLine();

        List<Product> foundByName = productDao.findByName(productNameSearch);
        try {
            if (!foundByName.isEmpty()) {
                for (Product product : foundByName) {
                    System.out.println(product);
                }
            } else {
                System.out.println("Товар не найден");
            }
        }catch (Exception ex){
            System.out.println("Ошибка: " + ex.getMessage());
        }
        return foundByName;
    }


    public void updateProductPriceById(Scanner scanner) {
        // Обновить стоимость товара по id
        System.out.println("Введите id товара для обновления стоимости:");
        Long updatePriceId = Long.parseLong(scanner.nextLine());
        System.out.println("Введите новую стоимость товара:");
        int newPrice = Integer.parseInt(scanner.nextLine());
        Product product = productDao.findByID(updatePriceId);
        try {
            if (product != null) {
                productDao.updateProductPriceById(updatePriceId, newPrice);
                System.out.println("Стоимость товара обновлена");
            } else {
                System.out.println("Товар с указанным id не найден.");
            }
        }catch (Exception ex){
            System.out.println("Ошибка: " + ex.getMessage());
        }
    }

    public void updateProductPriceByName(Scanner scanner) {
        // Обновить стоимость товара по названию
        System.out.println("Введите название товара для обновления стоимости:");
        String updatePriceName = scanner.nextLine();
        System.out.println("Введите новую стоимость товара:");
        int newPriceByName = Integer.parseInt(scanner.nextLine());

        List<Product> foundByName = productDao.findByName(updatePriceName);
        try {
            if (!foundByName.isEmpty()) {
                productDao.updateProductPriceByName(updatePriceName, newPriceByName);
                System.out.println("Стоимость товара обновлена");
            } else {
                System.out.println("Товар с указанным именем не найден.");
            }
        }catch (Exception ex){
            System.out.println("Ошибка: " + ex.getMessage());
        }
    }

    public void getTotalNumberOfProducts() {
        // Вывести общее количество товаров
        try {
            Long totalNumberOfProducts = productDao.getTotalNumberOfProducts();
            System.out.println("Общее количество товаров: " + totalNumberOfProducts);
        }catch (Exception ex){
            System.out.println("Ошибка: " + ex.getMessage());
        }
    }

    public void getProductsInPriceRange(Scanner scanner) {
        System.out.println("Введите минимальную цену:");
        int minPrice = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите максимальную цену:");
        int maxPrice = Integer.parseInt(scanner.nextLine());
        try {
            List<Product> productsInPriceRange = productDao.findProductsInPriceRange(minPrice, maxPrice);
            for (Product product : productsInPriceRange) {
                System.out.println(product);
            }
        }catch (Exception ex){
            System.out.println("Ошибка: " + ex.getMessage());
        }
    }


    public void getTotalCostOfProducts() {
        // Подсчитать общую стоимость всех товаров
        Long totalCost = productDao.getTotalCostOfProducts();
        System.out.println("Общая стоимость всех товаров: " + totalCost);
    }

    public void getTopNExpensiveProducts(Scanner scanner) {
        // Показать топ N товаров с самой высокой стоимостью
        System.out.println("Введите количество товаров в топе:");
        int topN = Integer.parseInt(scanner.nextLine());
        List<Product> topProducts = productDao.findTopNExpensiveProducts(topN);
        for (Product product : topProducts) {
            System.out.println(product);
        }
    }

    public Product getMostExpensiveProduct() {
        // Найти самый дорогой товар
        try {
            Product mostExpensiveProduct = productDao.findMostExpensiveProduct();
            System.out.println("Самый дорогой товар: " + mostExpensiveProduct);
        }catch (Exception ex){
            System.out.println("Ошибка: " + ex.getMessage());
        }
        return productDao.findMostExpensiveProduct();
    }

    public void applyDiscountById(Scanner scanner) {
        // Предоставить скидку на товар по его id
        try {
            System.out.println("Введите id товара для предоставления скидки:");
            Long discountProductId = Long.parseLong(scanner.nextLine());
            System.out.println("Введите процент скидки:");
            int discountPercentage = Integer.parseInt(scanner.nextLine());

            productDao.applyDiscountById(discountProductId, discountPercentage);
            System.out.println("Скидка применена");
        }catch (Exception ex){
            System.out.println("Ошибка: " + ex.getMessage());
        }
    }
    
}



