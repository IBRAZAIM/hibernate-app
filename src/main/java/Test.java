import service.CategoryService;
import service.ProductService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;
public class Test {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        ProductService productService = new ProductService(manager);
        CategoryService categoryService = new CategoryService(manager);

        while (true) {
            printMenu();
            String num = scanner.nextLine();

            switch (num) {
                case "1":
                    // Создать товар
                    CreateProduct createProduct = new CreateProduct();
                    createProduct.createProduct();

                    break;
                case "2":
                    // Вывести все товары
                    productService.getAllProducts();
                    break;
                case "3":
                    // Найти товар по id
                    productService.getProductById(scanner);
                    break;
                case "4":
                    // Найти товар по названию
                    productService.getProductsByName(scanner);
                    break;
                case "5":
                    // Удалить товар по id
                    DeleteProduct deleteProduct = new DeleteProduct();
                    deleteProduct.deleteProduct(scanner, manager);
                    break;
                case "6":
                    // Обновить стоимость товара по id
                    productService.updateProductPriceById(scanner);
                    break;
                case "7":
                    // Обновить стоимость товара по названию
                    productService.updateProductPriceByName(scanner);
                    break;
                case "8":
                    // Вывести все товары в ценовом диапазоне
                    productService.getProductsInPriceRange(scanner);
                    break;
                case "9":
                    // Подсчитать общую стоимость всех товаров
                    productService.getTotalCostOfProducts();
                    break;
                case "10":
                    // Вывести общее количество товаров
                    productService.getTotalNumberOfProducts();
                    break;
                case "11":
                    // Найти самый дорогой товар
                    productService.getMostExpensiveProduct();
                    break;
                case "12":
                    // Показать топ N товаров с самой высокой стоимостью
                    productService.getTopNExpensiveProducts(scanner);
                    break;
                case "13":
                    // Предоставить скидку на товар по его id
                    productService.applyDiscountById(scanner);
                    break;
                case "14":
                    // Работа с категориями
                    categoryMenu();
                    categoryService.categoryFunc();
                    break;
                case "15":
                    //Создание новой категории
                    CreateCategory createCategory = new CreateCategory();
                    createCategory.createCategory(scanner);
                    break;
                case "16"://TODO доделать
                   //UpdateProduct update = new UpdateProduct();
                   //update.updateProduct(scanner, manager);
                    System.out.println("В доработке...");
                   break;
                case "0":
                    // Выход из программы
                    System.exit(0);

            }
        }
    }

    //TODO новый функционал


    public static void printMenu() {
        System.out.println("-------------------------------------------------------------");
        System.out.println("Выберите команду:");
        System.out.println("1. Создать товар");
        System.out.println("2. Вывести все товары");
        System.out.println("3. Найти товар по id");
        System.out.println("4. Найти товар по названию");
        System.out.println("5. Удалить продукт");
        System.out.println("6. Обновить стоимость товара по id");
        System.out.println("7. Обновить стоимость товара по названию");
        System.out.println("8. Вывести все товары в ценовом диапазоне");
        System.out.println("9. Подсчитать общую стоимость всех товаров");
        System.out.println("10. Вывести общее количество товаров");
        System.out.println("11. Найти самый дорогой товар");
        System.out.println("12. Показать топ N товаров с самой высокой стоимостью");
        System.out.println("13. Предоставить скидку на товар по его id");
        System.out.println("14.Работа с категориями");
        System.out.println("15.Создать категорию");
        System.out.println("16.Обновить продукт и его данные");
        System.out.println("0. Выйти");
        System.out.println("-------------------------------------------------------------");
    }

    public static void categoryMenu() {
        System.out.println("Выберите действие с категориями:");
        System.out.println("a. Найти список товаров в категории");
        System.out.println("b. Найти количество товаров в каждой категории");
        System.out.println("c. Найти самый дорогой товар в каждой категории");
        System.out.println("d. Предоставить скидку товарам в категории");
        System.out.println("0. Вернуться в предыдущее меню");
    }
}