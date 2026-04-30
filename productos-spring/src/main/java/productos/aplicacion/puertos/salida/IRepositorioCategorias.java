package productos.aplicacion.puertos.salida;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;


import productos.dominio.modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import org.springframework.data.jpa.repository.Query;

@NoRepositoryBean
public interface IRepositorioCategorias extends CrudRepository<Categoria, String> {

    @Query("SELECT c FROM Categoria c WHERE c.padre IS NULL")
    List<Categoria> recuperarCategoriasRaiz() throws RepositorioException;

    @Query("SELECT c FROM Categoria c WHERE c.padre.id = :id")
    List<Categoria> recuperarDescendientes(@Param("id") String id) 
        throws RepositorioException, EntidadNoEncontrada;

    @Query("SELECT c.id FROM Categoria c WHERE c.id = :idCategoria OR c.padre.id = :idCategoria")
    List<String> getIdsCategoriaYDescendientes(@Param("idCategoria") String idCategoria) 
        throws RepositorioException, EntidadNoEncontrada;
}