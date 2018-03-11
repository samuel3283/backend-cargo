package pe.com.boox.cargo.dao.repository.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.Subasta;
import pe.com.boox.cargo.model.UsuarioTransporte;

public class SubastaMapper implements RowMapper<Subasta> {

	public Subasta mapRow(ResultSet rs, int rowNum) throws SQLException {
		Subasta bean = new Subasta();
		try{
			bean.setCodigo(rs.getInt(1));
			
			bean.setCodigoGuia(rs.getInt(2));
			bean.setCodigoUsuarioTransporte(rs.getInt(3));
			UsuarioTransporte usuarioTransporte = new UsuarioTransporte();
			usuarioTransporte.setCodigo(rs.getInt(3));


			BigDecimal flete = rs.getBigDecimal(4);
			flete = flete.setScale(2);
			
			bean.setMonto(String.valueOf(flete));
			bean.setFecRegistro(rs.getString(5));			
			bean.setPlaca(rs.getString(6));			
			bean.setBrevete(rs.getString(7));						
			
			bean.setUsuarioTransporte(usuarioTransporte);

		} catch (Exception e) {
			e.printStackTrace();
		}				
          return bean;    
	}
	
	public String ucFirst(String str) {
	    if (str.isEmpty()) {
	        return str;            
	    } else {
	        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase(); 
	    }
	}

}
