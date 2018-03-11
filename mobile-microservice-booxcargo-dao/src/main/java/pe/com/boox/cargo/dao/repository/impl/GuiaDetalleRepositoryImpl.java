package pe.com.boox.cargo.dao.repository.impl;

import java.math.BigDecimal;
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
import pe.com.boox.cargo.dao.repository.GuiaDetalleRepository;
import pe.com.boox.cargo.dao.repository.GuiaRepository;
import pe.com.boox.cargo.dao.repository.mapper.EmpresaMapper;
import pe.com.boox.cargo.dao.repository.mapper.GuiaDetalleMapper;
import pe.com.boox.cargo.dao.repository.mapper.GuiaMapper;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.GuiaDetalle;


@SuppressWarnings("all")
@Repository
public class GuiaDetalleRepositoryImpl implements GuiaDetalleRepository {

	private final Logger logger = LoggerFactory.getLogger(GuiaDetalleRepositoryImpl.class);

	@Autowired
	@Resource(name = "jdbcTemplateMySql")
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertGuiaDetalle(GuiaDetalle guia) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("INSERT INTO tbl_guia_detalle ");
		sql_insert_user.append("(CODIGO_GUIA,CANTIDAD,DESCRIPCION,PESO, ");
		sql_insert_user.append("UNIDAD,COSTO,TIPO_CARGA,FEC_REGISTRO) ");
		sql_insert_user.append("VALUES (?,?,?,?,?,?,?,STR_TO_DATE(?, '%d/%m/%Y %H:%i:%S')) ");
		logger.info("insertGuiaDetalle:::SQL:::"+sql_insert_user);
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHora = sdf.format(dt);
        BigDecimal costo = new BigDecimal("0.00");
        try {
        	costo = new BigDecimal(guia.getCosto());
        } catch(Exception e) {
        	costo = new BigDecimal("0.00");
        	logger.info("Error convert bigdecimal: "+e.getMessage());
        }
        
		logger.info("insertGuiaDetalle::"+guia.toString()+"==>fechaHora:"+fechaHora);
        
		Object[] params = new Object[] {
				guia.getCodigoGuia(),guia.getCantidad(),guia.getDescripcion(),
				guia.getPeso(),guia.getUnidad(),costo,guia.getTipoCarga(),fechaHora
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin insertGuiaDetalle");
				
	}

	@Override
	public void deleteGuiaDetalle(GuiaDetalle guia) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("DELETE FROM tbl_guia_detalle ");
		sql_insert_user.append("WHERE CODIGO_GUIA=? ");
		logger.info("deleteGuiaDetalle:::SQL:::"+sql_insert_user);
        
		Object[] params = new Object[] { guia.getCodigoGuia() };
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin deleteGuiaDetalle");
				
	}

	@Override
	public GuiaDetalle getGuiaDetalle(GuiaDetalle guia) throws Exception {
  		logger.info("getGuia getGuia:::::::::::::::::"+guia.toString());
		List <GuiaDetalle> lista = null;
		GuiaDetalle bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT g.CODIGO, g.CODIGO_GUIA,g.CANTIDAD,");		
		sql_find_user.append("g.DESCRIPCION,g.PESO, g.UNIDAD,COSTO, ");
		sql_find_user.append("DATE_FORMAT(g.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("g.tipo_carga, c.descripcion  ");
		sql_find_user.append("FROM tbl_guia_detalle g ");
		sql_find_user.append("left join tbl_tipo_carga c on c.codigo=g.tipo_carga ");
		sql_find_user.append("where g.CODIGO_GUIA = ? and g.ESTADO = ? ");
		
  		guia.setEstado(1);
  		logger.info("obtener guia:"+sql_find_user);
  		Object[] params = new Object[] {
  				guia.getCodigoGuia(),guia.getEstado()
		};
    	lista = (List <GuiaDetalle> )jdbcTemplate.query(sql_find_user.toString(),params, new GuiaDetalleMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new GuiaDetalle();
    		bean = lista.get(0);
    	}

		return bean;	
	}


	@Override
	public List<GuiaDetalle> listGuiaDetalle(GuiaDetalle guia) throws Exception {
  		logger.info("listGuiaDetalle::::::::::::"+guia.toString());
		List <GuiaDetalle> lista = null;
		GuiaDetalle bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT g.CODIGO, g.CODIGO_GUIA,g.CANTIDAD,");		
		sql_find_user.append("g.DESCRIPCION,g.PESO, g.UNIDAD,COSTO, ");
		sql_find_user.append("DATE_FORMAT(g.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("g.tipo_carga, c.descripcion  ");
		sql_find_user.append("FROM tbl_guia_detalle g ");
		sql_find_user.append("left join tbl_tipo_carga c on c.codigo=g.tipo_carga ");
		sql_find_user.append("where g.CODIGO_GUIA = ? and g.ESTADO = ? ");

  		guia.setEstado(1);
  		logger.info("listGuiaDetalle guia:"+sql_find_user);
  		Object[] params = new Object[] {
  				guia.getCodigoGuia(),guia.getEstado()
		};
  		lista = (List <GuiaDetalle> )jdbcTemplate.query(sql_find_user.toString(), params, new GuiaDetalleMapper());
  		
		return lista;	
	}

}
