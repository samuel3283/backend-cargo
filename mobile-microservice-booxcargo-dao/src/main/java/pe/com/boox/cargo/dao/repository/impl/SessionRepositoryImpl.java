package pe.com.boox.cargo.dao.repository.impl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pe.com.boox.cargo.dao.repository.SessionRepository;
import pe.com.boox.cargo.dao.repository.mapper.SessionMapper;
import pe.com.boox.cargo.model.Session;

@SuppressWarnings("all")
@Repository
public class SessionRepositoryImpl implements SessionRepository {

	private final Logger logger = LoggerFactory.getLogger(SessionRepositoryImpl.class);

	@Autowired
	@Resource(name = "jdbcTemplateMySql")
	private JdbcTemplate jdbcTemplate;

	
	@Override
	public void insertSession(Session session) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("INSERT INTO tbl_session ");
		sql_insert_user.append("(DEVICE,DEVICETYPE,TOKEN, RUC,NOMBRE,APELLIDO,EMAIL,TIPDOC,NUMDOC, ");
		sql_insert_user.append("TELEFONO,FEC_REGISTRO,FEC_EXPIRA,FEC_MODIFICA,ESTADO) ");
		sql_insert_user.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		logger.info("insertSession");
			
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
        
        int intervalo = 300;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt); 
        calendar.add(Calendar.SECOND, intervalo); 
        
        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaExpira = sdf.format(calendar.getTime());
        
		Object[] params = new Object[] {
		session.getDevice(), session.getDeviceType(), session.getToken(), session.getRuc(), session.getNombre(), session.getApellido(),
		session.getEmail(), session.getTipoDocumento(), session.getNumDocumento(),session.getTelefono(),
		fechaHora, fechaExpira, fechaHora, session.getEstado()
		};
		logger.info("ini ses  ==>:fechaExpira"+fechaExpira+"==>fechaHora:"+fechaHora);
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin ses");

	}

	
	@Override
	public Session getSessionByToken(Session session) throws Exception {
		logger.info("Repository.getSessionByToken init");
		List <Session> lista = null;
		Session bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO, DEVICE,DEVICETYPE,TOKEN, NOMBRE,APELLIDO,EMAIL,TIPDOC,NUMDOC, ");
		sql_find_user.append("RUC,TELEFONO, ESTADO, ");
		sql_find_user.append("DATE_FORMAT(FEC_EXPIRA,'%d/%m/%Y %H:%i:%s') FEC_EXP, ");
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("DATE_FORMAT(FEC_MODIFICA,'%d/%m/%Y %H:%i:%s') FEC_MOD ");
  		sql_find_user.append("FROM tbl_session WHERE TOKEN = ? ");
		logger.info("Repository.getSessionByToken::"+session.getToken());
  		Object[] params = new Object[] {
			session.getToken()
		};
    	lista = (List <Session> )jdbcTemplate.query(sql_find_user.toString(),params, new SessionMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new Session();
    		bean = lista.get(0);
    	}

		logger.info("getSessionByToken fin");
		return bean;	
	}
	

	@Override
	public void deleteSession(Session session) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("DELETE FROM tbl_session ");
		sql_insert_user.append("WHERE TOKEN=? ");
		logger.info("deleteSession::"+sql_insert_user);
			        
		Object[] params = new Object[] {
		session.getToken()
		};
		logger.info("ini del");
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin del");
				
	}

	


}
