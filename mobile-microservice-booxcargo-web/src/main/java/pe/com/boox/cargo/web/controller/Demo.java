package pe.com.boox.cargo.web.controller;

public class Demo {

	public static void main(String[] args) {

		try {
			String velocidadMax = "80";
			double velocidad = Double.parseDouble(velocidadMax);
			double valor = Double.parseDouble("14.198659896850586");
			if(valor>velocidad) {
				System.out.println("Exceso de Velocidad.");
			}		
			
			
		} catch(Exception e) {
			System.out.println("Error Exceso de Velocidad:"+e.getMessage());			
		}
		

	}

}
