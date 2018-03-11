package pe.com.boox.cargo.dao.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import pe.com.boox.cargo.model.GuiaEstado;


public class GuiaEstadoMapper implements RowMapper<GuiaEstado> {

	public GuiaEstado mapRow(ResultSet rs, int rowNum) throws SQLException {
		GuiaEstado bean = new GuiaEstado();

			bean.setCodigo(rs.getInt(1));
			bean.setCodigoGuia(rs.getInt(2));
			bean.setEstado(rs.getInt(3));
			bean.setNota(rs.getString(4));			
			bean.setFecRegistro(rs.getString(5));			
			bean.setDesEstado(rs.getString(6));
			
          return bean;    
	}

}
