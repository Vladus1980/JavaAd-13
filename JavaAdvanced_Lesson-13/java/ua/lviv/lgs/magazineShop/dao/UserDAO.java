package dao;

import domain.User;

public interface UserDAO extends DAOAbstractCRUD<User>{

	User readByEmail(String email) throws DAOException;

	
	
}
