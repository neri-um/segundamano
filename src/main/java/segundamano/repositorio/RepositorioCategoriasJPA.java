package segundamano.repositorio;

import repositorio.RepositorioJPA;
import segundamano.modelo.Categoria;

public class RepositorioCategoriasJPA extends RepositorioJPA<Categoria> {

	@Override

	public Class<Categoria> getClase() {

		return Categoria.class;

	}

}
