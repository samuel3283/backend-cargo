package pe.com.boox.cargo.dao.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.UsuarioEmpresa;


public class UsuarioEmpresaMapper implements RowMapper<UsuarioEmpresa> {

	public UsuarioEmpresa mapRow(ResultSet rs, int rowNum) throws SQLException {
		UsuarioEmpresa bean = new UsuarioEmpresa();
		try{
			bean.setCodigo(rs.getInt(1));
			bean.setRuc(rs.getString(2));
			bean.setNombre(rs.getString(3));
			bean.setApellido(rs.getString(4));
			bean.setEmail(rs.getString(5));
			bean.setPassword(rs.getString(6));
			bean.setTipoDocumento(rs.getString(7));
			bean.setNumDocumento(rs.getString(8));
			bean.setTelefono(rs.getString(9));			
			bean.setFechaRegistro(rs.getString(10));			
			
			Empresa empresa = new Empresa();
			empresa.setRuc(bean.getRuc());
			empresa.setRazonSocial(rs.getString(12));
			bean.setEmpresa(empresa);
			
		} catch (Exception e) {
			e.printStackTrace();
		}				
          return bean;    
	}

}
