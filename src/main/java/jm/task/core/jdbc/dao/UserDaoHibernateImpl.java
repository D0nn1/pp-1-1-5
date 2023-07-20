package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession();) {
            try {
                session.beginTransaction();
                session.createSQLQuery("CREATE TABLE IF NOT EXISTS `mydbtest`.`users` (\n" +
                        "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `name` VARCHAR(45) NOT NULL,\n" +
                        "  `lastName` VARCHAR(45) NOT NULL,\n" +
                        "  `age` INT(3) NULL,\n" +
                        "  PRIMARY KEY (`id`))\n" +
                        "ENGINE = InnoDB\n" +
                        "DEFAULT CHARACTER SET = utf8;").executeUpdate();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession();) {
            try {
                session.beginTransaction();
                session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession();) {
            try {
                session.beginTransaction();
                session.save(new User(name, lastName, age));
                session.getTransaction().commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        }
    }


    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            try {
                session.beginTransaction();
                session.delete(session.get(User.class, id));
                session.getTransaction().commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.getCurrentSession()) {
            try {
                session.beginTransaction();
                return session.createQuery("FROM User", User.class).list();
            } catch (HibernateException e) {
                e.printStackTrace();
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession();) {
            try {
                session.beginTransaction();
                session.createQuery("delete User").executeUpdate();
                session.getTransaction().commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        }
    }


//    private  <T> T run(Function<Session, T> function) {
//        Session session = null;
//        try {
//            session = sessionFactory.getCurrentSession();
//            session.beginTransaction();
//            T t = function.apply(session);
//            session.getTransaction().commit();
//            return t;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            if (session.getTransaction() != null) {
//                session.getTransaction().rollback();
//            }
//        } finally {
//            session.close();
//        }
//        return null;
//    }


    }
