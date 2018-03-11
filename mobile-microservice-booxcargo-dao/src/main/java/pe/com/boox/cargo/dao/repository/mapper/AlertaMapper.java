package pe.com.boox.cargo.dao.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import pe.com.boox.cargo.model.Alertas;
import pe.com.boox.cargo.model.GuiaEstado;


public class AlertaMapper implements RowMapper<Alertas> {

	public Alertas mapRow(ResultSet rs, int rowNum) throws SQLException {
		Alertas bean = new Alertas();

			bean.setCodigo(rs.getInt(1));
			bean.setCodigoGuia(rs.getInt(2));
			bean.setAlerta(rs.getString(3));
			bean.setEstado(rs.getInt(4));			
			bean.setFecRegistro(rs.getString(5));			
			bean.setDesEstado(rs.getString(6));			
			bean.setNumGuia(rs.getString(7));			

          return bean;    
	}

}
