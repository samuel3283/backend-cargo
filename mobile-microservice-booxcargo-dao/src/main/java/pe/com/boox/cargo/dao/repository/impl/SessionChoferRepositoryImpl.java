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

import pe.com.boox.cargo.dao.repository.SessionChoferRepository;
import pe.com.boox.cargo.dao.repository.SessionRepository;
import pe.com.boox.cargo.dao.repository.SessionTransportistaRepository;
import pe.com.boox.cargo.dao.repository.mapper.SessionChoferMapper;
import pe.com.boox.cargo.dao.repository.mapper.SessionMapper;
import pe.com.boox.cargo.dao.repository.mapper.SessionTransportistaMapper;
import pe.com.boox.cargo.model.Session;
import pe.com.boox.cargo.model.SessionChofer;
import pe.com.boox.cargo.model.SessionTransportista;

@SuppressWarnings("all")
@Repository
public class SessionChoferRepositoryImpl implements SessionChoferRepository {

	private final Logger logger = LoggerFactory.getLogger(SessionChoferRepositoryImpl.class);

	@Autowired
	@Resource(name = "jdbcTemplateMySql")
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertSession(SessionChofer session) throws Exception {
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("INSERT INTO tbl_session_chofer ");
		sql_insert_user.append("(DEVICE,DEVICETYPE,TOKEN, BREVETE,NOMBRE,APELLIDO,EMAIL,TIPDOC,NUMDOC, ");
		sql_insert_user.append("TELEFONO,FEC_REGISTRO,FEC_EXPIRA,FEC_MODIFICA,ESTADO,FOTO) ");
		sql_insert_user.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
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
		session.getDevice(), session.getDeviceType(), session.getToken(), session.getBrevete(), session.getNombre(), session.getApellido(),
		session.getEmail(), session.getTipoDocumento(), session.getNumDocumento(),session.getTelefono(),
		fechaHora, fechaExpira, fechaHora, session.getEstado(),session.getFoto()
		};
		logger.info("ini ses  ==>:fechaExpira"+fechaExpira+"==>fechaHora:"+fechaHora);
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin ses");
		
	}

	@Override
	public SessionChofer getSessionByToken(SessionChofer session) throws Exception {
		logger.info("Repository.getSessionByToken init");
		List <SessionChofer> lista = null;
		SessionChofer bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO, DEVICE,DEVICETYPE,TOKEN, NOMBRE,APELLIDO,EMAIL,TIPDOC,NUMDOC, ");
		sql_find_user.append("BREVETE,TELEFONO, ESTADO, ");
		sql_find_user.append("DATE_FORMAT(FEC_EXPIRA,'%d/%m/%Y %H:%i:%s') FEC_EXP, ");
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("DATE_FORMAT(FEC_MODIFICA,'%d/%m/%Y %H:%i:%s') FEC_MOD, FOTO ");
  		sql_find_user.append("FROM tbl_session_chofer WHERE TOKEN = ? ");
		logger.info("Repository.getSessionByToken::"+session.getToken());
  		Object[] params = new Object[] {
			session.getToken()
		};
    	lista = (List <SessionChofer> )jdbcTemplate.query(sql_find_user.toString(),params, new SessionChoferMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new SessionChofer();
    		bean = lista.get(0);
    	}

		logger.info("getSessionByToken fin");
		return bean;	
	}

	@Override
	public void deleteSession(SessionChofer session) throws Exception {
		
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("DELETE FROM tbl_session_chofer ");
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
