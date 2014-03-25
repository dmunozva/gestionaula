package es.isolutio.gestionaula.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mobandme.ada.ObjectContext;
import com.mobandme.ada.ObjectSet;
import com.mobandme.ada.exceptions.AdaFrameworkException;

public class ContextoDatos extends ObjectContext {

	private final static String TAG = "ContextoDatos";

	public ObjectSet cursoSet;
	public ObjectSet alumnoSet;
	
	public ContextoDatos(Context pContext){ 
		super(pContext, "gestionAula.db"); 

		inicializar();
	}

	private void inicializar(){
		//Enable DataBase Transactions to be used by the Save process.
		this.setUseTransactions(true);

		//Enable the creation of DataBase table indexes.
		this.setUseTableIndexes(true);

		//Enable LazyLoading capabilities.
		//this.useLazyLoading(true);

		//Set a custom encryption algorithm.
		this.setEncryptionAlgorithm("AES");

		//Set a custom encryption master pass phrase.
		//this.setMasterEncryptionKey("com.mobandme.ada.examples.advanced");

		try{
			if (cursoSet==null)
				cursoSet = new ObjectSet(Curso.class, this);
			if (alumnoSet==null)
				alumnoSet = new ObjectSet(Alumno.class, this);
		}catch(Exception e){
			Log.e(TAG, e.getMessage());
		}		

	}
		

	@Override
	protected void onPopulate(SQLiteDatabase pDatabase) {
		super.onPopulate(pDatabase);		

	}
}
