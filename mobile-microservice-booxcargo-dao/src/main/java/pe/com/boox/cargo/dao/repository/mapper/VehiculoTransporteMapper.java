package pe.com.boox.cargo.dao.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.UsuarioEmpresa;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.model.VehiculoTransporte;


public class VehiculoTransporteMapper implements RowMapper<VehiculoTransporte> {

	public VehiculoTransporte mapRow(ResultSet rs, int rowNum) throws SQLException {
		VehiculoTransporte bean = new VehiculoTransporte();
		try{
			bean.setCodigo(rs.getInt(1));

			UsuarioTransporte objeto = new UsuarioTransporte();
			objeto.setCodigo(rs.getInt(2));
			
			bean.setPlaca(rs.getString(3));			
			bean.setTpropiedad(rs.getString(4));			
			bean.setSoat(rs.getString(5));			
			bean.setFoto1(rs.getString(6));			
			bean.setFoto2(rs.getString(7));			
			bean.setFoto3(rs.getString(8));			
			bean.setCategoria(rs.getString(9));			
			bean.setTipo(rs.getString(10));			
			
			bean.setAlto(rs.getString(11));			
			bean.setAncho(rs.getString(12));			
			bean.setLargo(rs.getString(13));			
			bean.setFechaRegistro(rs.getString(14));			

			bean.setMarca(rs.getString(15));			
			bean.setModelo(rs.getString(16));			
			bean.setCapacidad(rs.getString(17));			
			bean.setAnio(rs.getString(18));			
			
			Chofer chofer = new Chofer();
			chofer.setCodigo(rs.getInt(19));
			bean.setChofer(chofer);
						
			bean.setKilometraje(rs.getString(20));			
			bean.setHorometro(rs.getString(21));			
			bean.setMantenimientoPreventivo(rs.getString(22));			
			bean.setVencimientoSoat(rs.getString(23));			
			bean.setVencimientoRt(rs.getString(24));			
					
		} catch (Exception e) {
			e.printStackTrace();
		}				
          return bean;    
	}

}
