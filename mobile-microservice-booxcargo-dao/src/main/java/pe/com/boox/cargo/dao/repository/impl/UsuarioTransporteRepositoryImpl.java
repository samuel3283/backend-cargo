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

import pe.com.boox.cargo.dao.repository.EmpresaRepository;
import pe.com.boox.cargo.dao.repository.UsuarioTransporteRepository;
import pe.com.boox.cargo.dao.repository.mapper.EmpresaMapper;
import pe.com.boox.cargo.dao.repository.mapper.UsuarioTransporteMapper;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.UsuarioTransporte;


@SuppressWarnings("all")
@Repository
public class UsuarioTransporteRepositoryImpl implements UsuarioTransporteRepository {

	private final Logger logger = LoggerFactory.getLogger(UsuarioTransporteRepositoryImpl.class);

	@Autowired
	@Resource(name = "jdbcTemplateMySql")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insertUsuarioTransporte(UsuarioTransporte request) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("INSERT INTO tbl_usuario_transporte ");
		sql_insert_user.append("(RUC,NOMBRE,APELLIDO,EMAIL,PASSWORD,TIPDOC,NUMDOC,TELEFONO,FEC_REGISTRO) ");
		sql_insert_user.append("VALUES (?,?,?,?,?,?,?,?,?) ");
		logger.info("tbl_usuario_transporte");
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
                
		Object[] params = new Object[] {
		request.getEmpresa().getRuc(), request.getNombre(), request.getApellido(),
		request.getEmail(), request.getPassword(), request.getTipoDocumento(),
		request.getNumDocumento(), request.getTelefono(), fechaHora
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin insertUsuarioTransporte");
				
	}

	@Override
	public void updateFotoUsuarioTransporte(UsuarioTransporte request) throws Exception {
	
	      logger.info("==>updateFotoUsuarioTransporte.....");
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("UPDATE tbl_usuario_transporte ");
		sql_insert_user.append("SET FOTO = ? ");
		sql_insert_user.append("WHERE CODIGO = ? ");
		Object[] params = new Object[] {
		request.getFoto(), request.getCodigo()
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin updateUsuarioTransporte");
	}

	@Override
	public UsuarioTransporte getUsuarioTransporte(UsuarioTransporte request) throws Exception {
		List <UsuarioTransporte> lista = null;
		UsuarioTransporte bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO,RUC,NOMBRE,APELLIDO,EMAIL,PASSWORD,TIPDOC,NUMDOC,TELEFONO,  "); 
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, PERFIL,FOTO ");
  		sql_find_user.append("FROM tbl_usuario_transporte WHERE EMAIL=? AND ESTADO=? ");
  		request.setEstado(1);
  		Object[] params = new Object[] {
  				request.getEmail(), request.getEstado()
		};
    	lista = (List <UsuarioTransporte> )jdbcTemplate.query(sql_find_user.toString(),params, new UsuarioTransporteMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new UsuarioTransporte();
    		bean = lista.get(0);
    	}

		return bean;	
	}

	@Override
	public UsuarioTransporte getUsuarioTransporteByEmail(UsuarioTransporte request) throws Exception {
		List <UsuarioTransporte> lista = null;
		UsuarioTransporte bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO,RUC,NOMBRE,APELLIDO,EMAIL,PASSWORD,TIPDOC,NUMDOC,TELEFONO,  "); 
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, PERFIL,FOTO ");
  		sql_find_user.append("FROM tbl_usuario_transporte WHERE EMAIL=? ");
  		request.setEstado(1);
  		Object[] params = new Object[] {	request.getEmail()	};
    	lista = (List <UsuarioTransporte> )jdbcTemplate.query(sql_find_user.toString(),params, new UsuarioTransporteMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new UsuarioTransporte();
    		bean = lista.get(0);
    	}
    	
		return bean;	
	}

	@Override
	public UsuarioTransporte getLoginUsuarioTransporte(UsuarioTransporte request) throws Exception {
		List <UsuarioTransporte> lista = null;
		UsuarioTransporte bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO,RUC,NOMBRE,APELLIDO,EMAIL,PASSWORD,TIPDOC,NUMDOC,TELEFONO,  "); 
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, PERFIL,FOTO ");
  		sql_find_user.append("FROM tbl_usuario_transporte WHERE EMAIL=? AND PASSWORD=? AND ESTADO=? ");
		request.setEstado(1);
   		Object[] params = new Object[] {
   				request.getEmail(), request.getPassword(), request.getEstado()
		};
    	lista = (List <UsuarioTransporte> )jdbcTemplate.query(sql_find_user.toString(),params, new UsuarioTransporteMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new UsuarioTransporte();
    		bean = lista.get(0);
    	}

		return bean;	
	}

	@Override
	public List<UsuarioTransporte> validaCodeEmail(UsuarioTransporte request) throws Exception {
		List <UsuarioTransporte> lista = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO,RUC,NOMBRE,APELLIDO,EMAIL,PASSWORD,TIPDOC,NUMDOC,TELEFONO,  "); 
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, PERFIL,FOTO ");
  		sql_find_user.append("FROM tbl_usuario_transporte WHERE EMAIL=? AND OLVIDO=? ");
		request.setEstado(1);
   		Object[] params = new Object[] {
   				request.getEmail(), request.getOlvido()
		};
    	lista = (List <UsuarioTransporte> )jdbcTemplate.query(sql_find_user.toString(),params, new UsuarioTransporteMapper());        	
		return lista;	
	}

	@Override
	public void updateOlvido(UsuarioTransporte request) throws Exception {
	      logger.info("==>updateOlvido.....");
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("UPDATE tbl_usuario_transporte ");
		sql_insert_user.append("SET OLVIDO = ? ");
		sql_insert_user.append("WHERE EMAIL = ? ");
		Object[] params = new Object[] {
		request.getOlvido(), request.getEmail()
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin updateOlvido");
	}
	
	@Override
	public void updatePassword(UsuarioTransporte request) throws Exception {
	      logger.info("==>updateFotoUsuarioTransporte.....");
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("UPDATE tbl_usuario_transporte ");
		sql_insert_user.append("SET PASSWORD = ? ");
		sql_insert_user.append("WHERE EMAIL = ? ");
		Object[] params = new Object[] {
		request.getPassword(), request.getEmail()
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin updateUsuarioTransporte");
	}


	
}
