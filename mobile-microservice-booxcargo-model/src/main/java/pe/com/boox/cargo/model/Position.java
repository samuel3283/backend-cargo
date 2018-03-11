package pe.com.boox.cargo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Position {

	private Location location;
	@JsonInclude(Include.NON_NULL)
	private Boolean stopover;
	
	public Position() {
		super();
	}

	
	public Position(Location location, Boolean stopover) {
		super();
		this.location = location;
		this.stopover = stopover;
	}


	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}


	public Boolean getStopover() {
		return stopover;
	}


	public void setStopover(Boolean stopover) {
		this.stopover = stopover;
	}
	
	
	
}
