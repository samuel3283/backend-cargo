package pe.com.boox.cargo.dao.repository;

import pe.com.boox.cargo.model.Session;

public interface SessionRepository {

	void insertSession(Session session) throws Exception;
	Session getSessionByToken(Session session) throws Exception;
	void deleteSession(Session session) throws Exception;

	
}
