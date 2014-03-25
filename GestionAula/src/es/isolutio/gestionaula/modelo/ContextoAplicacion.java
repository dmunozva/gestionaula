package es.isolutio.gestionaula.modelo;

import android.content.Context;

public class ContextoAplicacion {
	public static ContextoDatos Contexto;
	
	
	public static void inicializar(Context pContext) {
		if (Contexto == null) {
			Contexto = new ContextoDatos(pContext);
		}
	}
}