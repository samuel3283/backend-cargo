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
import pe.com.boox.cargo.dao.repository.mapper.ChoferMapper;
import pe.com.boox.cargo.dao.repository.mapper.EmpresaMapper;
import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.model.VehiculoTransporte;


@SuppressWarnings("all")
@Repository
public class ChoferRepositoryImpl implements ChoferRepository {

	private final Logger logger = LoggerFactory.getLogger(ChoferRepositoryImpl.class);

	@Autowired
	@Resource(name = "jdbcTemplateMySql")
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertChofer(Chofer request) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("INSERT INTO tbl_chofer ");
		sql_insert_user.append("(CODIGO_EMPRESA,NOMBRE,APELLIDO,BREVETE,TIPO,TELEFONO,EMAIL,TIPDOC,NUMDOC,VENCE_BREVETE,MULTA,INF_GRAVE,INF_MUY_GRAVE,FEC_REGISTRO) ");
		sql_insert_user.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		logger.info("insertChofer::"+request.toString());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
                
		Object[] params = new Object[] {
			request.getUsuarioTransporte().getCodigo(),request.getNombre(),request.getApellido(),request.getBrevete(),request.getTipo(),
			request.getTelefono(),request.getEmail(),request.getTipoDocumento(),request.getNumDocumento(),
			request.getExpiracionBrevete(), request.getMulta(), request.getInfraccionGrave(), request.getInfraccionMuyGrave(), fechaHora
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin insertChofer");
				
	}

	@Override
	public Integer getMaxIdChofer(Chofer request) throws Exception {

  		Integer bean = new Integer(0);
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT max(codigo) as id from tbl_chofer ");
		sql_find_user.append("where CODIGO_EMPRESA = ? ");
  		Object[] params = new Object[] { request.getUsuarioTransporte().getCodigo() };
  		bean = jdbcTemplate.queryForObject(sql_find_user.toString(),params,Integer.class);
  		logger.info("==>getMaxIdChofer:"+bean);
  		return bean;	
  	}

	//FOTOBREVETE
	@Override
	public Chofer getChofer(Chofer request) throws Exception {
  		logger.info("getChofer chofer:::::::::::::::::"+request.toString());
		List <Chofer> lista = null;
		Chofer bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO,CODIGO_EMPRESA,NOMBRE,APELLIDO,BREVETE,TIPO,TELEFONO,EMAIL,TIPDOC,NUMDOC, "); 
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, FOTOBREVETE, MULTA, INF_GRAVE, INF_MUY_GRAVE, VENCE_BREVETE ");
  		sql_find_user.append("FROM tbl_chofer WHERE CODIGO=? ");
  		request.setEstado(1);
  		logger.info("obtener getChofer:"+sql_find_user);
  		Object[] params = new Object[] {
  				request.getCodigo()
		};
    	lista = (List <Chofer> )jdbcTemplate.query(sql_find_user.toString(),params, new ChoferMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new Chofer();
    		bean = lista.get(0);
    	}

		return bean;	
	}

	
	@Override
	public Chofer getChoferByDocumento(Chofer request) throws Exception {
  		logger.info("getChofer getChoferByDocumento:::::::::::::::::"+request.toString());
		List <Chofer> lista = null;
		Chofer bean = null;
		StringBuilder sql_find_user = new StringBuilder();
/**/
		sql_find_user.append("SELECT CODIGO,CODIGO_EMPRESA,NOMBRE,APELLIDO,BREVETE,TIPO,TELEFONO,EMAIL,TIPDOC,NUMDOC, "); 
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, FOTOBREVETE, MULTA,INF_GRAVE,INF_MUY_GRAVE,VENCE_BREVETE ");
  		sql_find_user.append("FROM tbl_chofer WHERE NUMDOC=? ");
  		
		/*
		sql_find_user.append("SELECT c.CODIGO,c.CODIGO_EMPRESA,c.NOMBRE,c.APELLIDO,c.BREVETE,c.TIPO,c.TELEFONO,c.EMAIL,c.TIPDOC,c.NUMDOC, "); 
		sql_find_user.append("DATE_FORMAT(c.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, c.FOTOBREVETE, c.MULTA,c.INF_GRAVE,c.INF_MUY_GRAVE,c.VENCE_BREVETE, ");
		sql_find_user.append("v.CODIGO COD_VEHICULO ,v.CODIGO_USUARIO COD_USU,v.PLACA,v.TPROPIEDAD,v.SOAT,v.FOTO1,v.FOTO2,v.FOTO3,  "); 
		sql_find_user.append("v.CATEGORIA, v.TIPO, v.ALTO,v.ANCHO,v.LARGO, ");		
		sql_find_user.append("DATE_FORMAT(v.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG_VEH, ");
		sql_find_user.append("v.MARCA, v.MODELO, v.CAPACIDAD, v.ANIO_FABRICACION ,v.CODIGO_CHOFER, ");
		sql_find_user.append("v.KILOMETRAJE,v.HOROMETRO, DATE_FORMAT(v.MANT_PREVENTIVO,'%Y-%m-%d') MAN_PREVENTIVO, ");
		sql_find_user.append("DATE_FORMAT(v.VENCE_SOAT,'%Y-%m-%d') VENCI_SOAT, DATE_FORMAT(v.VENCE_RT,'%Y-%m-%d') VENCI_RT ");
  		sql_find_user.append("FROM tbl_chofer c ");
  		sql_find_user.append("left join tbl_vehiculo_transporte v on v.codigo_usuario = c.codigo_empresa and v.codigo_chofer =c.codigo ");
  		sql_find_user.append("WHERE c.NUMDOC=? ");
 */
  		/*
  		select c.*, v.* from tbl_chofer c
  		left join tbl_vehiculo_transporte v on v.codigo_usuario = c.codigo_empresa and v.codigo_chofer =c.codigo
  		where c.codigo=5
*/
  		request.setEstado(1);
  		logger.info("obtener getChofer:"+sql_find_user);
  		Object[] params = new Object[] {
  				request.getNumDocumento()
		};
    	//lista = (List <Chofer> )jdbcTemplate.query(sql_find_user.toString(),params, new ChoferVehiculoMapper());
  		lista = (List <Chofer> )jdbcTemplate.query(sql_find_user.toString(),params, new ChoferMapper());
    	if(lista!=null && lista.size()>0){
    		bean = new Chofer();
    		bean = lista.get(0);
    	}

		return bean;	
	}
	
	@Override
	public void updatePasswordNew(Chofer request) throws Exception {
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("update tbl_chofer set ");
		sql_insert_user.append("PASSWORD=? ");
		sql_insert_user.append("WHERE NUMDOC=? ");
		logger.info("updateChofer::"+request.toString());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
                
		Object[] params = new Object[] {
			request.getNombre(),request.getApellido(),request.getBrevete(),request.getTipo(),request.getTelefono(),
			request.getEmail(),request.getTipoDocumento(),request.getNumDocumento(),fechaHora,request.getCodigo()
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin updateChofer");
		
	}

	@Override
	public Integer getChoferNew(Chofer request) throws Exception {

  		Integer bean = new Integer(0);
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT count(codigo) as id from tbl_chofer ");
		sql_find_user.append("WHERE NUMDOC=? AND PASSWORD is null ");
  		Object[] params = new Object[] { request.getNumDocumento() };
  		bean = jdbcTemplate.queryForObject(sql_find_user.toString(),params,Integer.class);
  		logger.info("==>getMaxIdChofer:"+bean);
  		return bean;	
  	}

	@Override
	public Chofer getLoginChofer(Chofer request) throws Exception {
  		logger.info("getChofer chofer:::::::::::::::::"+request.toString());
		List <Chofer> lista = null;
		Chofer bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO,CODIGO_EMPRESA,NOMBRE,APELLIDO,BREVETE,TIPO,TELEFONO,EMAIL,TIPDOC,NUMDOC, "); 
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, FOTOBREVETE,MULTA,INF_GRAVE,INF_MUY_GRAVE,VENCE_BREVETE ");
  		sql_find_user.append("FROM tbl_chofer WHERE NUMDOC=? AND PASSWORD=? ");
  		request.setEstado(1);
  		logger.info("getLoginChofer:"+sql_find_user);
  		Object[] params = new Object[] {
  				request.getNumDocumento(), request.getPassword()
		};
    	lista = (List <Chofer> )jdbcTemplate.query(sql_find_user.toString(),params, new ChoferMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new Chofer();
    		bean = lista.get(0);
    	}

		return bean;	
	}

	@Override
	public List<Chofer> listChofer(Chofer request) throws Exception {
  		logger.info("listChofer chofer:::::::::::::::::"+request.toString());
		List <Chofer> lista = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO,CODIGO_EMPRESA,NOMBRE,APELLIDO,BREVETE,TIPO,TELEFONO,EMAIL,TIPDOC,NUMDOC, "); 
		sql_find_user.append("DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, FOTOBREVETE, MULTA,INF_GRAVE,INF_MUY_GRAVE,VENCE_BREVETE ");
  		sql_find_user.append("FROM tbl_chofer WHERE CODIGO_EMPRESA=? AND ESTADO=? ");
  		request.setEstado(1);
  		logger.info("listChofer:"+sql_find_user);
  		Object[] params = new Object[] {
  				request.getCodigoUsuarioTransporte(), request.getEstado()
		};
    	lista = (List <Chofer> )jdbcTemplate.query(sql_find_user.toString(),params, new ChoferMapper());        	
    	return lista;	
	}


	@Override
	public void updateChofer(Chofer request) throws Exception {
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("update tbl_chofer set ");
		sql_insert_user.append("NOMBRE=?, APELLIDO=?, BREVETE=?, TIPO=?, TELEFONO=?, ");
		sql_insert_user.append("EMAIL=?, TIPDOC=?, NUMDOC=?, FEC_MODIFICA=?, ");
		sql_insert_user.append("MULTA=?, INF_GRAVE=?, INF_MUY_GRAVE=?, VENCE_BREVETE=? ");
		sql_insert_user.append("WHERE CODIGO=? ");
		logger.info("updateChofer::"+request.toString());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
                
		Object[] params = new Object[] {
			request.getNombre(),request.getApellido(),request.getBrevete(),request.getTipo(),request.getTelefono(),
			request.getEmail(),request.getTipoDocumento(),request.getNumDocumento(),fechaHora,
			request.getMulta(), request.getInfraccionGrave(), request.getInfraccionMuyGrave(),
			request.getExpiracionBrevete(), request.getCodigo()
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin updateChofer");
		
	}

	@Override
	public void updateAdjunto(Chofer request, String foto) throws Exception {
		
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("update tbl_chofer set ");
		sql_insert_user.append("FOTOBREVETE = ? ");
		sql_insert_user.append("where CODIGO=? ");
				  
		logger.info("updateAdjunto==>"+sql_insert_user);                
		logger.info("==>foto:"+foto+"==>getCodigo:"+request.getCodigo()+"==>foto:"+foto);                
		Object[] params = new Object[] { foto, request.getCodigo()
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
	}

	
	@Override
	public void updateOlvido(Chofer request) throws Exception {
	      logger.info("==>updateOlvido.....");
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("UPDATE tbl_chofer ");
		sql_insert_user.append("SET OLVIDO = ? ");
		sql_insert_user.append("WHERE NUMDOC = ? ");
		Object[] params = new Object[] {
		request.getOlvido(), request.getNumDocumento()
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin updateOlvido");
	}
	
	@Override
	public void updatePassword(Chofer request) throws Exception {
	      logger.info("==>updatePassword.....");
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("UPDATE tbl_chofer ");
		sql_insert_user.append("SET PASSWORD = ? ");
		sql_insert_user.append("WHERE NUMDOC = ? ");
		Object[] params = new Object[] {
		request.getPassword(), request.getNumDocumento()
		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin updatePassword");
	}
}
