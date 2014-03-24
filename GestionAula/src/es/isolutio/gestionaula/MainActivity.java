package es.isolutio.gestionaula;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void salirIM(View v ){
		//comentario
		//comentario 2
		this.finish();
	}
	public void salir(View v) {
		this.finish();
	}
	public void salirireneblanca12(View v) {
		//modificoaaaa111
		this.finish();
	}
	
	
}
