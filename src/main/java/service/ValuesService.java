package service;

import DAO.OptionDao;
import DAO.ValuesDao;
import model.Category;
import model.Option;
import model.Product;
import model.Value;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ValuesService {
    private ValuesDao valuesDao;
    private CategoryService categoryService;
    private OptionService optionService;
    private OptionDao optionDao;
    private Value value;

    public ValuesService(EntityManager entityManager) {
        this.valuesDao = new ValuesDao(entityManager);
        this.optionDao = new OptionDao(entityManager);
    }

    public void addValue(Value value) {
        valuesDao.create(value);
    }
    public void printValues(Scanner scanner, EntityManager manager){
        List<Option> options  = optionDao.findOptionByName("Производитель");
        List<Value> values = valuesDao.printAllValues();

        System.out.println("Список значений:");
        for (Value value : values){
            System.out.println("id:" + value.getId() + " ." + value.getName());
        }
    }

    public void discountByValue(Scanner scanner, EntityManager manager) {
        System.out.println("Выберите значение для предоставления скидки!");
        printValues(scanner, manager);
        Long valueId = Long.parseLong(scanner.nextLine());
        Value value = manager.find(Value.class, valueId);
        List<Product> products = valuesDao.findProductByValues(value.getName());
        System.out.println("Введите процент скидки:");
        int discountPercent = Integer.parseInt(scanner.nextLine());
        manager.getTransaction().begin();
        for (Product product : products) {
            double discountFactor = (100 - discountPercent) / 100.0;
            int newPrice = (int) (product.getPrice() * discountFactor);
            product.setPrice(newPrice);
            manager.persist(product);
        }
        manager.getTransaction().commit();
        System.out.println("Скидка успешно применена!");
    }

}
