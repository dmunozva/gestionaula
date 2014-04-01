package es.isolutio.gestionaula.modelo;

import java.io.Serializable;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Databinding;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import es.isolutio.gestionaula.R;

@Table(name ="alumno")
public class Alumno extends Entity implements Serializable {

	/*Nobre del usuario*/
	@TableField(name = "nombre", datatype = Entity.DATATYPE_STRING)
	@Databinding(ViewId = R.id.edtAlumnoNombre)
	private String nombre;
	
	/*Apellido del alumno*/
	@TableField(name = "apellidos", datatype = Entity.DATATYPE_STRING)
	@Databinding(ViewId = R.id.edtAlumnoApellidos)
	private String apellido;
	
	/*dni del alumno*/
	@TableField(name = "dni", datatype = Entity.DATATYPE_STRING )
	//@Databinding(ViewId = R.id.edtAlumnoDni)
	private String dni;
	
	/*Foto del alumno*/
	@TableField(name = "fotografia", datatype = Entity.DATATYPE_STRING)
	//@Databinding(ViewId = R.id.edtAlumnoFoto)
	private String foto;
	
	/*Conocimientos previos*/
	@TableField(name = "conocimientosPrevios", datatype = Entity.DATATYPE_STRING)
	//@Databinding(ViewId = R.id.edtAlumnoConocPrevios)
	private String conoc_previos;
	
	public Alumno(){
		
	}

	public Alumno(String nombre, String apellido, String dni, String foto,
			String conoc_previos) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.foto = foto;
		this.conoc_previos = conoc_previos;
	}
	
	
	public void salvar() throws AdaFrameworkException {
		if (this.getID() == null) {
			this.setStatus(Entity.STATUS_NEW);
		} else {
			this.setStatus(Entity.STATUS_UPDATED);
		}
    	
    	ContextoAplicacion.Contexto.alumnoSet.save(this);
    }
	
	public void eliminar() throws AdaFrameworkException {
    	this.setStatus(Entity.STATUS_DELETED);
    	ContextoAplicacion.Contexto.alumnoSet.save(this);
    }
}
