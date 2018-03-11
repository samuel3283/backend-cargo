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
import pe.com.boox.cargo.dao.repository.GuiaEstadoRepository;
import pe.com.boox.cargo.dao.repository.GuiaRepository;
import pe.com.boox.cargo.dao.repository.mapper.AlertaMapper;
import pe.com.boox.cargo.dao.repository.mapper.EmpresaMapper;
import pe.com.boox.cargo.dao.repository.mapper.GuiaEstadoMapper;
import pe.com.boox.cargo.dao.repository.mapper.GuiaMapper;
import pe.com.boox.cargo.model.Alertas;
import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.GuiaEstado;


@SuppressWarnings("all")
@Repository
public class GuiaEstadoRepositoryImpl implements GuiaEstadoRepository {

	private final Logger logger = LoggerFactory.getLogger(GuiaEstadoRepositoryImpl.class);

	@Autowired
	@Resource(name = "jdbcTemplateMySql")
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertaGuiaEstado(Guia guia) throws Exception {
	
		StringBuilder sql_insert_user = new StringBuilder();
		sql_insert_user.append("INSERT INTO tbl_guia_estados ");
		sql_insert_user.append("(CODIGO_GUIA, ESTADO,NOTA,FEC_REGISTRO) "); 
		sql_insert_user.append("VALUES (?,?,?,STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s') ) ");
		
		logger.info("insertaGuiaEstado::"+guia.toString());
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHora = sdf.format(dt);
                
		Object[] params = new Object[] {
		guia.getCodigo(),guia.getEstado(),guia.getNota(),fechaHora };
		
		jdbcTemplate.update(sql_insert_user.toString(), params);
		
	}

	@Override
	public GuiaEstado getGuiaEstado(Guia guia) throws Exception {
  		logger.info("getGuiaEstado:::::::::::::::::"+guia.toString());
		List <GuiaEstado> lista = null;
		GuiaEstado bean = null;
		StringBuilder sql_find_user = new StringBuilder();

		sql_find_user.append("SELECT e.CODIGO,e.CODIGO_GUIA, e.ESTADO,e.NOTA, ");
		sql_find_user.append("DATE_FORMAT(e.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, p.VAL DES_ESTADO ");
		sql_find_user.append("FROM tbl_guia_estados e ");
		sql_find_user.append("inner join tbl_parametros p on e.ESTADO = p.COD and p.TIPO = 'ESTGUIA' ");
		sql_find_user.append("where e.CODIGO_GUIA = ? ");
		sql_find_user.append("and e.nota<>'' and e.NOTA not in ('Terminando Ruta','Iniciando Ruta') ");
		sql_find_user.append("order by e.CODIGO ");

  		Object[] params = new Object[] { guia.getCodigo() };
    	lista = (List <GuiaEstado> )jdbcTemplate.query(sql_find_user.toString(),params, new GuiaEstadoMapper());        	
    	if(lista!=null && lista.size()>0){
    		bean = new GuiaEstado();
    		bean = lista.get(0);
    	}

		return bean;	
	}


	@Override
	public List<GuiaEstado> listGuiaEstado(Guia guia) throws Exception {
  		logger.info("listGuiaEstado:::::::::::::::::"+guia.toString());
		List <GuiaEstado> lista = null;
		StringBuilder sql_find_user = new StringBuilder();

		sql_find_user.append("SELECT e.CODIGO,e.CODIGO_GUIA, e.ESTADO,e.NOTA, ");
		sql_find_user.append("DATE_FORMAT(e.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, p.VAL DES_ESTADO ");
		sql_find_user.append("FROM tbl_guia_estados e ");
		sql_find_user.append("inner join tbl_parametros p on e.ESTADO = p.COD and p.TIPO = 'ESTGUIA' ");
		sql_find_user.append("where e.CODIGO_GUIA = ? ");
		sql_find_user.append("and e.nota<>'' and e.NOTA not in ('Terminando Ruta','Iniciando Ruta') ");
		sql_find_user.append("order by e.CODIGO ");

  		Object[] params = new Object[] { guia.getCodigo() };
    	lista = (List <GuiaEstado> )jdbcTemplate.query(sql_find_user.toString(),params, new GuiaEstadoMapper());        	
   		return lista;	
	}
	
	@Override
	public List<GuiaEstado> listGuiaEstadoAlerta(Guia guia) throws Exception {
  		logger.info("listGuiaEstado:::::::::::::::::"+guia.toString());
		List <GuiaEstado> lista = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("SELECT e.CODIGO,e.CODIGO_GUIA, e.ESTADO,e.NOTA, ");
		sql_find_user.append("DATE_FORMAT(e.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, p.VAL DES_ESTADO ");
		sql_find_user.append("FROM tbl_guia_estados e ");
		sql_find_user.append("inner join tbl_parametros p on e.ESTADO = p.COD and p.TIPO = 'ESTGUIA' ");
		sql_find_user.append("where e.CODIGO_GUIA = ? and e.nota<>'' and e.NOTA not in ('Terminando Ruta','Iniciando Ruta') ");
		sql_find_user.append("order by e.CODIGO ");
		
  		Object[] params = new Object[] { guia.getCodigo() };
    	lista = (List <GuiaEstado> )jdbcTemplate.query(sql_find_user.toString(),params, new GuiaEstadoMapper());        	
   		return lista;	
	}

	@Override
	public List<Alertas> listAlerta(String ruc) throws Exception {
  		logger.info("listAlerta:::::"+ruc);
		List <Alertas> lista = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("select e.CODIGO,e.CODIGO_GUIA, e.NOTA, e.ESTADO, DATE_FORMAT(e.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("p.VAL DES_ESTADO, c.NROGUIA ");
		sql_find_user.append("from tbl_guia_estados e ");
		sql_find_user.append("inner join tbl_guia_carga c on c.CODIGO = e.CODIGO_GUIA ");
		sql_find_user.append("inner join tbl_parametros p on e.ESTADO = p.COD and p.TIPO = 'ESTGUIA' ");
		sql_find_user.append("where e.nota<>'' and e.NOTA not in ('Terminando Ruta','Iniciando Ruta')  ");
		sql_find_user.append("and c.RUC = ? ");
		sql_find_user.append("order by e.CODIGO desc ");
		
  		Object[] params = new Object[] { ruc };
    	lista = (List <Alertas> )jdbcTemplate.query(sql_find_user.toString(),params, new AlertaMapper());        	
   		return lista;	
	}

	
	@Override
	public List<Alertas> listAlertaTransporte(Integer codigoUsuarioTransporte) throws Exception {
  		logger.info("listAlertaTransporte:::::"+codigoUsuarioTransporte);
		List <Alertas> lista = null;
		StringBuilder sql_find_user = new StringBuilder();
		sql_find_user.append("select e.CODIGO,e.CODIGO_GUIA, e.NOTA, e.ESTADO, DATE_FORMAT(e.FEC_REGISTRO,'%d/%m/%Y %H:%i:%s') FEC_REG, ");
		sql_find_user.append("p.VAL DES_ESTADO, c.NROGUIA ");
						
		sql_find_user.append("from tbl_guia_estados e ");
		sql_find_user.append("inner join tbl_guia_carga c on c.CODIGO = e.CODIGO_GUIA ");

		
		sql_find_user.append("inner join tbl_subasta s on c.CODIGO = s.CODIGO_GUIA ");
		sql_find_user.append("inner join tbl_usuario_transporte u on u.CODIGO = s.CODIGO_USUARIO_TRANSPORTE ");
							
		sql_find_user.append("inner join tbl_parametros p on e.ESTADO = p.COD and p.TIPO = 'ESTGUIA' ");

		
		sql_find_user.append("where e.nota<>'' and e.NOTA not in ('Terminando Ruta','Iniciando Ruta')  ");
		sql_find_user.append("and s.CODIGO_USUARIO_TRANSPORTE = ? ");	
		sql_find_user.append("order by e.CODIGO desc ");
		
  		Object[] params = new Object[] { codigoUsuarioTransporte };
    	lista = (List <Alertas> )jdbcTemplate.query(sql_find_user.toString(),params, new AlertaMapper());        	
   		return lista;	
	}

}
