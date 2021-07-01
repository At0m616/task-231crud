package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JpaUserDaoImpl implements UserDao{

    @PersistenceContext(unitName = "emf")
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUserById(long id) {
        TypedQuery<User> query = entityManager
                .createQuery("select u from User u where u.id = :id",User.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery
                ("select u from User u",User.class)
                .getResultList();
    }

    @Override
    public void updateUser(User user) {
        User userUpdated = getUserById(user.getId());
        userUpdated.setFirstName(user.getFirstName());
        userUpdated.setLastName(user.getLastName());
        userUpdated.setEmail(user.getEmail());
    }

    @Override
    public void removeUser(long id) {
        entityManager.remove(getUserById(id));
    }
}
