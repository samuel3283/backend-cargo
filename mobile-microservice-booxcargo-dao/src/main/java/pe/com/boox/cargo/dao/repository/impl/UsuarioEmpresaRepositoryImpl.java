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
import pe.com.boox.cargo.dao.repository.UsuarioEmpresaRepository;
import pe.com.boox.cargo.dao.repository.mapper.EmpresaMapper;
import pe.com.boox.cargo.dao.repository.mapper.UsuarioEmpresaMapper;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.UsuarioEmpresa;


@SuppressWarnings("all")
@Repository
public class UsuarioEmpresaRepositoryImpl implements UsuarioEmpresaRepository {

	private final Logger logger = LoggerFactory.getLogger(UsuarioEmpresaRepositoryImpl.class);

	@Autowired
	@Resource(name = "jdbcTemplateMySql")
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("INSERT INTO tbl_usuario_empresa ");
		sql_insert_user.append("(RUC,NOMBRE,APELLIDO,EMAIL,PASSWORD,TIPDOC,NUMDOC,TELEFONO,FEC_REGISTRO) ");
		sql_insert_user.append("VALUES (?,?,?,?,?,?,?,?,?) ");
		logger.info("insertUsuarioEmpresa");
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
                
		Object[] params = new Object[] {
		usuarioEmpresa.getEmpresa().getRuc(), usuarioEmpresa.getNombre(), usuarioEmpresa.getApellido(),
		usuarioEmpresa.getEmail(), usuarioEmpresa.getPassword(), usuarioEmpresa.getTipoDocumento(),
		usuarioEmpresa.getNumDocumento(), usuarioEmpresa.getTelefono(), fechaHora
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin insertUsuarioEmpresa");
				
	}


	@Override
	public UsuarioEmpresa getUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) throws Exception {
		List <UsuarioEmpresa> lista = null;
		UsuarioEmpresa bean = null;
		StringBuilder sql_find_user = new StringBuilder();
/*		sql_find_user.append("SELECT CODIGO,RUC,NOMBRE,APELLIDO,EMAIL,PASSWORD,TIPDOC,NUMDOC,TELEFONO,  "); 
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, PERFIL ");
  		sql_find_user.append("FROM tbl_usuario_empresa WHERE EMAIL=? AND ESTADO=? ");
  */		
		sql_find_user.append("SELECT U.CODIGO,U.RUC,U.NOMBRE,U.APELLIDO,U.EMAIL,U.PASSWORD,U.TIPDOC,U.NUMDOC,U.TELEFONO,  "); 
		sql_find_user.append("DATE_FORMAT(U.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, U.PERFIL, E.RAZON_SOCIAL ");
  		sql_find_user.append("FROM tbl_usuario_empresa U LEFT JOIN tbl_empresa E ON U.RUC = E.RUC ");
  		sql_find_user.append("WHERE U.EMAIL=? AND U.ESTADO=? ");

  		usuarioEmpresa.setEstado(1);
  		Object[] params = new Object[] {
 				usuarioEmpresa.getEmail(), usuarioEmpresa.getEstado()
		};
    	lista = (List <UsuarioEmpresa> )jdbcTemplate.query(sql_find_user.toString(),params, new UsuarioEmpresaMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new UsuarioEmpresa();
    		bean = lista.get(0);
    	}

		return bean;	
	}

	@Override
	public UsuarioEmpresa getLoginUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) throws Exception {
		List <UsuarioEmpresa> lista = null;
		UsuarioEmpresa bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		/*
		sql_find_user.append("SELECT CODIGO,RUC,NOMBRE,APELLIDO,EMAIL,PASSWORD,TIPDOC,NUMDOC,TELEFONO,  "); 
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, PERFIL ");
  		sql_find_user.append("FROM tbl_usuario_empresa WHERE EMAIL=? AND PASSWORD=? AND ESTADO=? ");
  		*/
		sql_find_user.append("SELECT U.CODIGO,U.RUC,U.NOMBRE,U.APELLIDO,U.EMAIL,U.PASSWORD,U.TIPDOC,U.NUMDOC,U.TELEFONO,  "); 
		sql_find_user.append("DATE_FORMAT(U.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, U.PERFIL, E.RAZON_SOCIAL ");
  		sql_find_user.append("FROM tbl_usuario_empresa U LEFT JOIN tbl_empresa E ON U.RUC = E.RUC ");
  		sql_find_user.append("WHERE U.EMAIL=? AND U.PASSWORD=? AND U.ESTADO=? ");
  		
  		
		usuarioEmpresa.setEstado(1);
   		Object[] params = new Object[] {
 				usuarioEmpresa.getEmail(), usuarioEmpresa.getPassword(), usuarioEmpresa.getEstado()
		};
    	lista = (List <UsuarioEmpresa> )jdbcTemplate.query(sql_find_user.toString(),params, new UsuarioEmpresaMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new UsuarioEmpresa();
    		bean = lista.get(0);
    	}

		return bean;	
	}


	/*
	@Override
	public void updEstAtencionConductor(Conductor conductor) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("UPDATE tbl_conductor SET ESTATENCION=?" );
		sql_insert_user.append("WHERE EMAIL=? ");
		logger.info("updateEstadoSession");			        
		Object[] params = new Object[] {
				conductor.getEstadoAtencion(),conductor.getEmail()
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin updateEstadoSession");
					
	}*/
	
}
