package pe.com.boox.cargo.dao.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import pe.com.boox.cargo.model.Empresa;


public class EmpresaMapper implements RowMapper<Empresa> {

	public Empresa mapRow(ResultSet rs, int rowNum) throws SQLException {
		Empresa bean = new Empresa();
		try{
			bean.setCodigo(rs.getInt(1));
			bean.setRazonSocial(rs.getString(2));
			bean.setRuc(rs.getString(3));
			bean.setEstado(rs.getInt(4));
			bean.setFecRegistro(rs.getString(5));			

		} catch (Exception e) {
			e.printStackTrace();
		}				
          return bean;    
	}

}
