package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import dao.DAOException;
import dao.DAOFactory;
import dao.UserDAO;
import domain.User;

public class UserDAOImpl implements UserDAO {
	private EntityManager em = DAOFactory.getEntityManager();

	private Logger log = Logger.getLogger(UserDAOImpl.class);

	@Override
	public User insert(User user) throws DAOException {
		log.info("Creating new user in database...");

		try {
			log.trace("Opening transaction...");
			em.getTransaction().begin();
			log.trace("Persisting entity...");
			em.persist(user);
			log.trace("Commiting transaction to database...");
			em.getTransaction().commit();
		} catch (Exception e) {
			log.error("Creating user failed!");
			throw new DAOException("Creating user failed!", e);
		}

		log.trace("Returning User...");
		log.info(user + " is added to database!");
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> readAll() throws DAOException {
		log.info("Getting all users from database...");

		List<User> userList = new ArrayList<>();

		try {
			log.trace("Finding entities...");
			Query query = em.createQuery("SELECT e FROM User e");
			userList = query.getResultList();
		} catch (Exception e) {
			log.error("Getting list of users failed!");
			throw new DAOException("Getting list of users failed!", e);
		}

		log.trace("Returning list of users...");
		return userList;
	}

	@Override
	public User readByID(int id) throws DAOException {
		log.info("Getting user by id from database...");

		User user = null;

		try {
			log.trace("Finding entity...");
			user = em.find(User.class, id);
		} catch (Exception e) {
			log.error("Getting user by id failed!");
			throw new DAOException("Getting user by id failed!", e);
		}

		log.trace("Returning User...");
		log.info(user + " is getted from database!");
		return user;
	}

	public User readByEmail(String email) throws DAOException {
		log.info("Getting user by email from database...");

		User user = null;

		try {
			log.trace("Building query and finding entity...");
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
			Root<User> from = criteriaQuery.from(User.class);
			criteriaQuery.select(from);
			criteriaQuery.where(criteriaBuilder.equal(from.get("email"), email));
			TypedQuery<User> typedQuery = em.createQuery(criteriaQuery);
			user = typedQuery.getSingleResult();
		} catch (Exception e) {
			log.error("Getting user by email failed!");
			throw new DAOException("Getting user by email failed!", e);
		}

		log.trace("Returning User...");
		log.info(user + " is getted from database!");
		return user;
	}

	@Override
	public boolean updateByID(User user) throws DAOException {
		log.info("Updating user by id in database...");

		boolean result = false;

		try {
			log.trace("Opening transaction...");
			em.getTransaction().begin();
			log.trace("Updating entity...");
			em.merge(user);
			log.trace("Commiting transaction to database...");
			em.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			log.error("Updating user failed!");
			throw new DAOException("Updating user failed!", e);
		}

		if (result == false) {
			log.info("Updating user failed!");
		} else {
			log.trace("Returning result...");
			log.info("User with ID#" + user.getId() + " is updated in database!");
		}
		return result;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		log.info("Deleting user by id from database...");

		boolean result = false;

		try {
			log.info("Getting user by id from database...");
			User user = readByID(id);
			log.trace("Opening transaction...");
			em.getTransaction().begin();
			log.trace("Removing entity...");
			em.remove(user);
			log.trace("Commiting transaction to database...");
			em.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			log.error("Deleting user failed!");
			throw new DAOException("Deleting user failed!", e);
		}

		if (result == false) {
			log.info("Deleting user failed!");
		} else {
			log.trace("Returning result...");
			log.info("User with ID#" + id + " is deleted from database!");
		}
		return result;
	}


}