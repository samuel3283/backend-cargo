package pe.com.boox.cargo.model;

public class TipoCarga {
	
	private String codigo;;
	private String descripcion;
	private boolean check;
	
	public TipoCarga(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	public TipoCarga() {
		super();
		this.check = false;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	@Override
	public String toString() {
		return "BeanCheck [codigo=" + codigo + ", descripcion=" + descripcion + ", check=" + check + "]";
	}
	
	
}
