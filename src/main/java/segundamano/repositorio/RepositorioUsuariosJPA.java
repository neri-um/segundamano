package segundamano.repositorio;

import repositorio.RepositorioJPA;
import segundamano.modelo.Usuario;

public class RepositorioUsuariosJPA extends RepositorioJPA<Usuario> {

	@Override

	public Class<Usuario> getClase() {

		return Usuario.class;

	}

}
