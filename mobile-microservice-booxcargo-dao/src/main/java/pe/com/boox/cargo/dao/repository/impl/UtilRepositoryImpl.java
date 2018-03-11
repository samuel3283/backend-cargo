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
import pe.com.boox.cargo.dao.repository.UtilRepository;
import pe.com.boox.cargo.dao.repository.VehiculoTransporteRepository;
import pe.com.boox.cargo.dao.repository.mapper.BeanUtilMapper;
import pe.com.boox.cargo.dao.repository.mapper.DepartamentoMapper;
import pe.com.boox.cargo.dao.repository.mapper.DistritoMapper;
import pe.com.boox.cargo.dao.repository.mapper.EmpresaMapper;
import pe.com.boox.cargo.dao.repository.mapper.ProvinciaMapper;
import pe.com.boox.cargo.dao.repository.mapper.TipoCargaMapper;
import pe.com.boox.cargo.dao.repository.mapper.UsuarioTransporteMapper;
import pe.com.boox.cargo.dao.repository.mapper.VehiculoTransporteMapper;
import pe.com.boox.cargo.model.BeanUtil;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.model.VehiculoTransporte;


@SuppressWarnings("all")
@Repository
public class UtilRepositoryImpl implements UtilRepository {

	private final Logger logger = LoggerFactory.getLogger(UtilRepositoryImpl.class);

	@Autowired
	@Resource(name = "jdbcTemplateMySql")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insertTipoCarga(BeanUtil request,Integer idVehiculo) throws Exception {
		
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("INSERT INTO tbl_vehiculo_tipocarga ");
		sql_insert_user.append("(CODIGO_VEHICULO,TIPOCARGA) ");
		sql_insert_user.append("VALUES (?,?) ");
		logger.info("insertTipoCarga");		
		Object[] params = new Object[] { idVehiculo, request.getCodigo()	};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin insertTipoCarga");
	}

	@Override
	public List<BeanUtil> listTipoCarga(Integer request) throws Exception {
		List <BeanUtil> lista = null;
		BeanUtil bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO,CODIGO_VEHICULO,TIPOCARGA ");
  		sql_find_user.append("FROM tbl_vehiculo_tipocarga WHERE CODIGO_VEHICULO = ? ");
  		Object[] params = new Object[] { request };
    	lista = (List <BeanUtil> )jdbcTemplate.query(sql_find_user.toString(),params, new TipoCargaMapper());        	

    	return lista;	
	}

	@Override
	public void deleteTipoCarga(Integer request)  throws Exception {
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("delete from tbl_vehiculo_tipocarga ");
		sql_insert_user.append("where CODIGO_VEHICULO = ? ");
		Object[] params = new Object[] { request };
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin deleteTipoCarga");
  	}

	
	@Override
	public List<BeanUtil> listDepartamento() throws Exception {
		List <BeanUtil> lista = null;
		BeanUtil bean = null;
		Integer estado = 1;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("select CODIGO,DESCRIPCION ");
  		sql_find_user.append("from tbl_departamento where estado = ? ");
  		Object[] params = new Object[] { estado };
    	lista = (List <BeanUtil> )jdbcTemplate.query(sql_find_user.toString(),params, new DepartamentoMapper());        	

    	return lista;	
	}

	@Override
	public List<BeanUtil> listProvincia(String departamento) throws Exception {
		List <BeanUtil> lista = null;
		BeanUtil bean = null;
		Integer estado = 1;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("select CODIGO,DESCRIPCION ");
  		sql_find_user.append("from tbl_provincia where departamento = ? AND estado = ? ");
  		Object[] params = new Object[] { departamento, estado };
    	lista = (List <BeanUtil> )jdbcTemplate.query(sql_find_user.toString(),params, new ProvinciaMapper());        	

    	return lista;	
	}

	@Override
	public List<BeanUtil> listDistrito(String departamento, String provincia) throws Exception {
		List <BeanUtil> lista = null;
		BeanUtil bean = null;
		Integer estado = 1;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("select CODIGO,DESCRIPCION ");
  		sql_find_user.append("from tbl_distrito where departamento = ? AND provincia = ? ");
  		Object[] params = new Object[] { departamento, provincia };
    	lista = (List <BeanUtil> )jdbcTemplate.query(sql_find_user.toString(),params, new DistritoMapper());        	

    	return lista;	
	}

}
