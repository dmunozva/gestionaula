package es.isolutio.gestionaula.modelo;

import java.io.Serializable;

import com.mobandme.ada.Entity;
import com.mobandme.ada.ObjectSet;
import com.mobandme.ada.annotations.Databinding;
import com.mobandme.ada.annotations.RequiredFieldValidation;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import es.isolutio.gestionaula.R;

@Table(name = "curso")
	public class Curso  extends Entity implements Serializable {
	
	@TableField(name = "nombre", datatype = DATATYPE_STRING, required = true)
	@Databinding(ViewId = R.id.edtCursoNombre)
	//@RequiredFieldValidation(messageResourceId=R.string.error_campo_requerido)
	public String nombre;
	@TableField(name = "academia", datatype = DATATYPE_STRING, required = true)
	@Databinding(ViewId = R.id.edtCursoAcademia)
	public String academia;
	@TableField(name = "fechaInicio", datatype = DATATYPE_STRING, required = true)
	//@Databinding(ViewId = R.id.edtCursoFechaInicio)
	public String fechaInicio;
	@TableField(name = "fechaFin", datatype = DATATYPE_TEXT, required = true)
	//@Databinding(ViewId = R.id.edtCursoFechaFin)
	public String fechaFin;
	@TableField(name = "horaInicio", datatype = DATATYPE_TEXT, required = true)
	//@Databinding(ViewId = R.id.edtCursoHoraFin)
	public String horaInicio;
	@TableField(name = "horaFin", datatype = DATATYPE_TEXT, required = true)
	//@Databinding(ViewId = R.id.edtCursoHoraFin)
	public String horaFin;
	@TableField(name = "observaciones", datatype = DATATYPE_TEXT)
	//@Databinding(ViewId = R.id.edtCursoObservaciones)
	public String observaciones;
	
	
	public Curso() {
		super();
	}
	

	public Curso(String nombre, String academia, String fecha_inicio,
			String fecha_fin, String hora_inicio, String hora_fin,
			String observaciones) {
		super();
		this.nombre = nombre;
		this.academia = academia;
		this.fechaInicio = fecha_inicio;
		this.fechaFin = fecha_fin;
		this.horaInicio = hora_inicio;
		this.horaFin = hora_fin;
		this.observaciones = observaciones;
	}
		
	
	
	public void salvar() throws AdaFrameworkException {
		if (this.getID() == null) {
			this.setStatus(Entity.STATUS_NEW);
			
		} else {
			this.setStatus(Entity.STATUS_UPDATED);
		}
    	
    	ContextoAplicacion.Contexto.cursoSet.save(this);
    	
    	//ContextoAplicacion.Contexto.cursoSet.
    }
	
	
	public void eliminar() throws AdaFrameworkException {
    	this.setStatus(Entity.STATUS_DELETED);
    	ContextoAplicacion.Contexto.cursoSet.save(this);
    }
	

	public String toString() {
		return "CURSO: ("+this.getID()+") "+this.nombre+" "+this.academia;
	}

}
