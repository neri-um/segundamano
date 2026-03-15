package usuarios.servicio;

import java.time.LocalDate;
import java.util.List;

import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import usuarios.modelo.Usuario;
import usuarios.repositorio.RepositorioUsuariosAdHocJPA;

public class ServicioUsuarios implements IServicioUsuarios{

    private RepositorioUsuariosAdHocJPA repositorio =
            FactoriaRepositorios.getRepositorio(Usuario.class);

	@Override
	public String altaUsuario(String nombre, String apellidos, String email, String clave, LocalDate fechaNac,
			String telefono) throws RepositorioException {
	    if (nombre == null || nombre.trim().isEmpty()) {
	        throw new IllegalArgumentException("ERROR: El nombre es obligatorio");
	    }
	    if (apellidos == null || apellidos.trim().isEmpty()) {
	        throw new IllegalArgumentException("ERROR: Los apellidos son obligatorios");
	    }
	    if (email == null || email.trim().isEmpty()) {
	        throw new IllegalArgumentException("ERROR: El email es obligatorio");
	    }
	    if (clave == null || clave.trim().isEmpty()) {
	        throw new IllegalArgumentException("ERROR: La clave es obligatoria");
	    }
	    if (telefono == null || telefono.trim().isEmpty()) {
	        throw new IllegalArgumentException("ERROR: El telefono es obligatorio");
	    }
	    if (fechaNac == null) {
	        throw new IllegalArgumentException("ERROR: La fecha de nacimiento es obligatoria");
	    }
	    if (fechaNac.isAfter(LocalDate.now())) {
	        throw new IllegalArgumentException("ERROR: La fecha de nacimiento no puede ser futura");
	    }
	    Usuario u = new Usuario(email, nombre, apellidos, clave, fechaNac, telefono);
		return repositorio.add(u);
	}

	@Override
	public void modificarUsuario(String id, String nombre, String apellidos, String clave, LocalDate fechaNac, String telefono) throws RepositorioException, EntidadNoEncontrada {
	    
	    if (id == null || id.trim().isEmpty()) {
	        throw new IllegalArgumentException("ERROR: El ID de usuario es obligatorio");
	    }

	    Usuario usuario = repositorio.getById(id);

	    if (nombre != null && !nombre.trim().isEmpty()) {
	        usuario.setNombre(nombre);
	    }
	    if (apellidos != null && !apellidos.trim().isEmpty()) {
	        usuario.setApellidos(apellidos);
	    }
	    if (clave != null && !clave.trim().isEmpty()) {
	        usuario.setClave(clave);
	    }
	    if (fechaNac != null) {
	        if (fechaNac.isAfter(LocalDate.now())) {
	            throw new IllegalArgumentException("ERROR: La fecha de nacimiento no puede ser futura");
	        }
	        usuario.setFechaNacimiento(fechaNac);
	    }
	    
	    if (telefono != null && !telefono.trim().isEmpty()) {
	        if (!telefono.matches("\\d{9,}")) { 
	             throw new IllegalArgumentException("ERROR: El teléfono debe tener al menos 9 dígitos");
	        }
	        usuario.setTelefono(telefono);
	    }
	    repositorio.update(usuario);
	}

	@Override
	public Usuario recuperarUsuario(String id) throws RepositorioException, EntidadNoEncontrada {
		 if (id == null || id.trim().isEmpty())
		        throw new IllegalArgumentException("ERROR: El ID de usuario es obligatorio");

		    return repositorio.getById(id); 
	}

	@Override
	public List<Usuario> listarUsuarios() throws RepositorioException {
		 return repositorio.getAll();
	}

	
	@Override
	public Usuario login(String email, String clave) throws RepositorioException, EntidadNoEncontrada {
	    Usuario usuario = repositorio.getByEmail(email);
	    if (!usuario.getClave().equals(clave)) {
	        return null; // credenciales inválidas
	    }
	    return usuario;
	}

	
}
