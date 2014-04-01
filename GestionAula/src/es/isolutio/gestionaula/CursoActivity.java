package es.isolutio.gestionaula;

import java.util.ArrayList;
import java.util.List;

import com.mobandme.ada.DataBinder;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import es.isolutio.gestionaula.modelo.ContextoAplicacion;
import es.isolutio.gestionaula.modelo.Curso;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



public class CursoActivity extends Activity {
	
	//el curso c es para crear un curso de forma manual
	Curso c;
	Curso curso;
	EditText edtCursoNombre;
	EditText edtCursoAcademia;

	
	public ArrayAdapter<Curso> adaptadorCurso;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_curso);
		
		
		//creacion de un curso de forma manual
		c = new Curso("c5454","a2","f2","f2","h2","h2","obs2");
    	try {
			c.salvar();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		edtCursoNombre= (EditText)findViewById(R.id.edtCursoNombre);
		edtCursoAcademia= (EditText)findViewById(R.id.edtCursoAcademia);
		cargarSpinnerCurso();
		
		
		
	
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.curso, menu);
		return true;
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
	
	
	private void cargarSpinnerCurso() {

        Spinner spin = (Spinner) findViewById(R.id.spinnerCurso);
        adaptadorCurso = new ArrayAdapter<Curso>(this, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adaptadorCurso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try {
        	ContextoAplicacion.Contexto.cursoSet.fill();
        	//listaCursos.addAll(ContextoAplicacion.Contexto.cursoSet);
        	
        	} catch (AdaFrameworkException e) {
            Log.e("IBA", "Exception was caught creating ApplicationDataContext", e);
            Toast.makeText(this, "Error loading album list", Toast.LENGTH_SHORT).show();
        }
        spin.setAdapter(adaptadorCurso);
        ContextoAplicacion.Contexto.cursoSet.setAdapter(adaptadorCurso);

        spin.setOnItemSelectedListener( new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				curso =(Curso) ContextoAplicacion.Contexto.cursoSet.get(pos);
		        try {
					curso.bind(CursoActivity.this);
				} catch (AdaFrameworkException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void GuardarCurso(View v){
		try {
			curso.bind(this, DataBinder.BINDING_UI_TO_ENTITY);
			if (curso.validate(this)) {
				curso.salvar();
			} else {
				Toast toast = Toast.makeText(this, curso.getValidationResultString(), Toast.LENGTH_SHORT);
		        toast.show();
			}
			
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void LimpiarCurso(View v){
		edtCursoNombre.setText("");
		edtCursoAcademia.setText("");
		
	}
	
	public void EliminarCurso(View v){
		try {
			curso.eliminar();
			curso.salvar();
			cargarSpinnerCurso();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}

