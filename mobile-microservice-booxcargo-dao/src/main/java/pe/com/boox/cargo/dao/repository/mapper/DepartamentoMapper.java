package pe.com.boox.cargo.dao.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import pe.com.boox.cargo.model.BeanUtil;
import pe.com.boox.cargo.model.Empresa;


public class DepartamentoMapper implements RowMapper<BeanUtil> {

	public BeanUtil mapRow(ResultSet rs, int rowNum) throws SQLException {
		BeanUtil bean = new BeanUtil();
		try{
			bean.setCodigo(rs.getString(1));
			bean.setDescripcion(rs.getString(2));

		} catch (Exception e) {
			e.printStackTrace();
		}				
          return bean;    
	}

}
