package segundamano.repositorio;

import repositorio.RepositorioJPA;
import segundamano.modelo.Producto;

public class RepositorioProductosJPA extends RepositorioJPA<Producto> {

	@Override

	public Class<Producto> getClase() {

		return Producto.class;

	}

}
