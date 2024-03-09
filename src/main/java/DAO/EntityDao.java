package DAO;

import javax.persistence.EntityManager;

public abstract class EntityDao<T> {
    protected final EntityManager manager;
    private final Class<T> entityClass;


    public EntityDao(EntityManager entityManager, Class<T> entityClass) {
        this.manager = entityManager;
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            manager.getTransaction().rollback();
        }
    }

    public void update(T entity) {
        try {
            manager.getTransaction().begin();
            manager.merge(entity);
            manager.getTransaction().commit();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            manager.getTransaction().rollback();
        }
    }

    public void delete(T entity) {
        try {
            manager.getTransaction().begin();
            manager.remove(entity);
            manager.getTransaction().commit();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            manager.getTransaction().rollback();
        }
    }

    public T findByID(int id) {
        return manager.find(entityClass, id);
    }
}
