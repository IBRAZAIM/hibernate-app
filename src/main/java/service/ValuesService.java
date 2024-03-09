package service;

import DAO.ValuesDao;
import model.Value;

import javax.persistence.EntityManager;

public class ValuesService {
    private ValuesDao valuesDao;

    public ValuesService(EntityManager entityManager) {
        this.valuesDao = new ValuesDao(entityManager);
    }

    public void addValue(Value value) {
        valuesDao.create(value);
    }

    // ... (другие методы)
}
