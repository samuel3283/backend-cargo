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

import pe.com.boox.cargo.dao.repository.ChoferRepository;
import pe.com.boox.cargo.dao.repository.EmpresaRepository;
import pe.com.boox.cargo.dao.repository.RutaRepository;
import pe.com.boox.cargo.dao.repository.mapper.ChoferMapper;
import pe.com.boox.cargo.dao.repository.mapper.EmpresaMapper;
import pe.com.boox.cargo.dao.repository.mapper.GuiaMapper;
import pe.com.boox.cargo.dao.repository.mapper.RutaMapper;
import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.Ruta;


@SuppressWarnings("all")
@Repository
public class RutaRepositoryImpl implements RutaRepository {

	private final Logger logger = LoggerFactory.getLogger(RutaRepositoryImpl.class);

	@Autowired
	@Resource(name = "jdbcTemplateMySql")
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertRuta(Ruta ruta) throws Exception {
		List <Ruta> lista = null;
		Ruta bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("insert into tbl_ruta (CODIGO_GUIA,CODIGO_CHOFER,LAT,LNG,TIPO,NOTA,FEC_REGISTRO) ");
		sql_find_user.append("values (?,?,?,?,?,?,?) ");
		logger.info("insertRuta guia:"+sql_find_user);
		
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);

  		Object[] params = new Object[] {
  				ruta.getCodigoGuia(),ruta.getCodigoChofer(),ruta.getLat(),ruta.getLng(),ruta.getTipo(),ruta.getAlerta(),fechaHora
		};

		jdbcTemplate.update(sql_find_user.toString(), params);

	}

	@Override
	public void insertRutaOff(Ruta ruta) throws Exception {
		List <Ruta> lista = null;
		Ruta bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("insert into tbl_ruta (CODIGO_GUIA,CODIGO_CHOFER,LAT,LNG,TIPO,ALTURA,VELOCIDAD,APROXIMA,FEC_REFERENCIA,FEC_REGISTRO) ");
		sql_find_user.append("values (?,?,?,?,?,?,?,?,?,?) ");
		logger.info("insertRuta guia:"+sql_find_user);
		
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);

  		Object[] params = new Object[] {
  				ruta.getCodigoGuia(),ruta.getCodigoChofer(),ruta.getLat(),ruta.getLng(),ruta.getTipo(),
  				ruta.getAltura(),ruta.getVelocidad(),ruta.getAproximacion(),ruta.getFechaReferencia(),
  				fechaHora
		};

		jdbcTemplate.update(sql_find_user.toString(), params);

	}

	@Override
	public Ruta getRuta(Integer codigoGuia, int min, int max) throws Exception {
		List <Ruta> lista = null;
		Ruta bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO, CODIGO_GUIA,CODIGO_CHOFER,LAT,LNG,TIPO, ");
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %h:%i %p') FECHA_REGISTRO, NOTA ");
		sql_find_user.append("from tbl_ruta where CODIGO_GUIA=? ");
		logger.info("getRuta guia:"+sql_find_user);
		
  		Object[] params = new Object[] {
  				codigoGuia
		};

  		lista = (List <Ruta> )jdbcTemplate.query(sql_find_user.toString(), params, new RutaMapper());
    	if(lista!=null && lista.size()>0){
    		bean = new Ruta();
    		bean = lista.get(0);
    	}

  		return bean;
	}

	@Override
	public List<Ruta> listRuta(Integer codigoGuia, int min, int max) throws Exception {
		logger.info("listRuta ==>codigoGuia:"+codigoGuia);
		List <Ruta> lista = null;
		Ruta bean = null;
		StringBuilder sql_find_user = new StringBuilder();
/*		sql_find_user.append("SELECT CODIGO, CODIGO_GUIA,CODIGO_CHOFER,LAT,LNG,TIPO, ");
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %h:%i %p') FECHA_REGISTRO, ");
//		sql_find_user.append(" concat(concat(concat(concat('{ location: {lat:',LAT),', lng:'),LNG),'} }') position ");
		sql_find_user.append(" concat(concat(concat(concat('{ location'': {''lat'':',LAT),', ''lng'':'),LNG),'} }') position ");
		sql_find_user.append("from tbl_ruta where CODIGO_GUIA=? ");
		sql_find_user.append("limit ?,? ");
*/
		sql_find_user.append("SELECT CODIGO, CODIGO_GUIA,CODIGO_CHOFER,LAT,LNG,TIPO, ");
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %h:%i %p') FECHA_REGISTRO, NOTA ");
		sql_find_user.append("from tbl_ruta where CODIGO_GUIA=? ");
		sql_find_user.append("limit ?,? ");
		logger.info("listRuta guia:"+sql_find_user);
		
  		Object[] params = new Object[] {
  				codigoGuia,min,max
		};

  		lista = (List <Ruta> )jdbcTemplate.query(sql_find_user.toString(), params, new RutaMapper());
  		
		return lista;	
	}

	@Override
	public Integer cantidad(Integer codigoGuia) throws Exception {
  		logger.info("cantiidad:::"+codigoGuia);
		Integer bean = new Integer(0);
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT count(codigo) as id from tbl_ruta where CODIGO_GUIA = ? ");
  		Object[] params = new Object[] { codigoGuia };

  		bean = jdbcTemplate.queryForObject(sql_find_user.toString(),params,Integer.class);
  		logger.info("==>bean:"+bean);
  		return bean;
	}


	
}
