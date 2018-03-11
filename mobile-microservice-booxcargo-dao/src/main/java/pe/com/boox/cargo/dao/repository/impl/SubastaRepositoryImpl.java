package pe.com.boox.cargo.dao.repository.impl;

import java.math.BigDecimal;
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
import pe.com.boox.cargo.dao.repository.GuiaRepository;
import pe.com.boox.cargo.dao.repository.SubastaRepository;
import pe.com.boox.cargo.dao.repository.mapper.EmpresaMapper;
import pe.com.boox.cargo.dao.repository.mapper.GuiaMapper;
import pe.com.boox.cargo.dao.repository.mapper.SubastaMapper;
import pe.com.boox.cargo.dao.repository.mapper.SubastaMapper2;
import pe.com.boox.cargo.dao.repository.mapper.SubastaMapperAll;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.GuiaDetalle;
import pe.com.boox.cargo.model.Subasta;


@SuppressWarnings("all")
@Repository
public class SubastaRepositoryImpl implements SubastaRepository {

	private final Logger logger = LoggerFactory.getLogger(SubastaRepositoryImpl.class);

	@Autowired
	@Resource(name = "jdbcTemplateMySql")
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertSubasta(Subasta request) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("INSERT INTO tbl_subasta ");
		//sql_insert_user.append("(CODIGO_GUIA, CODIGO_USUARIO_TRANSPORTE, MONTO, PLACA, BREVETE,FEC_REGISTRO) ");
		//sql_insert_user.append("VALUES (?, ?, ?, ?, ?, STR_TO_DATE(?, '%d/%m/%Y %H:%i:%s') ) ");
		sql_insert_user.append("(CODIGO_GUIA, CODIGO_USUARIO_TRANSPORTE, PLACA, BREVETE,FEC_REGISTRO) ");
		sql_insert_user.append("VALUES (?, ?, ?, ?, STR_TO_DATE(?, '%d/%m/%Y %H:%i:%s') ) ");
		logger.info("insertSubasta::"+request.toString());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHora = sdf.format(dt);
        BigDecimal monto = new BigDecimal("0.00");
        try {
        	monto = new BigDecimal(request.getMonto());
        } catch(Exception e) {
        	monto = new BigDecimal("0.00");
        	logger.info("Error convert bigdecimal: "+e.getMessage());
        }                
		Object[] params = new Object[] {
		request.getCodigoGuia(), request.getUsuarioTransporte().getCodigo(), request.getPlaca(), request.getBrevete(), fechaHora
		};
		//request.getMonto(),
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin insertSubasta");		
	}

	@Override
	public void asignaSubasta(Subasta request) throws Exception {
		
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("UPDATE tbl_subasta SET ESTADO=2 ");
		sql_insert_user.append("WHERE CODIGO=? ");
		logger.info("asignaSubasta::"+request.toString());
		
		Object[] params = new Object[] { request.getCodigo() };
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin asignaSubasta");		
	}

	@Override
	public void deleteSubasta(Subasta request) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("DELETE FROM tbl_subasta ");
		sql_insert_user.append("WHERE CODIGO = ?" );
		logger.info("deleteSubasta::"+request.toString());
                
		Object[] params = new Object[] {	request.getCodigo()		};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin deleteSubasta");		
	}

	@Override
	public List<Subasta> lstSubasta(Subasta request) throws Exception {		
  		logger.info("getSubasta::::::"+request.toString());
		List <Subasta> lista = null;
		Subasta bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO, CODIGO_GUIA, CODIGO_USUARIO_TRANSPORTE, MONTO, DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FECHA_REGISTRO, PLACA, BREVETE ");
		sql_find_user.append("FROM tbl_subasta WHERE CODIGO_GUIA=? AND ESTADO=? ");
		
		request.setEstado(1);
		logger.info("obtener guia:"+sql_find_user);
  		Object[] params = new Object[] {
  				request.getCodigoGuia(),request.getEstado()
		};
    	lista = (List <Subasta> )jdbcTemplate.query(sql_find_user.toString(),params, new SubastaMapper());        	

		return lista;	
		}
	
	@Override
	public List<Subasta> lstGuiaSubasta(Subasta request) throws Exception {		
  		logger.info("lstGuiaSubasta::::::"+request.toString());
		List <Subasta> lista = null;
		Subasta bean = null;
		StringBuilder sql_find_user = new StringBuilder();		
		sql_find_user.append("SELECT S.CODIGO, S.CODIGO_GUIA, S.CODIGO_USUARIO_TRANSPORTE, S.MONTO, DATE_FORMAT(S.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FECHA_REGISTRO, S.PLACA, ");
		sql_find_user.append("EM.RAZON_SOCIAL, EM.RUC ");
		sql_find_user.append("FROM tbl_subasta S ");
		sql_find_user.append("LEFT JOIN tbl_usuario_transporte UT ON UT.CODIGO=S.`CODIGO_USUARIO_TRANSPORTE` ");
		sql_find_user.append("LEFT JOIN tbl_empresa EM ON EM.RUC=UT.RUC ");
		sql_find_user.append("LEFT JOIN tbl_vehiculo_transporte VT ON VT.PLACA=S.PLACA ");
		sql_find_user.append("WHERE S.CODIGO_GUIA=? AND S.ESTADO=? ");
		
		request.setEstado(1);
		logger.info("obtener guia:"+sql_find_user);
  		Object[] params = new Object[] {
  				request.getCodigoGuia(),request.getEstado()
		};
    	lista = (List <Subasta> )jdbcTemplate.query(sql_find_user.toString(),params, new SubastaMapper2());        	

		return lista;	
	}

	@Override
	public Subasta getSubasta(Subasta request) throws Exception {		
  		logger.info("getSubasta::::::"+request.toString());
		List <Subasta> lista = null;
		Subasta bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO, CODIGO_GUIA, CODIGO_USUARIO_TRANSPORTE, MONTO, DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FECHA_REGISTRO, PLACA, BREVETE ");		
		sql_find_user.append("FROM tbl_subasta WHERE CODIGO_GUIA=? AND CODIGO_USUARIO_TRANSPORTE=? AND ESTADO=? ");
		
		request.setEstado(1);
		logger.info("obtener guia:"+sql_find_user);
  		Object[] params = new Object[] {
  				request.getCodigoGuia(),request.getUsuarioTransporte().getCodigo(),request.getEstado()
		};
    	lista = (List <Subasta> )jdbcTemplate.query(sql_find_user.toString(),params, new SubastaMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new Subasta();
    		bean = lista.get(0);
    	}

		return bean;	
		}

	@Override
	public Subasta getSubastaAsignada(Subasta request) throws Exception {		
  		logger.info("getSubastaAsignada::::::"+request.toString());
		List <Subasta> lista = null;
		Subasta bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO, CODIGO_GUIA, CODIGO_USUARIO_TRANSPORTE, MONTO, DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FECHA_REGISTRO, PLACA, BREVETE ");		
		sql_find_user.append("FROM tbl_subasta WHERE CODIGO_GUIA=? AND ESTADO=? ");
		
		logger.info("obtener guia:"+sql_find_user);
  		Object[] params = new Object[] {
  				request.getCodigoGuia(),request.getEstado()
		};
    	lista = (List <Subasta> )jdbcTemplate.query(sql_find_user.toString(),params, new SubastaMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new Subasta();
    		bean = lista.get(0);
    	}

		return bean;	
		}

	@Override
	public Subasta getSubastaByCodigo(Subasta request) throws Exception {		
  		logger.info("getSubastaByCodigo::::::"+request.toString());
		List <Subasta> lista = null;
		Subasta bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CODIGO, CODIGO_GUIA, CODIGO_USUARIO_TRANSPORTE, MONTO, DATE_FORMAT(FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FECHA_REGISTRO, PLACA, BREVETE ");
		sql_find_user.append("FROM tbl_subasta WHERE CODIGO=? ");
		
		request.setEstado(1);
		Object[] params = new Object[] { request.getCodigo()	};
    	lista = (List <Subasta> )jdbcTemplate.query(sql_find_user.toString(),params, new SubastaMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new Subasta();
    		bean = lista.get(0);
    	}

		return bean;	
		}

	
	@Override
	public Integer getCantidadSuabastasGuia(Subasta request) throws Exception {
		Integer bean = 0;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT count(*) ");
		sql_find_user.append("FROM tbl_subasta WHERE CODIGO_GUIA=? AND ESTADO=? ");
		request.setEstado(1);
  		Object[] params = new Object[] {
			request.getCodigoGuia(), request.getEstado()
		};

  		bean = jdbcTemplate.queryForObject(sql_find_user.toString(),params,Integer.class);
  		logger.info("==>bean:"+bean);
  		return bean;

	}

	
	@Override
	public List<Subasta> lstSubastaAsg(Subasta request) throws Exception {		
  		logger.info("lstSubastaAsg::::::"+request.toString());
		List <Subasta> lista = null;
		Subasta bean = null;
		StringBuilder sql_find_user = new StringBuilder();

		
		sql_find_user.append("select s.CODIGO COD_SUB, s.PLACA PLA_SUB, s.BREVETE BRE_SUB, s.MONTO, ");
		sql_find_user.append("u.CODIGO COD_USU, u.RUC, u.NOMBRE NOM_USU, u.APELLIDO APE_USU, ");
		sql_find_user.append("v.CODIGO COD_VEH, v.CODIGO_CHOFER, v.PLACA, ");
		sql_find_user.append("v.MARCA, v.MODELO,v.CAPACIDAD,v.ANIO_FABRICACION,v.ALTO, v.ANCHO, v.LARGO,v.KILOMETRAJE, v.HOROMETRO, ");
		sql_find_user.append("DATE_FORMAT(v.VENCE_SOAT,'%d/%m/%Y') VENC_SOAT, DATE_FORMAT(v.VENCE_RT,'%d/%m/%Y') VENC_RT, DATE_FORMAT(v.MANT_PREVENTIVO,'%d/%m/%Y') MAN_PREVENTIVO, ");
		sql_find_user.append("c.CODIGO COD_CHO, c.NOMBRE NOM_CHO, c.APELLIDO APE_CHO, c.BREVETE, c.TIPO, c.TELEFONO, ");
		sql_find_user.append("c.EMAIL, c.TIPDOC, c.NUMDOC, c.FOTO, c.FOTOBREVETE, c.MULTA, c.INF_GRAVE, c.INF_MUY_GRAVE, ");
		sql_find_user.append("DATE_FORMAT(c.VENCE_BREVETE,'%d/%m/%Y') VENC_BREVETE, e.RAZON_SOCIAL, s.CODIGO_GUIA, s.CODIGO_USUARIO_TRANSPORTE,c.FOTOBREVETE,v.FOTO1 ");
		sql_find_user.append("from tbl_subasta s  ");
		sql_find_user.append("left join tbl_usuario_transporte u on u.codigo = s.codigo_usuario_transporte ");
		sql_find_user.append("left join tbl_empresa e on e.ruc = u.ruc ");
		sql_find_user.append("left join tbl_vehiculo_transporte v on v.placa = s.placa ");
		sql_find_user.append("left join tbl_chofer c on c.brevete = s.brevete ");
		sql_find_user.append("where s.CODIGO_GUIA = ? ");

		request.setEstado(1);
		logger.info("obtener guia:"+sql_find_user);
  		Object[] params = new Object[] {
  				request.getCodigoGuia()
		};
    	lista = (List <Subasta> )jdbcTemplate.query(sql_find_user.toString(),params, new SubastaMapperAll());        	

		return lista;	
		}

	@Override
	public List<Subasta> lstMiSubasta(Subasta request) throws Exception {		
  		logger.info("lstMiSubasta::::::"+request.toString());
		List <Subasta> lista = null;
		Subasta bean = null;
		StringBuilder sql_find_user = new StringBuilder();			
		sql_find_user.append("select s.CODIGO COD_SUB, s.PLACA PLA_SUB, s.BREVETE BRE_SUB, s.MONTO, ");
		sql_find_user.append("u.CODIGO COD_USU, u.RUC, u.NOMBRE NOM_USU, u.APELLIDO APE_USU, e.RAZON_SOCIAL, E.RUC NRO_RUC, ");
		sql_find_user.append("v.CODIGO COD_VEH, v.CODIGO_CHOFER, v.PLACA, ");
		sql_find_user.append("v.MARCA, v.MODELO,v.CAPACIDAD,v.ANIO_FABRICACION,v.ALTO, v.ANCHO, v.LARGO,v.KILOMETRAJE, v.HOROMETRO, ");
		sql_find_user.append("DATE_FORMAT(v.VENCE_SOAT,'%d/%m/%Y') VENC_SOAT, DATE_FORMAT(v.VENCE_RT,'%d/%m/%Y') VENC_RT, DATE_FORMAT(v.MANT_PREVENTIVO,'%d/%m/%Y') MAN_PREVENTIVO, ");
		sql_find_user.append("c.CODIGO COD_CHO, c.NOMBRE NOM_CHO, c.APELLIDO APE_CHO, c.BREVETE, c.TIPO, c.TELEFONO, ");
		sql_find_user.append("c.EMAIL, c.TIPDOC, c.NUMDOC, c.FOTO, c.FOTOBREVETE, c.MULTA, c.INF_GRAVE, c.INF_MUY_GRAVE, ");
		sql_find_user.append("DATE_FORMAT(c.VENCE_BREVETE,'%d/%m/%Y') VENC_BREVETE ");
		sql_find_user.append("from tbl_subasta s ");
		sql_find_user.append("left join tbl_usuario_transporte u on u.codigo = s.codigo_usuario_transporte ");
		sql_find_user.append("left join tbl_empresa e on e.ruc = u.ruc ");
		sql_find_user.append("left join tbl_vehiculo_transporte v on v.placa = s.placa ");
		sql_find_user.append("left join tbl_chofer c on c.brevete = s.brevete ");
		sql_find_user.append("where s.CODIGO_GUIA = ? ");
		
		
		request.setEstado(1);
		logger.info("obtener guia:"+sql_find_user);
  		Object[] params = new Object[] {
  				request.getCodigoGuia(),request.getEstado()
		};
    	lista = (List <Subasta> )jdbcTemplate.query(sql_find_user.toString(),params, new SubastaMapper2());        	

		return lista;	
	}

}
