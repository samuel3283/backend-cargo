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
import pe.com.boox.cargo.dao.repository.mapper.EmpresaMapper;
import pe.com.boox.cargo.model.Empresa;


@SuppressWarnings("all")
@Repository
public class EmpresaRepositoryImpl implements EmpresaRepository {

	private final Logger logger = LoggerFactory.getLogger(EmpresaRepositoryImpl.class);

	@Autowired
	@Resource(name = "jdbcTemplateMySql")
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertEmpresa(Empresa empresa) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("INSERT INTO tbl_empresa ");
		sql_insert_user.append("(RAZON_SOCIAL,RUC,FEC_REGISTRO) ");
		sql_insert_user.append("VALUES (?,?,?) ");
		logger.info("insertEmpresa::"+empresa.toString());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
                
		Object[] params = new Object[] {
		empresa.getRazonSocial(), empresa.getRuc(), fechaHora
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin insertEmpresa");
				
	}


	@Override
	public Empresa getEmpresa(Empresa empresa) throws Exception {
  		logger.info("getEmpresa empresa:::::::::::::::::"+empresa.toString());
		List <Empresa> lista = null;
		Empresa bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO, RAZON_SOCIAL, RUC, ESTADO,  "); 
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG ");
  		sql_find_user.append("FROM tbl_empresa WHERE RUC=? AND ESTADO=? ");
  		empresa.setEstado(1);
  		logger.info("obtener empresa:"+sql_find_user);
  		Object[] params = new Object[] {
		empresa.getRuc(), empresa.getEstado()
		};
    	lista = (List <Empresa> )jdbcTemplate.query(sql_find_user.toString(),params, new EmpresaMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new Empresa();
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
