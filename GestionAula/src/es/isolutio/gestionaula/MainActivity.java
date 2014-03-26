package es.isolutio.gestionaula;

import com.mobandme.ada.DataBinder;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import es.isolutio.gestionaula.modelo.Curso;
import es.isolutio.gestionaula.modelo.ContextoAplicacion;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {

	Curso c;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
        	ContextoAplicacion.inicializar(this);
        	
        	c = new Curso("c1","a1","f1","f2","h1","h2","obs");
        	//c.salvar();
        	c.bind(this);
			Log.i("DMV",c.toString());
			
			
			

			
			ContextoAplicacion.Contexto.cursoSet.fill();
			for(int i=0; i<ContextoAplicacion.Contexto.cursoSet.size(); i++) {
				Curso c2 = (Curso)ContextoAplicacion.Contexto.cursoSet.get(i);
				Log.i("DMV2",c2.toString());
			}
			/*
			Curso cmodif = (Curso)ContextoAplicacion.Contexto.cursoSet.getElementByID((long)2);
			cmodif.nombre = "nuevoNombre2";
			cmodif.salvar();
			

			
			ContextoAplicacion.Contexto.cursoSet.fill();
			for(int i=0; i<ContextoAplicacion.Contexto.cursoSet.size(); i++) {
				Curso c2 = (Curso)ContextoAplicacion.Contexto.cursoSet.get(i);
				Log.i("DMV3",c2.toString());
			}
			
			cmodif.eliminar();

			
			ContextoAplicacion.Contexto.cursoSet.fill();
			for(int i=0; i<ContextoAplicacion.Contexto.cursoSet.size(); i++) {
				Curso c2 = (Curso)ContextoAplicacion.Contexto.cursoSet.get(i);
				Log.i("DMV4",c2.toString());
			}
			*/
			
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    public void accion(View v) {
		try {
			c.bind(this, DataBinder.BINDING_UI_TO_ENTITY);
			c.salvar();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("DMV",c.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    

    
    
    
    
    
}
