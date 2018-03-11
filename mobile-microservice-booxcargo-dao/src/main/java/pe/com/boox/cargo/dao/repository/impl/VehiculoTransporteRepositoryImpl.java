package pe.com.boox.cargo.dao.repository.impl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
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
import pe.com.boox.cargo.dao.repository.VehiculoTransporteRepository;
import pe.com.boox.cargo.dao.repository.mapper.EmpresaMapper;
import pe.com.boox.cargo.dao.repository.mapper.UsuarioTransporteMapper;
import pe.com.boox.cargo.dao.repository.mapper.VehiculoTransporteMapper;
import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.model.VehiculoTransporte;


@SuppressWarnings("all")
@Repository
public class VehiculoTransporteRepositoryImpl implements VehiculoTransporteRepository {

	private final Logger logger = LoggerFactory.getLogger(VehiculoTransporteRepositoryImpl.class);

	@Autowired
	@Resource(name = "jdbcTemplateMySql")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insertVehiculoTransporte(VehiculoTransporte request) throws Exception {
		
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("INSERT INTO tbl_vehiculo_transporte ");
		sql_insert_user.append("(CODIGO_USUARIO,PLACA,TIPO,CATEGORIA,ALTO,ANCHO,LARGO,FEC_REGISTRO,MARCA, MODELO, CAPACIDAD, ANIO_FABRICACION, ");
		sql_insert_user.append("KILOMETRAJE,HOROMETRO, MANT_PREVENTIVO,VENCE_SOAT,VENCE_RT) ");
		sql_insert_user.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?, '%Y-%m-%d'), STR_TO_DATE(?, '%Y-%m-%d'), STR_TO_DATE(?, '%Y-%m-%d') ) ");
		//sql_insert_user.append("(CODIGO_USUARIO,PLACA,TPROPIEDAD,SOAT,FOTO1,FOTO2,FOTO3,FEC_REGISTRO) ");
		//sql_insert_user.append("VALUES (?,?,?,?,?,?,?,?) ");		

		logger.info("inserVehiculoTransporte");
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
                
		Object[] params = new Object[] {
		request.getUsuarioTransporte().getCodigo(), request.getPlaca(), request.getTipo(),
		request.getCategoria(), request.getAlto(), request.getAncho(), request.getLargo(), fechaHora,
		request.getMarca(), request.getModelo(), request.getCapacidad(),request.getAnio(),
		request.getKilometraje(), request.getHorometro(),
		request.getMantenimientoPreventivo(),request.getVencimientoSoat(), request.getVencimientoRt()
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin insertUsuarioTransporte");
				
	}

	@Override
	public void updateVehiculoTransporte(VehiculoTransporte request) throws Exception {
		
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("UPDATE tbl_vehiculo_transporte SET ");
		sql_insert_user.append("TIPO=?, CATEGORIA=?, ALTO=?, ANCHO=?, LARGO=?, FEC_MODIFICA=?, ");
		sql_insert_user.append("MARCA=?, MODELO=?, CAPACIDAD=?, ANIO_FABRICACION=?, ");
		sql_insert_user.append("KILOMETRAJE=?, HOROMETRO=?, MANT_PREVENTIVO=?, VENCE_SOAT=?, VENCE_RT=?  ");
		sql_insert_user.append("WHERE CODIGO=? ");
				
		logger.info("updateVehiculoTransporte");
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
                
		Object[] params = new Object[] {
		request.getTipo(), request.getCategoria(), request.getAlto(), request.getAncho(), request.getLargo(), 
		fechaHora, 	request.getMarca(), request.getModelo(), request.getCapacidad(),request.getAnio(),
		request.getKilometraje(), request.getHorometro(),
		request.getMantenimientoPreventivo(),request.getVencimientoSoat(), request.getVencimientoRt(),
		request.getCodigo()
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin updateVehiculoTransporte");
				
	}

	@Override
	public void asignaChofer(VehiculoTransporte request) throws Exception {
		
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("UPDATE tbl_vehiculo_transporte SET ");
		sql_insert_user.append("CODIGO_CHOFER=? ");
		sql_insert_user.append("WHERE CODIGO=? ");
				
		logger.info("asignaChofer");
                
		Object[] params = new Object[] {
		request.getChofer().getCodigo(), request.getCodigo()
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin asignaChofer");
				
	}

	public void updateAdjuntoVehiculoTransporte(VehiculoTransporte request, String foto, String tipo) throws Exception {
		
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("update tbl_vehiculo_transporte set ");
		if(tipo.equals("TARJETA"))
		sql_insert_user.append("TPROPIEDAD = ? ");
		if(tipo.equals("SOAT"))
		sql_insert_user.append("SOAT = ? ");
		if(tipo.equals("FOTO1"))
		sql_insert_user.append("FOTO1 = ? ");
		sql_insert_user.append("where CODIGO=? AND CODIGO_USUARIO=? ");
				  
		logger.info("updateAdjuntoVehiculoTransporte==>"+sql_insert_user);                
		logger.info("==>foto:"+foto+"==>getCodigo:"+request.getCodigo()+"==>getUsuarioTransporte().getCodigo():"+request.getUsuarioTransporte().getCodigo()+
				"==>tipo:"+tipo);                
		Object[] params = new Object[] { foto,
		request.getCodigo(), request.getUsuarioTransporte().getCodigo()
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
	}

	@Override
	public VehiculoTransporte getVehiculoTransporte(VehiculoTransporte request) throws Exception {
		List <VehiculoTransporte> lista = null;
		VehiculoTransporte bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO,CODIGO_USUARIO,PLACA,TPROPIEDAD,SOAT,FOTO1,FOTO2,FOTO3,  "); 
		sql_find_user.append("CATEGORIA, TIPO, ALTO,ANCHO,LARGO, ");		
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("MARCA, MODELO, CAPACIDAD, ANIO_FABRICACION ,CODIGO_CHOFER, ");
		sql_find_user.append("KILOMETRAJE,HOROMETRO, DATE_FORMAT(MANT_PREVENTIVO,'%Y-%m-%d') MAN_PREVENTIVO, ");
		sql_find_user.append("DATE_FORMAT(VENCE_SOAT,'%Y-%m-%d') VENCI_SOAT, DATE_FORMAT(VENCE_RT,'%Y-%m-%d') VENCI_RT ");
		
		sql_find_user.append("FROM tbl_vehiculo_transporte WHERE CODIGO = ? AND ESTADO = ? ");
  		request.setEstado(1);
  		Object[] params = new Object[] {
  				request.getCodigo(), request.getEstado()
		};
    	lista = (List <VehiculoTransporte> )jdbcTemplate.query(sql_find_user.toString(),params, new VehiculoTransporteMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new VehiculoTransporte();
    		bean = lista.get(0);
    	}

		return bean;	
	}


	@Override
	public VehiculoTransporte getVehiculoByUserAndPlaca(VehiculoTransporte request) throws Exception {
		List <VehiculoTransporte> lista = null;
		VehiculoTransporte bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO,CODIGO_USUARIO,PLACA,TPROPIEDAD,SOAT,FOTO1,FOTO2,FOTO3,  "); 
		sql_find_user.append("CATEGORIA, TIPO, ALTO,ANCHO,LARGO, ");		
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("MARCA, MODELO, CAPACIDAD, ANIO_FABRICACION ,CODIGO_CHOFER, ");
		sql_find_user.append("KILOMETRAJE,HOROMETRO, DATE_FORMAT(MANT_PREVENTIVO,'%Y-%m-%d') MAN_PREVENTIVO, ");
		sql_find_user.append("DATE_FORMAT(VENCE_SOAT,'%Y-%m-%d') VENCI_SOAT, DATE_FORMAT(VENCE_RT,'%Y-%m-%d') VENCI_RT ");
		sql_find_user.append("FROM tbl_vehiculo_transporte WHERE CODIGO_USUARIO = ? AND PLACA = ? ");
  		request.setEstado(1);
  		Object[] params = new Object[] {
  				request.getUsuarioTransporte().getCodigo(), request.getPlaca()
		};
    	lista = (List <VehiculoTransporte> )jdbcTemplate.query(sql_find_user.toString(),params, new VehiculoTransporteMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new VehiculoTransporte();
    		bean = lista.get(0);
    	}

		return bean;	
	}

	@Override
	public VehiculoTransporte getVehiculoByChofer(Chofer request) throws Exception {
		List <VehiculoTransporte> lista = null;
		VehiculoTransporte bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT v.CODIGO,v.CODIGO_USUARIO,v.PLACA,v.TPROPIEDAD,SOAT,v.FOTO1,v.FOTO2,v.FOTO3,  "); 
		sql_find_user.append("v.CATEGORIA, v.TIPO, v.ALTO,v.ANCHO,v.LARGO, ");		
		sql_find_user.append("DATE_FORMAT(v.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("v.MARCA, v.MODELO, v.CAPACIDAD, v.ANIO_FABRICACION ,v.CODIGO_CHOFER, ");
		sql_find_user.append("v.KILOMETRAJE,v.HOROMETRO, DATE_FORMAT(v.MANT_PREVENTIVO,'%Y-%m-%d') MAN_PREVENTIVO, ");
		sql_find_user.append("DATE_FORMAT(v.VENCE_SOAT,'%Y-%m-%d') VENCI_SOAT, DATE_FORMAT(v.VENCE_RT,'%Y-%m-%d') VENCI_RT ");
		sql_find_user.append("FROM tbl_vehiculo_transporte v WHERE v.CODIGO_USUARIO = ? AND v.CODIGO_CHOFER = ? ");
  		request.setEstado(1);
  		Object[] params = new Object[] {
  				request.getCodigoUsuarioTransporte(), request.getCodigo()
		};
    	lista = (List <VehiculoTransporte> )jdbcTemplate.query(sql_find_user.toString(),params, new VehiculoTransporteMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new VehiculoTransporte();
    		bean = lista.get(0);
    	}

		return bean;	
	}


	@Override
	public List<VehiculoTransporte> listVehiculoTransporte(VehiculoTransporte request) throws Exception {
		List <VehiculoTransporte> lista = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO,CODIGO_USUARIO,PLACA,TPROPIEDAD,SOAT,FOTO1,FOTO2,FOTO3,  "); 
		sql_find_user.append("CATEGORIA, TIPO, ALTO,ANCHO,LARGO, ");		
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("MARCA, MODELO, CAPACIDAD, ANIO_FABRICACION, CODIGO_CHOFER, ");
		sql_find_user.append("KILOMETRAJE,HOROMETRO, DATE_FORMAT(MANT_PREVENTIVO,'%Y-%m-%d') MAN_PREVENTIVO, ");
		sql_find_user.append("DATE_FORMAT(VENCE_SOAT,'%Y-%m-%d') VENCI_SOAT, DATE_FORMAT(VENCE_RT,'%Y-%m-%d') VENCI_RT ");
		sql_find_user.append("FROM tbl_vehiculo_transporte WHERE CODIGO_USUARIO = ? AND ESTADO = ? ");
  		request.setEstado(1);
  		Object[] params = new Object[] {
  				request.getUsuarioTransporte().getCodigo(), request.getEstado()
		};
    	lista = (List <VehiculoTransporte>)jdbcTemplate.query(sql_find_user.toString(),params, new VehiculoTransporteMapper());        	
    	
    	return lista;	
	}

	@Override
	public Integer getMaxIdVehiculoTransporte(VehiculoTransporte request) throws Exception {

  		Integer bean = new Integer(0);
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT max(codigo) as id from tbl_vehiculo_transporte ");
		sql_find_user.append("where CODIGO_USUARIO = ? ");
		//logger.info("getMaxIdVehiculoTransporte guia:"+sql_find_user);
  		Object[] params = new Object[] { request.getUsuarioTransporte().getCodigo() };
  		bean = jdbcTemplate.queryForObject(sql_find_user.toString(),params,Integer.class);
  		//bean = jdbcTemplate.queryForObject(sql_find_user.toString(),Integer.class);
  		logger.info("==>getMaxIdVehiculoTransporte:"+bean);
  		return bean;	
  	}
	
	@Override
	public VehiculoTransporte getVehiculoByGuia(Guia request) throws Exception {
			List <VehiculoTransporte> lista = null;
			VehiculoTransporte bean = null;
			StringBuilder sql_find_user = new StringBuilder();
			sql_find_user.append("SELECT V.CODIGO,V.CODIGO_USUARIO,V.PLACA,V.TPROPIEDAD,V.SOAT,V.FOTO1,V.FOTO2,V.FOTO3,  "); 
			sql_find_user.append("V.CATEGORIA, V.TIPO, V.ALTO,V.ANCHO,V.LARGO, ");		
			sql_find_user.append("DATE_FORMAT(V.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
			sql_find_user.append("V.MARCA, V.MODELO, V.CAPACIDAD, V.ANIO_FABRICACION ,V.CODIGO_CHOFER, ");
			sql_find_user.append("V.KILOMETRAJE,V.HOROMETRO, DATE_FORMAT(V.MANT_PREVENTIVO,'%Y-%m-%d') MAN_PREVENTIVO, ");
			sql_find_user.append("DATE_FORMAT(V.VENCE_SOAT,'%Y-%m-%d') VENCI_SOAT, DATE_FORMAT(V.VENCE_RT,'%Y-%m-%d') VENCI_RT ");
			
			sql_find_user.append("from tbl_guia_carga G  ");
			sql_find_user.append("inner join tbl_subasta S on S.codigo_guia=G.codigo and S.estado=2 ");
			sql_find_user.append("inner join tbl_vehiculo_transporte V on V.placa=S.placa  ");
			sql_find_user.append("where G.codigo = ? ");
					
	  		request.setEstado(1);
	  		Object[] params = new Object[] {
	  				request.getCodigo()
			};
	    	lista = (List <VehiculoTransporte> )jdbcTemplate.query(sql_find_user.toString(),params, new VehiculoTransporteMapper());
	    	if(lista!=null && lista.size()>0){
	    		bean = new VehiculoTransporte();
	    		bean = lista.get(0);
	    	}

			return bean;	
		}
	
}
