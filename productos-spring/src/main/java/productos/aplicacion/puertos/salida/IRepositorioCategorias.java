package productos.aplicacion.puertos.salida;

import java.util.List;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
=======
import productos.dominio.modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
>>>>>>> aff986665e43008b81af73c9ca6bd4ad369254a7

import productos.dominio.modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IRepositorioCategorias extends JpaRepository<Categoria, String> {
	List<Categoria> recuperarCategoriasRaiz() throws RepositorioException;

	void add(Categoria categoria) throws RepositorioException;

	void actualizarDescripcion(String id, String descripcion) throws RepositorioException, EntidadNoEncontrada;

	List<Categoria> recuperarDescendientes(String id) throws RepositorioException, EntidadNoEncontrada;

	List<String> getIdsCategoriaYDescendientes(String idCategoria) 
			throws RepositorioException, EntidadNoEncontrada;
}
