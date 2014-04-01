package es.isolutio.gestionaula;

import es.isolutio.gestionaula.modelo.ContextoAplicacion;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContextoAplicacion.inicializar(this);
    }

    
	public void irAAlumnos(View v){
		Intent intent = new Intent(MainActivity.this, AlumnoActivity.class);
		startActivity(intent);
	}
		
    
	public void irACurso(View v){
		Intent intent= new Intent(MainActivity.this, CursoActivity.class);
		startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    

    
    
    
    
    
}
