package es.isolutio.gestionaula;

import com.mobandme.ada.DataBinder;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import es.isolutio.gestionaula.modelo.Alumno;
import es.isolutio.gestionaula.modelo.ContextoAplicacion;
import es.isolutio.gestionaula.modelo.Curso;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AlumnoActivity extends Activity {

	Alumno a;
	Alumno alumno;
	EditText edtAlumnoNombre;
	EditText edtAlumnoApellidos;
	public ArrayAdapter<Alumno> adaptadorAlumno;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alumno);

		a = new Alumno("A1","aa","fafd","faf","fda");

		try {
			a.salvar();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		edtAlumnoNombre = (EditText) findViewById(R.id.edtAlumnoNombre);
		edtAlumnoApellidos = (EditText) findViewById(R.id.edtAlumnoApellidos);
		cargarSpinnerAlumno();


	}

	public void accion(View v) {
		try {
			a.bind(this, DataBinder.BINDING_UI_TO_ENTITY);
			a.salvar();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("IMR",a.toString());
	}


	private void cargarSpinnerAlumno() {

		Spinner spAlumno = (Spinner) findViewById(R.id.spinnerAlumno);
		adaptadorAlumno = new ArrayAdapter<Alumno>(this, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adaptadorAlumno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		try {
			ContextoAplicacion.Contexto.alumnoSet.fill();
			//listaCursos.addAll(ContextoAplicacion.Contexto.cursoSet);

		} catch (AdaFrameworkException e) {
			Log.e("IMR", "Exception was caught creating ApplicationDataContext", e);
			Toast.makeText(this, "ERROR AL CARGAR LISTA", Toast.LENGTH_SHORT).show();
		}
		spAlumno.setAdapter(adaptadorAlumno);
		ContextoAplicacion.Contexto.alumnoSet.setAdapter(adaptadorAlumno);

		spAlumno.setOnItemSelectedListener( new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				alumno =(Alumno) ContextoAplicacion.Contexto.alumnoSet.get(pos);
				try {
					alumno.bind(AlumnoActivity.this);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alumno, menu);
		return true;
	}

}
