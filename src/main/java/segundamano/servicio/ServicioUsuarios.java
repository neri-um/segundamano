package segundamano.servicio;

import java.io.IOException;
import java.time.LocalDate;

import segundamano.repositorio.RepositorioProductosAdHocJPA;

public class ServicioUsuarios implements IServicioUsuarios{

	private RepositorioProductosAdHocJPA repositorio;

    public ServicioUsuarios() throws IOException {
        this.repositorio = new RepositorioProductosAdHocJPA();
    }

	@Override
	public String altaUsuario(String nombre, String apellidos, String email, String clave, LocalDate fechaNac,
			int telefono) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modificarUsuario(String id, String nombre, String apellidos, String clave, String fechaNac,
			int telefono) {
		// TODO Auto-generated method stub
		
	}
}
