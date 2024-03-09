import model.Category;
import model.Option;
import model.Value;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class DeleteOptionAndValue {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        // Вывод всех категорий и их характеристик
        List<Category> categories = em.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList();
        for (Category category : categories) {
            System.out.println("Категория: " + category.getName());
            System.out.println("Характеристики:");
            for (Option option : category.getOptionsList()) {
                System.out.println(option.getId() + ": " + option.getName());
            }
            System.out.println();
        }
        // Выбор и удаление характеристик
        System.out.print("Введите номер категории для удаления характеристики: ");
        int categoryId = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите номер характеристики для удаления (или 0, чтобы удалить все характеристики): ");
        int optionIdToDelete = Integer.parseInt(scanner.nextLine());

        deleteOptionsForCategory(em, categoryId, optionIdToDelete);

        em.close();
        emf.close();
    }

    public static void deleteOptionsForCategory(EntityManager em, int categoryId, int optionIdToDelete) {
        em.getTransaction().begin();

        Category selectedCategory = em.find(Category.class, categoryId);

        List<Option> options = selectedCategory.getOptionsList();

        if (optionIdToDelete != 0) {
            Option optionToDelete = null;
            for (Option option : options) {
                if (option.getId() == optionIdToDelete) {
                    optionToDelete = option;
                    break;
                }
            }
            if (optionToDelete != null) {
                List<Value> values = optionToDelete.getValues();
                for (Value value : values) {
                    em.remove(value);
                }
                em.remove(optionToDelete);
                System.out.println("Характеристика '" + optionToDelete.getName() + "' успешно удалена из категории '" + selectedCategory.getName() + "'.");
                return;
            }
        } else {
            for (Option option : options) {
                List<Value> values = option.getValues();
                for (Value value : values) {
                    em.remove(value);
                }
                values.clear();
                em.remove(option);
            }
            options.clear();
            System.out.println("Все характеристики успешно удалены из категории '" + selectedCategory.getName() + "'.");
        }

        em.getTransaction().commit();
    }
}


