package pe.com.boox.cargo.dao.repository;

import pe.com.boox.cargo.model.SessionChofer;

public interface SessionChoferRepository {

	void insertSession(SessionChofer session) throws Exception;
	SessionChofer getSessionByToken(SessionChofer session) throws Exception;
	void deleteSession(SessionChofer session) throws Exception;

	
}
