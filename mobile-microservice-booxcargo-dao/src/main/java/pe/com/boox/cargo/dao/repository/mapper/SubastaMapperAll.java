package pe.com.boox.cargo.dao.repository.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import pe.com.boox.cargo.model.BeanUtil;
import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Empresa;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.Subasta;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.model.VehiculoTransporte;

public class SubastaMapperAll implements RowMapper<Subasta> {

	public Subasta mapRow(ResultSet rs, int rowNum) throws SQLException {
		Subasta bean = new Subasta();
		try{
			bean.setCodigo(rs.getInt(1));
			bean.setPlaca(rs.getString(2));			
			bean.setBrevete(rs.getString(3));			
			BigDecimal flete = rs.getBigDecimal(4);
			flete = flete.setScale(2);
			bean.setMonto(String.valueOf(flete));

			Empresa empresa = new Empresa();
			empresa.setRuc(rs.getString(6));
			empresa.setRazonSocial(rs.getString(39));

			bean.setCodigoGuia(rs.getInt(40));
			bean.setCodigoUsuarioTransporte(rs.getInt(41));
			
			UsuarioTransporte usuarioTransporte = new UsuarioTransporte();
			usuarioTransporte.setCodigo(rs.getInt(5));
			usuarioTransporte.setRuc(rs.getString(6));
			usuarioTransporte.setNombre(rs.getString(7));
			usuarioTransporte.setApellido(rs.getString(8));
			
			usuarioTransporte.setEmpresa(empresa);
			bean.setUsuarioTransporte(usuarioTransporte);

			VehiculoTransporte vehiculoTransporte = new VehiculoTransporte();
			vehiculoTransporte.setCodigo(rs.getInt(9));
			vehiculoTransporte.setPlaca(rs.getString(11));
			vehiculoTransporte.setMarca(rs.getString(12));
			vehiculoTransporte.setModelo(rs.getString(13));
			vehiculoTransporte.setCapacidad(rs.getString(14));
			vehiculoTransporte.setAnio(rs.getString(15));

			vehiculoTransporte.setAlto(rs.getString(16));
			vehiculoTransporte.setAncho(rs.getString(17));
			vehiculoTransporte.setLargo(rs.getString(18));
			vehiculoTransporte.setKilometraje(rs.getString(19));
			vehiculoTransporte.setHorometro(rs.getString(20));
			vehiculoTransporte.setVencimientoSoat(rs.getString(21));
			vehiculoTransporte.setVencimientoRt(rs.getString(22));
			vehiculoTransporte.setMantenimientoPreventivo(rs.getString(23));
			vehiculoTransporte.setFoto1(rs.getString(43));

			Chofer chofer = new Chofer();
			chofer.setCodigo(rs.getInt(24));
			chofer.setNombre(rs.getString(25));
			chofer.setApellido(rs.getString(26));

			chofer.setBrevete(rs.getString(27));
			chofer.setTipo(rs.getString(28));
			chofer.setTelefono(rs.getString(29));
			
			chofer.setEmail(rs.getString(30));
			chofer.setTipoDocumento(rs.getString(31));
			chofer.setNumDocumento(rs.getString(32));
			chofer.setFoto(rs.getString(33));
			chofer.setFotoBrevete(rs.getString(34));
			
			chofer.setMulta(rs.getString(35));
			chofer.setInfraccionGrave(rs.getString(36));
			chofer.setInfraccionMuyGrave(rs.getString(37));
			chofer.setExpiracionBrevete(rs.getString(38));
			chofer.setFotoBrevete(rs.getString(42));
			chofer.setUsuarioTransporte(null);

			vehiculoTransporte.setChofer(chofer);
			//vehiculoTransporte.setUsuarioTransporte(usuarioTransporte);
			vehiculoTransporte.setUsuarioTransporte(null);
			vehiculoTransporte.setTipoCarga(null);
			vehiculoTransporte.setListaTipoCarga(null);
			
			//bean.setChofer(chofer);
			bean.setVehiculoTransporte(vehiculoTransporte);


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
