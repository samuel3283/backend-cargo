package pe.com.boox.cargo.service.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Demo {

	public static String ucFirst(String str) {
	    if (str.isEmpty()) {
	        return str;            
	    } else {
	        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase(); 
	    }
	}
	public static void main(String[] args) {

		List<String> lista = new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7","8","9","10"));
		
		for(int i=0;i<lista.size()-1;i++) {
			
			System.out.println("["+i+"]==>"+lista.get(i)+", "+lista.get(i+1));
			
			
			//for(int j=1;j<lista.size();j++) {
							
		}
		
		/*
		System.out.println("==>valor::"+Util.getFecha("MM"));
		
		Calendar ahora = Calendar.getInstance();
		String valor = Util.getTimeFormat(ahora.getTime(), "YYYYMM");
		System.out.println("==>valor::"+valor.substring(0, 4));
		System.out.println("==>valor::"+valor.substring(4, 6));
		int res = Integer.valueOf(valor.substring(4, 6));
		System.out.println("==>valor::"+Util.getTimeFormat(ahora.getTime(), "YYYYMM"));
		for(int i=1;i<6;i++) {
			ahora.add(Calendar.MONTH, -1);			
			System.out.println("==>valor::"+Util.getTimeFormat(ahora.getTime(), "YYYYMM"));
		}
		*/
		/*
		String sCadena = "AVENIDA"; 
		System.out.println(ucFirst(sCadena));
		
		String valor="CS";
		System.out.println("==>valor::"+Util.getTipoCarga(valor));
		*/
		/*
		String name ="arbol.png";
		String valor = name.substring(name.lastIndexOf("."), name.length());
		System.out.println("==>valor::"+valor);
		String[] lista = name.split(".");
		System.out.println("==>lista::"+lista);
		int index = lista.length;
		System.out.println("==>index::"+index);
		String ext = lista[0];
		System.out.println("==>ext::"+ext);
		*/
	}

}
