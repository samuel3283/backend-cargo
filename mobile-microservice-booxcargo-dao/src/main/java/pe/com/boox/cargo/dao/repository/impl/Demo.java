package pe.com.boox.cargo.dao.repository.impl;

import java.util.ArrayList;
import java.util.List;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> recorrido = new ArrayList<String>();

		for(int z=1;z<=200;z++) {
			recorrido.add(String.valueOf(z));
		}
		
		int rango = recorrido.size() / 20;
		int cont=0;
		System.out.println("rango:"+rango);
		
		for(int i=0;i<recorrido.size();i=i+rango) {
			cont++;
			if(i%rango==0) {
				String valor = recorrido.get(i);
				System.out.println("["+cont+"]"+"VALOR:"+valor);
			}

		}
		
		
		
	}

}
