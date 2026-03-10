package productos.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import productos.modelo.Producto;

@NoRepositoryBean
public interface RepositorioProductos extends CrudRepository<Producto, String> {

}
