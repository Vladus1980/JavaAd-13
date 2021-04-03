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
import dao.MagazineDAO;
import domain.Magazine;

public class MagazineDAOImpl implements MagazineDAO {
	private EntityManager em = DAOFactory.getEntityManager();
	
	private Logger log = Logger.getLogger(MagazineDAOImpl.class);

	@Override
	public Magazine insert(Magazine magazine) throws DAOException {
		log.info("Creating new magazine in database...");

		try {
			log.trace("Opening transaction...");
			em.getTransaction().begin();
			log.trace("Persisting entity...");
			em.persist(magazine);
			log.trace("Commiting transaction to database...");
			em.getTransaction().commit();
		} catch (Exception e) {
			log.error("Creating magazine failed!");
			throw new DAOException("Creating magazine failed!", e);
		}

		log.trace("Returning Magazine...");
		log.info(magazine + " is added to database!");
		return magazine;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Magazine> readAll() throws DAOException {
		log.info("Getting all magazines from database...");
		
		List<Magazine> magazineList = new ArrayList<>();

		try {
			log.trace("Finding entities...");
			Query query = em.createQuery("SELECT e FROM Magazine e");
			magazineList = query.getResultList();
		} catch (Exception e) {
			log.error("Getting list of magazines failed!");
			throw new DAOException("Getting list of magazines failed!", e);
		}

		log.trace("Returning list of magazines...");
		return magazineList;
	}

	@Override
	public Magazine readByID(int id) throws DAOException {
		log.info("Getting magazine by id from database...");

		Magazine magazine = null;

		try {
			log.trace("Finding entity...");
			magazine = em.find(Magazine.class, id);
		} catch (Exception e) {
			log.error("Getting magazine by id failed!");
			throw new DAOException("Getting magazine by id failed!", e);
		}

		log.trace("Returning Magazine...");
		log.info(magazine + " is getted from database!");
		return magazine;
	}

	@Override
	public boolean updateByID(Magazine magazine) throws DAOException {
		log.info("Updating magazine by id in database...");
		
		boolean result = false;

		try {
			log.trace("Opening transaction...");
			em.getTransaction().begin();
			log.trace("Updating entity...");
			em.merge(magazine);
			log.trace("Commiting transaction to database...");
			em.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			log.error("Updating magazine failed!");
			throw new DAOException("Updating magazine failed!", e);
		}

		if (result == false) {
			log.info("Updating magazine failed!");
		} else {
			log.trace("Returning result...");
			log.info("Magazine with ID#" + magazine.getId() + " is updated in database!");
		}
		return result;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		log.info("Deleting magazine by id from database...");

		boolean result = false;

		try {
			log.info("Getting user by id from database...");
			Magazine magazine = readByID(id);
			log.trace("Opening transaction...");
			em.getTransaction().begin();
			log.trace("Removing entity...");
			em.remove(magazine);
			log.trace("Commiting transaction to database...");
			em.getTransaction().commit();
			result = true;
		} catch (Exception e) {
			log.error("Deleting magazine failed!");
			throw new DAOException("Deleting magazine failed!", e);
		}

		if (result == false) {
			log.info("Deleting magazine failed!");
		} else {
			log.trace("Returning result...");
			log.info("Magazine with ID#" + id + " is deleted from database!");
		}
		return result;
	}
}