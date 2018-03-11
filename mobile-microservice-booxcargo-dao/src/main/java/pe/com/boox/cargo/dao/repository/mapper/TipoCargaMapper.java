package pe.com.boox.cargo.dao.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import pe.com.boox.cargo.model.BeanUtil;
import pe.com.boox.cargo.model.Empresa;


public class TipoCargaMapper implements RowMapper<BeanUtil> {

	public BeanUtil mapRow(ResultSet rs, int rowNum) throws SQLException {
		BeanUtil bean = new BeanUtil();
		try{
			bean.setCodigo(rs.getString(3));
			bean.setDescripcion(getTipoCarga(bean.getCodigo()));

		} catch (Exception e) {
			e.printStackTrace();
		}				
          return bean;    
	}

	public String getTipoCarga(String tipoCarga) {
		String resultado ="";
		List<BeanUtil> lista = new ArrayList<BeanUtil>();
		lista.add(new BeanUtil("CS","Carga Seca"));
		lista.add(new BeanUtil("LI","Liquidos"));
		lista.add(new BeanUtil("GA","Gases"));
		lista.add(new BeanUtil("QU","Químicos"));
		lista.add(new BeanUtil("GR","Granel"));
		lista.add(new BeanUtil("AV","Animales Vivos"));
		lista.add(new BeanUtil("VE","Vehiculos"));
		lista.add(new BeanUtil("MP","Maquina Pesada"));
		
		for(BeanUtil bean: lista ) {
			
			if(bean.getCodigo().equals(tipoCarga)) {
				resultado = bean.getDescripcion();
			}			
			
		}
		return resultado;
	}
	
}
