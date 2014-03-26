package es.isolutio.gestionaula.modelo;

import java.io.Serializable;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Databinding;
import com.mobandme.ada.annotations.RequiredFieldValidation;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import es.isolutio.gestionaula.R;

@Table(name = "curso")
	public class Curso  extends Entity implements Serializable {
	
	@TableField(name = "nombre", datatype = DATATYPE_STRING, required = true)
//	@Databinding(ViewId = R.id.edtCursoNombre)
//	@RequiredFieldValidation(messageResourceId=R.string.error_campo_requerido)
	public String nombre;
	@TableField(name = "academia", datatype = DATATYPE_STRING, required = true)
//	@Databinding(ViewId = R.id.edtAcademiaCurso)
//	@RequiredFieldValidation(messageResourceId=R.string.error_campo_requerido)
	public String academia;
	@TableField(name = "fechaInicio", datatype = DATATYPE_STRING, required = true)
//	@Databinding(ViewId = R.id.edtFechaInicioCurso)
//	@RequiredFieldValidation(messageResourceId=R.string.error_campo_requerido)
	public String fechaInicio;
	@TableField(name = "fechaFin", datatype = DATATYPE_TEXT, required = true)
//	@Databinding(ViewId = R.id.edtFechaFinCurso)
//	@RequiredFieldValidation(messageResourceId=R.string.error_campo_requerido)
	public String fechaFin;
	@TableField(name = "horaInicio", datatype = DATATYPE_TEXT, required = true)
//	@Databinding(ViewId = R.id.edtHoraFinCurso)
//	@RequiredFieldValidation(messageResourceId=R.string.error_campo_requerido)
	public String horaInicio;
	@TableField(name = "horaFin", datatype = DATATYPE_TEXT, required = true)
//	@Databinding(ViewId = R.id.edtHoraFinCurso)
//	@RequiredFieldValidation(messageResourceId=R.string.error_campo_requerido)
	public String horaFin;
	@TableField(name = "observaciones", datatype = DATATYPE_TEXT)
//	@Databinding(ViewId = R.id.edtObservaciones)
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
	
	
	
//	//Insertamos la entidad curso.
//	Curso curso = new Curso();
//	curso.setNombre("GESTIÓN DE RECURSOS");
//	curso.setNumeroMaximoAlumnos(50);
//	curso.setPrecioHora(2.25);			
//	curso.setStatus(Entity.STATUS_NEW);			
//													
//	contextoDatos.cursoDao.add(curso);
//	contextoDatos.cursoDao.save();
//		
//	//Eliminamos el curso que hemos añadido. 
//	curso1 = contextoDatos.cursoDao.get(0);
//	curso1.setStatus(Entity.STATUS_DELETED);
//	contextoDatos.cursoDao.save();
//		
//	//Actualizamos una entidad.
//	curso = contextoDatos.cursoDao.get(0);
//	curso.setNumeroMaximoAlumnos(35);
//	curso.setStatus(Entity.STATUS_UPDATED);
//	contextoDatos.cursoDao.save();

}
