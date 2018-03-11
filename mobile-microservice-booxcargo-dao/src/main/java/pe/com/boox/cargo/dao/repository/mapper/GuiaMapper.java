package pe.com.boox.cargo.dao.repository.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import pe.com.boox.cargo.model.Guia;

public class GuiaMapper implements RowMapper<Guia> {

	public Guia mapRow(ResultSet rs, int rowNum) throws SQLException {
		Guia bean = new Guia();
		try{
			bean.setCodigo(rs.getInt(1));
			bean.setRuc(rs.getString(2));
			bean.setNumeroGuia(rs.getString(3));
			
			bean.setViaTipoOrigen(rs.getString(4));
			bean.setViaNombreOrigen(rs.getString(5));			
			bean.setNumeroOrigen(rs.getString(6));
			bean.setInteriorOrigen(rs.getString(7));
			bean.setZonaOrigen(rs.getString(8));
			bean.setDistritoOrigen(rs.getString(9));
			bean.setProvinciaOrigen(rs.getString(10));
			bean.setDepartamentoOrigen(rs.getString(11));

			
			bean.setViaTipoDestino(rs.getString(12));
			bean.setViaNombreDestino(rs.getString(13));			
			bean.setNumeroDestino(rs.getString(14));
			bean.setInteriorDestino(rs.getString(15));
			bean.setZonaDestino(rs.getString(16));
			
			bean.setDistritoDestino(rs.getString(17));
			bean.setProvinciaDestino(rs.getString(18));
			bean.setDepartamentoDestino(rs.getString(19));
			
			bean.setRazonSocialRemitente(rs.getString(20));
			bean.setRucRemitente(rs.getString(21));
			bean.setTipoDocRemitente(rs.getString(22));
			bean.setNumeroDocRemitente(rs.getString(23));
			
			bean.setRazonSocialDestinatario(rs.getString(24));
			bean.setRucDestinatario(rs.getString(25));
			bean.setTipoDocDestinatario(rs.getString(26));
			bean.setNumeroDocDestinatario(rs.getString(27));
	
			BigDecimal flete = rs.getBigDecimal(28);
			flete = flete.setScale(2);
			
			bean.setFlete(String.valueOf(flete));

			bean.setFechaEmision(rs.getString(29));
			bean.setFechaIniTraslado(rs.getString(30));
			bean.setFechaFinTraslado(rs.getString(31));

			bean.setFecRegistro(rs.getString(32));			
			bean.setNombreContactoRemitente(rs.getString(33));			
			bean.setTelefonoContactoRemitente(rs.getString(34));			
			bean.setNombreContactoDestinatario(rs.getString(35));			
			bean.setTelefonoContactoDestinatario(rs.getString(36));			
			
			bean.setDesViaTipoOrigen(ucFirst(rs.getString(37)));			
			bean.setDesViaTipoDestino(ucFirst(rs.getString(38)));			
			bean.setRazonSocial(rs.getString(39));			

			bean.setDesZonaOrigen(rs.getString(40));			
			bean.setDesZonaDestino(rs.getString(41));			
			bean.setDesDistritoOrigen(rs.getString(42));			
			bean.setDesDistritoDestino(rs.getString(43));			
			bean.setDesProvinciaOrigen(rs.getString(44));			
			bean.setDesProvinciaDestino(rs.getString(45));			
			bean.setDesDepartamentoOrigen(rs.getString(46));			
			bean.setDesDepartamentoDestino(rs.getString(47));			
			bean.setFechaIniTraslado(rs.getString(48));			
			bean.setHoraIniTraslado(rs.getString(49));			
			bean.setFechaFinTraslado(rs.getString(50));			
			bean.setHoraFinTraslado(rs.getString(51));			
			bean.setDesEstado(rs.getString(52));			
			bean.setEstado(rs.getInt(53));			
			
			
			bean.setLatOri(Double.parseDouble(rs.getString(54)));			
			bean.setLngOri(Double.parseDouble(rs.getString(55)));			
			bean.setLatDes(Double.parseDouble(rs.getString(56)));			
			bean.setLngDes(Double.parseDouble(rs.getString(57)));			

			bean.setDireccionOrigen(rs.getString(58));			
			bean.setDireccionDestino(rs.getString(59));			

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
