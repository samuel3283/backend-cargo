package pe.com.boox.cargo.dao.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import pe.com.boox.cargo.model.Session;
import pe.com.boox.cargo.model.SessionChofer;
import pe.com.boox.cargo.model.SessionTransportista;



public class SessionChoferMapper implements RowMapper<SessionChofer> {

	public SessionChofer mapRow(ResultSet rs, int rowNum) throws SQLException {
		SessionChofer bean = new SessionChofer();
		try{
			bean.setCodigo(rs.getInt(1));
			bean.setDevice(rs.getString(2));
			bean.setDeviceType(rs.getString(3));
			bean.setToken(rs.getString(4));
			
			bean.setNombre(rs.getString(5));
			bean.setApellido(rs.getString(6));
			bean.setEmail(rs.getString(7));
			bean.setTipoDocumento(rs.getString(8));
			bean.setNumDocumento(rs.getString(9));

			bean.setBrevete(rs.getString(10));
			bean.setTelefono(rs.getString(11));
			bean.setEstado(rs.getInt(12));
						
			bean.setFechaExpira(rs.getString(13));
			bean.setFechaRegistro(rs.getString(14));
			bean.setFechaModifica(rs.getString(15));
			bean.setFoto(rs.getString(16));

		} catch (Exception e) {
			e.printStackTrace();
		}				
          return bean;    
	}

}
