package service;

import java.util.Map;

import dao.DAOAbstractCRUD;
import dao.DAOException;
import domain.Magazine;

public interface MagazineService extends DAOAbstractCRUD<Magazine> {
	
	public Map<Integer, Magazine> readAllMap() throws DAOException;

}
