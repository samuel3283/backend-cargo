package pe.com.boox.cargo.dao.repository.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import pe.com.boox.cargo.model.Guia;

public class GuiaTiempoMapper implements RowMapper<Map<String, Object>> {

	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		Map<String, Object> bean = new HashMap<>();
		try{
			//bean.setCodigo(rs.getInt(1));
			bean.put("tiempo",rs.getString(1));
			bean.put("registro",rs.getString(2));
			bean.put("modificacion",rs.getString(3));
			bean.put("numGuia",rs.getString(5));
			bean.put("brevete",rs.getString(6));
			bean.put("placa",rs.getString(7));

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
