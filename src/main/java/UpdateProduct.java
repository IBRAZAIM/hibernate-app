import model.Category;
import model.Option;
import model.Product;
import model.Value;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;


public class UpdateProduct {
    public void updateProduct(Scanner scanner, EntityManager manager) {
        System.out.println("Обновление товара");
        System.out.println("Введите ID товара для обновления:");
        int productIdToUpdate = Integer.parseInt(scanner.nextLine());

        Product product = manager.find(Product.class, productIdToUpdate);
        if (product != null) {
            System.out.println("Введите новое название товара (или оставьте пустым для пропуска):");
            String newName = scanner.nextLine().trim();

            System.out.println("Введите новую цену товара (или оставьте пустым для пропуска):");
            int newPrice = 0;
            String priceInput = scanner.nextLine().trim();
            if (!priceInput.isEmpty()) {
                try {
                    newPrice = Integer.parseInt(priceInput);
                } catch (NumberFormatException ex) {
                    System.out.println("Неверный формат цены. Введите целое число.");
                    return;
                }
            }

            // Начало транзакции
            manager.getTransaction().begin();

            // Обновление названия и цены товара, если они были введены
            if (!newName.isEmpty()) {
                product.setName(newName);
            }
            if (newPrice != 0) {
                product.setPrice(newPrice);
            }


            // Обновление характеристик товара
            Category category = product.getCategory();
            List<Option> options = category.getOptionsList();
            for (Option optionValue : options){
                TypedQuery<Value> jpql = manager.createQuery("SELECT v FROM Value v WHERE v.product = :product AND v.option = :option", Value.class);
                jpql.setParameter("product", product);
                jpql.setParameter("option", optionValue);

                List<Value> values = jpql.getResultList();
                if (!values.isEmpty()) {
                    Value value = values.get(0);
                    System.out.println("Характеристика: " + optionValue.getName());
                    System.out.println("Текущее значение характеристики: " + value.getName());
                    System.out.println("Введите новое значение характеристики: ");
                    String newValue = scanner.nextLine().trim();
                    value.setName(newValue);

                    manager.merge(value);
                } else {
                    System.out.println("Характеристика: " + optionValue.getName());
                    System.out.println("Для данного товара и характеристики нет значений в базе данных.");
                    System.out.println("Хотите создать новое значение характеристики? (Да/Нет)");
                    String createNewValue = scanner.nextLine().trim();
                    if ("да".equalsIgnoreCase(createNewValue)) {
                        System.out.println("Введите значение характеристики: ");
                        String newValue = scanner.nextLine().trim();
                        Value newValueEntity = new Value();
                        newValueEntity.setProduct(product);
                        newValueEntity.setOption(optionValue);
                        newValueEntity.setName(newValue);

                        manager.persist(newValueEntity);
                    }
                }
            }
            // Обновление товара
            manager.merge(product);

            // Завершение транзакции
            manager.getTransaction().commit();
            System.out.println("Товар успешно обновлен");



        } else {
            System.out.println("Товар с указанным ID не найден");
        }
    }
}


