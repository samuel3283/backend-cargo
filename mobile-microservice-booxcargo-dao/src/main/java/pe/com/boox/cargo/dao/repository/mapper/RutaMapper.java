package pe.com.boox.cargo.dao.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.Ruta;
import pe.com.boox.cargo.model.UsuarioEmpresa;


public class RutaMapper implements RowMapper<Ruta> {


	public Ruta mapRow(ResultSet rs, int rowNum) throws SQLException {
		Ruta bean = new Ruta();
		try{
			bean.setCodigo(rs.getInt(1));
			bean.setCodigoGuia(rs.getInt(2));
			bean.setCodigoChofer(rs.getInt(3));
			
			bean.setLat(rs.getString(4).trim());
			bean.setLng(rs.getString(5).trim());
			bean.setTipo(rs.getString(6));
			bean.setFecha(rs.getString(7));
			bean.setAlerta(rs.getString(8));
					
		} catch (Exception e) {
			e.printStackTrace();
		}				
          return bean;    
	}

}
