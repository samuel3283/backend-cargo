package pe.com.boox.cargo.dao.repository.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.GuiaDetalle;


public class GuiaDetalleMapper implements RowMapper<GuiaDetalle> {

	public GuiaDetalle mapRow(ResultSet rs, int rowNum) throws SQLException {
		GuiaDetalle bean = new GuiaDetalle();
		try{
			bean.setCodigo(rs.getInt(1));
			bean.setCodigoGuia(rs.getInt(2));

			bean.setCantidad(rs.getInt(3));
			bean.setDescripcion(rs.getString(4));			
			
			bean.setPeso(rs.getString(5));
			bean.setUnidad(rs.getString(6));
	
			BigDecimal costo = rs.getBigDecimal(7);
			costo = costo.setScale(2);
			System.out.println("costo:::"+costo);
			
			bean.setCosto(String.valueOf(costo));	
			bean.setFecRegistro(rs.getString(8));			
			
			bean.setTipoCarga(rs.getString(9));
			bean.setDesTipoCarga(rs.getString(10));
			
		} catch (Exception e) {
			System.out.println("Error Mapper::::"+e.getMessage());
			e.printStackTrace();
		}				
          return bean;    
	}

}
