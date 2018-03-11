package pe.com.boox.cargo.dao.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.UsuarioEmpresa;


public class ChoferMapper implements RowMapper<Chofer> {


	public Chofer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Chofer bean = new Chofer();
		try{
			bean.setCodigo(rs.getInt(1));
			bean.setCodigoUsuarioTransporte(rs.getInt(2));
			bean.setNombre(rs.getString(3));
			bean.setApellido(rs.getString(4));			
			bean.setBrevete(rs.getString(5));
			bean.setTipo(rs.getString(6));
			bean.setTelefono(rs.getString(7));			
			bean.setEmail(rs.getString(8));
			bean.setTipoDocumento(rs.getString(9));
			bean.setNumDocumento(rs.getString(10));
			bean.setFechaRegistro(rs.getString(11));			
			bean.setFotoBrevete(rs.getString(12));
			
			bean.setMulta(rs.getString(13));
			bean.setInfraccionGrave(rs.getString(14));
			bean.setInfraccionMuyGrave(rs.getString(15));
			bean.setExpiracionBrevete(rs.getString(16));
			
		} catch (Exception e) {
			e.printStackTrace();
		}				
          return bean;    
	}

}
