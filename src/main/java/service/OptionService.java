package service;

import DAO.OptionDao;
import model.Category;
import model.Option;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class OptionService {
    private EntityManager manager;
    private OptionDao optionDao;
    private Scanner scanner = new Scanner(System.in);

    public OptionService(EntityManager entityManager) {
        this.manager = entityManager;
        this.optionDao = new OptionDao(entityManager);
    }


    public List<Option> getOptionsByCategoryId(int categoryId) {
        return optionDao.findOptionsByCategoryId(categoryId);
    }

    public void createOption() {
        try {
            System.out.println("Введите id категории:");
            int categoryId = Integer.parseInt(scanner.nextLine());

            Category category = manager.find(Category.class, categoryId);

            if (category == null) {
                System.out.println("Категория с таким id не найдена.");
                return;
            }

            System.out.println("Введите название характеристики:");
            String optionName = scanner.nextLine();

            if (optionName.trim().isEmpty()) {
                System.out.println("Название характеристики не может быть пустым.");
                return;
            }

            Option existingOption = manager.createQuery("SELECT o FROM Option o WHERE o.name = :name AND o.category = :category", Option.class)
                    .setParameter("name", optionName)
                    .setParameter("category", category)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElse(null);

            if (existingOption != null) {
                System.out.println("Такая характеристика уже существует для выбранной категории.");
                return;
            }

            Option newOption = new Option();
            newOption.setName(optionName);
            newOption.setCategory(category);

            manager.getTransaction().begin();
            manager.persist(newOption);
            manager.getTransaction().commit();

            System.out.println("Характеристика добавлена");
        } catch (Exception ex) {
            System.out.println("Ошибка при добавлении характеристики: " + ex.getMessage());
            manager.getTransaction().rollback();
        }
    }
}