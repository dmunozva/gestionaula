package es.isolutio.gestionaula.modelo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.content.SharedPreferences;

import com.mobandme.ada.Entity;
import com.mobandme.ada.ObjectSet;
import com.mobandme.ada.annotations.CustomValidation;
import com.mobandme.ada.annotations.Databinding;
import com.mobandme.ada.annotations.RequiredFieldValidation;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;
import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.mobandme.ada.validators.Validator;

import es.isolutio.puntopadel.R;
import es.isolutio.puntopadel.extras.AccesoInternet;
import es.isolutio.puntopadel.extras.Sincronizador;
import es.isolutio.puntopadel.modelo.Partido;

@Table(name = "usuario")
public class Usuario  extends Entity implements Serializable {

	@TableField(name = "nombre", datatype = DATATYPE_TEXT, required = true)
	@Databinding(ViewId = R.id.edTNombreUsu)
	@RequiredFieldValidation(messageResourceId=R.string.error_campo_requerido)
    public String nombre;
	@TableField(name = "email", datatype = DATATYPE_TEXT, required = true)
	@Databinding(ViewId = R.id.edTEmail)
    public String email;
	@TableField(name = "login", datatype = DATATYPE_TEXT, required = true)
	//@CustomValidation(validator = ValidadorUsuarioUnico.class, messageResourceId = R.string.error_campo_requerido)
	@RequiredFieldValidation(messageResourceId=R.string.error_campo_requerido)
	@Databinding(ViewId = R.id.edTLogin)
    public String login;
	@TableField(name = "password", datatype = DATATYPE_TEXT, required = true)
	@RequiredFieldValidation(messageResourceId=R.string.error_campo_requerido)
	@Databinding(ViewId = R.id.edTPassword)
    public String password;
	@TableField(name = "ciudad", datatype = DATATYPE_LONG, required = false)
	//@Databinding(ViewId = R.id.spCiudad)
    public Long ciudad;
	@TableField(name = "pais", datatype = DATATYPE_LONG, required = false)
	//@Databinding(ViewId = R.id.spPais)
    public Long pais;
	@TableField(name = "aceptado", datatype = DATATYPE_BOOLEAN, required = true)
    public Boolean aceptado;
	// @TableField(name = "partidos", datatype = DATATYPE_ENTITY_LINK, required = true)
    //private List<Partido> partidos = new ArrayList<Partido>();
	// @TableField(name = "amigos", datatype = DATATYPE_ENTITY_LINK)
    //private List<Amigo> amigos = new ArrayList<Amigo>();
    
	
	private List<Partido> partidos;
	private List<Torneo> torneos;
	private List<Usuario> usuariosAmigos;
		

    public Boolean aceptadoComoAmigo = true; // Solo sirve cuando formna parte de una lista de amigos  
	
	
    public Usuario() {
        super();
        this.nombre = "";
        this.email = "";
        this.login = "";
        this.password = "";
        this.ciudad = (long)0;
        this.pais = (long)0;
        this.subido = false;
        this.aceptado = false;
        //this.partidos = new ArrayList<Partido>();
        //this.amigos = new ArrayList<Usuario>();
    }

    public Usuario(String nombre, String email, String login, String password, long ciudad, long pais, Boolean subido, Boolean aceptado) {
        super();
        this.nombre = nombre;
        this.email = email;
        this.login = login;
        this.password = password;
        this.ciudad = ciudad;
        this.pais = pais;
        this.subido = subido;
        this.aceptado = aceptado;
        //this.partidos = new ArrayList<Partido>();
        //this.amigos = new ArrayList<Usuario>();
    }
    
    public String toString() {
    	//return this.getID() + "-" + this.login + "(" + this.nombre + ")"; 
    	return this.nombre + " (" + this.login + ")"; 
    }
    
    public static Usuario usuarioDesconocido() {
    	Usuario usDesconocido = new Usuario();
    	usDesconocido.nombre = "Desconocido";
    	usDesconocido.login = "-";
    	
    	return usDesconocido;
    }
    
    public void eliminarDependientes() throws AdaFrameworkException {
    	// No sube a internet porque se tendrá en cuenta en servidor directamente
    	ContextoAplicacion.Contexto.amigoSet.fill("idamigo = ? or idusuario = ?", new String[] {this.ID.toString(), this.ID.toString()}, null);
    	for (Amigo a : (List<Amigo>)ContextoAplicacion.Contexto.amigoSet) {
    		a.setStatus(a.STATUS_DELETED);
    	}
    	ContextoAplicacion.Contexto.amigoSet.save();

    	for (Torneo t : this.getTorneos()) {
    		t.eliminarDependientes();
    		t.setStatus(t.STATUS_DELETED);
    	}
    	ContextoAplicacion.Contexto.torneoSet.save();
    	
    	for (Partido p : this.getPartidos()) {
    		p.setStatus(p.STATUS_DELETED);
    	}
    	ContextoAplicacion.Contexto.partidoSet.save();
    }
    
    public List<Partido> getPartidos() throws AdaFrameworkException {
    	if (partidos == null) {
    		partidos = new ArrayList<Partido>();
    		ContextoAplicacion.Contexto.partidoSet.fill("idusuario = ?", new String[] {String.valueOf(this.ID)}, "fecha DESC , fechaPrevista DESC");

    		partidos.addAll(ContextoAplicacion.Contexto.partidoSet);
    	}
    	return partidos;
    }
    
    public void resetPartidos() {
    	partidos = null;
    }
    
    public List<Partido> getPartidosJugados(Torneo torneo) throws AdaFrameworkException {
		List<Partido> partidosJugados = new ArrayList<Partido>();
		String clausulaWhere;
		String[] valores;
		String valorID = String.valueOf(this.ID);;
		if (torneo == null) {
			clausulaWhere = "idJugadorLA = ? OR idJugadorLB = ? OR idJugadorVA = ? OR idJugadorVB = ?";
			valores = new String[] {valorID,valorID,valorID,valorID};
		} else {
			clausulaWhere = "idTorneo = ? AND (idJugadorLA = ? OR idJugadorLB = ? OR idJugadorVA = ? OR idJugadorVB = ?)";
			valores = new String[] {String.valueOf(torneo.getID()), valorID,valorID,valorID,valorID};
		}
		ContextoAplicacion.Contexto.partidoSet.fill(clausulaWhere, valores, "fecha");

   		partidosJugados.addAll(ContextoAplicacion.Contexto.partidoSet);
    	
    	return partidosJugados;
    }
    
    public List<Torneo> getTorneos() throws AdaFrameworkException {
    	if (torneos == null) {
    		torneos = new ArrayList<Torneo>();
    		ContextoAplicacion.Contexto.torneoSet.fill("idusuario = ?", new String[] {String.valueOf(this.ID)}, null);

    		torneos.addAll(ContextoAplicacion.Contexto.torneoSet);
    	}
    	return torneos;
    }
    
    public List<Usuario> getAmigos() throws AdaFrameworkException {
    	if (usuariosAmigos == null) {
    		usuariosAmigos = new ArrayList<Usuario>();
    		ContextoAplicacion.Contexto.amigoSet.clear();
    		ContextoAplicacion.Contexto.amigoSet.fill("idusuario = ?", new String[] {String.valueOf(this.ID)}, "aceptado");

    		ObjectSet amigos = ContextoAplicacion.Contexto.amigoSet;
    		for (Object amigo : amigos) {
    			Usuario usAmigo = (Usuario) ContextoAplicacion.Contexto.usuarioSet.getElementByID(((Amigo)amigo).idamigo);
        		if (usAmigo != null) {
        			usAmigo.aceptadoComoAmigo = ((Amigo)amigo).aceptado;
        			usuariosAmigos.add(usAmigo);
        		}
        	}
    	}
    	return usuariosAmigos;
    }

	
	public void solicitarAmistad(Long idAmigo) throws AdaFrameworkException {
		ContextoAplicacion.Contexto.amigoSet.fill("idusuario = ? AND idamigo = ?",
				new String[]{String.valueOf(idAmigo), String.valueOf(this.ID)}, null);
		if (ContextoAplicacion.Contexto.amigoSet.size() == 0) {
			Amigo amigo = new Amigo();
			amigo.idusuario = idAmigo;
			amigo.idamigo = this.ID;
			amigo.aceptado = false;
			amigo.setStatus(Entity.STATUS_NEW);
			//ContextoAplicacion.Contexto.amigoSet.save(amigo);	
			amigo.salvar(null);
		}
	}
	
	public void aceptarAmistad(Usuario usuarioAmigo) throws AdaFrameworkException {
		ContextoAplicacion.Contexto.amigoSet.fill("idusuario = ? AND idamigo = ?",
				new String[]{String.valueOf(this.ID), String.valueOf(usuarioAmigo.getID())}, null);
		if (ContextoAplicacion.Contexto.amigoSet.size() > 0) {
			Amigo amigo = (Amigo)ContextoAplicacion.Contexto.amigoSet.get(0);
			amigo.aceptado = true;
			amigo.setStatus(Entity.STATUS_UPDATED);
			//ContextoAplicacion.Contexto.amigoSet.save(amigo);	
			amigo.salvar(null);
		
			Amigo amigo2 = new Amigo();
			amigo2.idusuario = this.ID;
			amigo2.idamigo = amigo.idusuario;
			amigo2.aceptado = true;
			amigo2.setStatus(Entity.STATUS_NEW);
			//ContextoAplicacion.Contexto.amigoSet.save(amigo2);	
			amigo2.salvar(null);
		}
	}
	
	public void rechazarAmistad(Usuario usuarioAmigo) throws AdaFrameworkException {
		ContextoAplicacion.Contexto.amigoSet.fill("idusuario = ? AND idamigo = ?",
				new String[]{String.valueOf(this.ID), String.valueOf(usuarioAmigo.getID())}, null);
		if (ContextoAplicacion.Contexto.amigoSet.size() > 0) {
			Amigo amigo = (Amigo)ContextoAplicacion.Contexto.amigoSet.get(0);
			amigo.setStatus(Entity.STATUS_DELETED);
			//ContextoAplicacion.Contexto.amigoSet.save(amigo);
			amigo.salvar(null);
		}

		ContextoAplicacion.Contexto.amigoSet.fill("idusuario = ? AND idamigo = ?",
				new String[]{String.valueOf(usuarioAmigo.getID()), String.valueOf(this.ID)}, null);
		if (ContextoAplicacion.Contexto.amigoSet.size() > 0) {
			Amigo amigo = (Amigo)ContextoAplicacion.Contexto.amigoSet.get(0);
			amigo.setStatus(Entity.STATUS_DELETED);
			//ContextoAplicacion.Contexto.amigoSet.save(amigo);
			amigo.salvar(null);
		}
		
	}
	
	public void addAmigo(Long idAmigo) throws AdaFrameworkException {
		ContextoAplicacion.Contexto.amigoSet.fill("idusuario = ? AND idamigo = ?",
				new String[]{String.valueOf(this.ID), String.valueOf(idAmigo)}, null);
		if (ContextoAplicacion.Contexto.amigoSet.size() == 0) {
			Amigo amigo = new Amigo();
			amigo.idusuario = this.ID;
			amigo.idamigo = idAmigo;
			amigo.aceptado = true;
			amigo.subido = false;
			amigo.setStatus(Entity.STATUS_NEW);
			//ContextoAplicacion.Contexto.amigoSet.save(amigo);
			amigo.salvar(null);
		}

		ContextoAplicacion.Contexto.amigoSet.fill("idusuario = ? AND idamigo = ?",
				new String[]{String.valueOf(idAmigo), String.valueOf(this.ID)}, null);
		if (ContextoAplicacion.Contexto.amigoSet.size() == 0) {
			Amigo amigo2 = new Amigo();
			amigo2.idusuario = idAmigo;
			amigo2.idamigo = this.ID;
			amigo2.aceptado = true;
			amigo2.subido = false;
			amigo2.setStatus(Entity.STATUS_NEW);
			ContextoAplicacion.Contexto.amigoSet.save(amigo2);
			//TODO if (AccesoInternet.isOnline(getApplicationContext())) {
	        	new Sincronizador().execute(amigo2);
	        //}
		}
	}
    
    
	public static Usuario leerDePrefs(Context contexto) throws AdaFrameworkException {
		SharedPreferences prefs = contexto.getSharedPreferences("iSolutioPuntoPadelPrefs",Context.MODE_PRIVATE);

		Long id = prefs.getLong("id_usuario", -1);
		
		Usuario usuario = (Usuario)ContextoAplicacion.Contexto.usuarioSet.getElementByID(id);
		if (usuario == null) {
			usuario = new Usuario();
		}
		
		return usuario;
	}
	
	public void guardarEnPrefs(Context contexto) {
		SharedPreferences prefs = contexto.getSharedPreferences("iSolutioPuntoPadelPrefs",Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong("id_usuario", this.getID());
		editor.commit();
	}
	
    public static void guardarEnPrefs(Context contexto, Long idUsuario) {
		SharedPreferences prefs = contexto.getSharedPreferences("iSolutioPuntoPadelPrefs",Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong("id_usuario", idUsuario);
		editor.commit();
	}
	
	public static void borrarPrefs(Context contexto) {		
		SharedPreferences prefs = contexto.getSharedPreferences("iSolutioPuntoPadelPrefs",Context.MODE_PRIVATE);
	
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong("id_usuario", -1);
		editor.commit();
	}
	

	public class ValidadorUsuarioUnico extends Validator {
		 
	    @Override
	    public Boolean Validate(Entity pEntity, Field pField, Object pAnnotation, Object pValue) {
	    	String value = (String) pValue;
	        // Indicar aqui el que el login sea único 
	    	return !value.equals("Daniel");
	    }
	}
	
	public void sincronizaWeb(Context contexto) throws AdaFrameworkException {
		if (contexto == null || AccesoInternet.isOnline(contexto)) {
			// Amigos
			//ContextoAplicacion.Contexto.amigoSet.fill("idamigo = ? or idusuario = ?", new String[] {this.ID.toString(), this.ID.toString()}, null);
			ContextoAplicacion.Contexto.amigoSet.fill("idusuario = ?", new String[] {this.ID.toString()}, null);
	    	for (Amigo a : (List<Amigo>)ContextoAplicacion.Contexto.amigoSet) {
	    		if (!a.subido) {
	    			Usuario elAmigo = a.getAmigo();
	    			if (!elAmigo.subido) {
	    				new Sincronizador().execute(elAmigo);
	    			}
	    			new Sincronizador().execute(a);
	    		}
	    	}

	    	// Torneos
	    	for (Torneo t : this.getTorneos()) {
	    		if (!t.subido) {
	    			new Sincronizador().execute(t);
	    			// Equipos
	    			for (Equipo e : t.getEquipos()) {
	    				if (!e.subido) {
	    	    			new Sincronizador().execute(e);
	    	    		}
	    			}
	    		}
	    	}
	    		    	
	    	// Partidos
	    	for (Partido p : this.getPartidos()) {
	    		if (!p.subido) {
	    			new Sincronizador().execute(p);
	    		}
	    	}
		}
	}
    
    public void salvar(Context contexto) throws AdaFrameworkException {
    	ContextoAplicacion.Contexto.usuarioSet.save(this);
    	//if (this.getStatus() != Entity.STATUS_DELETED) {    	
	    	if (contexto == null || AccesoInternet.isOnline(contexto)) {
	        	new Sincronizador().execute(this);
	        }
    	//}
    }
	
    public JSONObject convierteAJson() throws JSONException {
    	JSONObject json = new JSONObject();

    	json.put("idweb", idweb);
    	json.put("login", login);
    	json.put("password", password);
    	json.put("nombre", nombre);
    	json.put("email", email);
    	json.put("ciudad", ciudad);
    	json.put("pais", pais);
    	json.put("subido", subido);
    	json.put("aceptado", aceptado);    	
    	
		return json;
	}
    
    public void actualizaRegistro() throws AdaFrameworkException {
    	if (this.id == null) 
    		this.setStatus(Entity.STATUS_NEW);
    	else 
    		this.setStatus(Entity.STATUS_UPDATED);
    	
    	ContextoAplicacion.Contexto.usuarioSet.save(this);
    	
    	
    }
	
}
