package pe.com.boox.cargo.model;

public class Coordenadas {

	private Location origen;
	private Location fin;
	
	public Coordenadas() {
		super();
	}

	public Coordenadas(Location origen, Location fin) {
		super();
		this.origen = origen;
		this.fin = fin;
	}

	public Location getOrigen() {
		return origen;
	}

	public void setOrigen(Location origen) {
		this.origen = origen;
	}

	public Location getFin() {
		return fin;
	}

	public void setFin(Location fin) {
		this.fin = fin;
	}

	
	
}
