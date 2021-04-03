package impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import dao.DAOException;
import dao.DAOFactory;
import dao.SubscribeDAO;
import domain.Subscribe;

public class SubscribeDAOImpl implements SubscribeDAO {
	private EntityManager em = DAOFactory.getEntityManager();
	
	private Logger log = Logger.getLogger(SubscribeDAOImpl.class);

	@Override
	public Subscribe insert(Subscribe subscribe) throws DAOException {
		log.info("Creating new subscribe in database...");

		try {
			log.trace("Opening transaction...");
			em.getTransaction().begin();
			log.trace("Persisting entity...");
			em.persist(subscribe);
			log.trace("Commiting transaction to database...");
			em.getTransaction().commit();
		} catch (Exception e) {
			log.error("Creating subscribe failed!");
			throw new DAOException("Creating subscribe failed!", e);
		}

		log.trace("Returning Subscribe...");
		log.info(subscribe + " is added to database!");
		return subscribe;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subscribe> readAll() throws DAOException {
		log.info("Getting all subscribes from database...");
		
		List<Subscribe> subscribeList = new ArrayList<>();

		try {
			log.trace("Finding entities...");
			Query query = em.createQuery("SELECT e FROM Subscribe e");
			subscribeList = query.getResultList();
		} catch (Exception e) {
			log.error("Getting list of subscribes failed!");
			throw new DAOException("Getting list of subscribes failed!", e);
		}

		log.trace("Returning list of subscribes...");
		return subscribeList;
	}

	@Override
	public Subscribe readByID(int id) throws DAOException {
		log.info("Getting subscribe by id from database...");
		
		Subscribe subscribe = null;

		try {
			log.trace("Finding entity...");
			subscribe = em.find(Subscribe.class, id);
		} catch (Exception e) {
			log.error("Getting subscribe by id failed!");
			throw new DAOException("Getting subscribe by id failed!", e);
		}

		log.trace("Returning Subscribe...");
		log.info(subscribe + " is getted from database!");
		return subscribe;
	}

	@Override
	public boolean updateByID(Subscribe subscribe) throws DAOException {
		log.info("Updating subscribe by id in database...");
		
		boolean result = false;

		try {
			log.trace("Opening transaction...");
			em.getTransaction().begin();
			log.trace("Updating entity...");
			em.merge(subscribe);
			log.trace("Commiting transaction to database...");
			em.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			log.error("Updating subscribe failed!");
			throw new DAOException("Updating subscribe failed!", e);
		}

		if (result == false) {
			log.info("Updating subscribe failed!");
		} else {
			log.trace("Returning result...");
			log.info("Subscribe with ID#" + subscribe.getId() + " is updated in database!");
		}
		return result;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		log.info("Deleting subscribe by id from database...");

		boolean result = false;

		try {
			log.info("Getting user by id from database...");
			Subscribe subscribe = readByID(id);
			log.trace("Opening transaction...");
			em.getTransaction().begin();
			log.trace("Removing entity...");
			em.remove(subscribe);
			log.trace("Commiting transaction to database...");
			em.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			log.error("Deleting subscribe failed!");
			throw new DAOException("Deleting subscribe failed!", e);
		}

		if (result == false) {
			log.info("Deleting subscribe failed, no rows affected!");
		} else {
			log.trace("Returning result...");
			log.info("Subscribe with ID#" + id + " is deleted from database!");
		}
		return result;
	}
}