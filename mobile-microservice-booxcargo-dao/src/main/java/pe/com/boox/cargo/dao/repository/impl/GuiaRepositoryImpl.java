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
import pe.com.boox.cargo.dao.repository.GuiaRepository;
import pe.com.boox.cargo.dao.repository.mapper.BeanUtilMapper;
import pe.com.boox.cargo.dao.repository.mapper.EmpresaMapper;
import pe.com.boox.cargo.dao.repository.mapper.GuiaMapper;
import pe.com.boox.cargo.dao.repository.mapper.GuiaTiempoMapper;
import pe.com.boox.cargo.dao.repository.mapper.GuiaVerifyMapper;
import pe.com.boox.cargo.model.BeanUtil;
import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.Guia;


@SuppressWarnings("all")
@Repository
public class GuiaRepositoryImpl implements GuiaRepository {

	private final Logger logger = LoggerFactory.getLogger(GuiaRepositoryImpl.class);

	@Autowired
	@Resource(name = "jdbcTemplateMySql")
	private JdbcTemplate jdbcTemplate;

	@Override
	public Integer insertaGuia(Guia guia) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		/*
		sql_insert_user.append("INSERT INTO tbl_guia_carga ");
		sql_insert_user.append("(RUC, NROGUIA,FEC_EMISION,FEC_FIN_TRASLADO,FEC_INI_TRASLADO,VIATIPO_ORI, ");
		sql_insert_user.append("VIANOMBRE_ORI,NRO_ORI,INTERIOR_ORI,ZONA_ORI,DIST_ORI, ");
		sql_insert_user.append("PROV_ORI,DEPT_ORI,VIATIPO_DES,VIANOMBRE_DES,NRO_DES, ");
		sql_insert_user.append("INTERIOR_DES,ZONA_DES,DIST_DES,PROV_DES,DEPT_DES, ");
		sql_insert_user.append("REM_RAZSOCIAL,REM_RUC,REM_TIPDOC,REM_NRODOC,DES_RAZSOCIAL, ");
		sql_insert_user.append("DES_RUC,DES_TIPDOC,DES_NRODOC,FEC_REGISTRO,FLETE) "); //29
		sql_insert_user.append("VALUES (?,?,STR_TO_DATE(?, '%d/%m/%Y %H:%i:%s'),STR_TO_DATE(?, '%d/%m/%Y %H:%i:%s'),STR_TO_DATE(?, '%d/%m/%Y %H:%i:%s'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		*/
		sql_insert_user.append("INSERT INTO tbl_guia_carga ");
		sql_insert_user.append("(RUC, NROGUIA,FEC_EMISION,FEC_FIN_TRASLADO,FEC_INI_TRASLADO,VIATIPO_ORI, ");
		sql_insert_user.append("VIANOMBRE_ORI,NRO_ORI,INTERIOR_ORI,ZONA_ORI,DIST_ORI, ");
		sql_insert_user.append("PROV_ORI,DEPT_ORI,VIATIPO_DES,VIANOMBRE_DES,NRO_DES, ");
		sql_insert_user.append("INTERIOR_DES,ZONA_DES,DIST_DES,PROV_DES,DEPT_DES, ");
		sql_insert_user.append("REM_RAZSOCIAL,REM_RUC,REM_TIPDOC,REM_NRODOC,DES_RAZSOCIAL, ");
		sql_insert_user.append("DES_RUC,DES_TIPDOC,DES_NRODOC,FEC_REGISTRO,FLETE, "); //29
		sql_insert_user.append("REM_CONTACTO,REM_TELEFONO,DES_CONTACTO,DES_TELEFONO, "); //29
		sql_insert_user.append("LAT_ORI,LONG_ORI,LAT_DES,LONG_DES,DIR_ORI,DIR_DES) "); //29
		sql_insert_user.append("VALUES (?,?,STR_TO_DATE(?, '%d/%m/%Y %H:%i:%s'),STR_TO_DATE(?, '%d/%m/%Y %H:%i:%s'),STR_TO_DATE(?, '%d/%m/%Y %H:%i:%s'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		//sql_insert_user.append("VALUES (?,?,STR_TO_DATE(?, '%d/%m/%Y %H:%i:%s'),STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'),STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		
		logger.info("insertGuia::"+guia.toString());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
        BigDecimal flete = new BigDecimal("0.00");
        try {
        	flete = new BigDecimal(guia.getFlete());
        } catch(Exception e) {
        	flete = new BigDecimal("0.00");
        	logger.info("Error convert bigdecimal: "+e.getMessage());
        }
                
		Object[] params = new Object[] {
		guia.getRuc(), guia.getNumeroGuia(),guia.getFechaEmision(),
		guia.getFechaFinTraslado(), guia.getFechaIniTraslado(),guia.getViaTipoOrigen(),
		guia.getViaNombreOrigen(),guia.getNumeroOrigen(),guia.getInteriorOrigen(),guia.getZonaOrigen(),guia.getDistritoOrigen(),
		guia.getProvinciaOrigen(),guia.getDepartamentoOrigen(),guia.getViaTipoDestino(),guia.getViaNombreDestino(),guia.getNumeroDestino(),
		guia.getInteriorDestino(),guia.getZonaDestino(),guia.getDistritoDestino(),guia.getProvinciaDestino(),guia.getDepartamentoDestino(),
		guia.getRazonSocialRemitente(),guia.getRucRemitente(),guia.getTipoDocRemitente(),guia.getNumeroDocRemitente(),guia.getRazonSocialDestinatario(),
		guia.getRucDestinatario(),guia.getTipoDocDestinatario(),guia.getNumeroDocDestinatario(),fechaHora,
		flete, guia.getNombreContactoRemitente(),guia.getTelefonoContactoRemitente(),
		guia.getNombreContactoDestinatario(), guia.getTelefonoContactoDestinatario(),
		guia.getLatOri(),guia.getLngOri(),guia.getLatDes(),guia.getLngDes(),
		guia.getDireccionOrigen(), guia.getDireccionDestino()
		};
		
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin insertGuia");
		logger.info("fin insertGuia");
		Integer valor = getMaxIdGuia(guia);
		logger.info("==>Fin getIdGuia:::"+valor);
		
		return valor;
	}

	@Override
	public Integer updateGuia(Guia guia) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		
		sql_insert_user.append("UPDATE tbl_guia_carga SET ");
		sql_insert_user.append("FEC_FIN_TRASLADO=?, FEC_INI_TRASLADO=?, VIATIPO_ORI=?, ");
		sql_insert_user.append("VIANOMBRE_ORI=?,NRO_ORI=?,INTERIOR_ORI=?,ZONA_ORI=?,DIST_ORI=?, ");
		sql_insert_user.append("PROV_ORI=?,DEPT_ORI=?,VIATIPO_DES=?,VIANOMBRE_DES=?,NRO_DES=?, ");
		sql_insert_user.append("INTERIOR_DES=?,ZONA_DES=?,DIST_DES=?,PROV_DES=?,DEPT_DES=?, ");
		sql_insert_user.append("REM_RAZSOCIAL=?,REM_RUC=?,REM_TIPDOC=?,REM_NRODOC=?,DES_RAZSOCIAL=?, ");
		sql_insert_user.append("DES_RUC=?,DES_TIPDOC=?,DES_NRODOC=?,FEC_MODIFICA=?,FLETE=?, "); 
		sql_insert_user.append("REM_CONTACTO=?,REM_TELEFONO=?,DES_CONTACTO=?,DES_TELEFONO=?, "); 
		
		sql_insert_user.append("LAT_ORI=IFNULL(?,LAT_ORI), LONG_ORI=IFNULL(?,LONG_ORI), ");
		sql_insert_user.append("LAT_DES=IFNULL(?,LAT_DES), LONG_DES=IFNULL(?,LONG_DES), ");
		sql_insert_user.append("DIR_ORI=?, DIR_DES=? "); 
		
		//CASE        WHEN Campo1 = '0' THEN null      END,
		//sql_insert_user.append("LAT_ORI=?, LONG_ORI=?, LAT_DES=?, LONG_DES=? ");
		sql_insert_user.append("WHERE CODIGO=? "); 
		
		logger.info("updateGuia::"+guia.toString());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
        BigDecimal flete = new BigDecimal("0.00");
        try {
        	flete = new BigDecimal(guia.getFlete());
        } catch(Exception e) {
        	flete = new BigDecimal("0.00");
        	logger.info("Error convert bigdecimal: "+e.getMessage());
        }                
		Object[] params = new Object[] {
		guia.getFechaFinTraslado(), guia.getFechaIniTraslado(),guia.getViaTipoOrigen(),
		guia.getViaNombreOrigen(),guia.getNumeroOrigen(),guia.getInteriorOrigen(),guia.getZonaOrigen(),guia.getDistritoOrigen(),
		guia.getProvinciaOrigen(),guia.getDepartamentoOrigen(),guia.getViaTipoDestino(),guia.getViaNombreDestino(),guia.getNumeroDestino(),
		guia.getInteriorDestino(),guia.getZonaDestino(),guia.getDistritoDestino(),guia.getProvinciaDestino(),guia.getDepartamentoDestino(),
		guia.getRazonSocialRemitente(),guia.getRucRemitente(),guia.getTipoDocRemitente(),guia.getNumeroDocRemitente(),guia.getRazonSocialDestinatario(),
		guia.getRucDestinatario(),guia.getTipoDocDestinatario(),guia.getNumeroDocDestinatario(),fechaHora,
		flete, guia.getNombreContactoRemitente(),guia.getTelefonoContactoRemitente(),
		guia.getNombreContactoDestinatario(), guia.getTelefonoContactoDestinatario(),
		guia.getLatOri(), guia.getLngOri(), guia.getLatDes(), guia.getLngDes(), 
		guia.getDireccionOrigen(), guia.getDireccionDestino(), guia.getCodigo()};
		
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin updateGuia");
		
		return guia.getCodigo();
	}

	
	@Override
	public void insertGuia(Guia guia) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		
		sql_insert_user.append("INSERT INTO tbl_guia_carga ");
		sql_insert_user.append("(RUC, NROGUIA,FEC_EMISION,FEC_INI_TRASLADO,VIATIPO_ORI, ");
		sql_insert_user.append("VIANOMBRE_ORI,NRO_ORI,INTERIOR_ORI,ZONA_ORI,DIST_ORI, ");
		sql_insert_user.append("PROV_ORI,DEPT_ORI,VIATIPO_DES,VIANOMBRE_DES,NRO_DES, ");
		sql_insert_user.append("INTERIOR_DES,ZONA_DES,DIST_DES,PROV_DES,DEPT_DES, ");
		sql_insert_user.append("REM_RAZSOCIAL,REM_RUC,REM_TIPDOC,REM_NRODOC,DES_RAZSOCIAL, ");
		sql_insert_user.append("DES_RUC,DES_TIPDOC,DES_NRODOC,FEC_REGISTRO,FLETE) "); //29
		sql_insert_user.append("VALUES (?,?,STR_TO_DATE(?, '%d/%m/%Y'),STR_TO_DATE(?, '%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		logger.info("insertGuia::"+guia.toString());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
        BigDecimal flete = new BigDecimal("0.00");
        try {
        	flete = new BigDecimal(guia.getFlete());
        } catch(Exception e) {
        	flete = new BigDecimal("0.00");
        	logger.info("Error convert bigdecimal: "+e.getMessage());
        }
        
        
		Object[] params = new Object[] {
		guia.getRuc(), guia.getNumeroGuia(),guia.getFechaEmision(),guia.getFechaIniTraslado(),
		guia.getViaTipoOrigen(),
		guia.getViaNombreOrigen(),guia.getNumeroOrigen(),guia.getInteriorOrigen(),guia.getZonaOrigen(),guia.getDistritoOrigen(),
		guia.getProvinciaOrigen(),guia.getDepartamentoOrigen(),guia.getViaTipoDestino(),guia.getViaNombreDestino(),guia.getNumeroDestino(),
		guia.getInteriorDestino(),guia.getZonaDestino(),guia.getDistritoDestino(),guia.getProvinciaDestino(),guia.getDepartamentoDestino(),
		guia.getRazonSocialRemitente(),guia.getRucRemitente(),guia.getTipoDocRemitente(),guia.getNumeroDocRemitente(),guia.getRazonSocialDestinatario(),
		guia.getRucDestinatario(),guia.getTipoDocDestinatario(),guia.getNumeroDocDestinatario(),fechaHora,
		flete};
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("fin insertGuia");
				
	}

	@Override
	public Guia getGuia(Guia guia) throws Exception {
  		logger.info("getGuia getGuia:::::::::::::::::"+guia.toString());
		List <Guia> lista = null;
		Guia bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT G.CODIGO, G.RUC, G.NROGUIA, ");
		sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		sql_find_user.append("G.DES_RAZSOCIAL,G.DES_RUC,G.DES_TIPDOC,G.DES_NRODOC, G.FLETE, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_EMISION,'%d/%m/%Y') FEC_EMI, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_FIN_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("REM_CONTACTO,REM_TELEFONO,DES_CONTACTO,DES_TELEFONO, "); 
		//sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		//sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		//sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		//sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		
		sql_find_user.append("VIO.DESCRIPCION AS DES_VIATIPO_ORI, VID.DESCRIPCION AS DES_VIATIPO_DES, EMP.RAZON_SOCIAL, ");
		sql_find_user.append("trim(ZO.DESCRIPCION) as ZONA_ORI, trim(ZD.DESCRIPCION) AS ZONA_DES, ");
		sql_find_user.append("DIO.DESCRIPCION AS DIST_ORI, DID.DESCRIPCION AS DIST_DES, ");
		sql_find_user.append("PO.DESCRIPCION AS PROV_ORI, PD.DESCRIPCION AS PROV_DES, ");
		sql_find_user.append("DO.DESCRIPCION AS DEPT_ORI, DD.DESCRIPCION AS DEPT_DES, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y') FECHA_INI_TRA, DATE_FORMAT(G.FEC_INI_TRASLADO,'%h:%i %p') HORA_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y') FECHA_FIN_TRA, DATE_FORMAT(G.FEC_FIN_TRASLADO,'%h:%i %p') HORA_FIN_TRA, ");
		sql_find_user.append("PAR.VAL STATUS, G.ESTADO, G.LAT_ORI, G.LONG_ORI, G.LAT_DES, G.LONG_DES, ");
		sql_find_user.append("G.DIR_ORI,G.DIR_DES ");
		sql_find_user.append("FROM tbl_guia_carga G ");
		sql_find_user.append("left join tbl_departamento DO on G.dept_ori = DO.codigo ");
		sql_find_user.append("left join tbl_departamento DD on G.dept_des = DD.codigo ");
		sql_find_user.append("left join tbl_provincia PO on G.prov_ori = PO.codigo AND PO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_provincia PD on G.prov_des = PD.codigo AND PD.departamento = G.dept_des ");
		sql_find_user.append("left join tbl_distrito DIO on G.dist_ori = DIO.codigo AND DIO.provincia = G.prov_ori AND DIO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_distrito DID on G.dist_des = DID.codigo AND DID.provincia = G.prov_des AND DID.departamento = G.dept_des ");
		
		sql_find_user.append("left join tbl_zona ZO on G.ZONA_ORI = ZO.codigo ");
		sql_find_user.append("left join tbl_zona ZD on G.ZONA_DES = ZD.codigo ");
		
		sql_find_user.append("left join tbl_via VIO on G.VIATIPO_ORI = VIO.codigo ");
		sql_find_user.append("left join tbl_via VID on G.VIATIPO_DES = VID.codigo ");
		
		sql_find_user.append("left join tbl_empresa EMP on G.RUC = EMP.RUC ");
		sql_find_user.append("left join tbl_parametros PAR on PAR.TIPO='ESTGUIA' AND PAR.COD = G.ESTADO ");
		sql_find_user.append("where G.CODIGO = ? ");

  		logger.info("obtener guia:"+sql_find_user);
  		Object[] params = new Object[] {
  		guia.getCodigo()
		};
    	lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(),params, new GuiaMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new Guia();
    		bean = lista.get(0);
    	}

		return bean;	
	}


	@Override
	public List<Guia> listGuia(Guia guia) throws Exception {
  		logger.info("listGuia getGuia:::::::::::::::::"+guia.toString());
		List <Guia> lista = null;
		Guia bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT G.CODIGO, G.RUC, G.NROGUIA, ");
		sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		sql_find_user.append("G.DES_RAZSOCIAL,G.DES_RUC,G.DES_TIPDOC,G.DES_NRODOC, G.FLETE, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_EMISION,'%d/%m/%Y') FEC_EMI, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_FIN_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("REM_CONTACTO,REM_TELEFONO,DES_CONTACTO,DES_TELEFONO, "); 
		//sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		//sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		//sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		//sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		
		sql_find_user.append("VIO.DESCRIPCION AS DES_VIATIPO_ORI, VID.DESCRIPCION AS DES_VIATIPO_DES, EMP.RAZON_SOCIAL, ");
		sql_find_user.append("trim(ZO.DESCRIPCION) as ZONA_ORI, trim(ZD.DESCRIPCION) AS ZONA_DES, ");
		sql_find_user.append("DIO.DESCRIPCION AS DIST_ORI, DID.DESCRIPCION AS DIST_DES, ");
		sql_find_user.append("PO.DESCRIPCION AS PROV_ORI, PD.DESCRIPCION AS PROV_DES, ");
		sql_find_user.append("DO.DESCRIPCION AS DEPT_ORI, DD.DESCRIPCION AS DEPT_DES, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y') FECHA_INI_TRA, DATE_FORMAT(G.FEC_INI_TRASLADO,'%h:%i %p') HORA_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y') FECHA_FIN_TRA, DATE_FORMAT(G.FEC_FIN_TRASLADO,'%h:%i %p') HORA_FIN_TRA, ");
		sql_find_user.append("PAR.VAL STATUS, G.ESTADO, G.LAT_ORI, G.LONG_ORI, G.LAT_DES, G.LONG_DES, ");
		sql_find_user.append("G.DIR_ORI,G.DIR_DES ");

		sql_find_user.append("FROM tbl_guia_carga G ");
		sql_find_user.append("left join tbl_departamento DO on G.dept_ori = DO.codigo ");
		sql_find_user.append("left join tbl_departamento DD on G.dept_des = DD.codigo ");
		sql_find_user.append("left join tbl_provincia PO on G.prov_ori = PO.codigo AND PO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_provincia PD on G.prov_des = PD.codigo AND PD.departamento = G.dept_des ");
		sql_find_user.append("left join tbl_distrito DIO on G.dist_ori = DIO.codigo AND DIO.provincia = G.prov_ori AND DIO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_distrito DID on G.dist_des = DID.codigo AND DID.provincia = G.prov_des AND DID.departamento = G.dept_des ");
		
		sql_find_user.append("left join tbl_zona ZO on G.ZONA_ORI = ZO.codigo ");
		sql_find_user.append("left join tbl_zona ZD on G.ZONA_DES = ZD.codigo ");
		
		sql_find_user.append("left join tbl_via VIO on G.VIATIPO_ORI = VIO.codigo ");
		sql_find_user.append("left join tbl_via VID on G.VIATIPO_DES = VID.codigo ");
		
		sql_find_user.append("left join tbl_empresa EMP on G.RUC = EMP.RUC ");
		sql_find_user.append("left join tbl_parametros PAR on PAR.TIPO='ESTGUIA' AND PAR.COD = G.ESTADO ");
		sql_find_user.append("where G.ruc = ?  AND G.estado in (1,2) ");
		guia.setEstado(1);
  		logger.info("listGuia guia:"+sql_find_user);
  		Object[] params = new Object[] {
  		guia.getRuc()
		};
    	lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(),params, new GuiaMapper());        	
  		//lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(), new GuiaMapper());
  		
		return lista;	
	}
	

	@Override
	public List<Guia> listVerifyGuia(Guia guia) throws Exception {
  		logger.info("listVerifyGuia:::::::::::::::::"+guia.toString());
		List <Guia> lista = null;
		Guia bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT G.CODIGO, G.RUC, G.NROGUIA, ");
		sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		sql_find_user.append("G.DES_RAZSOCIAL,G.DES_RUC,G.DES_TIPDOC,G.DES_NRODOC, G.FLETE, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_EMISION,'%d/%m/%Y') FEC_EMI, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_FIN_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("REM_CONTACTO,REM_TELEFONO,DES_CONTACTO,DES_TELEFONO, "); 
		//sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		//sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		//sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		//sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		
		sql_find_user.append("VIO.DESCRIPCION AS DES_VIATIPO_ORI, VID.DESCRIPCION AS DES_VIATIPO_DES, EMP.RAZON_SOCIAL, ");
		sql_find_user.append("trim(ZO.DESCRIPCION) as ZONA_ORI, trim(ZD.DESCRIPCION) AS ZONA_DES, ");
		sql_find_user.append("DIO.DESCRIPCION AS DIST_ORI, DID.DESCRIPCION AS DIST_DES, ");
		sql_find_user.append("PO.DESCRIPCION AS PROV_ORI, PD.DESCRIPCION AS PROV_DES, ");
		sql_find_user.append("DO.DESCRIPCION AS DEPT_ORI, DD.DESCRIPCION AS DEPT_DES, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y') FECHA_INI_TRA, DATE_FORMAT(G.FEC_INI_TRASLADO,'%h:%i %p') HORA_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y') FECHA_FIN_TRA, DATE_FORMAT(G.FEC_FIN_TRASLADO,'%h:%i %p') HORA_FIN_TRA, ");
		sql_find_user.append("PAR.VAL STATUS, G.ESTADO, G.LAT_ORI, G.LONG_ORI, G.LAT_DES, G.LONG_DES, ");
		sql_find_user.append("G.DIR_ORI,G.DIR_DES, G.INDINI, G.INDFIN ");

		sql_find_user.append("FROM tbl_guia_carga G ");
		sql_find_user.append("left join tbl_departamento DO on G.dept_ori = DO.codigo ");
		sql_find_user.append("left join tbl_departamento DD on G.dept_des = DD.codigo ");
		sql_find_user.append("left join tbl_provincia PO on G.prov_ori = PO.codigo AND PO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_provincia PD on G.prov_des = PD.codigo AND PD.departamento = G.dept_des ");
		sql_find_user.append("left join tbl_distrito DIO on G.dist_ori = DIO.codigo AND DIO.provincia = G.prov_ori AND DIO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_distrito DID on G.dist_des = DID.codigo AND DID.provincia = G.prov_des AND DID.departamento = G.dept_des ");
		
		sql_find_user.append("left join tbl_zona ZO on G.ZONA_ORI = ZO.codigo ");
		sql_find_user.append("left join tbl_zona ZD on G.ZONA_DES = ZD.codigo ");
		
		sql_find_user.append("left join tbl_via VIO on G.VIATIPO_ORI = VIO.codigo ");
		sql_find_user.append("left join tbl_via VID on G.VIATIPO_DES = VID.codigo ");
		
		sql_find_user.append("left join tbl_empresa EMP on G.RUC = EMP.RUC ");
		sql_find_user.append("left join tbl_parametros PAR on PAR.TIPO='ESTGUIA' AND PAR.COD = G.ESTADO ");
		sql_find_user.append("where G.ruc = ?  order by G.codigo");
		guia.setEstado(1);
  		logger.info("listGuia guia:"+sql_find_user);
  		Object[] params = new Object[] {
  		guia.getRuc()
		};
    	lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(),params, new GuiaVerifyMapper());        	
  		
		return lista;	
	}

	
	@Override
	public List<Guia> listMiGuia(Guia guia) throws Exception {
  		logger.info("listMiGuia getGuia:::::::::::::::::"+guia.toString());
		List <Guia> lista = null;
		Guia bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT G.CODIGO, G.RUC, G.NROGUIA, ");
		sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		sql_find_user.append("G.DES_RAZSOCIAL,G.DES_RUC,G.DES_TIPDOC,G.DES_NRODOC, G.FLETE, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_EMISION,'%d/%m/%Y') FEC_EMI, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_FIN_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("REM_CONTACTO,REM_TELEFONO,DES_CONTACTO,DES_TELEFONO, "); 
		//sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		//sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		//sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		//sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		
		sql_find_user.append("VIO.DESCRIPCION AS DES_VIATIPO_ORI, VID.DESCRIPCION AS DES_VIATIPO_DES, EMP.RAZON_SOCIAL, ");
		sql_find_user.append("trim(ZO.DESCRIPCION) as ZONA_ORI, trim(ZD.DESCRIPCION) AS ZONA_DES, ");
		sql_find_user.append("DIO.DESCRIPCION AS DIST_ORI, DID.DESCRIPCION AS DIST_DES, ");
		sql_find_user.append("PO.DESCRIPCION AS PROV_ORI, PD.DESCRIPCION AS PROV_DES, ");
		sql_find_user.append("DO.DESCRIPCION AS DEPT_ORI, DD.DESCRIPCION AS DEPT_DES, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y') FECHA_INI_TRA, DATE_FORMAT(G.FEC_INI_TRASLADO,'%h:%i %p') HORA_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y') FECHA_FIN_TRA, DATE_FORMAT(G.FEC_FIN_TRASLADO,'%h:%i %p') HORA_FIN_TRA, ");
		sql_find_user.append("PAR.VAL STATUS, G.ESTADO, G.LAT_ORI, G.LONG_ORI, G.LAT_DES, G.LONG_DES, ");
		sql_find_user.append("G.DIR_ORI,G.DIR_DES ");

		sql_find_user.append("FROM tbl_guia_carga G ");
		sql_find_user.append("left join tbl_departamento DO on G.dept_ori = DO.codigo ");
		sql_find_user.append("left join tbl_departamento DD on G.dept_des = DD.codigo ");
		sql_find_user.append("left join tbl_provincia PO on G.prov_ori = PO.codigo AND PO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_provincia PD on G.prov_des = PD.codigo AND PD.departamento = G.dept_des ");
		sql_find_user.append("left join tbl_distrito DIO on G.dist_ori = DIO.codigo AND DIO.provincia = G.prov_ori AND DIO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_distrito DID on G.dist_des = DID.codigo AND DID.provincia = G.prov_des AND DID.departamento = G.dept_des ");
		
		sql_find_user.append("left join tbl_zona ZO on G.ZONA_ORI = ZO.codigo ");
		sql_find_user.append("left join tbl_zona ZD on G.ZONA_DES = ZD.codigo ");
		
		sql_find_user.append("left join tbl_via VIO on G.VIATIPO_ORI = VIO.codigo ");
		sql_find_user.append("left join tbl_via VID on G.VIATIPO_DES = VID.codigo ");
		
		sql_find_user.append("left join tbl_empresa EMP on G.RUC = EMP.RUC ");
		sql_find_user.append("left join tbl_parametros PAR on PAR.TIPO='ESTGUIA' AND PAR.COD = G.ESTADO ");
		sql_find_user.append("where G.ruc = ?  order by G.ESTADO ");
		guia.setEstado(1);
  		logger.info("listGuia guia:"+sql_find_user);
  		Object[] params = new Object[] {
  		guia.getRuc()
		};
    	lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(),params, new GuiaMapper());        	
  		//lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(), new GuiaMapper());
  		
		return lista;	
	}

	@Override
	public List<Guia> listMiGuiaViajes(Integer codigoUsuarioTransporte) throws Exception {
  		logger.info("listMiGuiaViajes::::::"+codigoUsuarioTransporte);
		List <Guia> lista = null;
		Guia bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT G.CODIGO, G.RUC, G.NROGUIA, ");
		sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		sql_find_user.append("G.DES_RAZSOCIAL,G.DES_RUC,G.DES_TIPDOC,G.DES_NRODOC, G.FLETE, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_EMISION,'%d/%m/%Y') FEC_EMI, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_FIN_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("REM_CONTACTO,REM_TELEFONO,DES_CONTACTO,DES_TELEFONO, "); 
		//sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		//sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		//sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		//sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		
		sql_find_user.append("VIO.DESCRIPCION AS DES_VIATIPO_ORI, VID.DESCRIPCION AS DES_VIATIPO_DES, EMP.RAZON_SOCIAL, ");
		sql_find_user.append("trim(ZO.DESCRIPCION) as ZONA_ORI, trim(ZD.DESCRIPCION) AS ZONA_DES, ");
		sql_find_user.append("DIO.DESCRIPCION AS DIST_ORI, DID.DESCRIPCION AS DIST_DES, ");
		sql_find_user.append("PO.DESCRIPCION AS PROV_ORI, PD.DESCRIPCION AS PROV_DES, ");
		sql_find_user.append("DO.DESCRIPCION AS DEPT_ORI, DD.DESCRIPCION AS DEPT_DES, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y') FECHA_INI_TRA, DATE_FORMAT(G.FEC_INI_TRASLADO,'%h:%i %p') HORA_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y') FECHA_FIN_TRA, DATE_FORMAT(G.FEC_FIN_TRASLADO,'%h:%i %p') HORA_FIN_TRA, ");
		sql_find_user.append("PAR.VAL STATUS, G.ESTADO, G.LAT_ORI, G.LONG_ORI, G.LAT_DES, G.LONG_DES, ");
		sql_find_user.append("G.DIR_ORI,G.DIR_DES ");

		
		sql_find_user.append("FROM tbl_subasta s ");
		sql_find_user.append("inner join tbl_usuario_transporte u on u.CODIGO = s.CODIGO_USUARIO_TRANSPORTE ");
		sql_find_user.append("inner join tbl_guia_carga G on G.CODIGO = s.CODIGO_GUIA ");

		sql_find_user.append("left join tbl_departamento DO on G.dept_ori = DO.codigo ");
		sql_find_user.append("left join tbl_departamento DD on G.dept_des = DD.codigo ");
		sql_find_user.append("left join tbl_provincia PO on G.prov_ori = PO.codigo AND PO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_provincia PD on G.prov_des = PD.codigo AND PD.departamento = G.dept_des ");
		sql_find_user.append("left join tbl_distrito DIO on G.dist_ori = DIO.codigo AND DIO.provincia = G.prov_ori AND DIO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_distrito DID on G.dist_des = DID.codigo AND DID.provincia = G.prov_des AND DID.departamento = G.dept_des ");
		
		sql_find_user.append("left join tbl_zona ZO on G.ZONA_ORI = ZO.codigo ");
		sql_find_user.append("left join tbl_zona ZD on G.ZONA_DES = ZD.codigo ");
		
		sql_find_user.append("left join tbl_via VIO on G.VIATIPO_ORI = VIO.codigo ");
		sql_find_user.append("left join tbl_via VID on G.VIATIPO_DES = VID.codigo ");
		
		sql_find_user.append("left join tbl_empresa EMP on G.RUC = EMP.RUC ");
		sql_find_user.append("left join tbl_parametros PAR on PAR.TIPO='ESTGUIA' AND PAR.COD = G.ESTADO ");
		sql_find_user.append("where s.CODIGO_USUARIO_TRANSPORTE = ?  ");
  		logger.info("listGuia guia:"+sql_find_user);
  		Object[] params = new Object[] {
  				codigoUsuarioTransporte
		};
    	lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(),params, new GuiaMapper());        	
		return lista;	
	}

	
	@Override
	public List<Guia> listGuiaSubasta(Guia guia) throws Exception {
  		logger.info("listGuia getGuia:::::::::::::::::"+guia.toString());
		List <Guia> lista = null;
		Guia bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT G.CODIGO, G.RUC, G.NROGUIA, ");
		sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		sql_find_user.append("G.DES_RAZSOCIAL,G.DES_RUC,G.DES_TIPDOC,G.DES_NRODOC, G.FLETE, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_EMISION,'%d/%m/%Y') FEC_EMI, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_FIN_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("REM_CONTACTO,REM_TELEFONO,DES_CONTACTO,DES_TELEFONO, "); 
		//sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		//sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		//sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		//sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		
		sql_find_user.append("VIO.DESCRIPCION AS DES_VIATIPO_ORI, VID.DESCRIPCION AS DES_VIATIPO_DES, EMP.RAZON_SOCIAL, ");
		sql_find_user.append("trim(ZO.DESCRIPCION) as ZONA_ORI, trim(ZD.DESCRIPCION) AS ZONA_DES, ");
		sql_find_user.append("DIO.DESCRIPCION AS DIST_ORI, DID.DESCRIPCION AS DIST_DES, ");
		sql_find_user.append("PO.DESCRIPCION AS PROV_ORI, PD.DESCRIPCION AS PROV_DES, ");
		sql_find_user.append("DO.DESCRIPCION AS DEPT_ORI, DD.DESCRIPCION AS DEPT_DES, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y') FECHA_INI_TRA, DATE_FORMAT(G.FEC_INI_TRASLADO,'%h:%i %p') HORA_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y') FECHA_FIN_TRA, DATE_FORMAT(G.FEC_FIN_TRASLADO,'%h:%i %p') HORA_FIN_TRA, ");
		sql_find_user.append("PAR.VAL STATUS, G.ESTADO, G.LAT_ORI, G.LONG_ORI, G.LAT_DES, G.LONG_DES, ");
		sql_find_user.append("G.DIR_ORI,G.DIR_DES ");
		sql_find_user.append("FROM tbl_guia_carga G ");
		sql_find_user.append("left join tbl_departamento DO on G.dept_ori = DO.codigo ");
		sql_find_user.append("left join tbl_departamento DD on G.dept_des = DD.codigo ");
		sql_find_user.append("left join tbl_provincia PO on G.prov_ori = PO.codigo AND PO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_provincia PD on G.prov_des = PD.codigo AND PD.departamento = G.dept_des ");
		sql_find_user.append("left join tbl_distrito DIO on G.dist_ori = DIO.codigo AND DIO.provincia = G.prov_ori AND DIO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_distrito DID on G.dist_des = DID.codigo AND DID.provincia = G.prov_des AND DID.departamento = G.dept_des ");
		
		sql_find_user.append("left join tbl_zona ZO on G.ZONA_ORI = ZO.codigo ");
		sql_find_user.append("left join tbl_zona ZD on G.ZONA_DES = ZD.codigo ");
		
		sql_find_user.append("left join tbl_via VIO on G.VIATIPO_ORI = VIO.codigo ");
		sql_find_user.append("left join tbl_via VID on G.VIATIPO_DES = VID.codigo ");
		
		sql_find_user.append("left join tbl_empresa EMP on G.RUC = EMP.RUC ");
		sql_find_user.append("left join tbl_parametros PAR on PAR.TIPO='ESTGUIA' AND PAR.COD = G.ESTADO ");
		sql_find_user.append("where G.ruc = ?  AND G.estado in (3,4) ");
		guia.setEstado(1);
  		logger.info("listGuia guia:"+sql_find_user);
  		Object[] params = new Object[] {
  		guia.getRuc()
		};
    	lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(),params, new GuiaMapper());        	
  		
		return lista;	
	}

	
	@Override
	public List<Guia> listGuiaUltimos(Guia guia) throws Exception {
  		logger.info("listGuiaUltimos getGuia:::::::::::::::::"+guia.toString());
		List <Guia> lista = null;
		Guia bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT G.CODIGO, G.RUC, G.NROGUIA, ");
		sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		sql_find_user.append("G.DES_RAZSOCIAL,G.DES_RUC,G.DES_TIPDOC,G.DES_NRODOC, G.FLETE, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_EMISION,'%d/%m/%Y') FEC_EMI, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_FIN_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("REM_CONTACTO,REM_TELEFONO,DES_CONTACTO,DES_TELEFONO, "); 
		//sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		//sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		//sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		//sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		
		sql_find_user.append("VIO.DESCRIPCION AS DES_VIATIPO_ORI, VID.DESCRIPCION AS DES_VIATIPO_DES, EMP.RAZON_SOCIAL, ");
		sql_find_user.append("trim(ZO.DESCRIPCION) as ZONA_ORI, trim(ZD.DESCRIPCION) AS ZONA_DES, ");
		sql_find_user.append("DIO.DESCRIPCION AS DIST_ORI, DID.DESCRIPCION AS DIST_DES, ");
		sql_find_user.append("PO.DESCRIPCION AS PROV_ORI, PD.DESCRIPCION AS PROV_DES, ");
		sql_find_user.append("DO.DESCRIPCION AS DEPT_ORI, DD.DESCRIPCION AS DEPT_DES, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y') FECHA_INI_TRA, DATE_FORMAT(G.FEC_INI_TRASLADO,'%h:%i %p') HORA_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y') FECHA_FIN_TRA, DATE_FORMAT(G.FEC_FIN_TRASLADO,'%h:%i %p') HORA_FIN_TRA, ");
		sql_find_user.append("PAR.VAL STATUS, G.ESTADO, G.LAT_ORI, G.LONG_ORI, G.LAT_DES, G.LONG_DES, ");
		sql_find_user.append("G.DIR_ORI,G.DIR_DES ");
		sql_find_user.append("FROM tbl_guia_carga G ");
		sql_find_user.append("left join tbl_departamento DO on G.dept_ori = DO.codigo ");
		sql_find_user.append("left join tbl_departamento DD on G.dept_des = DD.codigo ");
		sql_find_user.append("left join tbl_provincia PO on G.prov_ori = PO.codigo AND PO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_provincia PD on G.prov_des = PD.codigo AND PD.departamento = G.dept_des ");
		sql_find_user.append("left join tbl_distrito DIO on G.dist_ori = DIO.codigo AND DIO.provincia = G.prov_ori AND DIO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_distrito DID on G.dist_des = DID.codigo AND DID.provincia = G.prov_des AND DID.departamento = G.dept_des ");
		
		sql_find_user.append("left join tbl_zona ZO on G.ZONA_ORI = ZO.codigo ");
		sql_find_user.append("left join tbl_zona ZD on G.ZONA_DES = ZD.codigo ");

		sql_find_user.append("left join tbl_via VIO on G.VIATIPO_ORI = VIO.codigo ");
		sql_find_user.append("left join tbl_via VID on G.VIATIPO_DES = VID.codigo ");
		
		sql_find_user.append("left join tbl_empresa EMP on G.RUC = EMP.RUC ");
		sql_find_user.append("left join tbl_parametros PAR on PAR.TIPO='ESTGUIA' AND PAR.COD = G.ESTADO ");
		
		
		sql_find_user.append("order by fec_ini_traslado desc limit 4 ");
		logger.info("listGuia guia:"+sql_find_user);

  		lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(), new GuiaMapper());
  		
		return lista;	
	}

	@Override
	public List<Guia> listGuiaAll(Guia guia) throws Exception {
  		logger.info("listGuiaAll getGuia:::::::::::::::::"+guia.toString());
		List <Guia> lista = null;
		Guia bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT G.CODIGO, G.RUC, G.NROGUIA, ");
		sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		sql_find_user.append("G.DES_RAZSOCIAL,G.DES_RUC,G.DES_TIPDOC,G.DES_NRODOC, G.FLETE, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_EMISION,'%d/%m/%Y') FEC_EMI, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_FIN_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("REM_CONTACTO,REM_TELEFONO,DES_CONTACTO,DES_TELEFONO, "); 
		
		sql_find_user.append("VIO.DESCRIPCION AS DES_VIATIPO_ORI, VID.DESCRIPCION AS DES_VIATIPO_DES, EMP.RAZON_SOCIAL, ");
		sql_find_user.append("trim(ZO.DESCRIPCION) as ZONA_ORI, trim(ZD.DESCRIPCION) AS ZONA_DES, ");
		sql_find_user.append("DIO.DESCRIPCION AS DIST_ORI, DID.DESCRIPCION AS DIST_DES, ");
		sql_find_user.append("PO.DESCRIPCION AS PROV_ORI, PD.DESCRIPCION AS PROV_DES, ");
		sql_find_user.append("DO.DESCRIPCION AS DEPT_ORI, DD.DESCRIPCION AS DEPT_DES, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y') FECHA_INI_TRA, DATE_FORMAT(G.FEC_INI_TRASLADO,'%h:%i %p') HORA_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y') FECHA_FIN_TRA, DATE_FORMAT(G.FEC_FIN_TRASLADO,'%h:%i %p') HORA_FIN_TRA, ");
		sql_find_user.append("PAR.VAL STATUS, G.ESTADO, G.LAT_ORI, G.LONG_ORI, G.LAT_DES, G.LONG_DES, ");
		sql_find_user.append("G.DIR_ORI,G.DIR_DES ");
		sql_find_user.append("FROM tbl_guia_carga G ");
		sql_find_user.append("left join tbl_departamento DO on G.dept_ori = DO.codigo ");
		sql_find_user.append("left join tbl_departamento DD on G.dept_des = DD.codigo ");
		sql_find_user.append("left join tbl_provincia PO on G.prov_ori = PO.codigo AND PO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_provincia PD on G.prov_des = PD.codigo AND PD.departamento = G.dept_des ");
		sql_find_user.append("left join tbl_distrito DIO on G.dist_ori = DIO.codigo AND DIO.provincia = G.prov_ori AND DIO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_distrito DID on G.dist_des = DID.codigo AND DID.provincia = G.prov_des AND DID.departamento = G.dept_des ");
		
		sql_find_user.append("left join tbl_zona ZO on G.ZONA_ORI = ZO.codigo ");
		sql_find_user.append("left join tbl_zona ZD on G.ZONA_DES = ZD.codigo ");

		sql_find_user.append("left join tbl_via VIO on G.VIATIPO_ORI = VIO.codigo ");
		sql_find_user.append("left join tbl_via VID on G.VIATIPO_DES = VID.codigo ");
		
		sql_find_user.append("left join tbl_empresa EMP on G.RUC = EMP.RUC ");
		sql_find_user.append("left join tbl_parametros PAR on PAR.TIPO='ESTGUIA' AND PAR.COD = G.ESTADO ");
				
		sql_find_user.append("where G.ESTADO in (2,3) ");
		sql_find_user.append("order by G.fec_ini_traslado desc ");
		logger.info("listGuiaAll guia:"+sql_find_user);
    	
  		lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(), new GuiaMapper());
  		
		return lista;	
	}


	@Override
	public List<Guia> listGuiaMiViaje(Guia guia, Integer codigoUsuarioTransporte) throws Exception {
  		logger.info("listGuiaAll getGuia:::::::::::::::::"+guia.toString());
		List <Guia> lista = null;
		Guia bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT G.CODIGO, G.RUC, G.NROGUIA, ");
		sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		sql_find_user.append("G.DES_RAZSOCIAL,G.DES_RUC,G.DES_TIPDOC,G.DES_NRODOC, G.FLETE, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_EMISION,'%d/%m/%Y') FEC_EMI, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_FIN_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("REM_CONTACTO,REM_TELEFONO,DES_CONTACTO,DES_TELEFONO, "); 
		
		sql_find_user.append("VIO.DESCRIPCION AS DES_VIATIPO_ORI, VID.DESCRIPCION AS DES_VIATIPO_DES, EMP.RAZON_SOCIAL, ");
		sql_find_user.append("trim(ZO.DESCRIPCION) as ZONA_ORI, trim(ZD.DESCRIPCION) AS ZONA_DES, ");
		sql_find_user.append("DIO.DESCRIPCION AS DIST_ORI, DID.DESCRIPCION AS DIST_DES, ");
		sql_find_user.append("PO.DESCRIPCION AS PROV_ORI, PD.DESCRIPCION AS PROV_DES, ");
		sql_find_user.append("DO.DESCRIPCION AS DEPT_ORI, DD.DESCRIPCION AS DEPT_DES, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y') FECHA_INI_TRA, DATE_FORMAT(G.FEC_INI_TRASLADO,'%h:%i %p') HORA_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y') FECHA_FIN_TRA, DATE_FORMAT(G.FEC_FIN_TRASLADO,'%h:%i %p') HORA_FIN_TRA, ");
		sql_find_user.append("PAR.VAL STATUS, G.ESTADO, G.LAT_ORI, G.LONG_ORI, G.LAT_DES, G.LONG_DES, ");
		sql_find_user.append("G.DIR_ORI,G.DIR_DES ");
		sql_find_user.append("FROM tbl_guia_carga G ");
				
		sql_find_user.append("inner join tbl_subasta S on G.CODIGO=S.CODIGO_GUIA AND S.ESTADO=2 AND S.CODIGO_USUARIO_TRANSPORTE=? ");

		sql_find_user.append("left join tbl_departamento DO on G.dept_ori = DO.codigo ");
		sql_find_user.append("left join tbl_departamento DD on G.dept_des = DD.codigo ");
		sql_find_user.append("left join tbl_provincia PO on G.prov_ori = PO.codigo AND PO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_provincia PD on G.prov_des = PD.codigo AND PD.departamento = G.dept_des ");
		sql_find_user.append("left join tbl_distrito DIO on G.dist_ori = DIO.codigo AND DIO.provincia = G.prov_ori AND DIO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_distrito DID on G.dist_des = DID.codigo AND DID.provincia = G.prov_des AND DID.departamento = G.dept_des ");
		
		sql_find_user.append("left join tbl_zona ZO on G.ZONA_ORI = ZO.codigo ");
		sql_find_user.append("left join tbl_zona ZD on G.ZONA_DES = ZD.codigo ");

		sql_find_user.append("left join tbl_via VIO on G.VIATIPO_ORI = VIO.codigo ");
		sql_find_user.append("left join tbl_via VID on G.VIATIPO_DES = VID.codigo ");
		
		sql_find_user.append("left join tbl_empresa EMP on G.RUC = EMP.RUC ");
		sql_find_user.append("left join tbl_parametros PAR on PAR.TIPO='ESTGUIA' AND PAR.COD = G.ESTADO ");
				
		sql_find_user.append("where G.ESTADO in (4,5,6) ");
		sql_find_user.append("order by G.fec_ini_traslado desc ");
		logger.info("listGuiaAll guia:"+sql_find_user);
    	
  		Object[] params = new Object[] {
  				codigoUsuarioTransporte
		};

  		lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(), params, new GuiaMapper());
  		
		return lista;	
	}

	@Override
	public List<Guia> listMisCargas(String ruc) throws Exception {
  		logger.info("listMisCargas:::::"+ruc);
		List <Guia> lista = null;
		Guia bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT G.CODIGO, G.RUC, G.NROGUIA, ");
		sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		sql_find_user.append("G.DES_RAZSOCIAL,G.DES_RUC,G.DES_TIPDOC,G.DES_NRODOC, G.FLETE, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_EMISION,'%d/%m/%Y') FEC_EMI, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_FIN_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("REM_CONTACTO,REM_TELEFONO,DES_CONTACTO,DES_TELEFONO, "); 
		
		sql_find_user.append("VIO.DESCRIPCION AS DES_VIATIPO_ORI, VID.DESCRIPCION AS DES_VIATIPO_DES, EMP.RAZON_SOCIAL, ");
		sql_find_user.append("trim(ZO.DESCRIPCION) as ZONA_ORI, trim(ZD.DESCRIPCION) AS ZONA_DES, ");
		sql_find_user.append("DIO.DESCRIPCION AS DIST_ORI, DID.DESCRIPCION AS DIST_DES, ");
		sql_find_user.append("PO.DESCRIPCION AS PROV_ORI, PD.DESCRIPCION AS PROV_DES, ");
		sql_find_user.append("DO.DESCRIPCION AS DEPT_ORI, DD.DESCRIPCION AS DEPT_DES, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y') FECHA_INI_TRA, DATE_FORMAT(G.FEC_INI_TRASLADO,'%h:%i %p') HORA_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y') FECHA_FIN_TRA, DATE_FORMAT(G.FEC_FIN_TRASLADO,'%h:%i %p') HORA_FIN_TRA, ");
		sql_find_user.append("PAR.VAL STATUS, G.ESTADO, G.LAT_ORI, G.LONG_ORI, G.LAT_DES, G.LONG_DES, ");
		sql_find_user.append("G.DIR_ORI,G.DIR_DES ");
		sql_find_user.append("FROM tbl_guia_carga G ");
		sql_find_user.append("left join tbl_departamento DO on G.dept_ori = DO.codigo ");
		sql_find_user.append("left join tbl_departamento DD on G.dept_des = DD.codigo ");
		sql_find_user.append("left join tbl_provincia PO on G.prov_ori = PO.codigo AND PO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_provincia PD on G.prov_des = PD.codigo AND PD.departamento = G.dept_des ");
		sql_find_user.append("left join tbl_distrito DIO on G.dist_ori = DIO.codigo AND DIO.provincia = G.prov_ori AND DIO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_distrito DID on G.dist_des = DID.codigo AND DID.provincia = G.prov_des AND DID.departamento = G.dept_des ");
		
		sql_find_user.append("left join tbl_zona ZO on G.ZONA_ORI = ZO.codigo ");
		sql_find_user.append("left join tbl_zona ZD on G.ZONA_DES = ZD.codigo ");
		
		sql_find_user.append("left join tbl_via VIO on G.VIATIPO_ORI = VIO.codigo ");
		sql_find_user.append("left join tbl_via VID on G.VIATIPO_DES = VID.codigo ");
		
		sql_find_user.append("left join tbl_empresa EMP on G.RUC = EMP.RUC ");
		sql_find_user.append("left join tbl_parametros PAR on PAR.TIPO='ESTGUIA' AND PAR.COD = G.ESTADO ");
		sql_find_user.append("where G.ruc = ?  AND G.estado in (5) ");
		sql_find_user.append("order by G.CODIGO DESC ");
		//logger.info("listGuiaAll guia:"+sql_find_user);
  		Object[] params = new Object[] { ruc };
  		lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(), params, new GuiaMapper());
  		
		return lista;	
	}
	
	@Override
	public Integer getIdGuia(Guia guia) throws Exception {
  		logger.info("getIdentity:::"+guia.toString());
		List <Guia> lista = null;
		Integer bean = new Integer(0);
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT @@identity AS id ");
  		logger.info("obtener guia:"+sql_find_user);
  		bean = jdbcTemplate.queryForObject(sql_find_user.toString(),Integer.class);
  		logger.info("==>bean:"+bean);
  		return bean;
	}

	@Override
	public Integer getMaxIdGuia(Guia guia) throws Exception {
  		logger.info("getIdentity:::"+guia.toString());
		List <Guia> lista = null;
		Integer bean = new Integer(0);
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT max(codigo) as id from tbl_guia_carga ");
  		logger.info("obtener guia:"+sql_find_user);
  		bean = jdbcTemplate.queryForObject(sql_find_user.toString(),Integer.class);
  		logger.info("==>bean:"+bean);
  		return bean;
	}

	@Override
	public Integer getMaxNumGuia() throws Exception {
		List <Guia> lista = null;
		Integer bean = new Integer(0);
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT max(numeracion) + 1 as id from tbl_numeracion where estado = 1 ");
  		logger.info("getMaxNumGuia:"+sql_find_user);
  		bean = jdbcTemplate.queryForObject(sql_find_user.toString(),Integer.class);
  		logger.info("==>getMaxNumGuia bean:"+bean);
  		return bean;
	}

	
	@Override
	public Integer getCantidadGuia(Guia guia) throws Exception {
  		logger.info("getIdentity:::"+guia.toString());
		List <Guia> lista = null;
		Integer bean = new Integer(0);
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT count(*) ");
		sql_find_user.append("FROM tbl_guia_carga where RUC=? AND ESTADO=? ");
		
		logger.info("getCantidadGuia:"+sql_find_user);
  		Object[] params = new Object[] {
  		guia.getRuc(),guia.getEstado()
		};

  		bean = jdbcTemplate.queryForObject(sql_find_user.toString(),params,Integer.class);
  		logger.info("==>bean:"+bean);
  		return bean;
	}

	
	@Override
	public List<BeanUtil> lstCantidadGuia(Guia guia) throws Exception {
  		logger.info("getIdentity:::"+guia.toString());
		List <BeanUtil> lista = null;
		Integer bean = new Integer(0);
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT count(*) cantidad, estado, estado status ");
		sql_find_user.append("FROM tbl_guia_carga where RUC=? group by estado ");

		logger.info("getCantidadGuia:"+sql_find_user);
  		Object[] params = new Object[] { guia.getRuc() };

  		lista = (List <BeanUtil> )jdbcTemplate.query(sql_find_user.toString(), params, new BeanUtilMapper());
  		return lista;
	}
	
	@Override
	public void updatecontadorGuia(Integer contador) throws Exception {
	
		logger.info("ini updatecontadorGuia::"+contador);
		StringBuilder sql_insert_user = new StringBuilder();		
		sql_insert_user.append("update tbl_numeracion set numeracion=? ");
		sql_insert_user.append("where estado = 1 ");
        
		Object[] params = new Object[] { contador };
		jdbcTemplate.update(sql_insert_user.toString(), params);
		logger.info("==>Fin updatecontadorGuia:::");

	}

	
	
	@Override
	public void updateEstadoGuia(Guia guia) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		
		sql_insert_user.append("UPDATE tbl_guia_carga SET ");
		sql_insert_user.append("ESTADO=?, FEC_MODIFICA=? "); 
		sql_insert_user.append("WHERE CODIGO=? "); 
	
		logger.info("updateEstadoGuia::"+guia.toString());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
                
		Object[] params = new Object[] {
		guia.getEstado(),fechaHora, guia.getCodigo() };
		
		jdbcTemplate.update(sql_insert_user.toString(), params);
		
	}


	@Override
	public void updateAprobarGuia(Guia guia) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		
		sql_insert_user.append("UPDATE tbl_guia_carga SET ");
		sql_insert_user.append("ESTADO=?, FEC_MODIFICA=?, CODIGO_CHOFER=? "); 
		sql_insert_user.append("WHERE CODIGO=? "); 
	
		logger.info("updateAprobarGuia::"+guia.toString());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
                
		Object[] params = new Object[] {
		guia.getEstado(),fechaHora, guia.getCodigoChofer(), guia.getCodigo() };
		
		jdbcTemplate.update(sql_insert_user.toString(), params);
		
	}

	
	
	@Override
	public List<Guia> listViajesChofer(Chofer request) throws Exception {
  		logger.info("listViajesChofer :::::::::::::::::"+request.toString());
		List <Guia> lista = null;
		Guia bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT G.CODIGO, G.RUC, G.NROGUIA, ");
		sql_find_user.append("G.VIATIPO_ORI,G.VIANOMBRE_ORI,G.NRO_ORI,G.INTERIOR_ORI, ");
		sql_find_user.append("G.ZONA_ORI,G.DIST_ORI,G.PROV_ORI,G.DEPT_ORI,G.VIATIPO_DES, ");
		sql_find_user.append("G.VIANOMBRE_DES,G.NRO_DES,G.INTERIOR_DES,G.ZONA_DES,G.DIST_DES, ");
		sql_find_user.append("G.PROV_DES,G.DEPT_DES,G.REM_RAZSOCIAL,G.REM_RUC,G.REM_TIPDOC,G.REM_NRODOC, ");
		sql_find_user.append("G.DES_RAZSOCIAL,G.DES_RUC,G.DES_TIPDOC,G.DES_NRODOC, G.FLETE, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_EMISION,'%d/%m/%Y') FEC_EMI, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y %H:%i:%s') FEC_FIN_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("REM_CONTACTO,REM_TELEFONO,DES_CONTACTO,DES_TELEFONO, "); 
		
		sql_find_user.append("VIO.DESCRIPCION AS DES_VIATIPO_ORI, VID.DESCRIPCION AS DES_VIATIPO_DES, EMP.RAZON_SOCIAL, ");
		sql_find_user.append("trim(ZO.DESCRIPCION) as ZONA_ORI, trim(ZD.DESCRIPCION) AS ZONA_DES, ");
		sql_find_user.append("DIO.DESCRIPCION AS DIST_ORI, DID.DESCRIPCION AS DIST_DES, ");
		sql_find_user.append("PO.DESCRIPCION AS PROV_ORI, PD.DESCRIPCION AS PROV_DES, ");
		sql_find_user.append("DO.DESCRIPCION AS DEPT_ORI, DD.DESCRIPCION AS DEPT_DES, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_INI_TRASLADO,'%d/%m/%Y') FECHA_INI_TRA, DATE_FORMAT(G.FEC_INI_TRASLADO,'%h:%i %p') HORA_INI_TRA, ");
		sql_find_user.append("DATE_FORMAT(G.FEC_FIN_TRASLADO,'%d/%m/%Y') FECHA_FIN_TRA, DATE_FORMAT(G.FEC_FIN_TRASLADO,'%h:%i %p') HORA_FIN_TRA, ");
		sql_find_user.append("PAR.VAL STATUS, G.ESTADO, G.LAT_ORI, G.LONG_ORI, G.LAT_DES, G.LONG_DES, ");
		sql_find_user.append("G.DIR_ORI,G.DIR_DES ");
		sql_find_user.append("FROM tbl_guia_carga G ");
		sql_find_user.append("left join tbl_departamento DO on G.dept_ori = DO.codigo ");
		sql_find_user.append("left join tbl_departamento DD on G.dept_des = DD.codigo ");
		sql_find_user.append("left join tbl_provincia PO on G.prov_ori = PO.codigo AND PO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_provincia PD on G.prov_des = PD.codigo AND PD.departamento = G.dept_des ");
		sql_find_user.append("left join tbl_distrito DIO on G.dist_ori = DIO.codigo AND DIO.provincia = G.prov_ori AND DIO.departamento = G.dept_ori ");
		sql_find_user.append("left join tbl_distrito DID on G.dist_des = DID.codigo AND DID.provincia = G.prov_des AND DID.departamento = G.dept_des ");
		
		sql_find_user.append("left join tbl_zona ZO on G.ZONA_ORI = ZO.codigo ");
		sql_find_user.append("left join tbl_zona ZD on G.ZONA_DES = ZD.codigo ");
		
		sql_find_user.append("left join tbl_via VIO on G.VIATIPO_ORI = VIO.codigo ");
		sql_find_user.append("left join tbl_via VID on G.VIATIPO_DES = VID.codigo ");
		
		sql_find_user.append("left join tbl_empresa EMP on G.RUC = EMP.RUC ");
		sql_find_user.append("left join tbl_parametros PAR on PAR.TIPO='ESTGUIA' AND PAR.COD = G.ESTADO ");

		sql_find_user.append("join tbl_subasta S on S.CODIGO_GUIA = G.CODIGO and S.BREVETE = ? ");
		sql_find_user.append("where G.estado = ? ");
		
  		logger.info("listGuia guia:"+sql_find_user);
  		Object[] params = new Object[] {
  				request.getBrevete(),request.getEstado()
		};
    	lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(),params, new GuiaMapper());        	
  		//lista = (List <Guia> )jdbcTemplate.query(sql_find_user.toString(), new GuiaMapper());
  		
		return lista;	
	}


	@Override
	public void updateIndicadorInicio(Guia guia) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		
		sql_insert_user.append("UPDATE tbl_guia_carga SET ");
		sql_insert_user.append("INDINI=?, FEC_MODIFICA=? "); 
		sql_insert_user.append("WHERE CODIGO=? "); 
		
		logger.info("updateIndicadorInicio::"+guia.toString());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
                
		Object[] params = new Object[] { guia.getVerificacionInicio(),fechaHora, guia.getCodigo() };		
		jdbcTemplate.update(sql_insert_user.toString(), params);		
	}

	@Override
	public void updateIndicadorFin(Guia guia) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		
		sql_insert_user.append("UPDATE tbl_guia_carga SET ");
		sql_insert_user.append("INDFIN=?, FEC_MODIFICA=? "); 
		sql_insert_user.append("WHERE CODIGO=? "); 
		
		logger.info("updateIndicadorInicio::"+guia.toString());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
                
		Object[] params = new Object[] { guia.getVerificacionFin(),fechaHora, guia.getCodigo() };		
		jdbcTemplate.update(sql_insert_user.toString(), params);		
	}


	@Override
	public Integer getCantidadXPeriodo(Guia guia, String periodo) throws Exception {
		List <Guia> lista = null;
		Integer bean = new Integer(0);
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("select count(G.codigo) as id from tbl_guia_carga G ");
		sql_find_user.append("where G.ruc = ? and  G.estado=7 ");
		sql_find_user.append("and DATE_FORMAT(G.FEC_REGISTRO,'%Y%m') = ? ");

		Object[] params = new Object[] { guia.getRuc(),periodo };		

  		logger.info("getCantidadXPeriodo:"+sql_find_user);
  		bean = jdbcTemplate.queryForObject(sql_find_user.toString(),params,Integer.class);
  		logger.info("==>getCantidadXPeriodo bean:"+bean);
  		return bean;
	}

		
	@Override
	public List<Map<String, Object>> tiempoViaje(String ruc) throws Exception {
  		logger.info("tiempoViaje:::::"+ruc);
		List <Map<String, Object>> lista = null;
		Guia bean = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT CONCAT(TIMESTAMPDIFF(DAY, G.FEC_REGISTRO, G.FEC_MODIFICA), ' dias, ', ");
		sql_find_user.append("MOD(TIMESTAMPDIFF(HOUR, G.FEC_REGISTRO, G.FEC_MODIFICA), 24), ' horas y ', ");
		sql_find_user.append("MOD(TIMESTAMPDIFF(MINUTE, G.FEC_REGISTRO, G.FEC_MODIFICA), 60), ' minutos ') tiempo, ");

		sql_find_user.append("DATE_FORMAT(G.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') fec_reg,DATE_FORMAT(G.FEC_MODIFICA,'%d/%m/%Y %H:%i:%s') fec_mod, ");
		sql_find_user.append("G.CODIGO,G.NROGUIA,S.BREVETE,S.PLACA ");

		sql_find_user.append("from tbl_guia_carga G, tbl_subasta S ");
		sql_find_user.append("where G.ruc = ? and G.estado=7 and G.codigo = S.codigo_guia ");
		sql_find_user.append("order by G.CODIGO DESC ");
  		Object[] params = new Object[] { ruc };
  		lista = (List <Map<String, Object>> )jdbcTemplate.query(sql_find_user.toString(), params, new GuiaTiempoMapper());
  		
		return lista;	
	}
	

}
