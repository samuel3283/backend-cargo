package pe.com.boox.cargo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Waypoints {

	private double lat;
	private double lng;
	@JsonInclude(Include.NON_NULL)
	private String nota;
	@JsonInclude(Include.NON_NULL)
	private String fecha;
		
	
	public Waypoints(double lat, double lng, String nota, String fecha) {
		super();
		this.lat = lat;
		this.lng = lng;
		this.nota = nota;
		this.fecha = fecha;
	}
	public Waypoints(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}
	public Waypoints() {
		super();
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
}
