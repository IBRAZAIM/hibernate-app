package order;

import order.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

class UserAuthenticator {
    public boolean authenticateUser(String username, String password, EntityManager manager) {
        TypedQuery<User> query = manager.createQuery("SELECT u FROM User u WHERE LOWER(u.login) = LOWER(:username) AND u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return !query.getResultList().isEmpty();
    }
}


