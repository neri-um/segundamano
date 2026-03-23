package productos.aplicacion.output;

import java.util.List;

import productos.dominio.modelo.Categoria;
import productos.infraestructura.persistencia.base.EntidadNoEncontrada;
import productos.infraestructura.persistencia.base.RepositorioException;

public interface IRepositorioCategorias {
	List<Categoria> recuperarCategoriasRaiz() throws RepositorioException;

	void add(Categoria categoria) throws RepositorioException;

	void actualizarDescripcion(String id, String descripcion) throws RepositorioException, EntidadNoEncontrada;

	List<Categoria> recuperarDescendientes(String id) throws RepositorioException, EntidadNoEncontrada;

	List<String> getIdsCategoriaYDescendientes(String idCategoria) 
			throws RepositorioException, EntidadNoEncontrada;
}
